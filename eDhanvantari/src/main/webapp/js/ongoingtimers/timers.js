const timersData = [];

/**
 * Timer class definition
 */
class Timer {
	constructor(id, displayId, stopButtonId, restartButtonId, endButtonId, fullName) {
		this.id = id;
		this.timer = null;
		this.isRunning = false;
		this.elapsedTime = 0;
		this.startTime = null;
		this.stopTime = null;
		this.startTimeMs = 0;
		this.displayElement = document.getElementById(displayId);
		this.stopButtonElement = document.getElementById(stopButtonId);
		this.restartButtonElement = document.getElementById(restartButtonId);
		this.endButtonElement = document.getElementById(endButtonId);
		this.name = fullName;

		// Restore state if available
		this.restoreState();
	}

	start() {
		if (!this.isRunning) {
			this.startTime = new Date();
			this.isRunning = true;
			this.startTimeMs = Date.now() - this.elapsedTime;
			this.timer = setInterval(() => this.updateTimerDisplay(), 1000);
			this.stopButtonElement.disabled = false;
			this.saveState();
		}
	}

	stop() {
		if (this.isRunning) {
			this.stopTime = new Date();
			clearInterval(this.timer);
			timersData.push(this.getTimerData()); // Accumulate timer data
			this.isRunning = false;
			this.updateTimerDisplay();
			this.saveState();
		}
	}

	reset() {
		this.stop(); // Stop the timer and update the state
		localStorage.removeItem(`timer${this.id}`); // Remove the specific timer data from local storage
		this.elapsedTime = 0; // Reset elapsed time
		this.startTimeMs = 0; // Reset start time in milliseconds
		this.startTime = null; // Clear the start time
		this.stopTime = null; // Clear the stop time
		this.isRunning = false; // Set running state to false
		if (this.displayElement) {
			this.displayElement.innerText = "00:00:00"; // Reset the display
		}
		this.saveState(); // Save the cleared state to local storage
		this.start();
		/*this.fullName = this.fullName;*/
	}

	calculateDuration(startTime, stopTime) {
		// Convert the ISO strings to Date objects
		const startDate = new Date(startTime);
		const stopDate = new Date(stopTime);

		// Calculate the difference in milliseconds
		const diffInMilliseconds = stopDate - startDate;

		// Convert milliseconds to minutes
		const diffInMinutes = Math.floor(diffInMilliseconds / (1000 * 60));

		return diffInMinutes;
	}

	formatDate(isoString) {
		const date = new Date(isoString);

		const pad = (num) => String(num).padStart(2, '0');

		const day = pad(date.getDate());
		const month = pad(date.getMonth() + 1); // Months are zero-based in JavaScript
		const year = date.getFullYear();

		let hours = date.getHours();
		const minutes = pad(date.getMinutes());
		const ampm = hours >= 12 ? 'PM' : 'AM';
		hours = hours % 12;
		hours = hours ? hours : 12; // The hour '0' should be '12'

		return `${day}/${month}/${year} ${pad(hours)}:${minutes} ${ampm}`;
	}

	end() {
		this.stop(); // Ensure the timer is stopped
		/*		let visitID = this.id.split("_")[0];
				document.getElementById(`sd&t_${visitID}`).innerText = this.formatDate(this.startTime.toISOString);
				document.getElementById(`ed&t_${visitID}`).innerText = this.formatDate(this.stopTime.toISOString);
				document.getElementById(`dura_${visitID}`).innerText = this.calculateDuration(this.startTime.toISOString, this.stopTime.toISOString)
				localStorage.removeItem(`timer${this.id}`); */// Remove the specific timer data from local storage
		localStorage.removeItem(`timer${this.id}`);
		// Optionally, remove from timersData array if needed
		delete timers[this.id]; // Remove the timer instance
	}

	updateTimerDisplay() {
		this.elapsedTime = Date.now() - this.startTimeMs;
		let totalSeconds = Math.floor(this.elapsedTime / 1000);
		let hours = Math.floor(totalSeconds / 3600);
		let minutes = Math.floor((totalSeconds % 3600) / 60);
		let seconds = totalSeconds % 60;

		const formattedTime =
			String(hours).padStart(2, '0') + ':' +
			String(minutes).padStart(2, '0') + ':' +
			String(seconds).padStart(2, '0');

		if (this.displayElement) {
			this.displayElement.innerText = formattedTime;
		}
	}

	restartDisplayTime() {
		this.elapsedTime = 0;
		const formattedTime =
			String(hours).padStart(2, '0') + ':' +
			String(minutes).padStart(2, '0') + ':' +
			String(seconds).padStart(2, '0');

		if (this.displayElement) {
			this.displayElement.innerText = formattedTime;
		}
	}

	getTimerData() {
		return {
			timerNumber: this.id,
			startTime: this.startTime,
			stopTime: this.stopTime
		};
	}

	saveState() {
		const state = {
			isRunning: this.isRunning,
			fullName: this.name,
			startDateNTime: this.startTime ? this.startTime.toISOString() : null,
			stopDateNTime: this.stopTime ? this.stopTime.toISOString() : null,
			elapsedTime: this.elapsedTime,
			startTimeMs: this.startTimeMs
		};
		localStorage.setItem(`timer${this.id}`, JSON.stringify(state));
	}

	restoreState() {
		const state = JSON.parse(localStorage.getItem(`timer${this.id}`));
		if (state) {
			this.isRunning = state.isRunning;
			this.fullName = state.name;
			this.startTime = state.startDateNTime ? new Date(state.startDateNTime) : null;
			this.stopTime = state.stopDateNTime ? new Date(state.stopDateNTime) : null;
			this.elapsedTime = state.elapsedTime;
			this.startTimeMs = state.startTimeMs;
			if (this.isRunning) {
				this.timer = setInterval(() => this.updateTimerDisplay(), 1000);
				if (this.stopButtonElement) {
				    this.stopButtonElement.disabled = false; // Example logic
				} else {
				    console.error(`Element with ID ${this.stopButtonElement} not found.`);
				}
			}
			this.updateTimerDisplay();
		}
	}
}

const timers = {};

/**
 * Global function to set a timer ID
 */
function setTimerID(id, patientFirstName, patientLastName) {
	let timestamp = new Date().toISOString(); // You can format the timestamp as needed
	let concatenatedID = id + '_' + timestamp;

	console.log("timer ID:" + concatenatedID);
	let fullName = patientFirstName + ' ' + patientLastName; // Add a space between first and last name
	console.log("patientFullName:" + fullName);
	addTimer(concatenatedID, fullName);
}

/**
 * Global function to add a timer
 */
function addTimer(id, fullName) {
	const [visitId, _] = id.split('_');

	timers[id] = new Timer(id, `timerDisplay_${visitId}`, `stopButton_${visitId}`, `restartButton_${visitId}`, `endButton_${visitId}`, fullName);
	timers[id].start();
	document.getElementById(`startButton_${visitId}`).style.display = 'none';
	document.getElementById(`stopButton_${visitId}`).disabled = false;
	document.getElementById(`restartButton_${visitId}`).style.display = 'none';
	document.getElementById(`endButton_${visitId}`).style.display = 'none';

	document.getElementById(`stopButton_${visitId}`).addEventListener('click', function() {
		timers[id].stop();
		document.getElementById(`stopButton_${visitId}`).style.display = `none`;
		document.getElementById(`restartButton_${visitId}`).style.display = 'inline-block';
		document.getElementById(`endButton_${visitId}`).style.display = 'inline-block'; // Show the end button
	});

	document.getElementById(`restartButton_${visitId}`).addEventListener('click', function() {
		timers[id].reset();
		document.getElementById(`restartButton_${visitId}`).style.display = 'none';
		document.getElementById(`stopButton_${visitId}`).style.display = `inline-block`;
		document.getElementById(`endButton_${visitId}`).style.display = 'none'; // Hide the end button again
	});

	document.getElementById(`endButton_${visitId}`).addEventListener('click', function() {
		timers[id].end();

		document.getElementById(`restartButton_${visitId}`).style.display = 'none';
		document.getElementById(`stopButton_${visitId}`).style.display = `none`;
		// Optionally hide or remove the timer UI if needed
		// Example: document.getElementById(timerContainer_${visitId}).style.display = 'none';
	});
}

// Initialize timers from localStorage on page load
window.onload = function() {
	const savedTimers = Object.keys(localStorage).filter(key => key.startsWith('timer'));
	savedTimers.forEach(timerKey => {
		const timerId = timerKey.replace('timer', '');
		addTimer(timerId);
	});
};


// Function to format date and time
function formatDateTime(dateTimeString, options) {
	const date = new Date(dateTimeString);
	return date.toLocaleString('en-US', options);
}

// Function to populate the table with data from localStorage
function populateTableFromLocalStorage() {
	// Get the table body element
	var tbody = document.querySelector("#timersTable tbody");

	// Clear any existing rows in the table body
	tbody.innerHTML = "";
	for (let i = 0; i < localStorage.length; i++) {

		let key = localStorage.key(i);

		if (key.startsWith('timer')) {
			let data = JSON.parse(localStorage.getItem(key));

			// Extract visit ID and visit Date from the key
			var [visitId, visitDateTime] = key.split('_').slice(0);
			var id = visitId.replace('timer', '');
			var visitDate = formatDateTime(visitDateTime, { day: '2-digit', month: '2-digit', year: 'numeric' });

			// Create a new table row
			var tr = document.createElement("tr");

			// Create and append the table cells
			var patientNameCell = document.createElement("td");
			patientNameCell.textContent = data.fullName;
			tr.appendChild(patientNameCell);

			var visitIDCell = document.createElement("td");
			visitIDCell.textContent = id;
			visitIDCell.className = id;
			tr.appendChild(visitIDCell);

			var visitDateCell = document.createElement("td");
			visitDateCell.textContent = visitDate;
			tr.appendChild(visitDateCell);

			var startTimeCell = document.createElement("td");
			var startTime = formatDateTime(data.startDateNTime, { hour: '2-digit', minute: '2-digit', hour12: true });
			startTimeCell.textContent = startTime;
			tr.appendChild(startTimeCell);

			var runningTimerCell = document.createElement("td");
			var timerSpan = document.createElement("span");
			timerSpan.id = `timerDisplay_${visitId}`;
			timerSpan.setAttribute("data-start-time-ms", data.startTimeMs);
			timerSpan.setAttribute("data-elapsed-time", data.elapsedTime);
			timerSpan.setAttribute("data-is-running", data.isRunning);
			timerSpan.className = "running-timer";
			tr.appendChild(timerSpan);
			runningTimerCell.appendChild(timerSpan);
			tr.appendChild(runningTimerCell);
			
			// Create the action button cell with an anchor tag
			var actionButtonCell = document.createElement("td");
			var viewLink = document.createElement("a");
			viewLink.href = `RenderEditExistingVisit.action?visitID=${id}`; // Replace with your URL and parameters
			viewLink.textContent = "View";
			viewLink.title = "View details"; // Optional: add a title for the link
			actionButtonCell.appendChild(viewLink);
			tr.appendChild(actionButtonCell);
			
			// Append the row to the table body
			tbody.appendChild(tr);
		}
	}
}

// Function to update the running timers
function updateRunningTimers() {
    var timerElements = document.querySelectorAll(".running-timer");
    timerElements.forEach(updateTimer);
}

// Function to update a single timer element
function updateTimer(timerElement) {
    var startTimeMs = parseInt(timerElement.getAttribute("data-start-time-ms"));
    var elapsedTime = parseInt(timerElement.getAttribute("data-elapsed-time"));
    var isRunning = timerElement.getAttribute("data-is-running") === "true";

    if (isRunning) {
        var currentTime = Date.now();
        var totalElapsedTime = elapsedTime + (currentTime - startTimeMs);

        var hours = Math.floor(totalElapsedTime / 3600000);
        var minutes = Math.floor((totalElapsedTime % 3600000) / 60000);
        var seconds = Math.floor((totalElapsedTime % 60000) / 1000);

        timerElement.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    } else {
        var totalSeconds = Math.floor(elapsedTime / 1000);
        var hours = Math.floor(totalSeconds / 3600);
        var minutes = Math.floor((totalSeconds % 3600) / 60);
        var seconds = totalSeconds % 60;

        timerElement.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    }
}

// Initialize the table and start updating timers
document.addEventListener("DOMContentLoaded", function() {
    populateTableFromLocalStorage();
    setInterval(updateRunningTimers, 1000);
});

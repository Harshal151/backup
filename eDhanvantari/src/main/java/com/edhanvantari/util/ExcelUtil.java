package com.edhanvantari.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.edhanvantari.form.PatientForm;

public class ExcelUtil extends DAOConnection {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	PreparedStatement preparedStatement1 = null;
	ResultSet resultSet1 = null;

	PreparedStatement preparedStatement2 = null;
	ResultSet resultSet2 = null;

	ResultSetMetaData metaData = null;

	String status = "error";

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @param excelFileName
	 * @return
	 */
	public String generateClinicalReport(PatientForm patientForm, String realPath, String excelFileName) {

		int currentRow = 1;

		int srNo = 1;

		SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		// int count = 0;

		int temp = 1;

		try {

			connection = getConnection();

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			/*
			 * Generate the query to fetch Patient Details
			 */
			String fetchDetailQuery1 = patientForm.getSavedQuery();

			preparedStatement = connection.prepareStatement(fetchDetailQuery1);

			resultSet = preparedStatement.executeQuery();

			metaData = resultSet.getMetaData();

			// getting total no of columns
			int noOfColumns = metaData.getColumnCount();

			XSSFSheet spreadSheet = wb.createSheet("Clinical Data Report");
			Row row;

			row = spreadSheet.createRow(0);

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 9);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);
			headerCellStyle.setWrapText(true);
			// headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 9);
			dataCellStyle.setFont(setDataFont);
			dataCellStyle.setWrapText(true);
			// dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			/*
			 * Generating XLSX Header value for Patient Information by iterating noOfColumns
			 * types
			 */
			spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));

			for (int i = 1; i <= noOfColumns; i++) {
				spreadSheet.setColumnWidth((short) i, (short) (256 * 25));
			}

			cell = row.createCell((short) 0);
			cell.setCellValue("Sr.No.");
			cell.setCellStyle(headerCellStyle);

			/*
			 * Giving values to header
			 */
			do {

				cell = row.createCell((short) temp);
				cell.setCellValue(metaData.getColumnName(temp));
				cell.setCellStyle(headerCellStyle);

				temp++;

				// count++;

			} while (temp <= noOfColumns);

			/*
			 * For Receipt items values
			 */
			while (resultSet.next()) {

				row = spreadSheet.createRow(currentRow++);

				temp = 1;

				cell = row.createCell((short) 0);
				cell.setCellValue(srNo);
				cell.setCellStyle(dataCellStyle);

				do {

					// Getting column type and if it is DATE, then formatting date into dd-MM-yyyy
					// format
					if (metaData.getColumnTypeName(temp).equals("DATE")) {
						cell = row.createCell((short) temp);

						if (resultSet.getString(temp) == null || resultSet.getString(temp) == "") {
							cell.setCellValue(resultSet.getString(temp));
						} else if (resultSet.getString(temp).isEmpty()) {
							cell.setCellValue(resultSet.getString(temp));
						} else {
							cell.setCellValue(dateFormat.format(dateToBeFormatted.parse(resultSet.getString(temp))));
						}

						cell.setCellStyle(dataCellStyle);
					} else {
						cell = row.createCell((short) temp);
						cell.setCellValue(resultSet.getString(temp));
						cell.setCellStyle(dataCellStyle);
					}

					temp++;

				} while (temp <= noOfColumns);

				srNo++;

			}

			/*
			 * writing to the xlsx file
			 */
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();

			/*
			 * Write the output to a file
			 */
			// FileOutputStream fileOut = new FileOutputStream(new File(realPath +
			// excelFileName));
			FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
			fileOut.write(outArray);
			fileOut.flush();
			fileOut.close();

			System.out.println("Excel sheet for clinical data report is created successfully.");

			status = "success";

		} catch (Exception exception) {

			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;

	}

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @param excelFileName
	 * @return
	 */
	public String generateBillingReport(PatientForm patientForm, String realPath, String excelFileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		String searchName = patientForm.getSearchPatientName();

		int currentRow = 1;

		int srNo = 1;

		int check = 0;

		try {

			connection = getConnection();

			if (patientForm.getSearchCriteria().equals("Customize")) {

				if (!patientForm.getStartDate().isEmpty() && !patientForm.getEndDate().isEmpty()) {

					String query = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, "
							+ "rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo,(SELECT otherMode FROM PaymentDetails WHERE receiptID = rc.id) AS otherMode, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = "
							+ "(SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p "
							+ "WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, "
							+ "(SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) "
							+ "FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) "
							+ "AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) "
							+ "AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE"
							+ " pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName "
							+ "FROM Receipt AS rc WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)) AND rc.receiptDate BETWEEN ? AND ?  ORDER BY rc.receiptDate DESC";

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setInt(1, patientForm.getClinicID());
					preparedStatement.setString(2,
							dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
					preparedStatement.setString(3,
							dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

					resultSet = preparedStatement.executeQuery();

				} else {

					String query = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, "
							+ "rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo,(SELECT otherMode FROM PaymentDetails WHERE receiptID = rc.id) AS otherMode, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = "
							+ "(SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p "
							+ "WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, "
							+ "(SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) "
							+ "FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) "
							+ "AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) "
							+ "AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE"
							+ " pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName "
							+ "FROM Receipt AS rc WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)) AND rc.receiptDate LIKE ?  ORDER BY rc.receiptDate DESC";

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setInt(1, patientForm.getClinicID());
					preparedStatement.setString(2,
							"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

					resultSet = preparedStatement.executeQuery();

				}

			} else {

				String query = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, "
						+ "rc.paymentType, rc.billingType, rc.activityStatus, rc.referenceReceiptNo,(SELECT otherMode FROM PaymentDetails WHERE receiptID = rc.id) AS otherMode, rc.visitID, rc.totalDiscount, (SELECT c.name FROM Clinic AS c WHERE c.id = "
						+ "(SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p "
						+ "WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) AS patientID, "
						+ "(SELECT name FROM PVVisitType WHERE id = (SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) "
						+ "FROM AppUser WHERE id = (SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) "
						+ "AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) "
						+ "AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, (SELECT pr.name FROM Practice AS pr WHERE"
						+ " pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID))) AS practiceName "
						+ "FROM Receipt AS rc WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?)) AND "
						+ patientForm.getSearchCriteria() +"  ORDER BY rc.receiptDate DESC";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setInt(1, patientForm.getClinicID());

				resultSet = preparedStatement.executeQuery();

			}

			/*
			 * if (patientForm.getStartDate().isEmpty() &&
			 * patientForm.getEndDate().isEmpty()) {
			 * 
			 * if (patientForm.getSearchCriteria().equals("PatientName")) {
			 * 
			 * String query = QueryMaker.RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_PATIENT_NAME;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setString(1, "%" + searchName + "%");
			 * preparedStatement.setInt(2, patientForm.getClinicID());
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else {
			 * 
			 * String query = QueryMaker.RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_RECEIPT_NO;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setString(1, "%" + searchName + "%");
			 * preparedStatement.setInt(2, patientForm.getClinicID());
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }
			 * 
			 * } else if (patientForm.getEndDate().isEmpty()) {
			 * 
			 * if (patientForm.getSearchCriteria().equals("PatientName")) {
			 * 
			 * String query =
			 * QueryMaker.RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_PATIENT_NAME_BY_START_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setString(1, "%" + searchName + "%");
			 * preparedStatement.setInt(2, patientForm.getClinicID());
			 * preparedStatement.setString(3, "%" +
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else {
			 * 
			 * String query =
			 * QueryMaker.RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_RECEIPT_NO_START_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setString(1, "%" + searchName + "%");
			 * preparedStatement.setInt(2, patientForm.getClinicID());
			 * preparedStatement.setString(3, "%" +
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }
			 * 
			 * } else {
			 * 
			 * if (patientForm.getSearchCriteria().equals("PatientName")) {
			 * 
			 * String query = QueryMaker.
			 * RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_PATIENT_NAME_BY_START_END_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setString(1, "%" + searchName + "%");
			 * preparedStatement.setInt(2, patientForm.getClinicID());
			 * preparedStatement.setString(3,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) +
			 * " 00:00"); preparedStatement.setString(4,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else {
			 * 
			 * String query =
			 * QueryMaker.RETRIEVE_PATIENT_RECEIPT_DETAILS_BY_RECEIPT_NO_START_END_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setString(1, "%" + searchName + "%");
			 * preparedStatement.setInt(2, patientForm.getClinicID());
			 * preparedStatement.setString(3,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) +
			 * " 00:00"); preparedStatement.setString(4,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }
			 * 
			 * }
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Billing Data Report");
			Row row;

			row = spreadSheet.createRow(0);
			
			spreadSheet.createFreezePane(0, 1, 0, 1);

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 9);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);
			headerCellStyle.setWrapText(true);
			// headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 9);
			dataCellStyle.setFont(setDataFont);
			dataCellStyle.setWrapText(true);
			// dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			/*
			 * Generating XLSX Header value for Patient billing Information
			 */
			spreadSheet.setColumnWidth((short) 0, (short) (256 * 4));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 2, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 3, (short) (256 * 13));
			spreadSheet.setColumnWidth((short) 4, (short) (256 * 10));
			spreadSheet.setColumnWidth((short) 5, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 6, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 7, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 8, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 9, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 10, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 11, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 12, (short) (256 * 7));
			spreadSheet.setColumnWidth((short) 13, (short) (256 * 7));
			spreadSheet.setColumnWidth((short) 14, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 15, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 16, (short) (256 * 9));

			/*
			 * Setting header value into Cell
			 */
			cell = row.createCell((short) 0);
			cell.setCellValue("Sr.No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Practice Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Clinic Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Doctor Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Visit Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Patient Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Registration No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Receipt No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Receipt Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Consultation Charges");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 10);
			cell.setCellValue("Total Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 11);
			cell.setCellValue("Net Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 12);
			cell.setCellValue("Advance Payment");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 13);
			cell.setCellValue("Balance Payment");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 14);
			cell.setCellValue("Billing Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 15);
			cell.setCellValue("Payment Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 16);
			cell.setCellValue("Total Discount");
			cell.setCellStyle(headerCellStyle);

			while (resultSet.next()) {

				check = 1;

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(srNo);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("practiceName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("clinicName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("clinicianName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(resultSet.getString("visitType"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("patientName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getString("regNumber"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getString("receiptNo"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getDouble("consultationCharges"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 10);
				cell.setCellValue(resultSet.getDouble("totalAmount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 11);
				cell.setCellValue(resultSet.getDouble("netAmount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 12);
				cell.setCellValue(resultSet.getDouble("advPayment"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 13);
				cell.setCellValue(resultSet.getDouble("balPayment"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 14);
				cell.setCellValue(resultSet.getString("billingType"));
				cell.setCellStyle(dataCellStyle);

				String paymentType = "";

				if (resultSet.getString("paymentType") == null || resultSet.getString("paymentType") == "") {
					paymentType = resultSet.getString("paymentType");
				} else if (resultSet.getString("paymentType").isEmpty()) {
					paymentType = resultSet.getString("paymentType");
				} else if (resultSet.getString("paymentType").contains("Other")) {
					paymentType = resultSet.getString("paymentType") + " (" + resultSet.getString("otherMode") + ")";
				} else {
					paymentType = resultSet.getString("paymentType");
				}

				cell = row.createCell((short) 15);
				cell.setCellValue(paymentType);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 16);
				cell.setCellValue(resultSet.getDouble("totalDiscount"));
				cell.setCellStyle(dataCellStyle);

				srNo++;

			}

			if (check == 0) {

				status = "input";

				System.out.println("no record found.");

			} else {

				/*
				 * writing to the xlsx file
				 */
				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				wb.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();

				/*
				 * Write the output to a file
				 */
				FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
				fileOut.write(outArray);
				fileOut.flush();
				fileOut.close();

				System.out.println("Excel sheet for billing report is created successfully.");

				status = "success";

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @param excelFileName
	 * @return
	 */
	public String generateCreditReport(PatientForm patientForm, String realPath, String excelFileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		String searchName = patientForm.getSearchPatientName();

		int currentRow = 1;

		int srNo = 1;

		int check = 0;

		try {

			connection = getConnection();

			if (patientForm.getSearchCriteria().equals("Customize")) {

				if (!patientForm.getStartDate().isEmpty() && !patientForm.getEndDate().isEmpty()) {

					String query = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, "
							+ "rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount,(SELECT otherMode FROM PaymentDetails WHERE receiptID = rc.id) AS otherMode, (SELECT c.name FROM Clinic AS c "
							+ "WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName,  (SELECT name FROM PVVisitType WHERE id = "
							+ "(SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = "
							+ "(SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p "
							+ "WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) "
							+ "AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = "
							+ "(SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, "
							+ "(SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)))"
							+ " AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate BETWEEN ? AND ?  ORDER BY rc.receiptDate DESC";

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setDouble(1, 0);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3,
							dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
					preparedStatement.setString(4,
							dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

					resultSet = preparedStatement.executeQuery();

				} else {

					String query = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, "
							+ "rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount,(SELECT otherMode FROM PaymentDetails WHERE receiptID = rc.id) AS otherMode, (SELECT c.name FROM Clinic AS c "
							+ "WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName,  (SELECT name FROM PVVisitType WHERE id = "
							+ "(SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = "
							+ "(SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p "
							+ "WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) "
							+ "AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = "
							+ "(SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, "
							+ "(SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)))"
							+ " AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND rc.receiptDate LIKE ? ORDER BY rc.receiptDate DESC";

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setDouble(1, 0);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3,
							"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

					resultSet = preparedStatement.executeQuery();

				}

			} else {

				String query = "SELECT rc.id, rc.receiptNo, rc.receiptDate, rc.consultationCharges, rc.totalAmount, rc.tax, rc.netAmount, rc.advPayment, rc.balPayment, rc.paymentType, "
						+ "rc.billingType, rc.activityStatus, rc.referenceReceiptNo, rc.visitID, rc.totalDiscount, (SELECT otherMode FROM PaymentDetails WHERE receiptID = rc.id) AS otherMode, (SELECT c.name FROM Clinic AS c "
						+ "WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)) AS clinicName,  (SELECT name FROM PVVisitType WHERE id = "
						+ "(SELECT visitTypeID FROM Visit WHERE id = rc.visitID)) AS visitType, (SELECT CONCAT_WS(' ',firstName, middleName, lastName) FROM AppUser WHERE id = "
						+ "(SELECT clinicianID FROM Visit WHERE id = rc.visitID)) AS clinicianName, (SELECT CONCAT(p.firstName,' ',p.middleName,' ',p.lastName) FROM Patient AS p "
						+ "WHERE p.id = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS patientName, (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID) "
						+ "AS patientID, (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AS clinicID, (SELECT cg.regNumber FROM ClinicRegistration AS cg WHERE cg.clinicID = "
						+ "(SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID) AND cg.patientID = (SELECT v.patientID FROM Visit AS v WHERE v.id = rc.visitID)) AS regNumber, "
						+ "(SELECT pr.name FROM Practice AS pr WHERE pr.id = (SELECT c.practiceID FROM Clinic AS c WHERE c.id = (SELECT v.clinicID FROM Visit AS v WHERE v.id = rc.visitID)))"
						+ " AS practiceName FROM Receipt AS rc WHERE rc.balPayment > ? AND rc.visitID IN (SELECT v.id FROM Visit AS v WHERE v.clinicID = ?) AND "
						+ patientForm.getSearchCriteria() +"  ORDER BY rc.receiptDate DESC";

				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setDouble(1, 0);
				preparedStatement.setInt(2, patientForm.getClinicID());

				resultSet = preparedStatement.executeQuery();

			}

			/*
			 * Checking whether searchPatientName is empty if so, then retrieving all
			 * patient's list with pending credits else retrieve list of specific patient
			 * name if pending available for that patient
			 */
			/*
			 * if (patientForm.getSearchPatientName().isEmpty()) {
			 * 
			 * if (patientForm.getStartDate().isEmpty() &&
			 * patientForm.getEndDate().isEmpty()) {
			 * 
			 * String query = QueryMaker.RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * preparedStatement.setDouble(1, 0); preparedStatement.setInt(2,
			 * patientForm.getClinicID());
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else if (patientForm.getEndDate().isEmpty()) {
			 * 
			 * String query =
			 * QueryMaker.RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_START_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * preparedStatement.setDouble(1, 0); preparedStatement.setInt(2,
			 * patientForm.getClinicID()); preparedStatement.setString(3, "%" +
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else {
			 * 
			 * String query =
			 * QueryMaker.RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_START_END_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * preparedStatement.setDouble(1, 0); preparedStatement.setInt(2,
			 * patientForm.getClinicID()); preparedStatement.setString(3,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) +
			 * " 00:00"); preparedStatement.setString(4,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }
			 * 
			 * } else {
			 * 
			 * if (patientForm.getStartDate().isEmpty() &&
			 * patientForm.getEndDate().isEmpty()) {
			 * 
			 * String query =
			 * QueryMaker.RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_PATIENT_NAME;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setDouble(1, 0); preparedStatement.setString(2, "%" +
			 * searchName + "%"); preparedStatement.setInt(3, patientForm.getClinicID());
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else if (patientForm.getEndDate().isEmpty()) {
			 * 
			 * String query = QueryMaker.
			 * RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_PATIENT_NAME_BY_START_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setDouble(1, 0); preparedStatement.setString(2, "%" +
			 * searchName + "%"); preparedStatement.setInt(3, patientForm.getClinicID());
			 * preparedStatement.setString(4, "%" +
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * } else {
			 * 
			 * String query = QueryMaker.
			 * RETRIEVE_PATIENT_PENDING_CREDITS_DETAILS_BY_PATIENT_NAME_BY_START_END_DATE;
			 * 
			 * preparedStatement = connection.prepareStatement(query);
			 * 
			 * if (searchName.contains(" ")) { searchName = searchName.replaceAll(" ", "%");
			 * }
			 * 
			 * preparedStatement.setDouble(1, 0); preparedStatement.setString(2, "%" +
			 * searchName + "%"); preparedStatement.setInt(3, patientForm.getClinicID());
			 * preparedStatement.setString(4,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) +
			 * " 00:00"); preparedStatement.setString(5,
			 * dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");
			 * 
			 * resultSet = preparedStatement.executeQuery();
			 * 
			 * }
			 * 
			 * }
			 */

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Credit Report");
			Row row;

			row = spreadSheet.createRow(0);

			spreadSheet.createFreezePane(0, 1, 0, 1);

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 9);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);
			headerCellStyle.setWrapText(true);
			// headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 9);
			dataCellStyle.setFont(setDataFont);
			dataCellStyle.setWrapText(true);
			// dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			/*
			 * Generating XLSX Header value for Patient billing Information
			 */
			spreadSheet.setColumnWidth((short) 0, (short) (256 * 4));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 2, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 3, (short) (256 * 13));
			spreadSheet.setColumnWidth((short) 4, (short) (256 * 10));
			spreadSheet.setColumnWidth((short) 5, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 6, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 7, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 8, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 9, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 10, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 11, (short) (256 * 9));
			spreadSheet.setColumnWidth((short) 12, (short) (256 * 7));
			spreadSheet.setColumnWidth((short) 13, (short) (256 * 7));
			spreadSheet.setColumnWidth((short) 14, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 15, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 16, (short) (256 * 9));

			/*
			 * Setting header value into Cell
			 */
			cell = row.createCell((short) 0);
			cell.setCellValue("Sr.No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Practice Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Clinic Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Doctor Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Visit Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Patient Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Registration No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Receipt No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Receipt Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Consultation Charges");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 10);
			cell.setCellValue("Total Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 11);
			cell.setCellValue("Net Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 12);
			cell.setCellValue("Advance Payment");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 13);
			cell.setCellValue("Balance Payment");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 14);
			cell.setCellValue("Billing Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 15);
			cell.setCellValue("Payment Type");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 16);
			cell.setCellValue("Total Discount");
			cell.setCellStyle(headerCellStyle);

			while (resultSet.next()) {

				check = 1;

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(srNo);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("practiceName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("clinicName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("clinicianName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(resultSet.getString("visitType"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("patientName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getString("regNumber"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getString("receiptNo"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getDouble("consultationCharges"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 10);
				cell.setCellValue(resultSet.getDouble("totalAmount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 11);
				cell.setCellValue(resultSet.getDouble("netAmount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 12);
				cell.setCellValue(resultSet.getDouble("advPayment"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 13);
				cell.setCellValue(resultSet.getDouble("balPayment"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 14);
				cell.setCellValue(resultSet.getString("billingType"));
				cell.setCellStyle(dataCellStyle);

				String paymentType = "";

				if (resultSet.getString("paymentType") == null || resultSet.getString("paymentType") == "") {
					paymentType = resultSet.getString("paymentType");
				} else if (resultSet.getString("paymentType").isEmpty()) {
					paymentType = resultSet.getString("paymentType");
				} else if (resultSet.getString("paymentType").contains("Other")) {
					paymentType = resultSet.getString("paymentType") + " (" + resultSet.getString("otherMode") + ")";
				} else {
					paymentType = resultSet.getString("paymentType");
				}

				cell = row.createCell((short) 15);
				cell.setCellValue(paymentType);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 16);
				cell.setCellValue(resultSet.getDouble("totalDiscount"));
				cell.setCellStyle(dataCellStyle);

				srNo++;

			}

			if (check == 0) {

				status = "input";

				System.out.println("no record found.");

			} else {

				/*
				 * writing to the xlsx file
				 */
				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				wb.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();

				/*
				 * Write the output to a file
				 */
				FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
				fileOut.write(outArray);
				fileOut.flush();
				fileOut.close();

				System.out.println("Excel sheet for credit report is created successfully.");

				status = "success";

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @param excelFileName
	 * @return
	 */
	public String generateStockReport(PatientForm patientForm, String realPath, String excelFileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		String searchName = patientForm.getSearchPatientName();

		int currentRow = 1;

		int srNo = 1;

		int check = 0;

		double totalAmount = 0D;
		double tax = 0D;
		double balancePayment = 0D;
		String stockReceiptNo = "";
		String stockReceiptDate = "";
		String createdByUsername = "";
		int stockReceiptID = 0;
		String practiceName = "";
		String clinicName = "";
		String supplierName = "";

		int statusCheck = 0;

		String[] array = { "Yes", "No" };

		try {

			connection = getConnection();

			/*
			 * Checking whether searchPatientName is empty if so, then retrieving all stock
			 * list else retrieving only those stock entries which contains that particular
			 * product
			 */
			if (patientForm.getSearchPatientName().isEmpty()) {

				if (patientForm.getStartDate().isEmpty() && patientForm.getEndDate().isEmpty()) {

					String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER;

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setInt(1, patientForm.getSupplierID());
					preparedStatement.setInt(2, patientForm.getClinicID());

					resultSet = preparedStatement.executeQuery();

				} else if (patientForm.getEndDate().isEmpty()) {

					String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_START_DATE;

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setInt(1, patientForm.getSupplierID());
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3,
							"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

					resultSet = preparedStatement.executeQuery();

				} else {

					String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_START_AND_END_DATE;

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setInt(1, patientForm.getSupplierID());
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3,
							dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
					preparedStatement.setString(4,
							dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

					resultSet = preparedStatement.executeQuery();

				}

			} else {

				/*
				 * Check what the search criteria is, i.e. either ProductName or StockRecptNo,
				 * and depending upon that retrieving details from Stock and Stock receipt table
				 */
				if (patientForm.getSearchCriteria().equals("StockRecptNo")) {

					if (patientForm.getStartDate().isEmpty() && patientForm.getEndDate().isEmpty()) {

						String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_STOCK_NO;

						preparedStatement = connection.prepareStatement(query);

						if (searchName.contains(" ")) {
							searchName = searchName.replaceAll(" ", "%");
						}

						preparedStatement.setInt(1, patientForm.getSupplierID());
						preparedStatement.setString(3, "%" + searchName + "%");
						preparedStatement.setInt(2, patientForm.getClinicID());

						resultSet = preparedStatement.executeQuery();

					} else if (patientForm.getEndDate().isEmpty()) {

						String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_STOCK_NO_AND_START_DATE;

						preparedStatement = connection.prepareStatement(query);

						if (searchName.contains(" ")) {
							searchName = searchName.replaceAll(" ", "%");
						}

						preparedStatement.setInt(1, patientForm.getSupplierID());
						preparedStatement.setString(3, "%" + searchName + "%");
						preparedStatement.setInt(2, patientForm.getClinicID());
						preparedStatement.setString(4,
								"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

						resultSet = preparedStatement.executeQuery();

					} else {

						String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_STOCK_NO_AND_START_AND_END_DATE;

						preparedStatement = connection.prepareStatement(query);

						if (searchName.contains(" ")) {
							searchName = searchName.replaceAll(" ", "%");
						}

						preparedStatement.setInt(1, patientForm.getSupplierID());
						preparedStatement.setString(3, "%" + searchName + "%");
						preparedStatement.setInt(2, patientForm.getClinicID());
						preparedStatement.setString(4,
								dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
						preparedStatement.setString(5,
								dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

						resultSet = preparedStatement.executeQuery();

					}

				} else {

					if (patientForm.getStartDate().isEmpty() && patientForm.getEndDate().isEmpty()) {

						String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_PRODUCT;

						preparedStatement = connection.prepareStatement(query);

						if (searchName.contains(" ")) {
							searchName = searchName.replaceAll(" ", "%");
						}

						preparedStatement.setInt(1, patientForm.getSupplierID());
						preparedStatement.setString(3, "%" + searchName + "%");
						preparedStatement.setInt(2, patientForm.getClinicID());

						resultSet = preparedStatement.executeQuery();

					} else if (patientForm.getEndDate().isEmpty()) {

						String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_PRODUCT_START_DATE;

						preparedStatement = connection.prepareStatement(query);

						if (searchName.contains(" ")) {
							searchName = searchName.replaceAll(" ", "%");
						}

						preparedStatement.setInt(1, patientForm.getSupplierID());
						preparedStatement.setString(3, "%" + searchName + "%");
						preparedStatement.setInt(2, patientForm.getClinicID());
						preparedStatement.setString(4,
								"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

						resultSet = preparedStatement.executeQuery();

					} else {

						String query = QueryMaker.RETRIEVE_STOCK_REPORT_DETAILS_BY_SUPPLIER_AND_PRODUCT_START_AND_END_DATE;

						preparedStatement = connection.prepareStatement(query);

						if (searchName.contains(" ")) {
							searchName = searchName.replaceAll(" ", "%");
						}

						preparedStatement.setInt(1, patientForm.getSupplierID());
						preparedStatement.setString(3, "%" + searchName + "%");
						preparedStatement.setInt(2, patientForm.getClinicID());
						preparedStatement.setString(4,
								dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
						preparedStatement.setString(5,
								dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

						resultSet = preparedStatement.executeQuery();

					}

				}

			}

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			/*
			 * iterating over resultSet and getting values from Stockreceipt table and also
			 * retrieving values from Stock table based on stockReceiptID
			 */
			while (resultSet.next()) {

				currentRow = 1;

				check = 1;

				srNo = 1;

				stockReceiptID = resultSet.getInt("id");
				stockReceiptDate = dateFormat.format(resultSet.getTimestamp("receiptDate"));
				stockReceiptNo = resultSet.getString("receiptNo");
				totalAmount = resultSet.getDouble("totalAmount");
				practiceName = resultSet.getString("practiceName");
				supplierName = resultSet.getString("supplierName");
				clinicName = resultSet.getString("clinicName");
				tax = resultSet.getDouble("tax");
				balancePayment = resultSet.getDouble("balPayment");
				createdByUsername = resultSet.getString("username");

				/*
				 * Checking what activityStatus value is, and depending upon that fetching stock
				 * details from Stock table with status as either Not Empty or Empty or both
				 */
				if (patientForm.getActivityStatus().equals("Both")) {

					/*
					 * Retrieving product details by stockReceiptID from Stock table
					 */
					String retrieveStockDetailsQuery = QueryMaker.RETRIEVE_STOCK_DETAILS_BY_STOCK_RECEIPT_ID;

					preparedStatement1 = connection.prepareStatement(retrieveStockDetailsQuery);

					preparedStatement1.setInt(1, stockReceiptID);

					resultSet1 = preparedStatement1.executeQuery();

					preparedStatement2 = connection.prepareStatement(retrieveStockDetailsQuery);

					preparedStatement2.setInt(1, stockReceiptID);

					resultSet2 = preparedStatement2.executeQuery();

				} else {

					/*
					 * Retrieving product details by stockReceiptID and status from Stock table
					 */
					String retrieveStockDetailsQuery = QueryMaker.RETRIEVE_STOCK_DETAILS_BY_STOCK_RECEIPT_ID_AND_STATUS;

					preparedStatement1 = connection.prepareStatement(retrieveStockDetailsQuery);

					preparedStatement1.setInt(1, stockReceiptID);
					preparedStatement1.setString(2, patientForm.getActivityStatus());

					resultSet1 = preparedStatement1.executeQuery();

					preparedStatement2 = connection.prepareStatement(retrieveStockDetailsQuery);

					preparedStatement2.setInt(1, stockReceiptID);
					preparedStatement2.setString(2, patientForm.getActivityStatus());

					resultSet2 = preparedStatement2.executeQuery();

				}

				if (resultSet2.next()) {

					XSSFSheet spreadSheet = wb.createSheet(stockReceiptNo + "-Stock Receipt");
					Row row;

					row = spreadSheet.createRow(0);

					/*
					 * Creating header in XLSX
					 */
					CellStyle headerCellStyle = wb.createCellStyle();

					/*
					 * Setting up the font
					 */
					Font setFont = wb.createFont();
					setFont.setFontHeightInPoints((short) 9);
					setFont.setBold(true);
					headerCellStyle.setFont(setFont);
					headerCellStyle.setWrapText(true);
					// headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
					// headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
					// headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
					// headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

					/*
					 * Setting up Data style in XLSX
					 */
					CellStyle dataCellStyle = wb.createCellStyle();
					Font setDataFont = wb.createFont();
					setDataFont.setFontHeightInPoints((short) 9);
					dataCellStyle.setFont(setDataFont);
					dataCellStyle.setWrapText(true);
					// dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
					// dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
					// dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
					// dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

					/*
					 * Initializing Cell reference variable
					 */
					Cell cell = null;

					/*
					 * Generating XLSX Header value for Patient billing Information
					 */
					spreadSheet.setColumnWidth((short) 0, (short) (256 * 5));
					spreadSheet.setColumnWidth((short) 1, (short) (256 * 11));
					spreadSheet.setColumnWidth((short) 2, (short) (256 * 12));
					spreadSheet.setColumnWidth((short) 3, (short) (256 * 11));
					spreadSheet.setColumnWidth((short) 4, (short) (256 * 11));
					spreadSheet.setColumnWidth((short) 5, (short) (256 * 7));
					spreadSheet.setColumnWidth((short) 6, (short) (256 * 8));
					spreadSheet.setColumnWidth((short) 7, (short) (256 * 7));
					spreadSheet.setColumnWidth((short) 8, (short) (256 * 10));
					spreadSheet.setColumnWidth((short) 9, (short) (256 * 7));
					spreadSheet.setColumnWidth((short) 10, (short) (256 * 8));
					spreadSheet.setColumnWidth((short) 11, (short) (256 * 7));
					spreadSheet.setColumnWidth((short) 12, (short) (256 * 9));
					spreadSheet.setColumnWidth((short) 13, (short) (256 * 11));

					/*
					 * Setting header value into Cell
					 */
					cell = row.createCell((short) 0);
					cell.setCellValue("Sr.No.");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue("Practice Name");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 2);
					cell.setCellValue("Clinic Name");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 3);
					cell.setCellValue("Supplier Name");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 4);
					cell.setCellValue("Product Name");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 5);
					cell.setCellValue("Cost Price");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 6);
					cell.setCellValue("Selling Price");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 7);
					cell.setCellValue("Net Stock");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 8);
					cell.setCellValue("Amount");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 9);
					cell.setCellValue("Tax Inclusive");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 10);
					cell.setCellValue("Tax Name");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 11);
					cell.setCellValue("Tax Percent");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 12);
					cell.setCellValue("Tax Amount");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 13);
					cell.setCellValue("Activity Status");
					cell.setCellStyle(headerCellStyle);

					while (resultSet1.next()) {

						check = 1;

						statusCheck = 1;

						row = spreadSheet.createRow(currentRow++);

						cell = row.createCell((short) 0);
						cell.setCellValue(srNo);
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 1);
						cell.setCellValue(practiceName);
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 2);
						cell.setCellValue(clinicName);
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 3);
						cell.setCellValue(supplierName);
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 4);
						cell.setCellValue(resultSet1.getString("productName"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 5);
						cell.setCellValue(resultSet1.getDouble("costPrice"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 6);
						cell.setCellValue(resultSet1.getDouble("sellingPrice"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 7);
						cell.setCellValue(resultSet1.getDouble("netStock"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 8);
						cell.setCellValue(resultSet1.getDouble("amount"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 9);
						cell.setCellValue(array[resultSet1.getInt("taxInclusive")]);
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 10);
						cell.setCellValue(resultSet1.getString("taxName"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 11);
						cell.setCellValue(resultSet1.getDouble("taxPercent"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 12);
						cell.setCellValue(resultSet1.getDouble("taxAmount"));
						cell.setCellStyle(dataCellStyle);

						cell = row.createCell((short) 13);
						cell.setCellValue(resultSet1.getString("status"));
						cell.setCellStyle(dataCellStyle);

						srNo++;

					}

					// creating row for Stock receipt no
					row = spreadSheet.createRow(currentRow++);

					/*
					 * Setting header and value for Stock receipt no
					 */
					cell = row.createCell((short) 0);
					cell.setCellValue("Stock Receipt No.");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(stockReceiptNo);
					cell.setCellStyle(dataCellStyle);

					// creating row for Stock receipt date
					row = spreadSheet.createRow(currentRow++);

					/*
					 * Setting header and value for Stock receipt date
					 */
					cell = row.createCell((short) 0);
					cell.setCellValue("Stock Receipt Date");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(stockReceiptDate);
					cell.setCellStyle(dataCellStyle);

					// creating row for Stock Total Amount
					row = spreadSheet.createRow(currentRow++);

					/*
					 * Setting header and value for Stock Total Amount
					 */
					cell = row.createCell((short) 0);
					cell.setCellValue("Total Amount");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(totalAmount);
					cell.setCellStyle(dataCellStyle);

					// creating row for tax amount
					row = spreadSheet.createRow(currentRow++);

					/*
					 * Setting header and value for tax amount
					 */
					cell = row.createCell((short) 0);
					cell.setCellValue("Tax");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(tax);
					cell.setCellStyle(dataCellStyle);

					// creating row for balance payment
					row = spreadSheet.createRow(currentRow++);

					/*
					 * Setting header and value for balance payment
					 */
					cell = row.createCell((short) 0);
					cell.setCellValue("Balance Payment");
					cell.setCellStyle(headerCellStyle);

					cell = row.createCell((short) 1);
					cell.setCellValue(balancePayment);
					cell.setCellStyle(dataCellStyle);

				} else {
					System.out.println("No records found..");
				}

			}

			if (check == 0 || statusCheck == 0) {

				status = "input";

				System.out.println("no record found.");

			} else {

				/*
				 * writing to the xlsx file
				 */
				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				wb.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();

				/*
				 * Write the output to a file
				 */
				FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
				fileOut.write(outArray);
				fileOut.flush();
				fileOut.close();

				System.out.println("Excel sheet for stock report is created successfully.");

				status = "success";

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet2);
			JDBCHelper.closeStatement(preparedStatement2);

			JDBCHelper.closeResultSet(resultSet1);
			JDBCHelper.closeStatement(preparedStatement1);

			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @param excelFileName
	 * @return
	 */
	public String generateSalesReport(PatientForm patientForm, String realPath, String excelFileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		String searchName = patientForm.getSearchPatientName();

		int currentRow = 1;

		int srNo = 1;

		int check = 0;

		try {

			connection = getConnection();

			/*
			 * Checking whether search text field in empty, if empty then retrieve product
			 * specific sales list else retrieve over all sales list
			 */
			if (patientForm.getSearchPatientName().isEmpty()) {

				if (patientForm.getStartDate().isEmpty() && patientForm.getEndDate().isEmpty()) {

					String query = QueryMaker.RETRIEVE_SALES_REPORT_LIST;

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setString(1, ActivityStatus.ACTIVE);
					preparedStatement.setInt(2, patientForm.getClinicID());

					resultSet = preparedStatement.executeQuery();

				} else if (patientForm.getEndDate().isEmpty()) {

					String query = QueryMaker.RETRIEVE_SALES_REPORT_LIST_BY_START_DATE;

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setString(1, ActivityStatus.ACTIVE);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3,
							"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

					resultSet = preparedStatement.executeQuery();

				} else {

					String query = QueryMaker.RETRIEVE_SALES_REPORT_LIST_BY_START_AND_END_DATE;

					preparedStatement = connection.prepareStatement(query);

					preparedStatement.setString(1, ActivityStatus.ACTIVE);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3,
							dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
					preparedStatement.setString(4,
							dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

					resultSet = preparedStatement.executeQuery();

				}

			} else {

				if (patientForm.getStartDate().isEmpty() && patientForm.getEndDate().isEmpty()) {

					String query = QueryMaker.RETRIEVE_SALES_REPORT_LIST_BY_PRODUCT;

					preparedStatement = connection.prepareStatement(query);

					if (searchName.contains(" ")) {
						searchName = searchName.replaceAll(" ", "%");
					}

					preparedStatement.setString(1, ActivityStatus.ACTIVE);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3, "%" + searchName + "%");

					resultSet = preparedStatement.executeQuery();

				} else if (patientForm.getEndDate().isEmpty()) {

					String query = QueryMaker.RETRIEVE_SALES_REPORT_LIST_BY_PRODUCT_AND_START_DATE;

					preparedStatement = connection.prepareStatement(query);

					if (searchName.contains(" ")) {
						searchName = searchName.replaceAll(" ", "%");
					}

					preparedStatement.setString(1, ActivityStatus.ACTIVE);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3, "%" + searchName + "%");
					preparedStatement.setString(4,
							"%" + dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + "%");

					resultSet = preparedStatement.executeQuery();

				} else {

					String query = QueryMaker.RETRIEVE_SALES_REPORT_LIST_BY_PRODUCT_AND_START_AND_END_DATE;

					preparedStatement = connection.prepareStatement(query);

					if (searchName.contains(" ")) {
						searchName = searchName.replaceAll(" ", "%");
					}

					preparedStatement.setString(1, ActivityStatus.ACTIVE);
					preparedStatement.setInt(2, patientForm.getClinicID());
					preparedStatement.setString(3, "%" + searchName + "%");
					preparedStatement.setString(4,
							dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
					preparedStatement.setString(5,
							dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

					resultSet = preparedStatement.executeQuery();

				}

			}

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("Sales Receipt");
			Row row;

			row = spreadSheet.createRow(0);

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 9);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);
			headerCellStyle.setWrapText(true);
			// headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 9);
			dataCellStyle.setFont(setDataFont);
			dataCellStyle.setWrapText(true);
			// dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			/*
			 * Generating XLSX Header value for Patient billing Information
			 */
			spreadSheet.setColumnWidth((short) 0, (short) (256 * 5));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 2, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 3, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 4, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 5, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 6, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 7, (short) (256 * 7));
			spreadSheet.setColumnWidth((short) 8, (short) (256 * 8));
			spreadSheet.setColumnWidth((short) 9, (short) (256 * 11));

			/*
			 * Setting header value into Cell
			 */
			cell = row.createCell((short) 0);
			cell.setCellValue("Sr.No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Practice Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Clinic Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Receipt No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Receipt Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Product Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Quantity/Weight(g)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Rate");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Receipt Create By");
			cell.setCellStyle(headerCellStyle);

			/*
			 * iterating over resultSet and getting values from Stockreceipt table and also
			 * retrieving values from Stock table based on stockReceiptID
			 */
			while (resultSet.next()) {

				check = 1;

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(srNo);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("practiceName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("clinicName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("receiptNo"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("product"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getDouble("quantity"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getDouble("rate"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(resultSet.getDouble("amount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getString("username"));
				cell.setCellStyle(dataCellStyle);

				srNo++;

			}

			if (check == 0) {

				status = "input";

				System.out.println("no record found.");

			} else {

				/*
				 * writing to the xlsx file
				 */
				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				wb.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();

				/*
				 * Write the output to a file
				 */
				FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
				fileOut.write(outArray);
				fileOut.flush();
				fileOut.close();

				System.out.println("Excel sheet for sales report is created successfully.");

				status = "success";

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

	/**
	 * 
	 * @param patientForm
	 * @param realPath
	 * @param excelFileName
	 * @return
	 */
	public String generate3CFormReport(PatientForm patientForm, String realPath, String excelFileName) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

		int currentRow = 1;

		int srNo = 1;

		int check = 0;

		try {

			connection = getConnection();

			String generate3CFormReportQuery = QueryMaker.RETRIEVE_RECEIPT_DETAILS_BY_START_AND_END_DATE;

			preparedStatement = connection.prepareStatement(generate3CFormReportQuery);

			preparedStatement.setString(4, ActivityStatus.ACTIVE);
			preparedStatement.setInt(3, patientForm.getClinicID());
			preparedStatement.setString(1,
					dateFormat1.format(dateFormat2.parse(patientForm.getStartDate())) + " 00:00");
			preparedStatement.setString(2, dateFormat1.format(dateFormat2.parse(patientForm.getEndDate())) + " 23:59");

			resultSet = preparedStatement.executeQuery();

			// Workbook wb = new HSSFWorkbook();
			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet spreadSheet = wb.createSheet("3C Form");
			Row row;

			row = spreadSheet.createRow(0);

			/*
			 * Creating header in XLSX
			 */
			CellStyle headerCellStyle = wb.createCellStyle();

			/*
			 * Setting up the font
			 */
			Font setFont = wb.createFont();
			setFont.setFontHeightInPoints((short) 9);
			setFont.setBold(true);
			headerCellStyle.setFont(setFont);
			headerCellStyle.setWrapText(true);
			// headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Setting up Data style in XLSX
			 */
			CellStyle dataCellStyle = wb.createCellStyle();
			Font setDataFont = wb.createFont();
			setDataFont.setFontHeightInPoints((short) 9);
			dataCellStyle.setFont(setDataFont);
			dataCellStyle.setWrapText(true);
			// dataCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderTop(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			// dataCellStyle.setBorderLeft(CellStyle.BORDER_THIN);

			/*
			 * Initializing Cell reference variable
			 */
			Cell cell = null;

			/*
			 * Generating XLSX Header value for Patient billing Information
			 */
			spreadSheet.setColumnWidth((short) 0, (short) (256 * 5));
			spreadSheet.setColumnWidth((short) 1, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 2, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 3, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 4, (short) (256 * 12));
			spreadSheet.setColumnWidth((short) 5, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 6, (short) (256 * 11));
			spreadSheet.setColumnWidth((short) 7, (short) (256 * 7));
			spreadSheet.setColumnWidth((short) 8, (short) (256 * 8));
			spreadSheet.setColumnWidth((short) 9, (short) (256 * 11));

			/*
			 * Setting header value into Cell
			 */
			cell = row.createCell((short) 0);
			cell.setCellValue("Sr.No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 1);
			cell.setCellValue("Practice Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 2);
			cell.setCellValue("Clinic Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 3);
			cell.setCellValue("Receipt No.");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 4);
			cell.setCellValue("Receipt Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 5);
			cell.setCellValue("Product Name");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 6);
			cell.setCellValue("Quantity/Weight(g)");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 7);
			cell.setCellValue("Rate");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 8);
			cell.setCellValue("Amount");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell((short) 9);
			cell.setCellValue("Receipt Create By");
			cell.setCellStyle(headerCellStyle);

			/*
			 * iterating over resultSet and getting values from Stockreceipt table and also
			 * retrieving values from Stock table based on stockReceiptID
			 */
			while (resultSet.next()) {

				check = 1;

				row = spreadSheet.createRow(currentRow++);

				cell = row.createCell((short) 0);
				cell.setCellValue(srNo);
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 1);
				cell.setCellValue(resultSet.getString("practiceName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 2);
				cell.setCellValue(resultSet.getString("clinicName"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 3);
				cell.setCellValue(resultSet.getString("receiptNo"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 4);
				cell.setCellValue(dateFormat.format(resultSet.getTimestamp("receiptDate")));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 5);
				cell.setCellValue(resultSet.getString("product"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 6);
				cell.setCellValue(resultSet.getDouble("quantity"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 7);
				cell.setCellValue(resultSet.getDouble("rate"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 8);
				cell.setCellValue(resultSet.getDouble("amount"));
				cell.setCellStyle(dataCellStyle);

				cell = row.createCell((short) 9);
				cell.setCellValue(resultSet.getString("username"));
				cell.setCellStyle(dataCellStyle);

				srNo++;

			}

			if (check == 0) {

				status = "input";

				System.out.println("no record found.");

			} else {

				/*
				 * writing to the xlsx file
				 */
				ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				wb.write(outByteStream);
				byte[] outArray = outByteStream.toByteArray();

				/*
				 * Write the output to a file
				 */
				FileOutputStream fileOut = new FileOutputStream(new File(realPath + "/" + excelFileName));
				fileOut.write(outArray);
				fileOut.flush();
				fileOut.close();

				System.out.println("Excel sheet for sales report is created successfully.");

				status = "success";

			}

		} catch (Exception exception) {
			exception.printStackTrace();

			status = "error";
		} finally {
			JDBCHelper.closeResultSet(resultSet);
			JDBCHelper.closeStatement(preparedStatement);
			JDBCHelper.closeConnection(connection);
		}

		return status;
	}

}

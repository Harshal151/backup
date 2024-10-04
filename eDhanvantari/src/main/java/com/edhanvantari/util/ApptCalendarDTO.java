package com.edhanvantari.util;

/**
 * 
 * @author kovid bioanalytics
 *
 */
public class ApptCalendarDTO {

	public int id;
	public String title;
	public String start;
	public String end;
	public String color;
	public String rendering;
	public int visitTypeID;
	public int visitID;


	/**
	 * @return the visitID
	 */
	public int getVisitID() {
		return visitID;
	}

	/**
	 * @param visitID the visitID to set
	 */
	public void setVisitID(int visitID) {
		this.visitID = visitID;
	}

	/**
	 * @return the visitTypeID
	 */
	public int getVisitTypeID() {
		return visitTypeID;
	}

	/**
	 * @param visitTypeID the visitTypeID to set
	 */
	public void setVisitTypeID(int visitTypeID) {
		this.visitTypeID = visitTypeID;
	}

	/**
	 * @return the rendering
	 */
	public String getRendering() {
		return rendering;
	}

	/**
	 * @param rendering
	 *            the rendering to set
	 */
	public void setRendering(String rendering) {
		this.rendering = rendering;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

}

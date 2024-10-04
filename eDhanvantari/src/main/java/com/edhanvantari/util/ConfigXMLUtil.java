package com.edhanvantari.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.edhanvantari.daoImpl.ReportDAOImpl;
import com.edhanvantari.daoInf.ReportDAOInf;

public class ConfigXMLUtil extends PathConfigXMLUtil {

	HttpServletRequest request = ServletActionContext.getRequest();
	Properties properties = new Properties();
	/*
	 * Fetching filePath from configuration XML file
	 */
	static String filePath = getConfigPath();

	
	File fXmlFile = new File(filePath + File.separator + "edhanvantari.xml");

	File practiceXMLFile = new File(filePath + File.separator + "PracticeConfig.xml");

	File prescriptionXMLFile = new File(filePath + File.separator + "Prescription.xml");

	File billXMLFile = new File(filePath + File.separator + "BillConfig.xml");

	File reportXMLFile = new File(filePath + File.separator + "reporting.xml");

	/**
	 * 
	 * @return DBIP
	 */
	public String getDBIP() {

		String DBIP = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBIP = eElement.getElementsByTagName("DB-IP").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBIP;
	}

	/**
	 * 
	 * @return DBPort
	 */
	public String getDBPort() {
		String DBPort = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBPort = eElement.getElementsByTagName("DB-Port").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBPort;
	}

	/**
	 * 
	 * @return DBName
	 */
	public String getDBName() {
		String DBName = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBName = eElement.getElementsByTagName("DB-Name").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBName;
	}

	/**
	 * 
	 * @return DBUsername
	 */
	public String getDBUsername() {
		String DBUsername = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBUsername = eElement.getElementsByTagName("DB-Username").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBUsername;
	}

	/**
	 * 
	 * @return DBPassword
	 */
	public String getDBPassword() {
		String DBPassword = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBPassword = eElement.getElementsByTagName("DB-Password").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBPassword;
	}
	
	/**
	 * 
	 * @return sessionTimeout
	 */
	public String getSessionTimeout() {
		String sessionTimeout = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					sessionTimeout = eElement.getElementsByTagName("sessiontimeout").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return sessionTimeout;
	}

	/**
	 * 
	 * @return invalidaTeAttempt
	 */
	public String getInvalidateAttempt() {
		String invalidaTeAttempt = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					invalidaTeAttempt = eElement.getElementsByTagName("invalidattempts").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return invalidaTeAttempt;
	}

	/**
	 * 
	 * @return emailFrom1
	 */
	public String getEmailTo() {
		String emailFrom1 = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					emailFrom1 = eElement.getElementsByTagName("email-to").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return emailFrom1;
	}

	/**
	 * 
	 * @return emailFrom2
	 */
	public String getEmailFrom() {
		String emailFrom2 = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					emailFrom2 = eElement.getElementsByTagName("email-from").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return emailFrom2;
	}

	/**
	 * 
	 * @return emailFrom2Pass
	 */
	public String getEmailFromPass() {
		String emailFrom2Pass = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					emailFrom2Pass = eElement.getElementsByTagName("email-from-pass").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return emailFrom2Pass;
	}

	/**
	 * 
	 * @return clinicTypeList
	 */
	public HashMap<String, String> getClinicTypeList() {
		HashMap<String, String> clinicTypeList = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("clinic-types");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					String[] clinicType = value.split("\n");

					for (int i = 0; i < clinicType.length; i++) {
						clinicTypeList.put(clinicType[i], clinicType[i]);
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return clinicTypeList;
	}

	/**
	 * 
	 * @return HashMap
	 */
	public HashMap<String, String> getDiagnoseList() {
		HashMap<String, String> diagnoseMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("diagnoses");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					String[] clinicType = value.split("\n");

					for (int i = 0; i < clinicType.length; i++) {
						diagnoseMap.put(clinicType[i].trim(), clinicType[i].trim());
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return diagnoseMap;
	}

	/**
	 * 
	 * @return drugMap
	 */
	public HashMap<String, String> getDrugList() {
		HashMap<String, String> drugMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(prescriptionXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("drugList");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					String[] drugType = value.split("\n");

					for (int i = 0; i < drugType.length; i++) {
						drugMap.put(drugType[i].trim(), drugType[i].trim());
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return drugMap;
	}

	/**
	 * 
	 * @return frequencyMap
	 */
	public HashMap<String, String> getFrequencyList() {
		HashMap<String, String> frequencyMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(prescriptionXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("frequencyvalues");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					String[] freq = value.split("\n");

					for (int i = 0; i < freq.length; i++) {
						frequencyMap.put(freq[i].trim(), freq[i].trim());
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return frequencyMap;
	}

	/**
	 * 
	 * @param drugName
	 * @return JSONObject
	 */
	public JSONObject valuesOnBehalfOfDrugName(String drugName) {
		JSONObject values = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(prescriptionXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("drug");

			JSONArray array = new JSONArray();
			values = new JSONObject();

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					JSONObject object = new JSONObject();

					Element element = (Element) nNode;

					String value = nNode.getTextContent().trim();

					if (drugName.equals(value)) {
						String dose = element.getAttribute("dose");
						String doseUnit = element.getAttribute("drugname");

						object.put("Dose", dose);
						object.put("DoseUnit", doseUnit);
						array.add(object);

						values.put("Release", array);
						return values;
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return values;
	}

	/**
	 * 
	 * @return chargeTypeMap
	 */
	public HashMap<String, String> getChargeTypeList() {
		HashMap<String, String> chargeTypeMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(billXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("chargeType");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					chargeTypeMap.put(value, value);
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return chargeTypeMap;
	}

	/**
	 * 
	 * @param chargeType
	 * @return JSONObject
	 */
	public JSONObject valuesOnBehalfOfChargeType(String chargeType) {
		JSONObject values = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(billXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("bill-item");

			JSONArray array = new JSONArray();
			values = new JSONObject();

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					JSONObject object = new JSONObject();

					Element element = (Element) nNode;

					String chargeType1 = element.getElementsByTagName("chargeType").item(0).getTextContent();

					if (chargeType.equals(chargeType1)) {
						String charge = element.getElementsByTagName("charge").item(0).getTextContent();

						object.put("Charge", charge);
						array.add(object);

						values.put("Release", array);
						return values;
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return values;
	}

	/**
	 * 
	 * @return
	 */
	public String getDOB() {
		String DOB = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DOB = eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DOB;
	}

	/**
	 * 
	 * @return
	 */
	public String getPageSize() {
		String pageSize = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					pageSize = eElement.getElementsByTagName("pageSize").item(0).getTextContent().trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return pageSize;
	}

	/**
	 * 
	 * @return
	 */
	public String getA5LetterHeadPath() {
		String A5letterHeadPath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					A5letterHeadPath = eElement.getElementsByTagName("letterHeadImageA5").item(0).getTextContent()
							.trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return A5letterHeadPath;
	}

	/**
	 * 
	 * @return
	 */
	public String getA4LetterHeadPath() {
		String getA4LetterHeadPath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					getA4LetterHeadPath = eElement.getElementsByTagName("letterHeadImageA4").item(0).getTextContent()
							.trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return getA4LetterHeadPath;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getDoctorList() {
		HashMap<String, String> doctorMap = new HashMap<String, String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("referralDoctors");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					String[] doctorList = value.split("\n");

					for (int i = 0; i < doctorList.length; i++) {
						doctorMap.put(doctorList[i].trim(), doctorList[i].trim());
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return doctorMap;
	}

	/**
	 * 
	 * @return
	 */
	public String getMedicalCertificateText() {
		String medicalCrtificateText = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					medicalCrtificateText = eElement.getElementsByTagName("medicalCertificate").item(0).getTextContent()
							.trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return medicalCrtificateText;
	}

	/**
	 * 
	 * @return
	 */
	public String getReferalLetterText() {
		String referealLetterText = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					referealLetterText = eElement.getElementsByTagName("referralLetter").item(0).getTextContent()
							.trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return referealLetterText;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getReferralDoctorList() {

		List<String> doctorList = new ArrayList<String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("referralDoctors");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					String value = nNode.getTextContent().trim();
					String[] doctor = value.split("\n");

					for (int i = 0; i < doctor.length; i++) {

						doctorList.add(doctor[i].trim());
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return doctorList;

	}

	/**
	 * 
	 * @return
	 */
	public String getImagePath() {
		String imagePath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					imagePath = eElement.getElementsByTagName("logo").item(0).getTextContent().trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return imagePath;
	}

	/**
	 * 
	 * @return
	 */
	public String getConsentDocPath() {
		String consentPath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(practiceXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-practice");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					consentPath = eElement.getElementsByTagName("consentDocumentPath").item(0).getTextContent().trim();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return consentPath;
	}

	/**
	 * 
	 * @return
	 */
	public String getSMSUsername() {

		String SMSUsername = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					SMSUsername = eElement.getElementsByTagName("SMS-Username").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return SMSUsername;
	}

	/**
	 * 
	 * @return
	 */
	public String getSMSPassword() {

		String SMSPassword = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					SMSPassword = eElement.getElementsByTagName("SMS-Password").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return SMSPassword;
	}

	/**
	 * 
	 * @return
	 */
	public String getSMSURL() {

		String SMSURL = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					SMSURL = eElement.getElementsByTagName("SMS-URL").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return SMSURL;
	}

	/**
	 * 
	 * @return
	 */
	public String getSMSSenderID() {

		String SMSSenderID = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					SMSSenderID = eElement.getElementsByTagName("SMS-SenderID").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return SMSSenderID;
	}

	/**
	 * 
	 * @return
	 *//*
	public String getReportFilePath() {

		String reportFilePath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					reportFilePath = eElement.getElementsByTagName("REPORT-FILE-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return reportFilePath;
	}

	*//**
	 * 
	 * @return
	 *//*
	public String getLogoFilePath() {

		String logoFilePath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					logoFilePath = eElement.getElementsByTagName("LOGO-FILE-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return logoFilePath;
	}*/

	/**
	 * 
	 * @param IPAddress
	 * @return
	 */
	public String getLatLong(String IPAddress) {

		String latLong = null;

		String url = "http://freegeoip.net/xml/" + IPAddress;

		System.out.println("Location tracking URL is..." + url);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL(url).openStream());

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Response");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					latLong = eElement.getElementsByTagName("Latitude").item(0).getTextContent() + ","
							+ eElement.getElementsByTagName("Longitude").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return latLong;
	}

	/**
	 * 
	 * @param practiceName
	 * @param clinicName
	 * @return
	 */
	public HashMap<String, String> retrievePatientRelatedTableList(String practiceName, String clinicName) {

		HashMap<String, String> map = new HashMap<String, String>();

		ConfigurationUtil configurationUtil = new ConfigurationUtil();

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reportXMLFile);

			doc.getDocumentElement().normalize();

			NodeList practiceList = doc.getElementsByTagName("Practice");

			for (int j = 0; j < practiceList.getLength(); j++) {
				Element practiceElement = (Element) practiceList.item(j);
				if (practiceElement.hasAttribute("name") && practiceElement.getAttribute("name").equals(practiceName)) {

					NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

					for (int i = 0; i < clinicList.getLength(); i++) {
						Element clinicElement = (Element) clinicList.item(i);

						if (clinicElement.hasAttribute("name")
								&& clinicElement.getAttribute("name").equals(clinicName)) {
							NodeList patientTableList = clinicElement.getElementsByTagName("Table");

							for (int k = 0; k < patientTableList.getLength(); k++) {
								Element patientTableElement = (Element) patientTableList.item(k);

								if (patientTableElement.hasAttribute("name")
										&& patientTableElement.getAttribute("name").equals("Patient AS pt")) {
									NodeList tableList = patientTableElement.getElementsByTagName("Table");

									for (int l = 0; l < tableList.getLength(); l++) {
										Node tableNode = tableList.item(l);

										Element tableElement = (Element) tableNode;

										// String[] nameArray = patientTableElement.getAttribute("name").split(" AS ");

										if (tableElement.getAttribute("parent")
												.equals(patientTableElement.getAttribute("name"))) {
											/*
											 * System.out.println(tableElement.getAttribute("name") + ".." +
											 * tableElement.getAttribute("key"));
											 */
											String[] array = tableElement.getAttribute("name").split(" AS ");
											// adding table name attribute into
											map.put(tableElement.getAttribute("name"), array[0]);
										}

									}
								}
							}
						}

					}
					break;

				}
			}

			// Sort map based on value
			map = configurationUtil.sortHashMapByValue(map);

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return map;

	}

	/**
	 * 
	 * @param tagName
	 * @param practiceName
	 * @param clinicName
	 * @return
	 */
	public boolean verifySingularOrCompositeTag(String tagName, String practiceName, String clinicName) {

		boolean check = false;

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reportXMLFile);

			doc.getDocumentElement().normalize();

			NodeList practiceList = doc.getElementsByTagName("Practice");

			for (int j = 0; j < practiceList.getLength(); j++) {
				Element practiceElement = (Element) practiceList.item(j);
				if (practiceElement.hasAttribute("name") && practiceElement.getAttribute("name").equals(practiceName)) {

					NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

					for (int i = 0; i < clinicList.getLength(); i++) {
						Element clinicElement = (Element) clinicList.item(i);

						if (clinicElement.hasAttribute("name")
								&& clinicElement.getAttribute("name").equals(clinicName)) {
							NodeList patientTableList = clinicElement.getElementsByTagName("Table");

							for (int k = 0; k < patientTableList.getLength(); k++) {
								Element patientTableElement = (Element) patientTableList.item(k);

								if (patientTableElement.hasAttribute("name")
										&& patientTableElement.getAttribute("name").equals("Patient AS pt")) {

									NodeList tableList = patientTableElement.getElementsByTagName("Table");

									for (int l = 0; l < tableList.getLength(); l++) {

										Element tableElement = (Element) tableList.item(l);

										if (tableElement.hasAttribute("name")
												&& tableElement.getAttribute("name").equals(tagName)) {

											if (tableElement.getTextContent().isEmpty()) {
												check = true;
											}

										}

									}
								}
							}
						}

					}
					break;

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();

			check = false;
		}

		return check;
	}

	public JSONObject retrieveReportConfigVisitRelatedTableList(String practiceName, String clinicName,
			String tagName) {

		ReportDAOInf reportDAOInf = new ReportDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONArray array1 = new JSONArray();

		JSONObject object = null;

		int check = 0;

		String keyValue = "";

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reportXMLFile);

			doc.getDocumentElement().normalize();

			NodeList practiceList = doc.getElementsByTagName("Practice");

			for (int j = 0; j < practiceList.getLength(); j++) {
				Element practiceElement = (Element) practiceList.item(j);
				if (practiceElement.hasAttribute("name") && practiceElement.getAttribute("name").equals(practiceName)) {

					NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

					for (int i = 0; i < clinicList.getLength(); i++) {
						Element clinicElement = (Element) clinicList.item(i);

						if (clinicElement.hasAttribute("name")
								&& clinicElement.getAttribute("name").equals(clinicName)) {
							NodeList patientTableList = clinicElement.getElementsByTagName("Table");

							for (int k = 0; k < patientTableList.getLength(); k++) {
								Element patientTableElement = (Element) patientTableList.item(k);

								if (patientTableElement.hasAttribute("name")
										&& patientTableElement.getAttribute("name").equals(tagName)) {
									NodeList tableList = patientTableElement.getElementsByTagName("Table");

									for (int l = 0; l < tableList.getLength(); l++) {
										Node tableNode = tableList.item(l);

										Element tableElement = (Element) tableNode;

										// String[] tagNameArr = patientTableElement.getAttribute("name").split(" AS ");

										if (tableElement.getAttribute("parent")
												.equals(patientTableElement.getAttribute("name"))) {

											object = new JSONObject();

											check = 1;

											String[] nameArr = tableElement.getAttribute("name").split(" AS ");

											keyValue = tableElement.getAttribute("key");

											object.put("check", check);
											object.put("tableName",tableElement.getAttribute("name") + "=" + nameArr[0]);
											object.put("tableCheck", "visit");

											array.add(object);

											values.put("Release", array);

										}

									}
								}
							}
						}

					}
					break;

				}
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				object.put("tableCheck", "visitEmpty");

				array.add(object);

				values.put("Release", array);

			}

			System.out.println("key value..." + keyValue);

			/*
			 * retrieving column list based on tagName and storing it with key value as
			 * Release1 in order to display column list for the selected tagName
			 */
			Connection connection = DAOConnection.getConnection();

			// Split tableName variable by ' AS ' in order to get only table name
			String[] tagNameArray = tagName.split(" AS ");

			String retrieveColumnListQuery = "SELECT * FROM ReportConfig WHERE tableName = '" + tagNameArray[0]
					+ "' AND reportFlag = 1";

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveColumnListQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();

				check = 1;

				object.put("check", check);

				object.put("columns", keyValue + "=" + tagNameArray[1] + "." + resultSet.getString("columnName"));
				object.put("columnType", resultSet.getString("dataType"));
				object.put("fieldName", resultSet.getString("fieldName"));
				object.put("tableCheck", "nonVisit");

				array1.add(object);

				values.put("Release1", array1);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {

			StringWriter stringWriter = new StringWriter();

			exception.printStackTrace(new PrintWriter(stringWriter));

			// calling exception mail send method to send mail about the exception details
			// on info@kovidbioanalytics.com
			EmailUtil emailUtil1 = new EmailUtil();
			emailUtil1.sendExceptionInfoEmail(stringWriter.toString(), "Retrieve Visit Related Table List: Exception");

			exception.printStackTrace();

			check = 0;

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "visit");

			array.add(object);

			values.put("Release", array);

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "visit");

			array1.add(object);

			values.put("Release1", array1);
		}

		return values;

	}
	
	/**
	 * 
	 * @param practiceName
	 * @param clinicName
	 * @param tagName
	 * @return
	 */
	public JSONObject retrieveVisitRelatedTableList(String practiceName, String clinicName, String tagName) {

		ReportDAOInf reportDAOInf = new ReportDAOImpl();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		JSONArray array1 = new JSONArray();

		JSONObject object = null;

		int check = 0;

		String keyValue = "";

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reportXMLFile);

			doc.getDocumentElement().normalize();

			NodeList practiceList = doc.getElementsByTagName("Practice");

			for (int j = 0; j < practiceList.getLength(); j++) {
				Element practiceElement = (Element) practiceList.item(j);
				if (practiceElement.hasAttribute("name") && practiceElement.getAttribute("name").equals(practiceName)) {

					NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

					for (int i = 0; i < clinicList.getLength(); i++) {
						Element clinicElement = (Element) clinicList.item(i);

						if (clinicElement.hasAttribute("name")
								&& clinicElement.getAttribute("name").equals(clinicName)) {
							NodeList patientTableList = clinicElement.getElementsByTagName("Table");

							for (int k = 0; k < patientTableList.getLength(); k++) {
								Element patientTableElement = (Element) patientTableList.item(k);

								if (patientTableElement.hasAttribute("name")
										&& patientTableElement.getAttribute("name").equals(tagName)) {
									NodeList tableList = patientTableElement.getElementsByTagName("Table");

									for (int l = 0; l < tableList.getLength(); l++) {
										Node tableNode = tableList.item(l);

										Element tableElement = (Element) tableNode;

										// String[] tagNameArr = patientTableElement.getAttribute("name").split(" AS ");

										if (tableElement.getAttribute("parent")
												.equals(patientTableElement.getAttribute("name"))) {

											object = new JSONObject();

											check = 1;

											String[] nameArr = tableElement.getAttribute("name").split(" AS ");

											keyValue = tableElement.getAttribute("key");

											object.put("check", check);
											object.put("tableName",
													tableElement.getAttribute("name") + "=" + nameArr[0]);
											object.put("tableCheck", "visit");

											array.add(object);

											values.put("Release", array);

										}

									}
								}
							}
						}

					}
					break;

				}
			}

			if (check == 0) {

				object = new JSONObject();

				object.put("check", check);
				object.put("tableCheck", "visitEmpty");

				array.add(object);

				values.put("Release", array);

			}

			System.out.println("key value..." + keyValue);

			/*
			 * retrieving column list based on tagName and storing it with key value as
			 * Release1 in order to display column list for the selected tagName
			 */
			Connection connection = DAOConnection.getConnection();

			// Split tableName variable by ' AS ' in order to get only table name
			String[] tagNameArray = tagName.split(" AS ");

			String retrieveColumnListQuery = "DESC " + tagNameArray[0];

			PreparedStatement preparedStatement = connection.prepareStatement(retrieveColumnListQuery);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				object = new JSONObject();
				
				check = 1;
				
				object.put("check", check);

				object.put("columns", keyValue + "=" + resultSet.getString("Field"));
				object.put("columnType", resultSet.getString("Type"));
				object.put("tableCheck", "nonVisit");

				array1.add(object);

				values.put("Release1", array1);

			}

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception exception) {
			exception.printStackTrace();

			check = 0;

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "visit");

			array.add(object);

			values.put("Release", array);

			object = new JSONObject();

			object.put("check", check);
			object.put("tableCheck", "visit");

			array1.add(object);

			values.put("Release1", array1);
		}

		return values;

	}

	/**
	 * 
	 * @param tagName
	 * @param practiceName
	 * @param clinicName
	 * @return
	 */
	public String retrieveKeyAttribute(String tagName, String practiceName, String clinicName) {

		String key = "";

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reportXMLFile);

			doc.getDocumentElement().normalize();

			NodeList practiceList = doc.getElementsByTagName("Practice");

			for (int j = 0; j < practiceList.getLength(); j++) {
				Element practiceElement = (Element) practiceList.item(j);
				if (practiceElement.hasAttribute("name") && practiceElement.getAttribute("name").equals(practiceName)) {

					NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

					for (int i = 0; i < clinicList.getLength(); i++) {
						Element clinicElement = (Element) clinicList.item(i);

						if (clinicElement.hasAttribute("name")
								&& clinicElement.getAttribute("name").equals(clinicName)) {
							NodeList patientTableList = clinicElement.getElementsByTagName("Table");

							for (int k = 0; k < patientTableList.getLength(); k++) {
								Element patientTableElement = (Element) patientTableList.item(k);

								if (patientTableElement.hasAttribute("name")
										&& patientTableElement.getAttribute("name").equals(tagName)) {

									key = patientTableElement.getAttribute("key");
								}
							}
						}

					}
					break;

				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return key;
	}

	/**
	 * 
	 * @param fromQuery
	 * @param practiceName
	 * @param clinicName
	 * @return
	 */
	public String retrieveConditionByTaFromQuery(String fromQuery, String practiceName, String clinicName) {

		String whereFinal = "";

		String arr[] = fromQuery.split(", ");

		try {

			for (int r = 0; r < arr.length; r++) {
				String tagName = arr[r];

				String parent = "";

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(reportXMLFile);

				doc.getDocumentElement().normalize();

				NodeList practiceList = doc.getElementsByTagName("Practice");

				for (int j = 0; j < practiceList.getLength(); j++) {
					Element practiceElement = (Element) practiceList.item(j);
					if (practiceElement.hasAttribute("name")
							&& practiceElement.getAttribute("name").equals(practiceName)) {

						NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

						for (int i = 0; i < clinicList.getLength(); i++) {
							Element clinicElement = (Element) clinicList.item(i);

							if (clinicElement.hasAttribute("name")
									&& clinicElement.getAttribute("name").equals(clinicName)) {
								NodeList patientTableList = clinicElement.getElementsByTagName("Table");

								for (int k = 0; k < patientTableList.getLength(); k++) {
									Element patientTableElement = (Element) patientTableList.item(k);

									if (patientTableElement.hasAttribute("name")
											&& patientTableElement.getAttribute("name").equals(tagName)) {

										parent = patientTableElement.getAttribute("parent");

										String key = patientTableElement.getAttribute("key");

										if (patientTableElement.hasAttribute("parent")) {

											String[] parArr = parent.split(" AS ");

											String[] tagNameArr = tagName.split(" AS ");

											whereFinal += parArr[1] + ".id = " + tagNameArr[1] + "." + key + " AND ";

										}
									}
								}
							}

						}

					}
				}
				
				//System.out.println("where condition..."+whereFinal);

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return whereFinal;
	}

	/**
	 * 
	 * @param tagName
	 * @param practiceName
	 * @param clinicName
	 * @return
	 */
	public JSONObject verifyfKeyExists(String tagName, String practiceName, String clinicName) {

		JSONObject object = new JSONObject();

		JSONObject values = new JSONObject();

		JSONArray array = new JSONArray();

		int check = 0;

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reportXMLFile);

			doc.getDocumentElement().normalize();

			NodeList practiceList = doc.getElementsByTagName("Practice");

			for (int j = 0; j < practiceList.getLength(); j++) {
				Element practiceElement = (Element) practiceList.item(j);
				if (practiceElement.hasAttribute("name") && practiceElement.getAttribute("name").equals(practiceName)) {

					NodeList clinicList = practiceElement.getElementsByTagName("Clinic");

					for (int i = 0; i < clinicList.getLength(); i++) {
						Element clinicElement = (Element) clinicList.item(i);

						if (clinicElement.hasAttribute("name")
								&& clinicElement.getAttribute("name").equals(clinicName)) {
							NodeList patientTableList = clinicElement.getElementsByTagName("Table");

							for (int k = 0; k < patientTableList.getLength(); k++) {
								Element patientTableElement = (Element) patientTableList.item(k);

								// splitting tagName by = in order to get column name which is at 1st index
								System.out.println("tagName: " +tagName+"-"+patientTableElement.getAttribute("fkey"));
								String[] newArr = tagName.split("=");
								
								System.out.println("new arr.."+newArr);

								// System.out.println("tagName: " +
								// tagName+"-"+patientTableElement.getAttribute("fkey"));
								// Splitting newArr[1] by . in order to get only column name
								String[] columnArray = newArr[1].split("\\.");
								
								System.out.println("columnarr.."+columnArray);

								if (patientTableElement.getAttribute("fkey").equals(columnArray[1])) {

									check = 1;

									object.put("attributes", patientTableElement.getAttribute("name") + "="
											+ patientTableElement.getAttribute("cname"));

									object.put("check", check);

									array.add(object);

									values.put("Release", array);

								}
							}
						}

					}
					break;

				}
			}

			if (check == 0) {

				object.put("attributes", "");

				object.put("check", check);

				array.add(object);

				values.put("Release", array);

			}

		} catch (Exception exception) {

			
			exception.printStackTrace();

			check = 0;
		}

		return values;
	}

	public String getContextPath() {
		String contextPath = "";

		try {

			contextPath = request.getServletContext().getRealPath("/");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return contextPath;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getS3RDMLReportFilePath() {

		String bucketName = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketName = eElement.getElementsByTagName("S3-FILE-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketName;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getS3OutFilePath() {

		String outFilePath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					outFilePath = eElement.getElementsByTagName("S3OUT-FILE-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return outFilePath;
	}

	

	/**
	 * 
	 * @return
	 */
	public String getS3RDMLFilePath() {

		String bucketName = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketName = eElement.getElementsByTagName("S3-LOGO-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getS3BucketRegion() {

		String bucketRegion = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketRegion = eElement.getElementsByTagName("S3BUCKET-REGION").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketRegion;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getAccessKey() {

		String bucketRegion = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketRegion = eElement.getElementsByTagName("ACCESS-KEY").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketRegion;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSecreteKey() {

		String bucketRegion = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketRegion = eElement.getElementsByTagName("SECRET-KEY").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketRegion;
	}

	
	
	public String getPcpNDTLocalPath() {

		String pcpNDTLocalPath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					pcpNDTLocalPath = eElement.getElementsByTagName("PCP-NDT-LOCAL-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return pcpNDTLocalPath;
	}

}

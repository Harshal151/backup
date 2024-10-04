package com.edhanvantari.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigListenerUtil extends PathConfigListenerUtil {

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDBIP(String realPath) {

		String DBIP = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	 * @param filePath
	 * @return
	 */
	public String getDBPort(String realPath) {
		String DBPort = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	 * @param filePath
	 * @return
	 */
	public String getDBName(String realPath) {
		String DBName = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	 * @param filePath
	 * @return
	 */
	public String getDBUsername(String realPath) {
		String DBUsername = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	 * @param filePath
	 * @return
	 */
	public String getDBPassword(String realPath) {
		String DBPassword = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	 * @return
	 */
	public String getSMSUsername(String realPath) {

		String SMSUsername = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getSMSPassword(String realPath) {

		String SMSPassword = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getSMSURL(String realPath) {

		String SMSURL = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getSMSSenderID(String realPath) {

		String SMSSenderID = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	 * @return Version
	 */
	public String getVersion(String realPath) {
		String versionName = null;
		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("edhanvantari-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					versionName = eElement.getElementsByTagName("VERSION").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 
	 * @return
	 */
	public String getS3RDMLReportFilePath(String realPath) {

		String bucketName = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getS3OutFilePath(String realPath) {

		String outFilePath = null;

		String filePath1 = getConfigPath(realPath);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getS3RDMLFilePath(String realPath) {

		String bucketName = null;

		String filePath1 = getConfigPath(realPath);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getS3BucketRegion(String realPath) {

		String bucketRegion = null;

		String filePath1 = getConfigPath(realPath);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getAccessKey(String realPath) {

		String bucketRegion = null;

		String filePath1 = getConfigPath(realPath);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
	public String getSecreteKey(String realPath) {

		String bucketRegion = null;

		String filePath1 = getConfigPath(realPath);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator + "edhanvantari.xml"));

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
}

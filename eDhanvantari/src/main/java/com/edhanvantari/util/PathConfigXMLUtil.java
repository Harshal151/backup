package com.edhanvantari.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Properties;

public class PathConfigXMLUtil {
	static HttpServletRequest request = ServletActionContext.getRequest();

	static String configPath = null;

	/**
	 * 
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static String getConfigPath() {
		
		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");

		//System.out.println("11real path is ::::: " + realPath+File.separator);

		File configXMLFile = new File(realPath+File.separator + "configuration.xml");
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc
					.getElementsByTagName("edhanvantari-configuration");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					configPath = eElement
							.getElementsByTagName("config-file-path").item(0)
							.getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return configPath;
	}

}

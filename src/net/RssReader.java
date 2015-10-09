package net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RssReader {

	String url;
	public static void main(String[] args){
		String url = "http://www.cbc.ca/cmlink/rss-canada";
		if (args.length < 1){
				System.out.println("Usage: Reader <url>");
				System.exit(0);
		}
		RssReader reader = new RssReader(url);
		System.out.println("Reading news from:");
		System.out.println(url);
		System.out.println(reader.getNews());
		
	}
	public RssReader(String url){
		this.url = url;
	}
	
	public String getNews(){
		StringBuilder headlineSB = new StringBuilder();
		try {
			String newsURL = url;
			URL url = new URL(newsURL);
			InputStream is = url.openStream();
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			int s=0;
			byte[] a = new byte[512];
			while ( (s = is.read(a) ) != -1 ){
				arrayOutputStream.write(a, 0, s);
			}
			
			ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
			DocumentBuilderFactory documentFactory=DocumentBuilderFactory.newInstance();
			documentFactory.setValidating(false);
			DocumentBuilder docBuilder = documentFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(arrayInputStream);
			NodeList nList = doc.getElementsByTagName("item");
			List<String> headlines = new ArrayList<String>();
			for (int i=0; i< nList.getLength(); i++){
				Node n = nList.item(i);
				NodeList nList2= n.getChildNodes();
				for (int j=0; j< nList2.getLength(); j++){
					if ("title".equals(nList2.item(j).getNodeName())){
						headlines.add(nList2.item(j).getTextContent());
					}
				}
			}

			for (int i=1; i<= headlines.size(); i++){
				headlineSB.append(i + " : "+headlines.get(i-1));
				headlineSB.append("\n");
			}
			is.close();
			//throw new IOException();
			return headlineSB.toString();
		} catch (MalformedURLException e) {
			headlineSB.setLength(0);
			headlineSB.append("Error getting news");
			return headlineSB.toString();
		} catch (DOMException e) {
			headlineSB.setLength(0);
			headlineSB.append("Error getting news");
			return headlineSB.toString();
		} catch (IOException e) {
			headlineSB.setLength(0);
			headlineSB.append("Error getting news");
			return headlineSB.toString();
		} catch (ParserConfigurationException e) {
			headlineSB.setLength(0);
			headlineSB.append("Error getting news");
			return headlineSB.toString();
		} catch (SAXException e) {
			headlineSB.setLength(0);
			headlineSB.append("Error getting news");
			return headlineSB.toString();
		}finally{
		}
	}

}

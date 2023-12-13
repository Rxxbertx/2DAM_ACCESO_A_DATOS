package trabajando_con_xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class HandlerSAX extends DefaultHandler {

	public HandlerSAX() {	
		
		super();
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("<xml version=\"1.1\" charset=utf-8");
		System.out.println("<DOCUMENTO>");
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
	
		System.out.print("<"+qName+">");
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		
		System.out.printf(new String(ch,start,length));
		
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		System.out.print("</"+qName+">");
		
		
	}
	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

		System.out.println("</DOCUMENTO>");

	}
	
	

}

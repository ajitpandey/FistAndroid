package com.sunita.interviewpractice.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sunita.interviewpractice.vo.LandingVo;
import com.sunita.interviewpractice.vo.LandingVoList;

 
public class SAXLandingHandler extends DefaultHandler {
 
    private LandingVoList landinglist;
    private String tempVal;
    private LandingVo landingVo;
 
    public SAXLandingHandler() {
    	landinglist = new LandingVoList();
    }
 
    public LandingVoList getLandingVoList() {
        return landinglist;
    }
 
    // Event Handlers
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // reset
        tempVal = "";
        if (qName.equalsIgnoreCase("landing")) {
            // create a new instance of employee
        	landinglist = new LandingVoList();
        }else if (qName.equalsIgnoreCase("link")) {
            // create a new instance of employee
        	landingVo = new LandingVo();
        }
        
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("link")) {
            // add it to the list
            landinglist.AddVoList(landingVo);
        } else if (qName.equalsIgnoreCase("text")) {
        	landingVo.setText(tempVal);
        } else if (qName.equalsIgnoreCase("value")) {
        	landingVo.setValue(tempVal);
        } 
    }
}
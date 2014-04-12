package com.sunita.interviewpractice.util;

import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

import com.sunita.interviewpractice.vo.InterviewQuestAnsVoList;
import com.sunita.interviewpractice.vo.LandingVoList;
 
public class SAXXMLParser {
    public static InterviewQuestAnsVoList parse(InputStream is) {	
    	InterviewQuestAnsVoList interviewQuestAnsVoList = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            SAXXMLHandler saxHandler = new SAXXMLHandler();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(new InputSource(is));
            // get the `Employee list`
            interviewQuestAnsVoList = saxHandler.getInterviewQuestAnsVoList();
 
        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
            ex.printStackTrace();
        }
 
        // return Employee list
        return interviewQuestAnsVoList;
    }
    
    public static LandingVoList parseLanding(InputStream is) {	
    	LandingVoList landinglist = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            // create a SAXXMLHandler
            SAXLandingHandler saxHandler = new SAXLandingHandler();
            // store handler in XMLReader
            xmlReader.setContentHandler(saxHandler);
            // the process starts
            xmlReader.parse(new InputSource(is));
            // get the `Employee list`
            landinglist = saxHandler.getLandingVoList();
 
        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
            ex.printStackTrace();
        }
 
        // return Employee list
        return landinglist;
    }
    
    
    
    
    
    
    
}

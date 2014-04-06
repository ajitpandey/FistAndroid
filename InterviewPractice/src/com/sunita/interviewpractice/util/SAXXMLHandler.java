package com.sunita.interviewpractice.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sunita.interviewpractice.vo.InterviewQuestAnsVo;
import com.sunita.interviewpractice.vo.InterviewQuestAnsVoList;

 
public class SAXXMLHandler extends DefaultHandler {
 
    private InterviewQuestAnsVoList interviewQuestAnsVoList;
    private String tempVal;
    private InterviewQuestAnsVo interviewQuestAnsVo;
 
    public SAXXMLHandler() {
    	interviewQuestAnsVoList = new InterviewQuestAnsVoList();
    }
 
    public InterviewQuestAnsVoList getInterviewQuestAnsVoList() {
        return interviewQuestAnsVoList;
    }
 
    // Event Handlers
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // reset
        tempVal = "";
        if (qName.equalsIgnoreCase("interview")) {
            // create a new instance of employee
        	interviewQuestAnsVoList = new InterviewQuestAnsVoList();
        }else if (qName.equalsIgnoreCase("qa")) {
            // create a new instance of employee
        	interviewQuestAnsVo = new InterviewQuestAnsVo();
        }
        
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("qa")) {
            // add it to the list
            interviewQuestAnsVoList.addInterQAVo(interviewQuestAnsVo);
        } else if (qName.equalsIgnoreCase("q")) {
        	interviewQuestAnsVo.setQuestion(tempVal);
        } else if (qName.equalsIgnoreCase("a")) {
        	interviewQuestAnsVo.addAnswer(tempVal);
        } else if (qName.equalsIgnoreCase("topic")) {
        	interviewQuestAnsVoList.setTopic(tempVal);
        }
    }
}
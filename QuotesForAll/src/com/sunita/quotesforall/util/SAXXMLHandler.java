package com.sunita.quotesforall.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sunita.quotesforall.vo.QuoteVo;
import com.sunita.quotesforall.vo.QuotesVo;

 
public class SAXXMLHandler extends DefaultHandler {
 
    private QuotesVo quotesVo;
    private String tempVal;
    private QuoteVo tempQuoteVo;
 
    public SAXXMLHandler() {
    	quotesVo = new QuotesVo();
    }
 
    public QuotesVo getQuotesVo() {
        return quotesVo;
    }
 
    // Event Handlers
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        // reset
        tempVal = "";
        if (qName.equalsIgnoreCase("quotes")) {
            // create a new instance of employee
        	quotesVo = new QuotesVo();
        }else if (qName.equalsIgnoreCase("quote")) {
            // create a new instance of employee
        	tempQuoteVo = new QuoteVo();
        }
        
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("quote")) {
            // add it to the list
            quotesVo.addQuoteVo(tempQuoteVo);
        } else if (qName.equalsIgnoreCase("text")) {
        	tempQuoteVo.setText(tempVal);
        } else if (qName.equalsIgnoreCase("authby")) {
        	tempQuoteVo.setAuthby(tempVal);
        } else if (qName.equalsIgnoreCase("topic")) {
        	quotesVo.setTopic(tempVal);
        }
    }
}
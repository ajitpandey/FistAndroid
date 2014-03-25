package com.sunita.quotesforall.util;

import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

import com.sunita.quotesforall.vo.QuotesVo;
 
public class SAXXMLParser {
    public static QuotesVo parse(InputStream is) {
    	QuotesVo quotesVo = null;
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
            quotesVo = saxHandler.getQuotesVo();
 
        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
        }
 
        // return Employee list
        return quotesVo;
    }
}

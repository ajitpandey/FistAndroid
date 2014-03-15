package com.sunita.lifeinuktest.file.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import com.sunita.lifeinuktest.MainActivity;
import com.sunita.lifeinuktest.util.PrintSysout;
import com.sunita.lifeinuktest.vo.QuestionAnswerVo;
import com.sunita.lifeinuktest.vo.TestResult;

public class CreateXMLFile {
	
	public boolean isFreeSpace(MainActivity mainActivity){
		return true;
	}
	
	public void deleteFile(MainActivity mainActivity, String fileName){
		mainActivity.deleteFile(fileName);
	}
	
	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public static List<String> ListDir(File f){
		List<String> fileList = new ArrayList<String>();
		File[] files = f.listFiles();
		
		for (File file : files){
			PrintSysout.printSysout("File Name : " + file.getName());
			fileList.add(file.getName());
		}

		return fileList;
	}
	
	
	public static void createFile(MainActivity mainActivity, String fileName, String xmlData) throws IllegalArgumentException, IllegalStateException, IOException {
		
		
		
		String fileNameToCreate = fileNameToCreate(mainActivity, fileName);
		
		PrintSysout.printSysout("-----------------WriteFile : " + fileNameToCreate);
		FileOutputStream fos;
		fos = mainActivity.openFileOutput(fileNameToCreate, Context.MODE_PRIVATE);

		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(fos, "UTF-8");
		serializer.startDocument(null, Boolean.valueOf(true));
		serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

		serializer.startTag(null, "root");

		//for (int j = 0; j < 3; j++) {

			serializer.startTag(null, "record");

			serializer.text(xmlData);
			PrintSysout.printSysout(xmlData);

			serializer.endTag(null, "record");
		//}
		serializer.endDocument();

		serializer.flush();

		fos.close();
	}

	private static String fileNameToCreate(Activity mainActivity, String fileName) {
		String newFileName = fileName;
		boolean trynext = true;
		int Count = 1;
		while(trynext){
			newFileName = Count + "_" +fileName;
			File file = mainActivity.getFileStreamPath(newFileName);
			if(file.exists()){
				Count++;
			}else{
				trynext = false;	
			}
		}
		
		return newFileName;
	}

	public static List<String> readfile(Activity activity, String fileName) throws IOException, ParserConfigurationException, SAXException, DOMException, XmlPullParserException {
		List<String> strList =new ArrayList<String>();
		String newFileName = fileName;
		boolean trynext = true;
		int Count = 1;
		while(trynext){
			newFileName = Count + "_" +fileName;
			File file = activity.getFileStreamPath(newFileName);
			if(file.exists()){
				Count++;
				strList.add(readfileEach(activity, newFileName));
			}else{
				trynext = false;	
			}
		}
		return strList;
	}
	
	public static String readfileEach(Activity activity, String filename) throws IOException, ParserConfigurationException, SAXException, DOMException, XmlPullParserException {
		PrintSysout.printSysout("-----------------ReadFile : " + filename);
		//File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		//PrintSysout.printSysout("##root : " + root.getAbsolutePath());
		//PrintSysout.printSysout("##root : " + root.getName());
		//ListDir(root);
		
		FileInputStream fis = null;
		InputStreamReader isr = null;

		fis = activity.openFileInput(filename);
		isr = new InputStreamReader(fis);
		char[] inputBuffer = new char[fis.available()];
		isr.read(inputBuffer);
		String data = new String(inputBuffer);
		isr.close();
		fis.close();

		/*
		 * converting the String data to XML format so that the DOM parser
		 * understand it as an XML input.
		 */
		InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));

		//ArrayList<QuizLevel> xmlDataList = new ArrayList<QuizLevel>();

		//QuizLevel xmlDataObj;
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		NodeList items = null;
		Document dom;

		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		dom = db.parse(is);
		// Normalise the document
		dom.getDocumentElement().normalize();

		PrintSysout.printSysout(dom.toString());
		items = dom.getElementsByTagName("record");

		List<String> strList = new ArrayList<String>();

		for (int i = 0; i < items.getLength(); i++) {
			Node item = items.item(i);
			PrintSysout.printSysout("Content : " + item.getTextContent());
			strList.add(item.getTextContent());
		}
		
		return strList.get(0);
	}
	
	public static List<TestResult>  parseStringXML(String textContent) throws XmlPullParserException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput( new StringReader ( textContent ) );
        return parseTestResultXML(xpp);
	}

	public static List<QuestionAnswerVo> getQuestionAnswerList(MainActivity mainActivity, String assetFileName) {
    	List<QuestionAnswerVo> voList = null;
    	XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			    //InputStream in_s = getApplicationContext().getAssets().open(level);
			InputStream in_s = mainActivity.getAssets().open(assetFileName);
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);

	            //PrintSysout.printSysout("step - 1");
	            voList = parseXML(parser);
	            //PrintSysout.printSysout("step - 2");
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return voList;
	}
    
	
	private static List<TestResult> parseTestResultXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		List<TestResult> TestResultVoList = null;
        int eventType = parser.getEventType();
        TestResult testResultVo = null;
        QuestionAnswerVo questionAnswerVo = null;
        //PrintSysout.printSysout("eventType - " + eventType);
        
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	TestResultVoList = new ArrayList<TestResult>();
                	//PrintSysout.printSysout("switch - 1");
                    break;
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	//PrintSysout.printSysout("switch - 2 - " + name);
                	if (name.equals("trslt")){
                    	testResultVo = new TestResult();
                    	//PrintSysout.printSysout("questionAnswerVo : " + questionAnswerVo);
                    }else if (testResultVo != null){
                    	if (name.equals("dt")){
                        	testResultVo.setDateTime(parser.nextText());
                        	//PrintSysout.printSysout("questionAnswerVo : " + questionAnswerVo);
                        } else if (name.equals("score")){
                        	testResultVo.setScore(parser.nextText());
                        	//PrintSysout.printSysout("questionAnswerVo : " + questionAnswerVo);
                        } else if (name.equals("quiz")){
                        	questionAnswerVo = new QuestionAnswerVo();
                        	testResultVo.addQaVo(questionAnswerVo);
                        	//PrintSysout.printSysout("questionAnswerVo : " + questionAnswerVo);
                        } else if (testResultVo != null){
                            if (name.equals("id")){
                            	questionAnswerVo.id = parser.nextText();
                            	//PrintSysout.printSysout("questionAnswerVo.question - " + questionAnswerVo.question);
                            } else if (name.equals("type")){
                            	questionAnswerVo.type = parser.nextText();
                            } else if (name.equals("selans")){
                            	questionAnswerVo.selectedAnswer = parser.nextText();
                            }
                        }
                    }
                    
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                	//PrintSysout.printSysout("switch - 3 - " + name);
                    if (name.equalsIgnoreCase("trslt") && questionAnswerVo != null){
                    	TestResultVoList.add(testResultVo);
                    } 
            }
            eventType = parser.next();
        }
    	//PrintSysout.printSysout("switch - 4 - " + voList.size());
        return TestResultVoList;
	}
	
    private static List<QuestionAnswerVo> parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		List<QuestionAnswerVo> voList = null;
        int eventType = parser.getEventType();
        QuestionAnswerVo questionAnswerVo = null;
        //PrintSysout.printSysout("eventType - " + eventType);
        
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	voList = new ArrayList<QuestionAnswerVo>();
                	//PrintSysout.printSysout("switch - 1");
                    break;
                case XmlPullParser.START_TAG:
                	name = parser.getName();
                	//PrintSysout.printSysout("switch - 2 - " + name);
                    if (name.equals("quiz")){
                    	questionAnswerVo = new QuestionAnswerVo();
                    	//PrintSysout.printSysout("questionAnswerVo : " + questionAnswerVo);
                    } else if (questionAnswerVo != null){
                        if (name.equals("question")){
                        	questionAnswerVo.question = parser.nextText();
                        	//PrintSysout.printSysout("questionAnswerVo.question - " + questionAnswerVo.question);
                        } else if (name.equals("answer")){
                        	questionAnswerVo.answer = parser.nextText();
                        } else if (name.equals("option1")){
                        	questionAnswerVo.option1 = parser.nextText();
                        } else if (name.equals("option2")){
                        	questionAnswerVo.option2 = parser.nextText();
                        } else if (name.equals("option3")){
                        	questionAnswerVo.option3 = parser.nextText();
                        } else if (name.equals("option4")){
                        	questionAnswerVo.option4 = parser.nextText();
                        } else if (name.equals("explanation")){
                        	questionAnswerVo.explanation = parser.nextText();
                        } else if (name.equals("type")){
                        	questionAnswerVo.type = parser.nextText();
                        } else if (name.equals("id")){
                        	questionAnswerVo.id = parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                	//PrintSysout.printSysout("switch - 3 - " + name);
                    if (name.equalsIgnoreCase("quiz") && questionAnswerVo != null){
                    	voList.add(questionAnswerVo);
                    } 
            }
            eventType = parser.next();
        }
    	//PrintSysout.printSysout("switch - 4 - " + voList.size());
        return voList;
	}
}

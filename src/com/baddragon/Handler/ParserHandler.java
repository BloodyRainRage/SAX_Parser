package com.baddragon.Handler;

import org.xml.sax.helpers.DefaultHandler;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ParserHandler extends DefaultHandler {

    private static List<String> tags  =  new ArrayList<>();
    private static Map<String,String> attributes = new HashMap<>();
    private String propertyString = null;
    private String attributeString;
    private boolean flag; //flag for handling start and end of the element
    private boolean attr_flag; //flag for handling attributes
    private FileWriter fileWriter;

    @Override
    public void startDocument() throws SAXException {
        try {
            fileWriter = new FileWriter("parsed.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void endDocument() throws SAXException {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        int length = attributes.getLength();

        if (length > 0) {
            for (int i=0; i<length; i++) {
                String name = attributes.getQName(i);
                String value = attributes.getValue(i);
                ParserHandler.attributes.put(name, value);
            }
            attr_flag = true;
        }
        flag = true;
        if(qName == null) { return;}
        tags.add(qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        flag = false;
        int index = tags.lastIndexOf(qName);
        tags.remove(index);
        propertyString = null;
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String text = new String(ch, start, length);
        if (text.contains("<")) return;
        propertyString = tags.get(0);
        int len = tags.size();
        for (int i = 1; i < len; i++) { ///forming a properties string of values of the text
            propertyString +=  "." + tags.get(i);
        }
        attributeString = propertyString;
        propertyString += "="+ text;
        try {
            if(flag && !text.endsWith(" ")) {
                fileWriter.append(propertyString + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(attr_flag) { ///forming a properties string of attributes values
            attributes.forEach((key, value)-> attributeString = attributeString + "." + key + "=" + value + "\n");
            try {
                fileWriter.append(attributeString);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            attr_flag = false;
            attributes.clear();
        }
    }
}
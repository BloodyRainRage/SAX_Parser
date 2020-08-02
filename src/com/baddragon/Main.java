package com.baddragon;

import com.baddragon.Handler.ParserHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            File input = new File("resources/task2.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance(); //creating a fabric
            SAXParser parser = factory.newSAXParser();  //creating an instance of SAXParser
            ParserHandler handler = new ParserHandler(); //creating an instance of handler
            parser.parse(input, handler); //starting parsing
        } catch (SAXException sax_e){
            sax_e.printStackTrace();
        } catch (ParserConfigurationException pce){
            pce.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}

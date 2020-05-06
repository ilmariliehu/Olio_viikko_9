package com.example.week9_1;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.IDN;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static java.lang.Integer.parseInt;

public class readXML {
    private static readXML XML = new readXML();
    public static readXML getInstance(){
        return XML;
    }

    ArrayList<Theatres> Theatrepointteri = new ArrayList<Theatres>();
    ArrayList theatrelist = new ArrayList();


    public ArrayList teatterit() {
        String id;
        String teatteri;

        //DocumentBuilder builder;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlstring = "https://www.finnkino.fi/xml/TheatreAreas/";

            Document doc = builder.parse(urlstring);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                Theatres teatteri2 = new Theatres();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    id = element.getElementsByTagName("ID").item(0).getTextContent();
                    teatteri = element.getElementsByTagName("Name").item(0).getTextContent();

                    teatteri2.setInfo(teatteri,id);
                    Theatrepointteri.add(teatteri2);
                    theatrelist.add(teatteri);


                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("########DONE########");
            return theatrelist;
        }
        //return theatrelist;
    }

    public void schedule(String Date, TextView print, String choice, String start, String end) {
       // int wanted = 0;
        String result = "";

       // ArrayList printed = new ArrayList();
        String id = "";
        for (int i = 1; i < Theatrepointteri.size(); i++) {

            if (Theatrepointteri.get(i).getName().equals(choice)) {
                id = Theatrepointteri.get(i).getID();
            }
        }

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            String osoite = "https://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + Date;
            //System.out.println(osoite);

            Document dok = builder.parse(osoite);
            dok.getDocumentElement().normalize();



            NodeList nList = dok.getDocumentElement().getElementsByTagName("Show");

            for (int i = 0;i< nList.getLength(); i++){
                Node node = nList.item(i);
                Element element = (Element) node;
                if (node.getNodeType() == node.ELEMENT_NODE) {

                    String teatteri = element.getElementsByTagName("Theatre").item(0).getTextContent();
                    String elokuva = element.getElementsByTagName("Title").item(0).getTextContent();
                    String aika = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();

                    if (start == null && end == null) {
                        result = result + teatteri + "elokuva: " + elokuva + " Klo: " + aika;
                        result = result + "\n\n";
                    } else {

                        String[] taAika = start.split(":");
                        int taAika1 = parseInt(taAika[0]);
                        //int taAika2 = parseInt(taAika[1]);

                        String[] tlAika = end.split(":");
                        int tlAika1 = parseInt(tlAika[0]);
                        //int tlAika2 = parseInt(tlAika[1]);


                        String[] anAika = aika.split("T");
                        String anAika1 = (anAika[1]);

                        String[] anAika2 = anAika1.trim().split(":");
                        String anAika3 = (anAika2[0]);
                        String anAika4 = (anAika2[1]);

                        int vertaaAika1 = parseInt(anAika3);
                        //int vertaaAika2 = parseInt(anAika4);

                        System.out.println(vertaaAika1);

                        System.out.println(taAika1 + "   " + tlAika1);

                        if (vertaaAika1 > taAika1 && vertaaAika1 < tlAika1) {

                            //System.out.println(teatteri + elokuva + " Klo: " + aika);
                            result = result + teatteri + "elokuva: " + elokuva + " Klo: " + aika;
                            result = result + "\n\n";
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            print.setMovementMethod(new ScrollingMovementMethod());
            print.setText(result);
        }



    }
}

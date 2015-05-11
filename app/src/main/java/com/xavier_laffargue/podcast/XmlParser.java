package com.xavier_laffargue.podcast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {

    BO_Podcast podcast;
    ArrayList<BO_Show> shows;


    public XmlParser() {

        podcast = new BO_Podcast();
        shows = new ArrayList<BO_Show>();


        /*
         * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
         */
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            /*
             * Etape 2 : création d'un parseur
             */
            final DocumentBuilder builder = factory.newDocumentBuilder();

	    /*
	     * Etape 3 : création d'un Document
	     */
            final Document document= builder.parse(new File("RMCInfochannel54.xml"));

            //Affiche du prologue
            System.out.println("*************PROLOGUE************");

	    /*
	     * Etape 4 : récupération de l'Element racine
	     */
            final Element racine = document.getDocumentElement();

            //Affichage de l'élément racine
            System.out.println("\n*************RACINE************");
            System.out.println(racine.getNodeName());

	    /*
	     * Etape 5 : récupération des personnes
	     */
            final NodeList racineNoeuds = racine.getChildNodes();
            final int nbRacineNoeuds = racineNoeuds.getLength();

            for (int i = 0; i<nbRacineNoeuds; i++) {
                if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element channel = (Element) racineNoeuds.item(i);


                    System.out.println("\n*************INFO GENERALE************");

                    final Element nom = (Element) channel.getElementsByTagName("title").item(0);
                    final Element link = (Element) channel.getElementsByTagName("link").item(0);
                    final Element description = (Element) channel.getElementsByTagName("description").item(0);
                    //final Element image = (Element) channel.getElementsByTagName("image").item(0);

                    podcast.setNom(nom.getTextContent());
                    podcast.setDescription(description.getTextContent());

                    System.out.println("title : " + nom.getTextContent());
                    System.out.println("link : " + link.getTextContent());
                    System.out.println("description : " + description.getTextContent());



                    final NodeList image = channel.getElementsByTagName("image");
                    final int nbImgElements = image.getLength();

                    for(int j = 0; j<nbImgElements; j++) {
                        final Element img = (Element) image.item(j);
                        podcast.setUrlImage(img.getElementsByTagName("url").item(0).getTextContent());
                    }




		    /*
		     * Etape 7 : récupération des numéros de téléphone
		     */
                    final NodeList item = channel.getElementsByTagName("item");
                    final int nbTelephonesElements = item.getLength();

                    for(int j = 0; j<nbTelephonesElements; j++) {

                        BO_Show unShow = new BO_Show();


                        System.out.println("\n*************SHOW************");

                        final Element show = (Element) item.item(j);


                        final Element title = (Element) show.getElementsByTagName("title").item(0);
                        final Element description_show = (Element) show.getElementsByTagName("description").item(0);
                        final Element enclosure = (Element) show.getElementsByTagName("enclosure").item(0);
                        final Element subtitle = (Element) show.getElementsByTagName("itunes:subtitle").item(0);


                        unShow.setDescription(subtitle.getTextContent());
                        unShow.setTitle(title.getTextContent());
                        unShow.setMp3(enclosure.getAttribute("url"));
                        /* unShow.setDatePublication();

                        System.out.println("title : " + title.getTextContent());
                        System.out.println("description_show : " + description_show.getTextContent());
                        System.out.println("enclosure : " + link.getTextContent());
                        System.out.println("subtitle : " + subtitle.getTextContent());
                        System.out.println("audio url : " + enclosure.getAttribute("url"));
                        */
                        shows.add(unShow);

                    }
                }
            }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
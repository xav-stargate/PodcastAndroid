package com.xavier_laffargue.podcast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParser {



    private BO_Podcast podcast;
    private ArrayList<BO_Show> shows;


    public BO_Podcast getPodcast() {
        return podcast;
    }

    public void setPodcast(BO_Podcast podcast) {
        this.podcast = podcast;
    }




    public XmlParser(URL url) {

        podcast = new BO_Podcast();
        shows = new ArrayList<>();


        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            final DocumentBuilder builder = factory.newDocumentBuilder();


            //final Document document= builder.parse(new File("RMCInfochannel54.xml"));
            final Document document= builder.parse(new InputSource(url.openStream()));

            final Element racine = document.getDocumentElement();

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





                    final NodeList image = channel.getElementsByTagName("image");
                    final int nbImgElements = image.getLength();

                    for(int j = 0; j<nbImgElements; j++) {
                        final Element img = (Element) image.item(j);
                        podcast.setUrlImage(img.getElementsByTagName("url").item(0).getTextContent());
                    }


                    final NodeList item = channel.getElementsByTagName("item");
                    final int nbTelephonesElements = item.getLength();



                    for(int j = 0; j<nbTelephonesElements; j++) {

                        BO_Show unShow = new BO_Show();


                        System.out.println("\n*************SHOW************");

                        final Element show = (Element) item.item(j);


                        final Element title = (Element) show.getElementsByTagName("title").item(0);
                        //final Element description_show = (Element) show.getElementsByTagName("description").item(0);
                        final Element enclosure = (Element) show.getElementsByTagName("enclosure").item(0);
                        final Element subtitle = (Element) show.getElementsByTagName("itunes:subtitle").item(0);
                        final Element pubDate = (Element) show.getElementsByTagName("pubDate").item(0);

                        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

                        try {
                            Date date = formatter.parse(pubDate.getTextContent());



                            unShow.setDatePublication(date.getTime());
                        } catch (ParseException ex) {

                        }





                        unShow.setDescription(subtitle.getTextContent());
                        unShow.setTitle(title.getTextContent());
                        unShow.setMp3(enclosure.getAttribute("url"));



                        shows.add(unShow);


                    }


                    podcast.setShows(shows);
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
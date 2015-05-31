package com.xavier_laffargue.podcast;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier on 25/04/2015.
 * Business Object : Podcast
 */
public class BO_Podcast {
    private long id;
    private String nom;
    private String urlImage;




    private String urlXML;
    private byte[] image;
    private String description;
    private ArrayList<BO_Show> shows;

    public BO_Podcast() {
    }

    public BO_Podcast(String _nom, String _description) {
        this.nom = _nom;
        this.description = _description;
    }

    public long getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }


    public ArrayList<BO_Show> getShows() {
        return shows;
    }


    public void setShows(ArrayList<BO_Show> shows) {
        this.shows=shows;
    }


      public String getUrlXML() {
          return urlXML;
      }

      public void setUrlXML(String urlXML) {
         this.urlXML = urlXML;
      }
}
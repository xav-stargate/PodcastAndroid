package com.xavier_laffargue.podcast;

/**
 * Created by Xavier on 25/04/2015.
 */
public class Podcast
{
    private long id;
    private String nom;
    private byte[] image;
    private String description;


    public Podcast() { }

    public Podcast(String _nom, String _description) { this.nom = _nom; this.description = _description; }

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
}

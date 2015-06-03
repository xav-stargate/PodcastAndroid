package com.xavier_laffargue.podcast;

import java.util.ArrayList;

/**
 * Created by Xavier on 28/04/2015.
 * Business Object : Show
 *
 */
public class BO_Show
{

    private long idShow;
    private long idPodcast;
    private String title;
    private String link;
    private String description;
    private String author;
    private String category;
    private String mp3;
    private Long datePublication;
    private String subtitle;
    private String duration;

    public boolean existe(ArrayList<BO_Show> shows)
    {
        for (final BO_Show show : shows)
        {
            if(show.getMp3().equals(this.getMp3()))
                return true;
        }

        return false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Long datePublication) {
        this.datePublication = datePublication;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIdShow() {
        return idShow;
    }

    public void setIdShow(long idShow) {
        this.idShow = idShow;
    }

    public long getIdPodcast() {
        return idPodcast;
    }

    public void setIdPodcast(long idPodcast) {
        this.idPodcast = idPodcast;
    }
}

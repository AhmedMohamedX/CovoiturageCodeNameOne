/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author AH Info
 */
public class Annonce {
    private trajet trajet_id;
    private User membre_id;
    private int id;
    private Date date;
    private double prix;
    private String deviation;
    private Date date_depart;
    private String commentaire;
    private int nb_places;
    private int rating;
    private int nbrrate;

    public trajet getTrajet_id() {
        return trajet_id;
    }

    public void setTrajet_id(trajet trajet_id) {
        this.trajet_id = trajet_id;
    }
    
    
    public User getMembre_id() {
        return membre_id;
    }

    public void setMembre_id(User membre_id) {
        this.membre_id = membre_id;
    }



    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public Date getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(Date date_depart) {
        this.date_depart = date_depart;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNbrrate() {
        return nbrrate;
    }

    public void setNbrrate(int nbrrate) {
        this.nbrrate = nbrrate;
    }

    public Annonce(Date date, double prix, String deviation, Date date_depart, String commentaire, int nb_places) {
        this.date = date;
        this.prix = prix;
        this.deviation = deviation;
        this.date_depart = date_depart;
        this.commentaire = commentaire;
        this.nb_places = nb_places;
    }

    public Annonce(User membre_id, Date date, double prix, String deviation, Date date_depart, String commentaire, int nb_places) {
        this.membre_id = membre_id;
        this.date = date;
        this.prix = prix;
        this.deviation = deviation;
        this.date_depart = date_depart;
        this.commentaire = commentaire;
        this.nb_places = nb_places;
    }

    public Annonce(trajet trajet_id, User membre_id, Date date, double prix, String deviation, Date date_depart, String commentaire, int nb_places) {
        this.trajet_id = trajet_id;
        this.membre_id = membre_id;
        this.date = date;
        this.prix = prix;
        this.deviation = deviation;
        this.date_depart = date_depart;
        this.commentaire = commentaire;
        this.nb_places = nb_places;
    }

  



    public Annonce() {
    }

    @Override
    public String toString() {
        return "Annonce{" + "id=" + id + ", date=" + date + ", prix=" + prix + ", deviation=" + deviation + ", date_depart=" + date_depart + ", commentaire=" + commentaire + ", nb_places=" + nb_places + ", rating=" + rating + ", nbrrate=" + nbrrate + '}';
    }

 
    
    
}

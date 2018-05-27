/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp;

import java.util.Date;
import java.text.DateFormat;

/**
 *
 * @author user
 */
public class reclamations {
    private int id ;
    private int membre_id;
    private User membre;
   
    private String sujet;
     private Date date;
    private String message;
    private String reponse;
    private int vu;
    private User targetMember = new User();

    public reclamations() {
    }

    public reclamations(int id, int membre_id, String sujet, Date date, String message, String reponse, int vu) {
        this.id = id;
        this.membre_id = membre_id;
        this.sujet = sujet;
        this.date = date;
        this.message = message;
        this.reponse = reponse;
        this.vu = vu;
    }

    public reclamations(int membre_id, String sujet, Date date, String message, String reponse) {
        this.membre_id = membre_id;
        this.sujet = sujet;
        this.date = date;
        this.message = message;
        this.reponse = reponse;
    }

    public reclamations(int id, int membre_id, String sujet, Date date, String message, String reponse) {
        this.id = id;
        this.membre_id = membre_id;
        this.sujet = sujet;
        this.date = date;
        this.message = message;
        this.reponse = reponse;
    }

    public User getMembre() {
        return membre;
    }

    public void setMembre(User membre) {
        this.membre = membre;
    }
    
    public User getTargetMember() {
        return targetMember;
    }

    public void setTargetMember(User targetMember) {
        this.targetMember = targetMember;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembre_id() {
        return membre_id;
    }

    public void setMembre_id(int membre_id) {
        this.membre_id = membre_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public int getVu() {
        return vu;
    }

    public void setVu(int vu) {
        this.vu = vu;
    }

    @Override
    public String toString() {
        return "reclamations{" + "id=" + id + ", membre_id=" + membre_id + ", sujet=" + sujet + ", date=" + date + ", message=" + message + ", reponse=" + reponse + ", vu=" + vu + ", targetMember=" + targetMember + '}';
    }
    
   
    

   

   
}

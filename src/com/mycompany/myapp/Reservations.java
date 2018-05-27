/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import java.util.Date;




/**
 *
 * @author Hard-System-Info
 */
public class Reservations {
    private int id;
    private int  annonce;
    private String chauffeur;
    private String passager1;
    private int passager2;
    private int passager3;
    private int passager4;
    private String DateReservation;
    private int nbplace;

    public Reservations(int annonce, String chauffeur, String passager1, int passager2, int passager3, int passager4, String DateReservation) {
        this.annonce = annonce;
        this.chauffeur = chauffeur;
        this.passager1 = passager1;
        this.passager2 = passager2;
        this.passager3 = passager3;
        this.passager4 = passager4;
        this.DateReservation = DateReservation;
    }

    public Reservations() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnonce() {
        return annonce;
    }

    public void setAnnonce(int annonce) {
        this.annonce = annonce;
    }

    public String getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(String chauffeur) {
        this.chauffeur = chauffeur;
    }

    public String getPassager1() {
        return passager1;
    }

    public void setPassager1(String passager1) {
        this.passager1 = passager1;
    }

    public int getPassager2() {
        return passager2;
    }

    public void setPassager2(int passager2) {
        this.passager2 = passager2;
    }

    public int getPassager3() {
        return passager3;
    }

    public void setPassager3(int passager3) {
        this.passager3 = passager3;
    }

    public int getPassager4() {
        return passager4;
    }

    public void setPassager4(int passager4) {
        this.passager4 = passager4;
    }

    public String getDateReservation() {
        return DateReservation;
    }

    public void setDateReservation(String DateReservation) {
        this.DateReservation = DateReservation;
    }



    public int getNbplace() {
        return nbplace;
    }

    public void setNbplace(int nbplace) {
        this.nbplace = nbplace;
    }
    
    
    
    
}

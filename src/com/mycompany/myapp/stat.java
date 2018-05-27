/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

/**
 *
 * @author Hard-System-Info
 */
public class stat {
     private String DateReservation;
     private Integer count;

    public stat(String DateReservation, Integer count) {
        this.DateReservation = DateReservation;
        this.count = count;
    }

    public stat() {
    }

    public String getDateReservation() {
        return DateReservation;
    }

    public void setDateReservation(String DateReservation) {
        this.DateReservation = DateReservation;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
     
    
}

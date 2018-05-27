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
public class temoignage {
      private int id;
      private User user;
      private String message;
      private String  date_tem;
      private int iduser ;

    public temoignage( ) {
       
     
      
      
    
    
}
    

    public void setId(int id) {
        this.id = id;
    }

    

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate_tem(String date_tem) {
        this.date_tem = date_tem;
    }

    public int getId() {
        return id;
    }

  

    public String getMessage() {
        return message;
    }

    public String getDate_tem() {
        return date_tem;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

   
  
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "temoignage{" + "id=" + id + ", user=" + user + ", message=" + message + ", date_tem=" + date_tem + '}';
    }

  
    
}

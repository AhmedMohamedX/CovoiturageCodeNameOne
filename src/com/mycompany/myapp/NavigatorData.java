/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;




public class NavigatorData {
    private final static NavigatorData instance = new NavigatorData();

    public static NavigatorData getInstance() {
        return instance;
    }        
          
    private User ConnectedUser;
    private Annonce SelectedAnnonce;
    private String vd;
    private String va;
    private String date;
    private User selectedUser;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va;
    }
    
    public String getVd() {
        return vd;
    }

    public void setVd(String vd) {
        this.vd = vd;
    }
    
    public void SetConnectedUser(User CUser){
        this.ConnectedUser=CUser;
    }
    
    public User getConnectedUser() {
        return ConnectedUser;
    }     
        public void SetSelectedAnnonce(Annonce annonce){
        this.SelectedAnnonce=annonce;
    }
    
    public Annonce getSelectedAnnonce() {
        return SelectedAnnonce;
    } 
}

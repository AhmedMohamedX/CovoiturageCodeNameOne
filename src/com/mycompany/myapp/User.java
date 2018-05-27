/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

/**
 *
 * @author adel
 */
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password; 
    private String role ; 
    private String musique ;
    private String photoprofilpath;
    private int tabagime ;
    private String sexe ; 
    private int Animaux;
    private String PermisConduire;
    private  int telephone;
    private int enabled;
    private String codeConfimation;
    private static int idofconnecteduser ;
    private int connect;
    private int jetons;
    private static String emailofconnecteduser ;

    public static String getEmailofconnecteduser() {
        return emailofconnecteduser;
    }

    public static void setEmailofconnecteduser(String emailofconnecteduser) {
        User.emailofconnecteduser = emailofconnecteduser;
    }

    public User(int id, String nom, String prenom, String password, String role,int enabled,String codeConfimation,int numtel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.role = role;
        this.enabled=enabled;
        this.codeConfimation=codeConfimation;
        this.telephone=numtel;
    }

    public User(String nom, String prenom, String musique, int tabagime, String sexe, int Animaux,int telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.musique = musique;
        this.tabagime = tabagime;
        this.sexe = sexe;
     
        this.Animaux = Animaux;

    
        this.telephone = telephone;
    }
    
    public User(int id, String username, String email) {
        this.id = id;
        this.nom = username;
        this.email = email;
    }

    public User(String nom, String email, String password) {
        this.nom = nom;
    
        this.email = email;
        this.password = password;
 
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static int getIdofconnecteduser() {
        return idofconnecteduser;
    }

    public String getMusique() {
        return musique;
    }

    public void setMusique(String musique) {
        this.musique = musique;
    }

    public int getTabagime() {
        return tabagime;
    }

    public void setTabagime(int tabagime) {
        this.tabagime = tabagime;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getAnimaux() {
        return Animaux;
    }

    public void setAnimaux(int Animaux) {
        this.Animaux = Animaux;
    }

    public String getPermisConduire() {
        return PermisConduire;
    }

    public void setPermisConduire(String PermisConduire) {
        this.PermisConduire = PermisConduire;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public static void setIdofconnecteduser(int idofconnecteduser) {
        User.idofconnecteduser = idofconnecteduser;
    }

    public String getPhotoprofilpath() {
        return photoprofilpath;
    }

    public void setPhotoprofilpath(String photoprofilpath) {
        this.photoprofilpath = photoprofilpath;
    }

    public int getEnabled() {
        return enabled;
    }

    public String getCodeConfimation() {
        return codeConfimation;
    }

    public void setCodeConfimation(String codeConfimation) {
        this.codeConfimation = codeConfimation;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", role=" + role + '}';
    }

    public User() {
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }

    public int getJetons() {
        return jetons;
    }

    public void setJetons(int jetons) {
        this.jetons = jetons;
    }

    
}

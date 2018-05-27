/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author AH Info
 */

 
public class AfficherAnnonce extends SideMenuBaseForm {
  

    public AfficherAnnonce(Resources res) {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("login-background.jpg");        
        Image tintedImage = Image.createImage(profilePic.getWidth(), profilePic.getHeight());
        Graphics g = tintedImage.getGraphics();
        g.drawImage(profilePic, 0, 0);
        g.drawImage(res.getImage("gradient-overlay.png"), 0, 0, profilePic.getWidth(), profilePic.getHeight());
        
        tb.getUnselectedStyle().setBgImage(tintedImage);
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> ((SideMenuBar)getToolbar().getMenuBar()).openMenu(null));

        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_EXIT_TO_APP);
        
        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent = 
                BorderLayout.north(
                    BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                );
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);
        settingsButton.addPointerPressedListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new LoginForm(res).show();
            }
        });
        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        add(BorderLayout.NORTH, separator);
        String datee = NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().substring(8, NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().length() - 12);
        String dateee = NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().substring(0, NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().length() - 25);
        Label v = new  Label(NavigatorData.getInstance().getVd()+"=>"+NavigatorData.getInstance().getVa());
        v.setUIID("RedLabel");
        
        Label date = new Label(dateee+" "+datee);
        date.setUIID("gold");
        Label prix = new Label(NavigatorData.getInstance().getSelectedAnnonce().getPrix()+" dt/personne");
        Label nb = new Label(NavigatorData.getInstance().getSelectedAnnonce().getNb_places()+" places");
        Label com1 = new Label("Commentaire :");
        com1.setUIID("bleufb");
        Label prix1 = new Label("Prix :");
        prix1.setUIID("bleufb");
        prix.setUIID("RedLabel");
        Label nb1 = new Label("Nombre de places :");
        nb1.setUIID("bleufb");
        nb.setUIID("RedLabel");
        Label com = new Label(NavigatorData.getInstance().getSelectedAnnonce().getCommentaire());
        com.setUIID("RedLabel");
        prix.setUIID("RedLabel");
        Button delete = new Button("SUPPRIMER");
        Button partager = new Button("PARTAGE FACEBOOK");
        partager.setUIID("LoginButton");
        delete.setUIID("suppButton");
        Container by = BoxLayout.encloseY(
                v,
                date,
                prix1,
                prix,
                nb1,
                nb,
                com1,
                com,
                delete,
                partager
                
                
        );
        add(BorderLayout.CENTER, by);
        by.setScrollableY(true);
        by.setScrollVisible(false);
        setupSideMenu(res);
        partager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               String accessToken = "EAACEdEose0cBAMdjI9TrY2lPl5k776YUpEDrduJHOrRJqUUZBjEZAZBFCZAERXpIt8iFMZA4hYPHuWRrnyXOu97T4TZCHbmHM5PmuJe9svlUi1LmUoJ3PC1RgxAkBkZCkm1YpyVCUJCyudbUsZAEAeI1VyqI3JAT714etedheFtumoZAuNZAU6ffuzK1tOhZBpNuiQZD";
        
        FacebookClient fbClient= new DefaultFacebookClient(accessToken);
         FacebookType response = fbClient.publish("me/feed", FacebookType.class,
                           Parameter.with("message","Annonce publié pour un départ le "
                           +NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString()+" avec un prix de "+NavigatorData.getInstance().getSelectedAnnonce().getPrix()+" dt/personne, pour "+NavigatorData.getInstance().getSelectedAnnonce().getNb_places()+" places,voici le commentaire d'autheur: "+NavigatorData.getInstance().getSelectedAnnonce().getCommentaire()),
                           Parameter.with("link", "http://127.168.0.1/covoiturage-EDT-ONE/web/app_dev.php/accueil"));
         System.out.println("fb.com/"+response.getId());
         Dialog.show("Succes", "Votre annonce à été partagé sur facebook", "Fermer", null);
            }
        });
        delete.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest messcon1 = new ConnectionRequest();
            messcon1.setUrl("http://127.168.0.1/pidev2017/getAnnonceR.php?id="+NavigatorData.getInstance().getSelectedAnnonce().getId()+"");
            messcon1.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    int f = 0;
                   byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
                       
                        if (s.length()==9) {
                            System.out.println("ok");
                            f=1;
                        } 
                                        
            if(f==0){
                  
            ConnectionRequest messcon = new ConnectionRequest();
            messcon.setUrl("http://127.168.0.1/pidev2017/deleteAnnonce.php?id="+NavigatorData.getInstance().getSelectedAnnonce().getId()+"");
            messcon.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    new mesAnnonces(res).show();
                   Dialog.show("Succes", "Annonce supprimer !", "Fermer", null);
                }

            });
 NetworkManager.getInstance().addToQueue(messcon);
            }else{
                Dialog.show("Erreur", "Vous ne pouvez pas supprimer cette annonce !", "Fermer", null);
            }
                }

            });
 NetworkManager.getInstance().addToQueue(messcon1);
            
           

            }
            
        });
    }

  
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }
   
}

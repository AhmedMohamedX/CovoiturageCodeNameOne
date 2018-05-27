
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
//import com.esprit.covoiturage.Entities.voiture;
//import www.esprit.covoiturage.DAO.voitureDAO;

/**
 *
 * @author sabrouch
 */
public class modifVoiture {
     public Form f;
     Resources theme;

    public modifVoiture(voiture v) {
        
        f=new Form("UpdateForm");
           Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage    icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Command getRecStatsCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new affichagevoiture(theme);
            }  
        };
        
        f.getToolbar().addCommandToLeftBar(getRecStatsCmd);
              theme = UIManager.initFirstTheme("/theme");
              
                         Container cy=new Container(BoxLayout.y());
           Container c5=new Container(BoxLayout.x());
            Label m = new Label("marque :");
            m.setUIID("RedLabel");
     TextField tfmarque = new TextField(v.getMarque()+"");
     tfmarque.setUIID("RedLabel");
                 c5.add(m).add(tfmarque);

 //  TextField tfcomfort = new TextField(v.getComfort()+"");
  Container c4=new Container(BoxLayout.x());
         Label c = new Label("couleur :");
         c.setUIID("RedLabel");
 TextField tfcouleur = new TextField(v.getCouleur()+"");
 tfcouleur.setUIID("RedLabel");
         c4.add(c).add(tfcouleur);

       //    TextField tfnb_places = new TextField (v.getNb_places()+"");
         //    TextField tfcategorie = new TextField (v.getCategorie()+"");
 
                  ComboBox<Integer>tfnb_places=new ComboBox<>();
                  
    for(int i=1;i<=4;i++){
        tfnb_places.addItem(i);
        
    }
     Container c3=new Container(BoxLayout.x());
             Label n = new Label("nombre de places :");
   
     c3.add(n).add(tfnb_places);
    ComboBox<String>tfcategorie=new ComboBox<>();
    tfcategorie.addItem("Mini");
        tfcategorie.addItem("familiale");
    tfcategorie.addItem("luxe et sport");
    
      Container c6=new Container(BoxLayout.x());
             Label cat = new Label("categorie :");
   
     c6.add(cat).add(tfcategorie);
     
     ComboBox<String>tfcomfort=new ComboBox<>();
    tfcomfort.addItem("peu confortable");
        tfcomfort.addItem("confortable");
    tfcomfort.addItem("tres confortable");
       Container c7=new Container(BoxLayout.x());
             Label com = new Label("confort :");
   
     c7.add(com).add(tfcomfort);
    
    
        Button b=new Button("update");
        b.setUIID("LoginButton");
/*        f.add(tfmarque);
        f.add(tfcomfort);
        f.add(tfcouleur);
        f.add(tfnb_places);
        f.add(tfcategorie);*/
       // f.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://127.168.0.1/pidev2017/updatevv.php?marque=" + tfmarque.getText() + "&comfort=" + tfcomfort.getSelectedItem()+ "&couleur=" + tfcouleur.getText() + "&nb_places=" + tfnb_places.getSelectedItem().toString()+"&categorie="+tfcategorie.getSelectedItem()+"&marque="+v.getMarque()+"");
              //  System.out.println("http://localhost/service/update.php?marque=" + tfmarque.getText() + "&comfort=" + tfcomfort.getText()+ "&couleur=" + tfcouleur.getText() + "&nb_places=" + tfnb_places.getText() +"&categorie="+tfcategorie.getText()+"&marque="+marque);
                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "modification effectué avec succés", "Ok", null);
                            new affichagevoiture(theme);
                        }else{
                             Dialog.show("erreur", "erreur", "Ok", null);
                        }
                    }
                });
                
                NetworkManager.getInstance().addToQueue(req);
                
            }
        });
                                 Container cy2=new Container(BoxLayout.y());

        Container cx=new Container(BoxLayout.x());
      cy.add(c5).add(c4).add(c3).add(c6).add(c7);
               f.add(cy);
               cy2.add(cx);
        cx.add(b);
     //   c.add(b1);
        f.add(cy2);
        
        f.show();
                
    }

    public Form getF() {
        return f;
    }
    
}

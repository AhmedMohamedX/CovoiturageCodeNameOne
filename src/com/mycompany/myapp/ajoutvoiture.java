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
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author fenina
 */
public class ajoutvoiture {
    Form f ;
    User u;
         private Resources theme;
    public User getuserconnected(String json) {
        User u = null;
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
            Map<String, Object> user1 = (Map<String, Object>) user.get("user");

            u = new User();
            u.setId(Integer.parseInt(user1.get("id").toString()));
            u.setNom(user1.get("username").toString());
            u.setPrenom(user1.get("prenom").toString());

            u.setSexe(user1.get("sexe").toString());
            u.setTelephone(Integer.parseInt(user1.get("telephone").toString()));

        } catch (IOException ex) {
        }
        return u;

    }
    public ajoutvoiture(Resources theme){
           f=new Form("Ajouter");
             Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage    icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Command getRecStatsCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new affichagevoiture(theme);
            }  
        };
        
        f.getToolbar().addCommandToLeftBar(getRecStatsCmd);
                                Container cy=new Container(BoxLayout.y());
           Container c5=new Container(BoxLayout.x());
         Label m = new Label("marque :");
         m.setUIID("RedLabel");
     TextField tfmarque = new TextField();
     tfmarque.setUIID("RedLabel");
     c5.add(m).add(tfmarque);
  // TextField tfcomfort = new TextField();
  Container c4=new Container(BoxLayout.x());
         Label c = new Label("couleur :");
c.setUIID("RedLabel");
   TextField tfcouleur = new TextField();
   tfcouleur.setUIID("RedLabel");
        c4.add(c).add(tfcouleur);

  //         TextField tfnb_places = new TextField ();
          //   TextField tfcategorie = new TextField ();
 
    
    
        Button b=new Button("ajouter");
     
       b.setUIID("LoginButton");
      //  f.add(tfcouleur);
      //  f.add(tfnb_places);
       
        
              ComboBox<Integer>comboa=new ComboBox<>();
    for(int i=1;i<=4;i++){
        comboa.addItem(i);
        
    }
               Container c3=new Container(BoxLayout.x());
             Label n = new Label("nombre de places :");
   
     c3.add(n).add(comboa);
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
 //f.add(tfcomfort);
  //   f.add(tfcategorie);
 
ConnectionRequest con2 = new ConnectionRequest();
        con2.setUrl("http://127.168.0.1/pidev2017/getUserByEmail.php?email=" + User.getEmailofconnecteduser());
        con2.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                u = getuserconnected(new String(con2.getResponseData()));

            }
        });
  //  f.add(comboa);
                      b.addActionListener(x->{
if(tfmarque.getText().equals("") || tfcouleur.getText().equals("")){
 Dialog.show("erreur", "vueillez remplir les champs correctement", "Ok", null);
}
else{
                ConnectionRequest req = new ConnectionRequest();
           //     u = getuserconnected(new String(con2.getResponseData()));
                req.setUrl("http://127.168.0.1/pidev2017/insertvv.php?chauffeur_id="+NavigatorData.getInstance().getConnectedUser().getId()+"&marque=" + tfmarque.getText() + "&comfort=" + tfcomfort.getSelectedItem()+ "&couleur=" + tfcouleur.getText() + "&nb_places=" + comboa.getSelectedItem().toString() +"&categorie="+tfcategorie.getSelectedItem()+"");
              //  System.out.println("http://localhost/service/insert.php?marque=" + tfmarque.getText() + "&comfort=" + tfcomfort.getText()+ "&couleur=" + tfcouleur.getText() + "&nb_places=" + tfnb_places.getText() +"&categorie"+tfcategorie);
                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        
                            Dialog.show("Confirmation", "Ajout effectué avec succés", "Ok", null);
                        new affichagevoiture(theme);
                    }
                });
                
                NetworkManager.getInstance().addToQueue(req);
         //         affichagevoiture m = new affichagevoiture();
            
}
            }
        );
               Container c2=new Container(BoxLayout.x());
        cy.add(c5).add(c4).add(c3).add(c6).add(c7);
               f.add(cy);
               f.add(b);
     //   c.add(b1);
        f.add(c2);
        
        f.show();
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
}

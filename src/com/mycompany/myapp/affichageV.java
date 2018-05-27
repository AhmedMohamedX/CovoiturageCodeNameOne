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
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fenina
 */
public class affichageV {
    Resources theme;
    public ArrayList<voiture> getListVoiture(String json) {
        ArrayList<voiture> listvoitures = new ArrayList<>();
        
        try {
            
            JSONParser j = new JSONParser();
            Map<String, Object> voitures= j.parseJSON(new CharArrayReader(json.toCharArray()));
            
         
            //System.out.println(albums.toString());
           
      
                String s = voitures.get("voiture").toString();
             //   System.out.println(s);
           
                //
           
                //11
                if(s.startsWith("["))
                {
                   // System.out.println("Methode 1");
         List<Map<String, Object>> list = (List<Map<String, Object>>) voitures.get("voiture");
      
            for (Map<String, Object> obj : list) {
                
                 
                 voiture a = new voiture();
                a.setId(Integer.parseInt(obj.get("id").toString()));
             Label lm = new Label("marque :");
                a.setMarque(obj.get("marque").toString());
                  Label lc= new Label("confort :");
                a.setComfort(obj.get("comfort").toString());
                a.setCouleur(obj.get("couleur").toString());
                a.setNb_places(Integer.parseInt(obj.get("nbplaces").toString()));
                a.setCategorie(obj.get("categorie").toString());
                listvoitures.add(a);
            }}
               else if(s.startsWith("{"))
                {
               //     System.err.println("Methode 2");
                
                   Map<String, Object> obj = (Map<String, Object>) voitures.get("voiture");
           
         voiture a = new voiture();
             a.setId(Integer.parseInt(obj.get("id").toString()));
            //        a.setId(Integer.parseInt(obj.get("id").toString()));

                a.setMarque(obj.get("marque").toString());
                a.setComfort(obj.get("comfort").toString());
                a.setCouleur(obj.get("couleur").toString());
                a.setNb_places(Integer.parseInt(obj.get("nbplaces").toString()));
                a.setCategorie(obj.get("categorie").toString());
                listvoitures.add(a);

                }
           //     supprimerVoiture ss = new supprimerVoiture();
                
             
         /*      ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/service/remove.php?&id="+15+"");
              //  System.out.println("http://localhost/service/insert.php?marque=" + tfmarque.getText() + "&comfort=" + tfcomfort.getText()+ "&couleur=" + tfcouleur.getText() + "&nb_places=" + tfnb_places.getText() +"&categorie"+tfcategorie.getText()+"&id"+15);
                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "supression effectué avec succés", "Ok", null);
                        }else{
                             Dialog.show("erreur", "errur", "Ok", null);
                        }
                    }
                });
                
                NetworkManager.getInstance().addToQueue(req);*/
         
         
         /*Button btnOk = new Button("supprimer");

btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/codenameone/remove.php?nom=" + v.getNom());

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("suppression", "Suppresion effectué avec succés", "Ok", null);
                        }
                        
                    }
                    
                });
                
                NetworkManager.getInstance().addToQueue(req);
                       
                       

            }
        
        });
            
          
           */
   
            
        } catch (IOException ex) {
         }
      //  mainmenu();
        return listvoitures;
}
    

    
}

/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.util.ArrayList;

/**
 *
 * @author Shai Almog
 */
public class affichagevoiture extends SideMenuBaseForm {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};
ArrayList<voiture> voitures= new ArrayList<>();
Form f;
    public affichagevoiture(Resources res) {
      
       Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage    icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Command getRecStatsCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new ProfileForm(res).show();
            }  
        };
       
         f=new Form();
    f.getToolbar().addCommandToLeftBar(getRecStatsCmd);
    f.setTitle("Mes voitures");
                   icon = FontImage.createMaterial(FontImage.MATERIAL_ADD_BOX, s);
        Command addRecCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ajoutvoiture aj = new ajoutvoiture(res);
                aj.getF().show();
            }  
        };
                         
  f.getToolbar().addCommandToRightBar(addRecCmd);
ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/selectvv.php");
         con.addResponseListener(new ActionListener<NetworkEvent>() {
  
            @Override
            public void actionPerformed(NetworkEvent evt) {
              affichageV commentaireDAO = new affichageV();
            
                  
              for(int i = 0 ;i<commentaireDAO.getListVoiture(new String(con.getResponseData())).size();i++)
              {
              // System.out.println("test commentaire : "+commentaireDAO.getListCommentaire(new String(con.getResponseData())).get(i).getDatePost()+"\n");
             
               voitures.add(commentaireDAO.getListVoiture(new String(con.getResponseData())).get(i));
                       
                
              }
              for (voiture v : voitures){
                  
                  f.add(displayCommentaire(v,res));
         
            
            
              }
          
                
                    
                   
                   
                f.show();
                        
                        
                        
                        
                  
  

            }
            
        });
      
        NetworkManager.getInstance().addToQueue(con);
    f.show();
    }
        public Container displayCommentaire(voiture v,Resources res) {
        
        Container ctn1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container ctn2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

       Container ctn3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
          TextField txtSpacer = new TextField();
          txtSpacer.setVisible(false);
          txtSpacer.setEditable(false);
     
      Label lm = new Label ("marque : ");
       Label lmarque = new Label(v.getMarque());
       ctn3.add(lm).add(lmarque);
              Container ctn4 = new Container(new BoxLayout(BoxLayout.X_AXIS));

       Label lc = new Label("confort : ");
       
       Label lcomfort = new Label(v.getComfort());
       ctn4.add(lc).add(lcomfort);
                     Container ctn5 = new Container(new BoxLayout(BoxLayout.X_AXIS));
Label lco = new Label("couleur : ");
              Label lcouleur = new Label(v.getCouleur());
              ctn5.add(lco).add(lcouleur);
                    Container ctn6 = new Container(new BoxLayout(BoxLayout.X_AXIS));
Label ln = new Label("nombre de places : ");

       Label lnbplaces = new Label(v.getNb_places()+"");
       ctn6.add(ln).add(lnbplaces);
             Container ctn7 = new Container(new BoxLayout(BoxLayout.X_AXIS));
Label lca = new Label("categorie : ");
       Label lcategorie = new Label (v.getCategorie());
    ctn7.add(lca).add(lcategorie);
        Button btnOk = new Button("supprimer");
        btnOk.setUIID("LoginButton");
    

btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://127.168.0.1/pidev2017/removevv.php?marque=" + v.getMarque());

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);

                        if (s.equals("success")) {
                            Dialog.show("suppression", "Suppresion effectué avec succés", "Ok", null);
                            new affichagevoiture(res);
                        }
                        
                    }
                    
                });
                
                NetworkManager.getInstance().addToQueue(req);
                       
             
            }
        
        });
  Button btnOk1 = new Button("Modifier");
  btnOk1.setUIID("LoginButton");
             

btnOk1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
      modifVoiture mv = new modifVoiture(v);
       mv.getF().show();
            }});
        
      ctn2.add(txtSpacer);
       ctn2.add(ctn3).add(ctn4).add(ctn5).add(ctn6).add(ctn7).add(btnOk).add(btnOk1);
       
        
     
     

   
        ctn1.add(ctn2);
         
        return ctn1;
    
      
       
       
       
       
    }

  
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }

 
}

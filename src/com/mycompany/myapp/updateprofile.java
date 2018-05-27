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
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
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
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Shai Almog
 */
public class updateprofile extends SideMenuBaseForm {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public updateprofile(Resources res) {
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
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH, 
                        FlowLayout.encloseIn(
                                new Label(NavigatorData.getInstance().getConnectedUser().getNom(), "WelcomeBlue")
                        ));
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
        setupSideMenu(res);
        
        TextField nom = new TextField();
        TextField prenom = new TextField();
        TextField telephone = new TextField();
        
        nom.setUIID("RedLabel");
        prenom.setUIID("RedLabel"); 
        telephone.setUIID("RedLabel");
        Button  update = new Button("Modifier");
        update.setUIID("LoginButton");
        
           ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/getUserByEmail.php?email="+User.getEmailofconnecteduser());
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(getuserconnected(new String(con.getResponseData())));
               nom.setText(getuserconnected(new String(con.getResponseData())).getNom());
               prenom.setText(getuserconnected(new String(con.getResponseData())).getPrenom());
               
               telephone.setText(getuserconnected(new String(con.getResponseData())).getTelephone()+"");
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        
        
         update.addActionListener(x->{
         
        ConnectionRequest con1 = new ConnectionRequest(); 
         
         
           con1.setUrl("http://127.168.0.1/pidev2017/UpdateProfile.php?nom=" +nom.getText()+"&prenom="+prenom.getText()+"&numtel="+telephone.getText()+"&email="+ User.getEmailofconnecteduser() );
           con1.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
                        System.out.println(s);
                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "Modification avec succées", "Ok", null);
                        } else {
                            Dialog.show("Erreur", "erreur", "Ok", null);
                        }
                    }
                });
             NetworkManager.getInstance().addToQueue(con1);

         });
                Container by = BoxLayout.encloseY(
nom,prenom,telephone,update
                
                
        );
        add(BorderLayout.CENTER, by);
        
    }

  
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }

 public User getuserconnected(String json){
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
}

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

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.Map;

/**
 * The Login form
 *
 * @author Shai Almog
 */
public class LoginForm extends Form {
    TextField login = new TextField("", "Login", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Welcome, ", "WelcomeWhite"),
                new Label("To Share The Fare", "WelcomeBlue")
        );
        
        getTitleArea().setUIID("Container");
        
        Image profilePic = theme.getImage("car_14444.png");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        profilePicLabel.setMask(mask.createMask());
        
        
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");

       
       loginButton.addActionListener(x->{
                  ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://127.168.0.1/pidev2017/Authentification.php?login=" + login.getText() + "&password=" + password.getText() +"");

                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
                        System.out.println(s);
                        if (s.equals("success")) {
                            User.setEmailofconnecteduser(login.getText());
                            
                            
                            
                            ConnectionRequest con1 = new ConnectionRequest();
                            con1.setUrl("http://127.168.0.1/pidev2017/authentification1.php?username="+login.getText()+"");
                             con1.addResponseListener(new ActionListener<NetworkEvent>() {
                              @Override
                               public void actionPerformed(NetworkEvent evt) {
                
                           User u = authentification(new String(con1.getResponseData()));
                           NavigatorData.getInstance().SetConnectedUser(u);
                           new ProfileForm(theme).show();
                        }
            
                          });
                              NetworkManager.getInstance().addToQueue(con1);
            
                            
                            
                            
                            
                
                            
                            
                            
                            
                            
                            
                            
                            
                            
                   
          
          
                        } else {
                            Dialog.show("Erreur", "Merci de vérifier vos paramétres de connexion", "Ok", null);
                        }
                    }
                });

                NetworkManager.getInstance().addToQueue(req);
            
            
        });
        
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton
                
        );
        add(BorderLayout.CENTER, by);
        
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
    
public User authentification(String json) {
        User user = new User();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
                  
            Map<String, Object> Mapprofil = (Map<String, Object>) etudiants.get("User");
              System.out.println(Mapprofil);
            if (Mapprofil != null) {
                
                user.setId(Integer.parseInt(Mapprofil.get("id").toString()));
                user.setPassword(Mapprofil.get("password").toString());
                user.setNom(Mapprofil.get("username").toString());
                System.out.println(Mapprofil.get("username").toString());
                
                  

            }
        } catch (IOException ex) {
        }
        return user;

    }
    
    
}

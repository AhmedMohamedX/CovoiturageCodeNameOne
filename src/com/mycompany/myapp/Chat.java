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
import com.codename1.components.MultiButton;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Shai Almog
 */
public class Chat extends SideMenuBaseForm {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public Chat(Resources res) {
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
        initGuiBuilderComponents(res);
        setupSideMenu(res);
        
            
        
    }

  
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }

    private void initGuiBuilderComponents(Resources res) {
        
        setLayout(new BorderLayout());
         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         c1.setScrollableY(true);
        c1.setScrollVisible(false);
        c1.setName("c1");
        TextField f1 = new  TextField();
        f1.setHint("Ecrire votre message ici");
        f1.setUIID("mess");
        Button send = new Button();
        FontImage.setMaterialIcon(send, FontImage.MATERIAL_SEND);
        Container ok = BoxLayout.encloseX(f1, send);
        addComponent(BorderLayout.CENTER,c1);
        addComponent(BorderLayout.SOUTH,ok);
         
        send.addActionListener((e) -> {
            
            String text = f1.getText();
            System.out.println(text);
            if(text != ""){
           
            // we make outgoing messages translucent to indicate that they weren't received yet
            
            f1.setText("");       
            ConnectionRequest messcon = new ConnectionRequest();
            messcon.setUrl("http://127.168.0.1/pidev2017/InsertMessage.php?content=" + text + "&author_id=" + NavigatorData.getInstance().getConnectedUser().getId()+"");
            messcon.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    
                    c1.removeAll();
                    initGuiBuilderComponents(res);
                    c1.revalidate();
                }

            });
 NetworkManager.getInstance().addToQueue(messcon);
            }
            });
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/getchat.php");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    for (Message u : getListMessage(new String(con.getResponseData()))) {
                        ConnectionRequest conn = new ConnectionRequest();
                        System.out.println(u);
                        String url = "http://127.168.0.1/pidev2017/select.php?id=" + u.getEmetteur().getId()+"";
                        conn.setUrl(url);
                        conn.addResponseListener(new ActionListener<NetworkEvent>() {
                            @Override
                            public void actionPerformed(NetworkEvent evt) {
                                User p = getListUsers(new String(conn.getResponseData()));
                                u.setEmetteur(p);
                                Container c = new Container(new BorderLayout());
                                MultiButton m = new MultiButton();
                                Label on = new Label();
                                
                                
                                c1.addComponent(c);
                                c.setName("c");
                                c.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, m);
                                FontImage.setMaterialIcon(on, FontImage.MATERIAL_CHAT_BUBBLE_OUTLINE);
                                c.addComponent(com.codename1.ui.layouts.BorderLayout.EAST, on);
                                m.setUIID("Label");
                                m.setName("Multi_Button_1");
                                
                                URLImage imgURL = null;
                                
                                if (p.getId() == NavigatorData.getInstance().getConnectedUser().getId()) {
                                    EncodedImage encImg = EncodedImage.createFromImage(res.getImage("téléchargement.png"), false);
                                    System.out.println("me");
                                imgURL = URLImage.createToStorage(encImg, "sss", "http://127.168.0.1/téléchargement.png");
                                imgURL.fetch();
                                } else { 
                                    EncodedImage encImg1 = EncodedImage.createFromImage(res.getImage("icon2.png"), false);
                                    System.out.println("not me");
                                imgURL = URLImage.createToStorage(encImg1, "ssssss", "http://127.168.0.1/icon2.png");
                                imgURL.fetch();
                                
                                }
                                String datee = u.getDateEnvoie().toString().substring(8, u.getDateEnvoie().toString().length() - 12);
                                String dateee = u.getDateEnvoie().toString().substring(0, u.getDateEnvoie().toString().length() - 25);
                                m.setIcon(imgURL);
                                m.setPropertyValue("line1",""+dateee+datee );
                                m.setPropertyValue("line2", p.getNom());
                                m.setPropertyValue("line3", u.getContenu());
                                m.setPropertyValue("uiid1", "inbox");
                                m.setPropertyValue("uiid2", "Label");
                                m.setPropertyValue("uiid3", "RedLabel");
                                Label l = new Label();
                                c1.addComponent(l);
                                c.setName("c");
                                
                                l.setUIID("Separator");
                                l.setName("separator1");
                                l.setShowEvenIfBlank(true);
                                
                                c1.revalidate();
                                
                            }
                        });
                        NetworkManager.getInstance().addToQueue(conn);
                    }
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        refreshTheme();
            Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                     c1.removeAll();
                    initGuiBuilderComponents(res);
                    c1.revalidate();
                } catch (InterruptedException ex) {
                       Log.getReportingLevel();
                }
            }
        });
            t1.start();
    }

public ArrayList<Message> getListMessage(String json) throws ParseException {
        ArrayList<Message> listmsg = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> chat = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) chat.get("message");

            for (Map<String, Object> obj : list) {
                Message e = new Message();
                e.setIdMessage(Integer.parseInt(obj.get("idMessage").toString()));
                
                String dateStr = obj.get("dateEnvoie").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                e.setDateEnvoie(dateenvoyer);
                e.setContenu(obj.get("contenu").toString());
                User f = new User();
                f.setId(Integer.parseInt(obj.get("idEmetteur").toString()));
                e.setEmetteur(f);
                listmsg.add(e);
            }

        } catch (IOException ex) {
        }
        return listmsg;

    }
public User getListUsers(String json) {
        User profil = new User();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            Map<String, Object> Mapprofil = (Map<String, Object>) etudiants.get("user");
            if (Mapprofil != null) {
                profil.setId(Integer.parseInt(Mapprofil.get("id").toString()));
                profil.setNom(Mapprofil.get("username").toString());
                profil.setPhotoprofilpath(Mapprofil.get("photo_membre").toString());
                
                
            }
        } catch (IOException ex) {
        }
        return profil;

    }

 
}

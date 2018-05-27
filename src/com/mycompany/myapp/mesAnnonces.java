/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

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
import com.codename1.ui.EncodedImage;
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
 * @author AH Info
 */
public class mesAnnonces extends SideMenuBaseForm{



    public mesAnnonces(Resources res) {
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        
        Image profilePic = res.getImage("login-background.jpg");        
        Image tintedImage = Image.createImage(profilePic.getWidth(), profilePic.getHeight());
        Graphics g = tintedImage.getGraphics();
        g.drawImage(profilePic, 0, 0);
        g.drawImage(res.getImage("gradient-overlay.png"), 0, 0, profilePic.getWidth(), profilePic.getHeight());
        
        tb.getUnselectedStyle().setBgImage(tintedImage);
        
        Button menuButton = new Button("Mes annonces");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> ((SideMenuBar)getToolbar().getMenuBar()).openMenu(null));

        
        
        Label space = new Label("", "TitlePictureSpace");
        
        space.setShowEvenIfBlank(true);
        Container titleComponent = 
                BorderLayout.north(
                    BorderLayout.west(menuButton)
                );
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);
        
        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        add(BorderLayout.NORTH, separator);
         initGuiBuilderComponents(res);
        setupSideMenu(res);
        
       
    }

  
    
    private void initGuiBuilderComponents(Resources res) {
        Toolbar tb = getToolbar();
        tb.addSearchCommand(e -> {

            
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                System.out.println("null text");
                
                new mesAnnonces(res).show();
                
                
                
                
                
                
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
               
                text = text.toLowerCase();
                System.out.println(text);
                getContentPane().animateLayout(150);
                initGuiBuilderComponentsE(res, text);
            
            }

            System.out.println("fin");
        }, 4);
        
        setLayout(new BorderLayout());
         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         c1.setScrollableY(true);
        c1.setScrollVisible(false);
        c1.setName("c1");
       
        addComponent(BorderLayout.CENTER,c1);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/getannonces.php?id="+NavigatorData.getInstance().getConnectedUser().getId());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    try {
                        for (Annonce u : getListannonces(new String(con.getResponseData()))) {
                            ConnectionRequest conn = new ConnectionRequest();
                            System.out.println(u);
                            String url = "http://127.168.0.1/pidev2017/selectT.php?id=" + u.getTrajet_id().getId()+"";
                            conn.setUrl(url);
                            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    trajet p = getT(new String(conn.getResponseData()));
                                    u.setTrajet_id(p);
                                    
                                    ConnectionRequest conn1 = new ConnectionRequest();
                                    System.out.println(u);
                                    String url = "http://127.168.0.1/pidev2017/selectV.php?id=" + p.getVille_dep()+"";
                                    conn1.setUrl(url);
                                    conn1.addResponseListener(new ActionListener<NetworkEvent>() {
                                        @Override
                                        public void actionPerformed(NetworkEvent evt) {
                                            Ville vd = getV(new String(conn1.getResponseData()));
                                            NavigatorData.getInstance().setVd(vd.getNom());
                                            
                                            ConnectionRequest conn2 = new ConnectionRequest();
                                            System.out.println(u);
                                            String url = "http://127.168.0.1/pidev2017/selectV.php?id=" + p.getVille_arr()+"";
                                            conn2.setUrl(url);
                                            conn2.addResponseListener(new ActionListener<NetworkEvent>() {
                                                @Override
                                                public void actionPerformed(NetworkEvent evt) {
                                                    Ville va = getV(new String(conn2.getResponseData()));
                                                    NavigatorData.getInstance().setVa(va.getNom());
                                                    Container c = new Container(new BorderLayout());
                                                    MultiButton m = new MultiButton();
                                                    
                                                    
                                                    c1.addComponent(c);
                                                    c.setName("c");
                                                    c.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, m);
                                                    m.setUIID("Label");
                                                    m.setName("Multi_Button_1");
                                                    
                                                    String datee = u.getDate_depart().toString().substring(8, u.getDate_depart().toString().length() - 12);
                                                    String dateee = u.getDate_depart().toString().substring(0, u.getDate_depart().toString().length() - 25);
                                                    m.setPropertyValue("line1",vd.getNom()+"=>"+va.getNom());
                                                    m.setPropertyValue("uiid1", "RedLabel");
                                                    m.setPropertyValue("line2",""+dateee+" "+datee+"");
                                                    m.setPropertyValue("line3", "prix : "+u.getPrix()+" dt/personne");
                                                    m.setPropertyValue("line4", "nombre de places : "+u.getNb_places()+"");
                                                    m.setPropertyValue("uiid2", "gold");
                                                    m.setPropertyValue("uiid3", "bleufb");
                                                    m.setPropertyValue("uiid4", "RedLabel");
                                                    Label l = new Label();
                                                    c1.addComponent(l);
                                                    c.setName("c");
                                                    
                                                    l.setUIID("Separator");
                                                    l.setName("separator1");
                                                    l.setShowEvenIfBlank(true);
                                                    m.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent evt) {
                                                            
                                                            NavigatorData.getInstance().SetSelectedAnnonce(u);
                                                            new AfficherAnnonce(res).show();
                                                        }
                                                    });
                                                    c1.revalidate();
                                                    
                                                    
                                                    
                                                }
                                            });
                                            NetworkManager.getInstance().addToQueue(conn2);
                                            
                                            
                                            
                                        }
                                    });
                                    NetworkManager.getInstance().addToQueue(conn1);
                                }
                            });
                            NetworkManager.getInstance().addToQueue(conn);
                        }
                    } catch (IOException ex) {
                        Log.getReportingLevel();
                    }
                        
                    
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        refreshTheme();
    }

public ArrayList<Annonce> getListannonces(String json) throws ParseException, IOException {

       ArrayList<Annonce> listmsg = new ArrayList<>();
        JSONParser j = new JSONParser();

        Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
        
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("annonce");
            if(list == null){
                return null;
            }
            for (Map<String, Object> obj : list) {
                Annonce e = new Annonce();
                e.setId(Integer.parseInt(obj.get("id").toString()));
                String dateStr = obj.get("date_depart").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                e.setDate_depart(dateenvoyer);
                e.setDeviation(obj.get("deviation").toString());
                e.setCommentaire(obj.get("commentaire").toString());
                e.setNb_places(Integer.parseInt(obj.get("nb_places").toString()));
                e.setPrix(Integer.parseInt(obj.get("prix").toString()));
                trajet t = new trajet();
                t.setId(Integer.parseInt(obj.get("trajet_id").toString()));
                e.setTrajet_id(t);
                listmsg.add(e);

            }
        } catch (ClassCastException e) {
            Map<String, Object> list = (Map<String, Object>) etudiants.get("annonce");
            Annonce ee = new Annonce();
                ee.setId(Integer.parseInt(list.get("id").toString()));
                String dateStr = list.get("date_depart").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                ee.setDate_depart(dateenvoyer);
                ee.setDeviation(list.get("deviation").toString());
                ee.setCommentaire(list.get("commentaire").toString());
                ee.setNb_places(Integer.parseInt(list.get("nb_places").toString()));
                ee.setPrix(Integer.parseInt(list.get("prix").toString()));
                trajet t = new trajet();
                t.setId(Integer.parseInt(list.get("trajet_id").toString()));
                ee.setTrajet_id(t);
                ee.setTrajet_id(t);
            listmsg.add(ee);
        }
        return listmsg;

    }
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }
    
public trajet getT(String json) {
        trajet profil = new trajet();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            Map<String, Object> Mapprofil = (Map<String, Object>) etudiants.get("trajet");
            if (Mapprofil != null) {
                profil.setId(Integer.parseInt(Mapprofil.get("id").toString()));
                profil.setVille_dep(Integer.parseInt(Mapprofil.get("ville_depart_id").toString()));
                profil.setVille_arr(Integer.parseInt(Mapprofil.get("ville_arrive_id").toString()));
                
            }
        } catch (IOException ex) {
            
        }
        return profil;

    }

 
public Ville getV(String json) {
        Ville profil = new Ville();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            Map<String, Object> Mapprofil = (Map<String, Object>) etudiants.get("ville");
            if (Mapprofil != null) {
                profil.setId(Integer.parseInt(Mapprofil.get("id").toString()));
                profil.setNom(Mapprofil.get("nom").toString());
                
            }
        } catch (IOException ex) {
            
        }
        return profil;

    }

public ArrayList<Annonce> getListannoncesR(String json) throws ParseException, IOException {
        ArrayList<Annonce> listmsg = new ArrayList<>();
        JSONParser j = new JSONParser();

        Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
        
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("annonce");
            if(list == null){
                return null;
            }
            for (Map<String, Object> obj : list) {
                 Annonce e = new Annonce();
                e.setId(Integer.parseInt(obj.get("id").toString()));
                String dateStr = obj.get("date_depart").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                e.setDate_depart(dateenvoyer);
                e.setDeviation(obj.get("deviation").toString());
                e.setCommentaire(obj.get("commentaire").toString());
                e.setNb_places(Integer.parseInt(obj.get("nb_places").toString()));
                e.setPrix(Integer.parseInt(obj.get("prix").toString()));
                trajet t = new trajet();
                t.setId(Integer.parseInt(obj.get("trajet_id").toString()));
                e.setTrajet_id(t);
                listmsg.add(e);

            }
        } catch (ClassCastException e) {
            Map<String, Object> list = (Map<String, Object>) etudiants.get("annonce");
                Annonce ee = new Annonce();
                ee.setId(Integer.parseInt(list.get("id").toString()));
                String dateStr = list.get("date_depart").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                ee.setDate_depart(dateenvoyer);
                ee.setDeviation(list.get("deviation").toString());
                ee.setCommentaire(list.get("commentaire").toString());
                ee.setNb_places(Integer.parseInt(list.get("nb_places").toString()));
                ee.setPrix(Integer.parseInt(list.get("prix").toString()));
                trajet t = new trajet();
                t.setId(Integer.parseInt(list.get("trajet_id").toString()));
                ee.setTrajet_id(t);
            listmsg.add(ee);
        }
        return listmsg;
}
 private void initGuiBuilderComponentsE(Resources res,String text) {
    setLayout(new BorderLayout());
         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         removeAll();
         c1.setScrollableY(true);
        c1.setScrollVisible(false);
        c1.setName("c1");
       
        addComponent(BorderLayout.CENTER,c1);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/getannoncesPrix.php?id="+NavigatorData.getInstance().getConnectedUser().getId()+"&prix="+text);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    try {
                        if(getListannoncesR(new String(con.getResponseData()))==null){
                            removeAll();
                            
                        }else{
                        for (Annonce u : getListannoncesR(new String(con.getResponseData()))) {
                            ConnectionRequest conn = new ConnectionRequest();
                            System.out.println(u);
                            String url = "http://127.168.0.1/pidev2017/selectT.php?id=" + u.getTrajet_id().getId()+"";
                            conn.setUrl(url);
                            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    trajet p = getT(new String(conn.getResponseData()));
                                    u.setTrajet_id(p);
                                    
                                    ConnectionRequest conn1 = new ConnectionRequest();
                                    System.out.println(u);
                                    String url = "http://127.168.0.1/pidev2017/selectV.php?id=" + p.getVille_dep()+"";
                                    conn1.setUrl(url);
                                    conn1.addResponseListener(new ActionListener<NetworkEvent>() {
                                        @Override
                                        public void actionPerformed(NetworkEvent evt) {
                                            Ville vd = getV(new String(conn1.getResponseData()));
                                            NavigatorData.getInstance().setVd(vd.getNom());
                                            
                                            ConnectionRequest conn2 = new ConnectionRequest();
                                            System.out.println(u);
                                            String url = "http://127.168.0.1/pidev2017/selectV.php?id=" + p.getVille_arr()+"";
                                            conn2.setUrl(url);
                                            conn2.addResponseListener(new ActionListener<NetworkEvent>() {
                                                @Override
                                                public void actionPerformed(NetworkEvent evt) {
                                                    Ville va = getV(new String(conn2.getResponseData()));
                                                    NavigatorData.getInstance().setVa(va.getNom());
                                                    Container c = new Container(new BorderLayout());
                                                    MultiButton m = new MultiButton();
                                                    
                                                    
                                                    c1.addComponent(c);
                                                    c.setName("c");
                                                    c.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, m);
                                                    m.setUIID("Label");
                                                    m.setName("Multi_Button_1");
                                                    
                                                    String datee = u.getDate_depart().toString().substring(8, u.getDate_depart().toString().length() - 12);
                                                    String dateee = u.getDate_depart().toString().substring(0, u.getDate_depart().toString().length() - 25);
                                                    m.setPropertyValue("line1",vd.getNom()+"=>"+va.getNom());
                                                    m.setPropertyValue("uiid1", "RedLabel");
                                                    m.setPropertyValue("line2",""+dateee+" "+datee+"");
                                                    m.setPropertyValue("line3", "prix : "+u.getPrix()+" dt/personne");
                                                    m.setPropertyValue("line4", "nombre de places : "+u.getNb_places()+"");
                                                    m.setPropertyValue("uiid2", "gold");
                                                    m.setPropertyValue("uiid3", "bleufb");
                                                    m.setPropertyValue("uiid4", "RedLabel");
                                                    Label l = new Label();
                                                    c1.addComponent(l);
                                                    c.setName("c");
                                                    
                                                    l.setUIID("Separator");
                                                    l.setName("separator1");
                                                    l.setShowEvenIfBlank(true);
                                                    m.addActionListener(new ActionListener() {
                                                        @Override
                                                        public void actionPerformed(ActionEvent evt) {
                                                            
                                                            NavigatorData.getInstance().SetSelectedAnnonce(u);
                                                            new AfficherAnnonce(res).show();
                                                        }
                                                    });
                                                    c1.revalidate();
                                                    
                                                    
                                                    
                                                }
                                            });
                                            NetworkManager.getInstance().addToQueue(conn2);
                                            
                                            
                                            
                                        }
                                    });
                                    NetworkManager.getInstance().addToQueue(conn1);
                                }
                            });
                            NetworkManager.getInstance().addToQueue(conn);
                        }
                        }
                    } catch (IOException ex) {
                        Log.getReportingLevel();
                    }
                        
                    
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        refreshTheme();

}
}
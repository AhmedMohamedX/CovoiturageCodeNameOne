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
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



 
public class AfficherAnnonceRech extends SideMenuBaseForm {
  
Container by = new Container();
    public AfficherAnnonceRech(Resources res) {
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
        Image iu = res.getImage("téléchargement.png");
        Label t = new Label(iu);
                                    
        String datee = NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().substring(8, NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().length() - 12);
        String dateee = NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().substring(0, NavigatorData.getInstance().getSelectedAnnonce().getDate_depart().toString().length() - 25);
        Label v = new  Label(NavigatorData.getInstance().getVd()+"=>"+NavigatorData.getInstance().getVa());
        v.setUIID("RedLabel");
        
        Label date = new Label(dateee+" "+datee);
        date.setUIID("gold");
        Label prix = new Label(NavigatorData.getInstance().getSelectedAnnonce().getPrix()+" dt/personne");
        Label nb = new Label(NavigatorData.getInstance().getSelectedAnnonce().getNb_places()+" places");
        
        Label prix1 = new Label("Prix :");
        prix1.setUIID("bleufb");
        prix.setUIID("RedLabel");
        Label nb1 = new Label("Nombre de places :");
        nb1.setUIID("bleufb");
        nb.setUIID("RedLabel");
        Label name = new Label(NavigatorData.getInstance().getSelectedUser().getNom()+" | "+NavigatorData.getInstance().getSelectedUser().getSexe()+" | "+NavigatorData.getInstance().getSelectedUser().getTelephone());
        name.setUIID("bleufb");
        

        prix.setUIID("RedLabel");
                Container x = BoxLayout.encloseX(
                t,
                name
        );
                Slider r = createStarRankSlider(0);
                Button rate = new Button("Rate");
                rate.setUIID("rate");
        TextField com = new TextField();
        com.setUIID("RedLabel");
        com.setHint("écrire votre commentaire");
         by = BoxLayout.encloseY(
                x,
                v,
                date,
                prix1,
                prix,
                nb1,
                nb
                
                
                
                
        ).add(FlowLayout.encloseCenter(r,rate));
                rate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               r.setEditable(false);
               rate.setVisible(false);
               int rate = r.getProgress();
               int nb1 = NavigatorData.getInstance().getSelectedAnnonce().getNbrrate();
               int nb2 = nb1+1;
               int rate0 = NavigatorData.getInstance().getSelectedAnnonce().getRating();
               int rate1 = ((nb1*rate0)+rate)/nb2;
                
               ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/rate.php?id="+NavigatorData.getInstance().getSelectedAnnonce().getId()+"&rate="+rate1+"&nb="+nb2);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
               }
        });
        NetworkManager.getInstance().addToQueue(con);
               
            }
        });
        setupSideMenu(res);
        initGuiBuilderComponents(res);
               Button send = new Button();
        FontImage.setMaterialIcon(send, FontImage.MATERIAL_SEND);
        Container ok = BoxLayout.encloseX(com, send);
        addComponent(BorderLayout.SOUTH,ok);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                    String text = com.getText();
            if(text != ""){
            com.setText("");  
             ConnectionRequest messcon1 = new ConnectionRequest();
            messcon1.setUrl("http://127.168.0.1/pidev2017/getThread.php?id="+NavigatorData.getInstance().getSelectedAnnonce().getId()+"");
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
                
            
            String perma = "http://127.168.0.1/covoiturage-EDT-ONE/web/app_dev.php/membre/reservation/"+NavigatorData.getInstance().getSelectedAnnonce().getId();
            ConnectionRequest messcon = new ConnectionRequest();
            messcon.setUrl("http://127.168.0.1/pidev2017/InsertThread.php?id=" + NavigatorData.getInstance().getSelectedAnnonce().getId()+"&permalink="+perma+"&isc="+1+"&numc="+1);
            messcon.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                     String perma = "http://127.168.0.1/covoiturage-EDT-ONE/web/app_dev.php/membre/reservation/"+NavigatorData.getInstance().getSelectedAnnonce().getId();
            ConnectionRequest messcon0 = new ConnectionRequest();
            messcon0.setUrl("http://127.168.0.1/pidev2017/InsertComment.php?id=" + NavigatorData.getInstance().getSelectedAnnonce().getId()+"&body="+text+"&in="+""+"&m_id="+NavigatorData.getInstance().getConnectedUser().getId());
            messcon0.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    
                    
                }

            });
 NetworkManager.getInstance().addToQueue(messcon0);  
                    
                }

            });
 NetworkManager.getInstance().addToQueue(messcon);  
          

 
new AfficherAnnonceRech(res).show();
            }else{
                
               
            ConnectionRequest messcon0 = new ConnectionRequest();
            messcon0.setUrl("http://127.168.0.1/pidev2017/InsertComment.php?id=" + NavigatorData.getInstance().getSelectedAnnonce().getId()+"&body="+text+"&in="+""+"&m_id="+NavigatorData.getInstance().getConnectedUser().getId());
            messcon0.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    
                    
                }

            });
 NetworkManager.getInstance().addToQueue(messcon0); 
                new AfficherAnnonceRech(res).show();
                
                
            }
                }

            });
 NetworkManager.getInstance().addToQueue(messcon1);
                 

            }
            }
        });
       
        
    }
private void initGuiBuilderComponents(Resources res) {
 
        setLayout(new BorderLayout());
         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         c1.setScrollableY(true);
        c1.setScrollVisible(false);
        c1.setName("c1");
        addComponent(BorderLayout.CENTER,c1);
        
        
        
        
              ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/getcoms.php?id="+NavigatorData.getInstance().getSelectedAnnonce().getId());
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    if(getListCom(new String(con.getResponseData()))!=null){
                            
                    for (comment u : getListCom(new String(con.getResponseData()))) {
                        ConnectionRequest conn = new ConnectionRequest();
                        System.out.println(u);
                        String url = "http://127.168.0.1/pidev2017/select.php?id=" + u.getMembre_id().getId()+"";
                        conn.setUrl(url);
                        conn.addResponseListener(new ActionListener<NetworkEvent>() {
                            @Override
                            public void actionPerformed(NetworkEvent evt) {
                                User p = getListUsers(new String(conn.getResponseData()));
                                u.setMembre_id(p);
                                Container c = new Container(new BorderLayout());
                                MultiButton m = new MultiButton();
                                Label on = new Label();
                                
                                
                                c1.addComponent(c);
                                c.setName("c");
                                by.add(FlowLayout.encloseCenter(m));
                                FontImage.setMaterialIcon(on, FontImage.MATERIAL_CHAT_BUBBLE_OUTLINE);
                                c.addComponent(BorderLayout.WEST, on);
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
                                m.setIcon(imgURL);
                                 String datee = u.getCreated_at().toString().substring(8, u.getCreated_at().toString().length() - 12);
                                 String dateee = u.getCreated_at().toString().substring(0, u.getCreated_at().toString().length() - 25);
                                m.setPropertyValue("line1",""+dateee+" "+datee+"" );
                                m.setPropertyValue("line2", u.getBody());
                                m.setPropertyValue("uiid1", "Label");
                                m.setPropertyValue("uiid2", "Label");
  
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
                    }
                } catch (ParseException ex) {
                    Log.getReportingLevel();
                } catch (IOException ex) {
                    Log.getReportingLevel();
                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
        refreshTheme();
        
        
        
        
        
        add(BorderLayout.CENTER, by);
        by.setScrollableY(true);
        by.setScrollVisible(false);
      
}
  
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }
    private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}
   private Slider createStarRankSlider(double a) {
    Slider starRank = new Slider();
    starRank.setEditable(false);
    starRank.addActionListener(e->{
        
    });
    starRank.setEditable(true);
    starRank.setMinValue(0);
    
    starRank.setMaxValue(5);
    starRank.setProgress((int) a);
    Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
            derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
    Style s = new Style(0xffff33, 0, fnt, (byte)0);
    Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    s.setOpacity(100);
    s.setFgColor(0);
    Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
    initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
    initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
    initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
    starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
    return starRank;
}
   
   
public ArrayList<comment> getListCom(String json) throws ParseException, IOException {
        ArrayList<comment> listmsg = new ArrayList<>();
        JSONParser j = new JSONParser();

        Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
        
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("comment");
            if(list == null){
                return null;
            }
            for (Map<String, Object> obj : list) {
                 comment e = new comment();
                e.setId(Integer.parseInt(obj.get("id").toString()));
                String dateStr = obj.get("created_at").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                e.setCreated_at(dateenvoyer);
                e.setBody(obj.get("body").toString());
                User t = new User();
                t.setId(Integer.parseInt(obj.get("membre_id").toString()));
                e.setMembre_id(t);
                listmsg.add(e);

            }
        } catch (ClassCastException e) {
            Map<String, Object> list = (Map<String, Object>) etudiants.get("comment");
                 comment ee = new comment();
                ee.setId(Integer.parseInt(list.get("id").toString()));
                String dateStr = list.get("created_at").toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateenvoyer = sdf.parse(dateStr);
                ee.setCreated_at(dateenvoyer);
                ee.setBody(list.get("body").toString());
                User tt = new User();
                tt.setId(Integer.parseInt(list.get("membre_id").toString()));
                ee.setMembre_id(tt);
                listmsg.add(ee);
        }
        return listmsg;
}public User getListUsers(String json) {
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
   


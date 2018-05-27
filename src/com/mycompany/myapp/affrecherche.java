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
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.Slider;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


    

public class affrecherche extends SideMenuBaseForm {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};

    public affrecherche(Resources res) {
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
        
        addComponent(BorderLayout.CENTER,c1);
        
         
       String vd1  = NavigatorData.getInstance().getVd().toString().substring(0, NavigatorData.getInstance().getVd().toString().length() - 1);
       String va1  = NavigatorData.getInstance().getVa().toString().substring(0, NavigatorData.getInstance().getVa().toString().length() - 1);
            
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.168.0.1/pidev2017/getRecherche.php?date="+NavigatorData.getInstance().getDate()+"&vd="+vd1+"&va="+va1);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    try {
                         if(getListannonces(new String(con.getResponseData()))!=null){
                        for (Annonce u : getListannonces(new String(con.getResponseData()))) {
                            ConnectionRequest conn = new ConnectionRequest();
                            String url = "http://127.168.0.1/pidev2017/select.php?id=" + u.getMembre_id().getId()+"";
                            conn.setUrl(url);
                            conn.addResponseListener(new ActionListener<NetworkEvent>() {
                                @Override
                                public void actionPerformed(NetworkEvent evt) {
                                    User p = getListUsers(new String(conn.getResponseData()));
                                    u.setMembre_id(p);
                                    Container c = new Container(new BorderLayout());
                                    MultiButton m = new MultiButton();
                                    
                                    
                                    
                                    c1.addComponent(c);
                                    c.setName("c");
                                    
                                    c.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, m);
                                    m.setUIID("Label");
                                    m.setName("Multi_Button_1");
                                    EncodedImage encImg = EncodedImage.createFromImage(res.getImage("téléchargement.png"), false);
                                    URLImage imgURL = null;
                                    
                                    if (p.getPhotoprofilpath().toString().length() == 2) {
                                        imgURL = URLImage.createToStorage(encImg, "ss", "http://127.168.0.1/téléchargement.png");
                                        imgURL.fetch();
                                    } else {
                                        imgURL = URLImage.createToStorage(encImg, p.getPhotoprofilpath().toString(), "http://127.168.0.1/icon2.png");
                                        imgURL.fetch();
                                        
                                    }
                                    
                                    m.setIcon(imgURL);
                                    String datee = u.getDate_depart().toString().substring(8, u.getDate_depart().toString().length() - 12);
                                    String dateee = u.getDate_depart().toString().substring(0, u.getDate_depart().toString().length() - 25);
                                    m.setPropertyValue("line1",""+dateee+" "+datee+"" );
                                    m.setPropertyValue("line2", "prix : "+u.getPrix()+" dt/personne");
                                    m.setPropertyValue("line3", "nombre de places : "+u.getNb_places()+"");
                                    m.setPropertyValue("uiid1", "RedLabel");
                                    m.setPropertyValue("uiid2", "RedLabel");
                                    m.setPropertyValue("uiid3", "RedLabel");
                                    if(u.getMembre_id().getAnimaux()==0){
                                        Image an = res.getImage("forbidden_dog_animal-512.png");
                                        Label a = new Label(an);
                                        c.addComponent(BorderLayout.EAST,a);
                                    }
                                    if(u.getMembre_id().getTabagime()==0){
                                        Image ta = res.getImage("No_smoking_symbol.svg.png");
                                        Label t = new Label(ta);
                                        c.addComponent(BorderLayout.EAST,t);
                                    }
                                    
                                    Slider r = createStarRankSlider(u.getRating());
                                    r.setEditable(false);
                                    c1.add(FlowLayout.encloseCenter(r));
                                    Button f1 = new Button("Réserver");
                                    f1.setUIID("res");
                                    
                                    c1.addComponent(f1);
                                    f1.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent evt) {
                                            NavigatorData.getInstance().SetSelectedAnnonce(u);
                                            NavigatorData.getInstance().setSelectedUser(p);
                                            new AfficherAnnonceRech(res).show();
                                        }
                                    });
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
                        }}else{
                               
                                Label no = new Label("Pas de résultats pour votre recherche");
                                Container c = new Container(new BorderLayout());
                                c.addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, no);
                                c1.add(c);
                                c1.revalidate();
                                removeComponent(c1);
                                addComponent(BorderLayout.CENTER,c1);
                                
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
                User u = new User();
                u.setId(Integer.parseInt(obj.get("membre_id").toString()));
                e.setMembre_id(u);
                e.setTrajet_id(t);
                if(obj.get("nbrrate").toString().length() ==2){
                    e.setNbrrate(0);
                }else{
                e.setNbrrate(Integer.parseInt(obj.get("nbrrate").toString()));
                }
                 if(obj.get("rating").toString().length() ==2){
                e.setRating(0);
                 }else{
                     e.setRating(Integer.parseInt(obj.get("rating").toString()));
                 }
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
                User u = new User();
                u.setId(Integer.parseInt(list.get("membre_id").toString()));
                ee.setMembre_id(u);
                ee.setTrajet_id(t);
                if(list.get("nbrrate").toString().length() ==2){
                    ee.setNbrrate(0);
                }else{
                ee.setNbrrate(Integer.parseInt(list.get("nbrrate").toString()));
                }
                 if(list.get("rating").toString().length() ==2){
                ee.setRating(0);
                 }else{
                     ee.setRating(Integer.parseInt(list.get("rating").toString()));
                 }
                listmsg.add(ee);
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
                profil.setTabagime(Integer.parseInt(Mapprofil.get("tabagisme").toString()));
                profil.setAnimaux(Integer.parseInt(Mapprofil.get("animaux").toString()));
                profil.setTelephone(Integer.parseInt(Mapprofil.get("telephone").toString()));
                profil.setSexe(Mapprofil.get("sexe").toString());
                
            }
        } catch (IOException ex) {
        }
        return profil;

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
 
}


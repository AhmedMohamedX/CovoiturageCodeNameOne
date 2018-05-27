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
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.processing.Result;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.DateSpinner;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

/**
 * Represents a user profile in the app, the first form we open after the walkthru
 *
 * @author Shai Almog
 */
public class ProfileForm extends SideMenuBaseForm {
    Form hi ;
       private static final String MAPS_KEY = "AIzaSyBilfVJ4HcDcQcqcbkBeuQakGeCWZaOpgI"; // Your maps key here
       public ProfileForm(Resources res) {
       super(BoxLayout.y());
        Toolbar tb = getToolbar();
        
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("icon.png");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        profilePicLabel.addPointerReleasedListener(e->{
            new updateprofile(res).show();
        });
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> ((SideMenuBar)getToolbar().getMenuBar()).openMenu(null));
        DateSpinner s = new DateSpinner();
        Label vd = new Label("                 SHARE THE");
       vd.setUIID("gold");
        Label va = new Label("FARE");
       va.setUIID("gold");
        Container remainingTasks = BoxLayout.encloseY(
                        
                        vd
                );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                        
                        va
        );
        completedTasks.setUIID("RemainingTasks");

        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseY(
                                    new Label(NavigatorData.getInstance().getConnectedUser().getNom(), "Title")
                                  
                                )
                            ).add(BorderLayout.WEST, profilePicLabel),
                        GridLayout.encloseIn(2, remainingTasks, completedTasks)
                );
               
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_SEARCH);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        fab.getAllStyles().setMargin(BOTTOM, completedTasks.getPreferredH() - fab.getPreferredH() / 2);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp, CENTER, BOTTOM));
                        
        
        setupSideMenu(res);
        
          
             hi = new Form("AutoComplete", new BoxLayout(BoxLayout.Y_AXIS));

if(MAPS_KEY == null) {
    hi.add(new SpanLabel("This demo requires a valid google API key to be set in the constant apiKey, "
            + "you can get this key for the webservice (not the native key) by following the instructions here: "
            + "https://developers.google.com/places/web-service/get-api-key"));
    hi.getToolbar().addCommandToRightBar(MAPS_KEY, null, e -> Display.getInstance().execute("https://developers.google.com/places/web-service/get-api-key"));
    hi.show();
    return;
}
 final DefaultListModel<String> options = new DefaultListModel<>();

 AutoCompleteTextField ac = new AutoCompleteTextField(options) {
     @Override
     protected boolean filter(String text) {
         if(text.length() == 0) {
             return false;
         }
         String[] l = searchLocations(text);
         if(l == null || l.length == 0) {
             return false;
         }

         options.removeAll();
         for(String s : l) {
             options.addItem(s);
         }
         return true;
     }
 };
  AutoCompleteTextField ac1 = new AutoCompleteTextField(options) {
     @Override
     protected boolean filter(String text) {
         if(text.length() == 0) {
             return false;
         }
         String[] l = searchLocations(text);
         if(l == null || l.length == 0) {
             return false;
         }

         options.removeAll();
         for(String s : l) {
             options.addItem(s);
             
         }
         return true;
     }
 };

 ac.setMinimumElementsShownInPopup(5);
 ac1.setMinimumElementsShownInPopup(5);
 ac.setUIID("RedLabel");
 ac1.setUIID("RedLabel");
 
 ac.setHint("Ville départ");
 ac1.setHint("Ville arrivée");
 Container ok = GridLayout.encloseIn(2, ac, ac1);
 
 DateSpinner s0 = new DateSpinner();
 s0.setVisible(false);
           
 add( s);
 add(ok);
           add(s0);
 fab.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {
               
               
               String date = s.getCurrentYear()+"-";
               String month = String.valueOf(s.getCurrentMonth());
               String day = String.valueOf(s.getCurrentDay());
               if(month.length()==1){
                   month="0"+month;
               }
               date=date+month+"-";
               
               if(day.length()==1){
                   day="0"+day;
               }
               date=date+day;
               System.out.println(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateenvoyer=null;
               try {
                   dateenvoyer = sdf.parse(date);
               } catch (ParseException ex) {
                   Log.getReportingLevel();
               }
                System.out.println(dateenvoyer);
               Date sys = new Date();
               System.out.println(sys);
               
               
               String tday = String.valueOf( s0.getCurrentDay());
               if(tday.length()==1){
                   tday="0"+tday;
               }
               
               String tm = String.valueOf( s0.getCurrentMonth());
               if(tm.length()==1){
                   tm="0"+tm;
               }
                  
               String ty = String.valueOf( s0.getCurrentYear());
               int test=0;
               if(Integer.parseInt(ty)<s.getCurrentYear()){
                   test=1;
               }
               if(Integer.parseInt(ty)==s.getCurrentYear()){
                   if(Integer.parseInt(tm)<Integer.parseInt(month)){
                   test=1;
               }
               }
               if(Integer.parseInt(ty)==s.getCurrentYear()){
                   if(Integer.parseInt(tm)==Integer.parseInt(month)){
                      if(Integer.parseInt(tday)<=Integer.parseInt(day)){
                   test=1;
               }
               }
               }
               
               
               if(test==1){
               
               if(ac.getText()=="" || ac1.getText()==""){
                   Dialog.show("Erreur", "Merci de remplir tous les champs", "Fermer", null);
               }else{
                   NavigatorData.getInstance().setVd(ac.getText());
                   NavigatorData.getInstance().setVa(ac1.getText());
                   NavigatorData.getInstance().setDate(date);
                   new affrecherche(res).show();
               }
               
               }else{
                   Dialog.show("Erreur", "Merci de choisir une date supérieur à la date d'aujoud'hui", "Fermer", null);
               }
               
                   
           }
       });
 
    }
    String[] searchLocations(String text) {
    try {
        if(text.length() > 0) {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json");
            r.addArgument("key",MAPS_KEY );
            r.addArgument("input", text);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            String[] res = Result.fromContent(result).getAsStringArray("//description");
            return res;
        }
    } catch(Exception err) {
        Log.e(err);
    }
    
    return null;
}  
    
   

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }

    public Form getF() {
        return hi;
    }

    public void setF(Form f) {
        this.hi = f;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;


import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author adel
 */
public class googlemapsearch {
    Form hi ;
       private static final String MAPS_KEY = "AIzaSyBilfVJ4HcDcQcqcbkBeuQakGeCWZaOpgI"; // Your maps key here

    public  googlemapsearch(Resources theme){
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
 hi.add(ac);
 hi.add(ac1);
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
    

    public Form getF() {
        return hi;
    }

    public void setF(Form f) {
        this.hi = f;
    }
    
}

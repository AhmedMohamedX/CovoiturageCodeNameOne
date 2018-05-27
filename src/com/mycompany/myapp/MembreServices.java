/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mehdi9951
 */
public class MembreServices {
    public List<User> getMembresButCurrent()
    {
        List<User> membres = new ArrayList<>();
        
        try
        {
            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://127.168.0.1/pidev2017/getMembersButCurrent.php?membre_id=" + NavigatorData.getInstance().getConnectedUser().getId());
            
            NetworkManager.getInstance().addToQueueAndWait(con);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(con.getResponseData()), "UTF-8"));
            
            List<Map<String, Object>> list = new ArrayList<>();
            
            try {
                list = (java.util.List<Map<String, Object>>) response.get("membre");
            } catch (ClassCastException e) {
                // when only one record is found causes above exception
                list.add((Map<String, Object>) response.get("membre"));
            }
            
            for (Map<String, Object> obj : list) {
                User m = new User();
                
                m.setId(Integer.valueOf(obj.get("id").toString()));
                m.setNom(obj.get("nom").toString());
                m.setPrenom(obj.get("prenom").toString());
                
                membres.add(m);
            }

        }
        catch (IOException ex) {
            Log.e(ex);
        }
        catch (NullPointerException err) {
            Dialog.show("Notice", "Pas de d'utilisateurs trouvées", "OK", null);
            Log.e(err);
        }
        
        return membres;
    }
}

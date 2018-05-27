/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.io.CharArrayReader;
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
public class ReclamationsServices {
    public ArrayList<reclamations> getListReclamation() {
        ArrayList<reclamations> listReclamations = new ArrayList<>();
        
        try
        {
            ConnectionRequest con = new ConnectionRequest();
            con.setUrl("http://127.168.0.1/pidev2017/selectRecsByMID.php?membre_id=" + NavigatorData.getInstance().getConnectedUser().getId());
            
            NetworkManager.getInstance().addToQueueAndWait(con);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());
            
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(con.getResponseData()), "UTF-8"));
            
            List<Map<String, Object>> list = new ArrayList<>();
            
            try {
                list = (java.util.List<Map<String, Object>>) response.get("reclamation");
            } catch (ClassCastException e) {
                // when only one record is found causes above exception
                list.add((Map<String, Object>) response.get("reclamation"));
            }
            
            for (Map<String, Object> obj : list) {
                reclamations e = new reclamations();
                User m = new User();
                
                m.setNom(obj.get("nom").toString());
                m.setPrenom(obj.get("prenom").toString());
                
                e.setId(Integer.valueOf(obj.get("id").toString()));
                e.setSujet(obj.get("sujet").toString());
                e.setMessage(obj.get("message").toString());
                e.setDate(new SimpleDateFormat("yyyy-MM-d").parse(obj.get("date").toString()));
                e.setReponse(obj.get("reponse").toString());
                
                User targetM = new User();
                if (e.getSujet().equals("membre"))
                    targetM.setNom(obj.get("tmFullName").toString());
                else
                    targetM.setId(0);
                
                e.setTargetMember(targetM);
                e.setMembre(m);
                
                listReclamations.add(e);
            }

        }
        catch (IOException | ParseException ex) {
            Log.e(ex);
        }
        catch (NullPointerException err) {
            Dialog.show("Warning", "Pas de reclamations trouvées", "OK", null);
            Log.e(err);
        }
        
       return listReclamations;
    }
    
    public boolean deleteReclamation(int rec_id) {
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://127.168.0.1/pidev2017/suppRec.php", true);

            connectionRequest.addArgument("rec_id", String.valueOf(rec_id));

            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());

            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("status").toString().equals("success"))
                return true;
        }
        catch (Exception err)
        {
            Log.e(err);
        }
        
        return false;
    }
    
    public boolean insertReclamation(reclamations reclamation) {
        try {
            ConnectionRequest connectionRequest = new ConnectionRequest(""
                    + "http://127.168.0.1/pidev2017/insertRec.php", true);

            connectionRequest.addArgument("sujet", reclamation.getSujet());
            connectionRequest.addArgument("membre_id", String.valueOf(NavigatorData.getInstance().getConnectedUser().getId()));
            connectionRequest.addArgument("message", reclamation.getMessage());
            connectionRequest.addArgument("targetMemberID", String.valueOf(reclamation.getTargetMember().getId()));

            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            NetworkManager.getInstance().addErrorListener(e -> e.consume());

            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(
                    new ByteArrayInputStream(connectionRequest.getResponseData()), "UTF-8"));
            
            if (response.get("status").toString().equals("success"))
                return true;
        }
        catch (Exception err)
        {
            Log.e(err);
        }
        
        return false;
    }
}

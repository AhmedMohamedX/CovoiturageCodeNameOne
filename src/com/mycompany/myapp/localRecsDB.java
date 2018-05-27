/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.Log;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mehdi9951
 */
public class localRecsDB {
       private Database localDB;
    
    public localRecsDB() {
        boolean exists = Database.exists("memberRecs");
        try
        {
            localDB = Database.openOrCreate("memberRecs");
            
            if (!exists)
            {
                // date type is either Text, Real or Numeric 
                // Our date format is "YYYY-MM-DD HH:MM:SS.SSS" of type TEXT.
                localDB.execute("create table reclamations(id integer PRIMARY KEY, "
                        + "membre_id integer, sujet text, targetMemberID integer, "
                        + "date text, message text, reponse text);");
            }
        }
        catch (IOException err) {
            Log.e(err);
        }
    }
    
    public ArrayList<reclamations> localRecsList()
    {
        ArrayList<reclamations> reclamationsList = new ArrayList<>();
        
        try
        {
            // dans notre cas on ne sauvegarde que la derniere liste des reclamations
            // qui ne concernera qu'un utilisateur
            Cursor c = localDB.executeQuery("select * from reclamations");
            
            while (c.next())
            {
                reclamations rec = new reclamations();
                Row r = c.getRow();
                
                rec.setId(r.getInteger(0));
                rec.setMembre(new User(r.getInteger(1), "", ""));
                rec.setSujet(r.getString(2));
                rec.setTargetMember(new User(r.getInteger(3), "", ""));
                rec.setDate(new SimpleDateFormat("yyyy-MM-d").parse(r.getString(4)));
                rec.setMessage(r.getString(5));
                rec.setReponse(r.getString(6));
                
                reclamationsList.add(rec);
            }
        }
        catch (IOException | ParseException err) {
            Log.e(err);
        }
        
        return reclamationsList;
    }
    
    public boolean insertReclamation(reclamations reclamation)
    {
        // date format
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
        try
        {
            localDB.execute("insert into reclamations (id, membre_id, sujet, targetMemberID, date, message, reponse) " 
                    + "values(" + reclamation.getId() + ", " + reclamation.getMembre().getId() + ", '" + reclamation.getSujet() + "', " 
                    + reclamation.getTargetMember().getId()+ ", '"+ dateFormat.format(reclamation.getDate()) + "', '" 
                    + reclamation.getMessage()+"', '" + reclamation.getReponse()+ "');");
        }
        catch (IOException ex)
        {
            Log.e(ex);
            return false;
        }
        
        return true;
    }
    
    /**
     * @param booking_id
     * @param purgeClause used to delete the entire local database content
     * @return 
     */
    public boolean purgeDB()
    {
        try
        {
            localDB.execute("delete from reclamations");
        }
        catch (IOException ex)
        {
            Log.e(ex);
            return false;
        }
        
        return true;
    }
}

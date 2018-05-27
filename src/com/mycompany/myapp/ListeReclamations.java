/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mehdi9951
 */
public class ListeReclamations {
    Form listForm;
    ArrayList<reclamations> userRecs = new ReclamationsServices().getListReclamation();
    // used to store all modelContainers
    List<Component> ctnModels = new ArrayList<>();
    
    public ListeReclamations(Resources theme)
    {
        
        listForm = new Form("Liste des reclamations", new BoxLayout(BoxLayout.Y_AXIS));
        
        // date format
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        
        // setting container, picker and reset
        Container ctnResearch = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Picker datePicker = new Picker();
        Button btnReset = new Button();
        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_MOVIE_FILTER, s);
        btnReset.setIcon(icon);
        
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setUIID("RedLabel");
        datePicker.addActionListener((e) -> {
            for (Component model : ctnModels)
                listForm.removeComponent(model);
            
            ctnModels.clear();
            refreshReclamations(dateFormat, theme, datePicker.getDate());
        });
        
        btnReset.addActionListener((e) -> {
            for (Component model : ctnModels)
                listForm.removeComponent(model);
            
            ctnModels.clear();
            
            refreshReclamations(dateFormat, theme, null);
        });
        
        ctnResearch.add(datePicker);
        ctnResearch.add(btnReset);
        
        listForm.add(ctnResearch);
        
        // render list to a model and add them to form
        refreshReclamations(dateFormat, theme, null);
        
        icon = FontImage.createMaterial(FontImage.MATERIAL_ADD_BOX, s);
        Command addRecCmd = new Command("Reclamer", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new InsererReclamation(theme, listForm).getInsertForm().show();
            }  
        };
            
        icon = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s);
        Command getRecStatsCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
               new ProfileForm(theme).show();
            }  
        };
        
        listForm.setToolbar(new Toolbar());
        listForm.getToolbar().addCommandToRightBar(addRecCmd);
        listForm.getToolbar().addCommandToLeftBar(getRecStatsCmd);
        
        
    }

    private Label createSeparator()
    {
        Label separator = new Label("");
        separator.setShowEvenIfBlank(true);
            
        return separator;
    }
    
    public Form getListForm() {
        return listForm;
    }

    private void refreshReclamations(DateFormat dateFormat, Resources theme, Date targetSearchDate) {
        for (reclamations reclamation : userRecs) 
        {
            Container ctnModel = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container ctnUserProfile = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container ctnTheOthers = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container ctnButtons = new Container(new BoxLayout(BoxLayout.X_AXIS));
            ctnUserProfile.setUIID("RedLabel");
           
            Image profilePic = theme.getImage("icon.png");
            Image mask = theme.getImage("round-mask.png");
             profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
             Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
             profilePicLabel.setMask(mask.createMask());
             
            Button btnDelete = new Button();
            
            Style s = UIManager.getInstance().getComponentStyle("Title");
            FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
            btnDelete.setIcon(icon);
            
            btnDelete.addActionListener((e) -> {
                if (new ReclamationsServices().deleteReclamation(reclamation.getId()))
                {
                    Dialog.show("Notice", "Reclamation Supprimée", "OK", null);
                    listForm.removeComponent(ctnModel);
                }
                else
                    Dialog.show("Error", "Une Erreur est survenue", "OK", null);
            });
            
            ctnUserProfile.add(profilePicLabel);
            ctnUserProfile.add("  " + reclamation.getMembre().getNom() + " " + reclamation.getMembre().getPrenom());
            
            ctnTheOthers.add(createSeparator());
            ctnTheOthers.add(createSeparator());
            ctnTheOthers.add("Date Reclamation : " + dateFormat.format(reclamation.getDate()));
            ctnTheOthers.add(createSeparator());
            ctnTheOthers.add("Sujet : " + reclamation.getSujet());
            
            if (reclamation.getSujet().equals("membre"))
            {
                ctnTheOthers.add(createSeparator());
                ctnTheOthers.add("Target Member : " + reclamation.getTargetMember().getNom());
            }
            
            ctnTheOthers.add(createSeparator());
            ctnTheOthers.add("Message : " + reclamation.getMessage());
            ctnTheOthers.add(createSeparator());
            ctnTheOthers.add("Response : " + reclamation.getReponse());
            
            ctnButtons.add(btnDelete);
            
            ctnTheOthers.add(createSeparator());
            ctnTheOthers.add(ctnButtons);
            
            ctnModel.add(ctnUserProfile);
            ctnModel.add(ctnTheOthers);
            
            ctnModel.setScrollableX(true);
            
            
                if (targetSearchDate != null)
                {       
                    Calendar recDate = Calendar.getInstance();
                    Calendar targetDate = Calendar.getInstance();
                    
                    recDate.setTime(reclamation.getDate());
                    targetDate.setTime(targetSearchDate);
                    
                    if (recDate.get(Calendar.YEAR) == targetDate.get(Calendar.YEAR) &&
                            recDate.get(Calendar.MONTH) == targetDate.get(Calendar.MONTH) &&
                                recDate.get(Calendar.DAY_OF_MONTH) == targetDate.get(Calendar.DAY_OF_MONTH))
                    {
                        ctnModels.add(ctnModel);
                        listForm.add(ctnModel);
                    }
                }
                else
                {
                    ctnModels.add(ctnModel);
                    listForm.add(ctnModel);
                }
            
        }
        
    }
}

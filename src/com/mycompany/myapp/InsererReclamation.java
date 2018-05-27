/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mehdi9951
 */
public class InsererReclamation {
    Form insertForm;
    
    public InsererReclamation(Resources theme, Form listForm) {
        LocalNotification n = new LocalNotification();
n.setId("ok");
n.setAlertBody("It's time to take a break and look at me");
n.setAlertTitle("Break Time!");

        
        List<User> targetMs = new MembreServices().getMembresButCurrent();
        
        insertForm = new Form("Envoyer Reclamation", new BoxLayout(BoxLayout.Y_AXIS));
        
        Container ctnAllOfThem = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        Picker sujetPicker = new Picker();
        Picker membresPicker = new Picker();
        sujetPicker.setUIID("RedLabel");
        membresPicker.setUIID("RedLabel");
        Label lblMs = new Label("Membre a Signaler");
        lblMs.setUIID("bleufb");
        TextField txtMessage = new TextField("", "Message");
        txtMessage.setUIID("RedLabel");
        Button btnInsert = new Button("Envoyer");
        btnInsert.setUIID("LoginButton");
        sujetPicker.setType(Display.PICKER_TYPE_STRINGS);
        membresPicker.setType(Display.PICKER_TYPE_STRINGS);
        
        if (targetMs.isEmpty())
            sujetPicker.setStrings("Application");
        else
            sujetPicker.setStrings("Application", "Membre");
        
        sujetPicker.setSelectedString("Application");
        
        sujetPicker.addActionListener((e) -> {
            if (sujetPicker.getSelectedString().equals("Membre"))
            {
                insertForm.removeComponent(btnInsert);
                insertForm.add(lblMs);
                insertForm.add(membresPicker);
                insertForm.add(btnInsert);
            }
            else
            {
                insertForm.removeComponent(lblMs);
                insertForm.removeComponent(membresPicker);
            }
        });
        
        List<String> membersNames = new ArrayList<>();
        for (User targetM : targetMs)
            membersNames.add(targetM.getNom() + " " + targetM.getPrenom());
        
        membresPicker.setStrings(membersNames.toArray(new String[0]));
        membresPicker.setSelectedString(membersNames.get(0));
        
        btnInsert.addActionListener((e) -> {
            if (!txtMessage.getText().equals(""))
            {
                reclamations reclamation = new reclamations();
                
                reclamation.setSujet(sujetPicker.getSelectedString());
                reclamation.setMessage(txtMessage.getText());
                
                // picker getSelectedStringIndex doesn't exist so use of below logic
                User targetM = new User(0, "", "");
                
                for (User target : targetMs)
                    
                    // string equality with space doesn't work there fore below condition length
                    if ((target.getNom().equals(membresPicker.getSelectedString().
                             substring(membresPicker.getSelectedString().indexOf(" ") + 1, membresPicker.getSelectedString().length()))))
                        targetM = target;
                
                
                reclamation.setTargetMember(targetM);
                
                if (new ReclamationsServices().insertReclamation(reclamation))
                {Display.getInstance().scheduleLocalNotification(
        n,
        System.currentTimeMillis() + 10 * 1000, // fire date/time
        LocalNotification.REPEAT_MINUTE  // Whether to repeat and what frequency
);
                    
                    Dialog.show("Notice", "Reclamation Envoyée", "OK", null);
                    
                    new ListeReclamations(theme).getListForm().showBack();
                }
                else
                    Dialog.show("Error", "Une Erreur C'est Produite", "OK", null);
            }
            else
            {
                Dialog.show("Error", "Remplissez tous les champs", "OK", null);
            }
        });
        
        ctnAllOfThem.add(createSeparator());
        ctnAllOfThem.add("Message");
        ctnAllOfThem.add(txtMessage);
        ctnAllOfThem.add(createSeparator());
        ctnAllOfThem.add("Sujet");
        ctnAllOfThem.add(sujetPicker);
        ctnAllOfThem.add(createSeparator());
//        ctnAllOfThem.add(lblMs);
//        ctnAllOfThem.add(membresPicker);
        ctnAllOfThem.add(createSeparator());
        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_EXIT_TO_APP, s);
        Command goBackCmd = new Command("Retourner", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listForm.showBack();
            }  
        };
            
        insertForm.setToolbar(new Toolbar());
        insertForm.getToolbar().addCommandToRightBar(goBackCmd);
        
        insertForm.add(ctnAllOfThem);
        insertForm.add(btnInsert);
    }

    private Label createSeparator()
    {
        Label separator = new Label("");
        separator.setShowEvenIfBlank(true);
            
        return separator;
    }
    
    public Form getInsertForm() {
        return insertForm;
    }
}

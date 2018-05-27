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
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.CubicLineChart;
import com.codename1.charts.views.PieChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Shai Almog
 */
public class stats extends SideMenuBaseForm {
    private static final int[] COLORS = {0xf8e478, 0x60e6ce, 0x878aee};
    private static final String[] LABELS = {"Design", "Coding", "Learning"};
    Form f2,f3, f6;

    public stats(Resources res) {
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
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH, 
                        FlowLayout.encloseIn(
                                new Label(NavigatorData.getInstance().getConnectedUser().getNom(), "WelcomeBlue")
                        ));
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
         setupSideMenu(res);
        
         createPieChartForm();
         
         
         
         
    }

  
    
    @Override
    protected void showOtherForm(Resources res) {
        new ProfileForm(res).show();
    }
private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 10});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
   protected CategorySeries buildCategoryDataset(String title, ArrayList<stat>stats) {
        CategorySeries series = new CategorySeries(title);
       
        for (stat stat : stats) {
            series.add(stat.getDateReservation(), stat.getCount());

        }

        return series;
    }
     
    public ArrayList<stat> getStat(String json) {
        ArrayList<stat> Stat = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> categories = j.parseJSON(new CharArrayReader(json.toCharArray()));

            ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) categories.get("stat");           

            for (Map<String, Object> obj : list) {
                stat c = new stat();
                c.setCount(Integer.parseInt(obj.get("count").toString()));
                c.setDateReservation(obj.get("date_reservation").toString());
                Stat.add(c);
            }

        


        } catch (IOException ex) {
        }
        return Stat;
    }
      ArrayList<stat> liste = new ArrayList<>();

    public Form createPieChartForm() {

        ConnectionRequest con7 = new ConnectionRequest();
        con7.setUrl("http://127.168.0.1/pidev2017/stat.php?id=34");
        con7.addResponseListener((NetworkEvent evt) -> {
            liste = getStat(new String(con7.getResponseData()));
            // Set up the renderer
            int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
            DefaultRenderer renderer = buildCategoryRenderer(colors);
            renderer.setZoomButtonsVisible(true);
            renderer.setZoomEnabled(true);
            renderer.setChartTitleTextSize(20);
            renderer.setDisplayValues(true);
            renderer.setShowLabels(true);
            SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
            r.setGradientEnabled(true);
            r.setGradientStart(0, ColorUtil.BLUE);
            r.setGradientStop(0, ColorUtil.GREEN);
            r.setHighlighted(true);
            
            // Create the chart ... pass the values and renderer to the chart object.
            PieChart chart = new PieChart(buildCategoryDataset("Statistique", liste), renderer);
            
            // Wrap the chart in a Component so we can add it to a form
            ChartComponent d = new ChartComponent(chart);
            
            // Create a form and show it.
//                f.setLayout(new BorderLayout());
//                f.addComponent(BorderLayout.CENTER, d);
//              //  f.show();


    Form f = new Form("Statistique", new BorderLayout());
    add(BorderLayout.CENTER, d);
    refreshTheme();
//    f.show();
   

               

        });
        NetworkManager.getInstance().addToQueue(con7);
        return f6;
    }
}

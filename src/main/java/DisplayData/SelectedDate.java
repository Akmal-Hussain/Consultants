/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplayData;

import java.awt.Color;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.java.ReadData.DatesReader;
import org.jdatepicker.ComponentColorDefaults;
import org.jdatepicker.ComponentTextDefaults;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.SqlDateModel;


/**
 *
 * @author pi
 */
public class SelectedDate {
    LocalDate date;
    JButton button;
    LocalDate superDate;
  
    public void setSuperDate(LocalDate d) {
        this.superDate = d;
    }

    public LocalDate getDate() {
        return date;
    }
    
            public SelectedDate(JComponent b, JFrame f){
        
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.MON, "M");
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.TUE, "TU");
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.WED, "W");
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.THU, "TH");
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.FRI, "F");
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.SAT, "Sa");
                ComponentTextDefaults.getInstance().setText(ComponentTextDefaults.Key.SUN, "Su");
                
                ComponentColorDefaults defaults = ComponentColorDefaults.getInstance();

        defaults.setColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR, Color.ORANGE);
        defaults.setColor(ComponentColorDefaults.Key.BG_MONTH_SELECTOR, Color.BLACK);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_HEADER, Color.RED);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_HEADER, Color.GREEN);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_THIS_MONTH, Color.BLACK);
        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_OTHER_MONTH, Color.DARK_GRAY);
        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY, Color.YELLOW);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID, Color.LIGHT_GRAY);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_NOT_SELECTABLE, Color.RED);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_SELECTED, Color.BLACK);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_SELECTED, Color.YELLOW);

        defaults.setColor(ComponentColorDefaults.Key.FG_GRID_TODAY_SELECTED, Color.RED);
        defaults.setColor(ComponentColorDefaults.Key.BG_GRID_TODAY_SELECTED, Color.YELLOW);

        defaults.setColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_ENABLED, Color.PINK);
        defaults.setColor(ComponentColorDefaults.Key.FG_TODAY_SELECTOR_DISABLED, Color.YELLOW);
        defaults.setColor(ComponentColorDefaults.Key.BG_TODAY_SELECTOR, Color.BLACK);

        defaults.setColor(ComponentColorDefaults.Key.POPUP_BORDER, Color.WHITE);
        
                SetLookAndFeel.setLookAndFeel();
               
           
        SqlDateModel model = new SqlDateModel();
        
        
        
        JDatePanel datePanel = new JDatePanel(model);
        
       
        
        //datePicker = new JDatePickerImpl(datePanel, null);
        Popup pop = PopupFactory.getSharedInstance().getPopup(b, datePanel, b.getLocationOnScreen().x+100, b.getLocationOnScreen().y-50);
pop.show();
        
datePanel.getModel().addChangeListener(new ChangeListener(){


            @Override
            public void stateChanged(ChangeEvent pce) {
                Date selectedDate = (Date) datePanel.getModel().getValue();
                if (selectedDate != null) {
                    date = selectedDate.toLocalDate();
                    System.out.println(date);
//                    superDate = date;
                    changeDate(date);
                    /*System.out.println(DatesReader.getRange()[0]);
                    DatesReader.updateDatesReader();
                    pop.hide();
                    f.dispose();
                    new ConfirmDates();*/
                }
               
                
               
            }
        }
);
        
     // date = selectedDate.toLocalDate();
       // System.out.println(date);

    }
                
     public void changeDate(LocalDate d) {
         //DatesReader.getRange()[0] = d;
     }          
            
            public static void main(String[] args) {
                //new SelectedDate();
    }
/*
    @Override
    public void actionPerformed(ActionEvent ae) {
        

       JDatePickerImpl datePicker;
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
p.put("text.today", "Today");
p.put("text.month", "Month");
p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        datePicker = new JDatePickerImpl(datePanel, null);
                                                 Popup pop = PopupFactory.getSharedInstance().getPopup(button, datePanel, button.getLocationOnScreen().x, button.getLocationOnScreen().y-50);
pop.show();
        
datePanel.getModel().addChangeListener(new ChangeListener(){


            @Override
            public void stateChanged(ChangeEvent pce) {
                Date selectedDate = (Date) datePanel.getModel().getValue();
                if (selectedDate != null) {
                    date = selectedDate.toLocalDate();
                    System.out.println(date);
                }
               
                
               
            }
        }
);
        
     // date = selectedDate.toLocalDate();
       // System.out.println(date);

    }*/
}

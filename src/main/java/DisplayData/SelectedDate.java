/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplayData;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.java.ReadData.DatesReader;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

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
                super();
                JDatePickerImpl datePicker;
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
p.put("text.today", "Today");
p.put("text.month", "Month");
p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        datePicker = new JDatePickerImpl(datePanel, null);
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
                    System.out.println(DatesReader.getRange()[0]);
                    DatesReader.updateDatesReader();
                    pop.hide();
                    f.dispose();
                    new ConfirmDates();
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

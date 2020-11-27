/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplayData;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.ReadData.DatesReader;

/**
 *
 * @author pi
 */
public class ConfirmData extends JFrame{

    /**
     * @param args the command line arguments
     */
    public ConfirmData() {
        super("Confirm Data");
        SetLookAndFeel.setLookAndFeel();
        setSize(800,600);
        setLocationRelativeTo(null);
        
        BorderLayout b = new BorderLayout();
        b.setVgap(30);
        
        JPanel fullPane = new JPanel();
        fullPane.setLayout(b);
        setBackground(fullPane.getBackground());
        
        JPanel titlePane = new JPanel();
        JLabel title = new JLabel("Dates");
        title.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        titlePane.add(title);
        
        JPanel infoPane = new JPanel();
        GridLayout g = new GridLayout(3,1);       
        infoPane.setLayout(g);
        
        
        JPanel rangePane = new JPanel();
        rangePane.setLayout(g);

        FlowLayout f = new FlowLayout();
        f.setAlignment(FlowLayout.LEFT);
        
        JPanel rangeTitle = new JPanel();
        JPanel rangeFrom = new JPanel();
        JPanel rangeTo = new JPanel();
        
        rangeTitle.setLayout(f);
        rangeFrom.setLayout(f);
        rangeTo.setLayout(f);
       
        JLabel rangeTitlE = new JLabel("Date Range");
        rangeTitlE.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        JLabel rangeFroM = new JLabel("From: ");
        JLabel rangeTO =   new JLabel("To:     ");
        
        JButton from = new JButton(DatesReader.getRange()[0].toString());
        JButton to = new JButton(DatesReader.getRange()[1].toString());

        
        rangeTitle.add(rangeTitlE);
        rangeFrom.add(rangeFroM);
        rangeFrom.add(from);
        rangeTo.add(rangeTO);
        rangeTo.add(to);
        
        rangePane.add(rangeTitle);
        rangePane.add(rangeFrom);
        rangePane.add(rangeTo);
        
        
        JPanel bankHolidayPane = new JPanel();
        bankHolidayPane.setLayout(f);
        
        JPanel bankHolidayTitle = new JPanel();
        JLabel bankHolidayTitlE = new JLabel("Bank Holidays:");
        bankHolidayTitlE.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        bankHolidayTitle.add(bankHolidayTitlE);
        
        JPanel bankHolidayButtons = new JPanel();
        
        int x = DatesReader.getBankHolidays().length;
        bankHolidayButtons.setLayout(new GridLayout(x,1));
        
        for (LocalDate bankHoliday : DatesReader.getBankHolidays()) {
        JButton bankHolidayButton = new JButton(bankHoliday.toString());
        bankHolidayButtons.add(bankHolidayButton);
        }
        
        bankHolidayPane.add(bankHolidayTitle);
        bankHolidayPane.add(bankHolidayButtons);
        
        JPanel schoolHolidayPane = new JPanel();
        schoolHolidayPane.setLayout(f);
        
        JPanel schoolHolidayTitle = new JPanel();
        JLabel schoolHolidayTitlE = new JLabel("School Holidays:");
        schoolHolidayTitlE.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        schoolHolidayTitle.add(schoolHolidayTitlE);
        
        JPanel schoolHolidayButtons = new JPanel();
        
        int y= DatesReader.getSchoolHolidays().length;
        System.out.println(y);
        
        schoolHolidayButtons.setLayout(new GridLayout(y,1));
        for (LocalDate [] dates : DatesReader.getSchoolHolidays()) {
            JPanel holidayPanel = new JPanel();
            JButton schoolHolidayButton1 = new JButton(dates[0].toString());
            JLabel arrow = new JLabel("  \u21e8  ");
            arrow.setFont(new Font(Font.SERIF, Font.BOLD, 30));
            JButton schoolHolidayButton2 = new JButton(dates[1].toString());
            holidayPanel.add(schoolHolidayButton1);
            holidayPanel.add(arrow);
            holidayPanel.add(schoolHolidayButton2);
            schoolHolidayButtons.add(holidayPanel);
                    
        }
       // JButton schoolHolidayButton = new JButton("32104812080");
       // JButton schoolHolidayButton2 = new JButton("9843928423 \u21e8");
       // schoolHolidayButtons.add(schoolHolidayButton);
       // schoolHolidayButtons.add(schoolHolidayButton2);
        
        schoolHolidayPane.add(schoolHolidayTitle);
        schoolHolidayPane.add(schoolHolidayButtons);
                
        
        JPanel lowerPane = new JPanel();
        lowerPane.setLayout(new BorderLayout());
        JPanel modifyOrConfirm = new JPanel();
        modifyOrConfirm.setLayout(new BorderLayout());
        JButton modify = new JButton("Modify");
        JButton confirm = new JButton("Confirm");
        modifyOrConfirm.add(modify,BorderLayout.WEST);
        modifyOrConfirm.add(confirm, BorderLayout.EAST);
        lowerPane.add(modifyOrConfirm, BorderLayout.EAST);
    
                
        
        infoPane.add(rangePane);
        infoPane.add(bankHolidayPane);
        infoPane.add(schoolHolidayPane);
        
        fullPane.add(lowerPane, BorderLayout.SOUTH);
        fullPane.add(infoPane, BorderLayout.CENTER);
        fullPane.add(titlePane, BorderLayout.NORTH);
        add(fullPane);
                
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           setVisible(true);
          
    }
    
   @Override
    public Insets getInsets() {
        return new Insets(50, 10, 10, 10);
    }
   
    public static void main(String[] args) {
   new DatesReader("src/main/resources/Data/Dates.xml");
        new ConfirmData();
    }
    
}

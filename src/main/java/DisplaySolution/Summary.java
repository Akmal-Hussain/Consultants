/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplaySolution;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.java.DisplayData.SetLookAndFeel;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.RunData.Shift;
import main.java.RunData.ShiftList;

/**
 *
 * @author pi
 */
public class Summary extends JFrame {

    public Summary(ShiftList solved) {
        super("Consultant Summary");
        SetLookAndFeel.setLookAndFeel();
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
              Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 30);
        Font font2 = new Font(Font.SERIF, Font.BOLD, 20);
        

        JPanel northPanel = new JPanel(new BorderLayout());
        //Title
        JPanel pagetitle = new JPanel();
        JLabel pageTitle = new JLabel("Solving Problem");
        pagetitle.add(pageTitle);

        //Start Box
        JPanel buttons = new JPanel();
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        buttons.add(close);

        northPanel.add(pagetitle, BorderLayout.CENTER);
        northPanel.add(buttons, BorderLayout.EAST);
        pane.add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout((ConsultantList.getConsultantList().size() + 1), 7));

        String[] labels = new String[]{"Consultants","COW Weeks", "COW Weekends", "NOW Weeks", "NOW Weekends", "Paed On Calls", "Neo On Calls"};

        for (String label : labels) {
            JPanel labelPanel = new JPanel();
            JLabel labelLabel = new JLabel(label);
            labelLabel.setFont(font2);
            labelPanel.add(labelLabel);
            centerPanel.add(labelPanel);
        }

        for (ConsultantReader c : ConsultantList.getConsultantList()) {

            JPanel consultant = new JPanel();
            JLabel consultAnt = new JLabel(c.toString());
            consultant.add(consultAnt);
            centerPanel.add(consultant);
            for (int x = 0; x < 6; x++) {
                String shiftType = "";
                DayOfWeek dayOfWeek = null;
                switch (x) {
                    case 0:
                        shiftType = "COW";
                        dayOfWeek = DayOfWeek.MONDAY;
                        break;
                    case 1:
                        shiftType = "COW";
                        dayOfWeek = DayOfWeek.FRIDAY;
                        break;
                    case 2:
                        shiftType = "NOW";
                        dayOfWeek = DayOfWeek.MONDAY;
                        break;
                    case 3:
                        shiftType = "NOW";
                        dayOfWeek = DayOfWeek.FRIDAY;
                        break;
                    case 4:
                        shiftType = "PaedOnCall";
                        dayOfWeek = null;
                        break;
                    case 5:
                        shiftType = "NeoOnCall";
                        dayOfWeek = null;
                        break;
                }
                int numShifts = 0;
                
                for (Shift shift: solved.getShiftList()) {
                    if (shift.getConsultant().equals(c)) {
                        if (shift.getShiftType().equals(shiftType) &&
                                dayOfWeek == null) {
                            numShifts++;
                            
                        } else
                        if (shift.getShiftType().equals(shiftType) &&
                                shift.getStartDate().getDayOfWeek() == dayOfWeek) {
                            numShifts++;                            
                        } 
                    } 
                    
                }

                JPanel numberPanel = new JPanel();
                JLabel numberLabel = new JLabel(""+numShifts);
                numberLabel.setFont(font);
                numberPanel.add(numberLabel);
                centerPanel.add(numberPanel);

            }

        }
        //centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pane.add(centerPanel, BorderLayout.CENTER);
        add(pane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ConsultantList("/main/resources/Data/All_Consultants.xml");

      //  new Summary();
    }

}

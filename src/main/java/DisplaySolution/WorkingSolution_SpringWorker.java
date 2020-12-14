/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplaySolution;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import main.java.DisplayData.SetLookAndFeel;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;
import main.java.RunData.RunRota_SpringWorker;
import main.java.RunData.Shift;
import main.java.RunData.ShiftList;
import org.apache.commons.collections4.map.MultiKeyMap;

/**
 *
 * @author pi
 */
public class WorkingSolution_SpringWorker extends JFrame implements ActionListener{
    MultiKeyMap keyMap;
    List<LocalDate> dateRange;
    
    public WorkingSolution_SpringWorker () {
        super("Processing Problem");
        SetLookAndFeel.setLookAndFeel();
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        
        // North Panel code
        JPanel northPanel = new JPanel(new BorderLayout());
        
        //Title
        JPanel pagetitle = new JPanel();
        JLabel pageTitle = new JLabel("Solving Problem");
        pagetitle.add(pageTitle);        
        
        //Start Box
        JPanel buttons = new JPanel();
        JButton start = new JButton("Start");
        start.addActionListener(this);
        buttons.add(start);
        
        northPanel.add(pagetitle,BorderLayout.CENTER);
        northPanel.add(buttons, BorderLayout.EAST);
        pane.add(northPanel,BorderLayout.NORTH);
                
        //West Panel code
        int x = ConsultantList.getConsultantList().size()+1;
        JPanel westPanel = new JPanel(new GridLayout(x,1));
        JPanel consultantTitle = new JPanel();
        JLabel consultanttitle = new JLabel("Consultants");
        consultantTitle.add(consultanttitle);
        westPanel.add(consultantTitle);
        for (ConsultantReader c: ConsultantList.getConsultantList()) {
            JPanel consultant = new JPanel();
            JLabel consultAnt = new JLabel(c.toString()); 
            consultant.add(consultAnt);
            westPanel.add(consultant);                    
        }
        westPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        pane.add(westPanel,BorderLayout.WEST);

        //Center Panel code
        
        Period period = Period.between(DatesReader.getRange()[0], DatesReader.getRange()[1]);
        JPanel centerPanel = new JPanel(new GridLayout(x,period.getDays()));
        List<JLabel> label = new ArrayList<>();
        
        LocalDate date = DatesReader.getRange()[0];
        dateRange = new ArrayList<>();
        for (int z=0; z<=period.getDays(); z++) {
            JPanel tester = new JPanel();
            JLabel test = new JLabel(date.toString());
            tester.add(test);
            label.add(test);
            centerPanel.add(tester);
            dateRange.add(date);
            date = date.plusDays(1);
        }
       
        keyMap = new MultiKeyMap<>();
        
        for (ConsultantReader c : ConsultantList.getConsultantList()){
            for (LocalDate d : dateRange) {
                JPanel panel = new JPanel();
                ConsultantPanel consultantPane = new ConsultantPanel();
                panel.add(consultantPane);
                keyMap.put(c,d, consultantPane);
                centerPanel.add(panel);
                
            }
        }
        JScrollPane scroller = new JScrollPane(centerPanel, VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_ALWAYS);
     
        
        pane.add(scroller,BorderLayout.CENTER);




        
        add(pane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        System.out.println(Thread.currentThread().getName());
        System.out.println("1");
        new RunRota_SpringWorker(this);
        System.out.println("2");
    
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("/main/resources/Data/All_Consultants.xml");
        new WorkingSolution_SpringWorker();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ConsultantPanel pane = (ConsultantPanel) keyMap.get(ConsultantList.getConsultantList().get(0),dateRange.get(0));
        
        pane.getNOW().setVisible(true);
        pane.getNeo().setVisible(true);
        //revalidate();
        //repaint();
    }
    
    public void update(ShiftList s) {
        
        for (Object entry: keyMap.values()) {
            ConsultantPanel p = (ConsultantPanel) entry;
            p.getCOW().setVisible(false);
            p.getNOW().setVisible(false);
            p.getPaed().setVisible(false);
            p.getNeo().setVisible(false);
        }
        repaint();
        /*
        for (Map.Entry<MultiKey<?>,ConsultantPanel> entry :  keyMap.entrySet()){
            entry.getValue().getCOW().setVisible(false);
                        entry.getValue().getNOW().setVisible(false);
                                    entry.getValue().getPaed().setVisible(false);
                                    entry.getValue().getNeo().setVisible(false);


            
        }*/
  
        
        
        for (Shift shift : s.getShiftList()) {
            ConsultantPanel pane = (ConsultantPanel) keyMap.get(shift.getConsultant(), shift.getStartDate());
         
            if (shift.getShiftType().equalsIgnoreCase("COW")) {
                pane.getCOW().setVisible(true);
            } 
            
              if (shift.getShiftType().equalsIgnoreCase("NOW")) {
                pane.getNOW().setVisible(true);
            } 
            
                if (shift.getShiftType().equalsIgnoreCase("PaedOnCall")) {
                pane.getPaed().setVisible(true);
            }  
                  if (shift.getShiftType().equalsIgnoreCase("NeoOnCall")) {
                pane.getNeo().setVisible(true);
            } 
        }
    }
    
    public void heatMap(Shift s) {
                 ConsultantPanel pane = (ConsultantPanel) keyMap.get(s.getConsultant(), s.getStartDate());
                 
            if (s.getShiftType().equalsIgnoreCase("COW")) {
                pane.getCOW().setBackground(Color.RED);
            } 
            
              if (s.getShiftType().equalsIgnoreCase("NOW")) {
                pane.getNOW().setBackground(Color.RED);
            } 
            
                if (s.getShiftType().equalsIgnoreCase("PaedOnCall")) {
                pane.getPaed().setBackground(Color.RED);
            }  
                  if (s.getShiftType().equalsIgnoreCase("NeoOnCall")) {
                pane.getNeo().setBackground(Color.RED);
            } 
               //  pane.setBackground(Color.RED);

        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplayData;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import main.java.DisplaySolution.WorkingSolution;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.ReadData.DatesReader;
import main.java.ReadData.FullOrPartTime;
import main.java.ReadData.ShiftStructureReader;
import main.java.ReadData.TypeOfWorking;

/**
 *
 * @author pi
 */
public class ConfirmStaff extends JFrame implements ListSelectionListener, ActionListener{

    List<ConsultantReader> consultantList;
    ConsultantReader selectedConsultant;
    ShiftTypeBox weekDays;
    ShiftTypeBox weekEnds;
    ShiftTypeBox onCalls;
    JRadioButton full;
    JRadioButton part;
    JCheckBox mon;
    JCheckBox tue;
    JCheckBox wed;
    JCheckBox thurs;
    JCheckBox fri;
    Dimension d;
    Dimension d2;
    FlowLayout f;
    JPanel leaveButtons;
    JPanel consultantBalance;
    
    public ConfirmStaff(List<ConsultantReader> consultantList) {
        super("Confirm Consultant Info");
        SetLookAndFeel.setLookAndFeel();
        setSize(800, 650);
        setLayout(new BorderLayout());

      f = new FlowLayout();
        f.setAlignment(FlowLayout.LEFT);
        d = new Dimension(400, 30);
        
        JPanel titlePane = new JPanel();
        JLabel title = new JLabel("Consultant Info");
        titlePane.add(title);
        add(titlePane, BorderLayout.NORTH);

        JPanel centerPane = new JPanel();
        centerPane.setLayout(new GridLayout(1, 2));
        JPanel westPane = new JPanel();
        westPane.setLayout(new BoxLayout(westPane, BoxLayout.Y_AXIS));
        JPanel consultantTitle = new JPanel();
        JLabel consultanttitle = new JLabel("Consultants");
        consultantTitle.setLayout(f);
        consultantTitle.setMaximumSize(d);
       
        consultantTitle.add(consultanttitle);
        JPanel consultantButton = new JPanel();
        consultantButton.setLayout(f);
                
        JList consultantJList = new JList(consultantList.toArray());
       consultantJList.setSelectedIndex(0);
       selectedConsultant = consultantList.get(0);
        consultantJList.addListSelectionListener(this);
        consultantButton.add(consultantJList);
        
        
        westPane.add(consultantTitle);
        westPane.add(consultantButton);

        centerPane.add(westPane);
        
        //East Panel with ShiftTypes, FullTime vs PartTime button group
        // Days working, Leave Dates, Shifts Carried Forward

        
        
        
        JPanel eastPane = new JPanel();
        eastPane.setLayout(new BoxLayout(eastPane, BoxLayout.Y_AXIS));
        JPanel shiftTypes = new JPanel();
        shiftTypes.setMaximumSize(d);
        shiftTypes.setLayout(f);
        JLabel shiftType = new JLabel("Shift Types");
        shiftTypes.add(shiftType);
        JPanel weekdayShifts = new JPanel();
        
        weekdayShifts.setMaximumSize(d);
        

        JPanel weekendShifts = new JPanel();
        JPanel onCallShifts = new JPanel();
        weekendShifts.setMaximumSize(d);
        onCallShifts.setMaximumSize(d);
        weekdayShifts.setLayout(f);
        weekendShifts.setLayout(f);
        onCallShifts.setLayout(f);
        JLabel weekdays = new JLabel("Weekdays \u27f6");
        weekDays = new ShiftTypeBox();
        weekDays.setSelectedItem(selectedConsultant.getWeekdays());
        
        JLabel weekends = new JLabel("Weekends \u27f6");
        weekEnds = new ShiftTypeBox();
        weekEnds.setSelectedItem(selectedConsultant.getWeekends());
        
        JLabel oncalls = new JLabel("On Calls \u27f6");
        onCalls = new ShiftTypeBox();
        onCalls.setSelectedItem(selectedConsultant.getOnCalls());
        
        weekdayShifts.add(weekdays);
        weekdayShifts.add(weekDays);
        weekendShifts.add(weekends);
        weekendShifts.add(weekEnds);
        onCallShifts.add(oncalls);
        onCallShifts.add(onCalls);

        JLabel newLine = new JLabel(" ");
        JPanel fulltime = new JPanel();
        fulltime.setMaximumSize(d);
        fulltime.setLayout(f);
        ButtonGroup fullOrPart = new ButtonGroup();
        full = new JRadioButton("Full Time", selectedConsultant.getFullOrPartTime() == FullOrPartTime.Full);
        part = new JRadioButton("Part Time", selectedConsultant.getFullOrPartTime() == FullOrPartTime.PartTime);
        fullOrPart.add(full);
        fullOrPart.add(part);
        fulltime.add(full);
        fulltime.add(part);
        
        JLabel newLine1 = new JLabel( "  ");
        JPanel normalDays = new JPanel();
        normalDays.setMaximumSize(d);
        normalDays.setLayout(f);
        JLabel normalDay = new JLabel("Normal Working Days");
        normalDays.add(normalDay);
        JPanel normalWorkingDays = new JPanel();
        normalWorkingDays.setMaximumSize(d);
        normalWorkingDays.setLayout(f);
         mon = new JCheckBox("Mon", true);
         tue = new JCheckBox("Tues", true);
         wed = new JCheckBox("Wed", true);
        thurs = new JCheckBox("Thurs", true);
        DaysWorking(selectedConsultant);
      //  fri = new JCheckBox("Fri", true);
     //   fri.setEnabled(false);
        
        normalWorkingDays.add(mon);
        normalWorkingDays.add(tue);
        normalWorkingDays.add(wed);
        normalWorkingDays.add(thurs);
      //  normalWorkingDays.add(fri);
        
        JLabel newLine2 = new JLabel( "  ");
        JPanel leaveDays = new JPanel();
        JLabel leavedays = new JLabel("Leave/Study Dates");
        leaveDays.setMaximumSize(d);
        leaveDays.setLayout(f);
        leaveDays.add(leavedays);
        
        leaveButtons = new JPanel();
        
        LeaveButtons(leaveButtons, selectedConsultant);
        /*
// consultantList.get(0) needs to be replaced with correct consultant from selected list
        int y= selectedConsultant.getLeaveDatesArray().length;

        Dimension d2 = new Dimension();
        d2.setSize(d.getWidth(),d.getHeight()*y);
        
        
        
        leaveButtons.setMaximumSize(d2);
        leaveButtons.setLayout(new GridLayout(y,1));
        for (LocalDate [] dates : selectedConsultant.getLeaveDatesArray()) {
            JPanel leaveDates = new JPanel();
            leaveDates.setMaximumSize(d2);
            leaveDates.setLayout(f);
            JButton leaveButton1 = new JButton(dates[0].toString());
            JLabel arrow = new JLabel("  \u27f6 ");
           // arrow.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            JButton leaveButton2 = new JButton(dates[1].toString());
            leaveDates.add(leaveButton1);
            leaveDates.add(arrow);
            leaveDates.add(leaveButton2);
            leaveButtons.add(leaveDates);                    
        }
 */       
        
        System.out.println(Thread.currentThread().getName());
        
        JLabel newLine3 = new JLabel( "  ");
        
        JPanel carriedDates = new JPanel();
        carriedDates.setMaximumSize(d);
        carriedDates.setLayout(f);
        
        JLabel carrieddates = new JLabel("Dates Carried Forward");
        carriedDates.add(carrieddates);
        consultantBalance = new JPanel();
        consultantBalance.setLayout(new BoxLayout(consultantBalance, BoxLayout.Y_AXIS));
        BalanceValues(consultantBalance,selectedConsultant);
        
        
        
        /*for (Map.Entry<String,Double> balance : selectedConsultant.getBalance().entrySet()) {
            JPanel balanceEntry = new JPanel();
            balanceEntry.setMaximumSize(d);
            JLabel key = new JLabel(balance.getKey() + ": ");
            JTextField value = new JTextField(balance.getValue().toString(),5);
            balanceEntry.setLayout(f);
            balanceEntry.add(key);
            balanceEntry.add(value);
            consultantBalance.add(balanceEntry);
        }*/
        
        eastPane.add(shiftTypes);
        eastPane.add(weekdayShifts);
        eastPane.add(weekendShifts);
        eastPane.add(onCallShifts);
        eastPane.add(newLine);
        eastPane.add(fulltime);
        eastPane.add(newLine1);
        eastPane.add(normalDays);
        eastPane.add(normalWorkingDays);
        eastPane.add(newLine2);
        eastPane.add(leaveDays);
        eastPane.add(leaveButtons);
        eastPane.add(newLine3);
        eastPane.add(carriedDates);
        eastPane.add(consultantBalance);
       eastPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPane.add(eastPane);
        
        add(centerPane, BorderLayout.CENTER);
        
        JPanel lowerPane = new JPanel();
        lowerPane.setLayout(new BorderLayout());
        JPanel modifyOrConfirm = new JPanel();
        modifyOrConfirm.setLayout(new BorderLayout());
        //JButton modify = new JButton("Modify");
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(this);
       // modifyOrConfirm.add(modify, BorderLayout.WEST);
        modifyOrConfirm.add(confirm, BorderLayout.EAST);
        lowerPane.add(modifyOrConfirm, BorderLayout.EAST);
        add(lowerPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                new ConsultantList("/main/resources/Data/All_Consultants.xml");
new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConfirmStaff(ConsultantList.getConsultantList());
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
            JList list = (JList) lse.getSource();
          //  System.out.println(list.getSelectedValue());
            selectedConsultant = (ConsultantReader) list.getSelectedValue();
            weekDays.setSelectedItem(selectedConsultant.getWeekdays());
            weekEnds.setSelectedItem(selectedConsultant.getWeekends());
            onCalls.setSelectedItem(selectedConsultant.getOnCalls());
            full.setSelected(selectedConsultant.getFullOrPartTime() == FullOrPartTime.Full);
            part.setSelected(selectedConsultant.getFullOrPartTime() == FullOrPartTime.PartTime);
            DaysWorking(selectedConsultant);
          /*  mon.setSelected(selectedConsultant.getDaysWorkingList().contains(DayOfWeek.MONDAY));
            tue.setSelected(selectedConsultant.getDaysWorkingList().contains(DayOfWeek.TUESDAY));
            wed.setSelected(selectedConsultant.getDaysWorkingList().contains(DayOfWeek.WEDNESDAY));
            thurs.setSelected(selectedConsultant.getDaysWorkingList().contains(DayOfWeek.THURSDAY));
         //   fri.setSelected(selectedConsultant.getDaysWorkingList().contains(DayOfWeek.FRIDAY));*/
            LeaveButtons(leaveButtons,selectedConsultant);
            BalanceValues(consultantBalance, selectedConsultant);

            this.revalidate();
            this.repaint();
            pack();
            System.out.println(selectedConsultant.getFullOrPartTime());
            

    }
    
    public void LeaveButtons(JPanel pane,ConsultantReader c) {
        pane.removeAll();
        int y= c.getLeaveDatesArray().length;

        d2 = new Dimension();
        d2.setSize(d.getWidth(),d.getHeight()*y);
        
        
        
        pane.setMaximumSize(d2);
        pane.setLayout(new GridLayout(y,1));
        for (LocalDate [] dates : c.getLeaveDatesArray()) {
            JPanel leaveDates = new JPanel();
            leaveDates.setMaximumSize(d2);
            leaveDates.setLayout(f);
            JButton leaveButton1 = new JButton(dates[0].toString());
            JLabel arrow = new JLabel("  \u27f6 ");
           // arrow.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            JButton leaveButton2 = new JButton(dates[1].toString());
            leaveDates.add(leaveButton1);
            leaveDates.add(arrow);
            leaveDates.add(leaveButton2);
            pane.add(leaveDates);                    
        }
        
       
    }
    
    public void BalanceValues(JPanel pane, ConsultantReader c) {
        pane.removeAll();
        for (Map.Entry<String,Double> balance : selectedConsultant.getBalance().entrySet()) {
            JPanel balanceEntry = new JPanel();
         //balanceEntry.setMaximumSize(d);
            JLabel key = new JLabel(balance.getKey() + ": ");
            JTextField value = new JTextField(balance.getValue().toString(),5);
            balanceEntry.setLayout(f);
            balanceEntry.add(key);
            balanceEntry.add(value);
            pane.add(balanceEntry);
        }
    }
    
    void DaysWorking(ConsultantReader c) {
        mon.setSelected(c.getDaysWorkingList().contains(DayOfWeek.MONDAY));
            tue.setSelected(c.getDaysWorkingList().contains(DayOfWeek.TUESDAY));
            wed.setSelected(c.getDaysWorkingList().contains(DayOfWeek.WEDNESDAY));
            thurs.setSelected(c.getDaysWorkingList().contains(DayOfWeek.THURSDAY));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       this.dispose();
       new WorkingSolution();

    }
    class ShiftTypeBox extends JComboBox {
        ShiftTypeBox (){
            super(TypeOfWorking.values());
            
        }
        
    
        
    }

}

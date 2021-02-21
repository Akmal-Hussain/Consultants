/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import main.java.DisplayData.ConfirmDates;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.ReadData.DatesReader;
import main.java.ReadData.PinnedShiftsReader;
import main.java.ReadData.ShiftStructureReader;

/**
 *
 * @author pi
 */
public class StartGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DatesReader("Resources/Data/Dates.xml");
        new ShiftStructureReader("Resources/Data/Shift_Structure.xml");
        new ConsultantList("Resources/Data/All_Consultants.xml");
        new PinnedShiftsReader("Resources/Data/Pinned_Shifts.xml");

       
        ShiftList unsolvedShiftList = new ShiftList();
        ConsultantList.addShiftTargets(unsolvedShiftList);
        for(ConsultantReader c: ConsultantList.getConsultantList()){
            System.out.println(c);
            System.out.println("COW week target:    "+ c.getCOW_WeekTarget());
            System.out.println("Now week Target:    " +c.getNOW_WeekTarget());
            System.out.println("COW w/e Target:     " + c.getCOW_WeekendTarget());
            System.out.println("NOW w/e Target:     " + c.getNOW_WeekendTarget());
            System.out.println("OnCalls:            " + c.getOnCallsTarget());
        }
       // System.exit(-1);
        new ConfirmDates();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import main.java.DisplayData.TitleScreen;
import main.java.ReadData.ConsultantList;
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
                System.out.println(ConsultantList.getConsultantList().get(1).getConsultantName());

        System.out.println(ConsultantList.getConsultantList().get(1).getLeaveDates().get(1));
        new TitleScreen();
    }
    
}

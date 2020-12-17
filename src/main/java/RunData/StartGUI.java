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
        new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("/main/resources/Data/All_Consultants.xml");
        new PinnedShiftsReader("/main/resources/Data/Pinned_Shifts.xml");
        new TitleScreen();
    }
    
}

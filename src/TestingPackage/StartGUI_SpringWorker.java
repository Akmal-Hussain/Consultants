/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingPackage;

//import main.java.DisplayData.ConfirmData_SpringWorker;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;

/**
 *
 * @author pi
 */
public class StartGUI_SpringWorker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("/main/resources/Data/All_Consultants.xml");
      // new ConfirmData_SpringWorker();
    }
    
}

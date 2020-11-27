/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import main.java.DisplayData.DisplaySolution;
import main.java.DisplayData.TitleScreen;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

/**
 *
 * @author dakhussain
 */
public class RunRota {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        // Build the Solver
        SolverFactory<ShiftList> solverFactory = SolverFactory.createFromXmlResource(
                "main/resources/Configuration/config.xml");
        Solver<ShiftList> solver = solverFactory.buildSolver();
        
        new TitleScreen();

        //Load the problem
        new DatesReader("src/main/resources/Data/Dates.xml");
        new ShiftStructureReader("src/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("src/main/resources/Data/All_Consultants.xml");
        ShiftList unsolvedShiftList = new ShiftList();
        ConsultantList.addShiftTargets(unsolvedShiftList);

        
        ShiftList solvedShiftList = solver.solve(unsolvedShiftList);
        //Display the result with heat map and constraint matching...
        new DisplaySolution(solvedShiftList, solver);
 
    }

}

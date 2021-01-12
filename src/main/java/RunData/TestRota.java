/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import main.java.DisplayData.DisplaySolution;
import main.java.DisplaySolution.WorkingSolution;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.ReadData.DatesReader;
import main.java.ReadData.PinnedShiftsReader;
import main.java.ReadData.ShiftStructureReader;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

/**
 *
 * @author pi
 */
public class TestRota {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     /*           //Load the problem
        new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("/main/resources/Data/All_Consultants.xml");
              new PinnedShiftsReader("/main/resources/Data/Pinned_Shifts.xml");

        // ConsultantReader ann = (ConsultantList.getConsultantList().get(0));
       // System.out.println(ann.getFullOrPartTime().getFactor());
      //  System.exit(-1);
                SolverFactory<ShiftList> solverFactory = SolverFactory.createFromXmlResource(
                "main/resources/Configuration/config.xml");
        Solver<ShiftList> solver = solverFactory.buildSolver();
        ShiftList unsolvedShiftList = new ShiftList();
        ConsultantList.addShiftTargets(unsolvedShiftList);
      //  WorkingSolution display = new WorkingSolution();
       /* 
        solver.addEventListener(new SolverEventListener<ShiftList>() {
            @Override
            public void bestSolutionChanged(BestSolutionChangedEvent<ShiftList> event) {
if (event.getNewBestSolution().getScore().isFeasible()) {
     display.update(event.getNewBestSolution());
}
            }
        });
        ShiftList solvedShiftList = solver.solve(unsolvedShiftList);
        //display.update(solvedShiftList);
        //Display the result with heat map and constraint matching...
        new DisplaySolution(solvedShiftList, solver);


   */ }
     
    
}

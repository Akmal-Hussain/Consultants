/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import java.util.List;
import javax.swing.SwingWorker;
import main.java.DisplayData.DisplaySolution;
import main.java.DisplaySolution.WorkingSolution;
import main.java.DisplaySolution.ShiftScore;

import main.java.ReadData.ConsultantList;
import main.java.ReadData.DatesReader;
import main.java.ReadData.PinnedShiftsReader;
import main.java.ReadData.ShiftStructureReader;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

/**
 *
 * @author dakhussain
 */
public class RunRota extends SwingWorker <ShiftList,ShiftScore> {
    ShiftList solvedShiftList;
    ShiftList unsolvedShiftList;
    Solver<ShiftList> solver;
    WorkingSolution display;
    ShiftScore shiftScore;
    
    public RunRota(WorkingSolution display) {
    // Build the Solver
    
        this.display = display;
        this.solver = display.getSolver();
       /* 
        SolverFactory<ShiftList> solverFactory = SolverFactory.createFromXmlResource(
                "main/resources/Configuration/config.xml");
        solver = solverFactory.buildSolver();
        
       

      
        unsolvedShiftList = new ShiftList();
        ConsultantList.addShiftTargets(unsolvedShiftList);
 //       WorkingSolution display = new WorkingSolution();
        */
        this.execute();
        
        //Display the result with heat map and constraint matching...
      //  new DisplaySolution(solvedShiftList, solver, display);
 
    }

    /**
     * @param args the command line arguments
     */
    
    @Override
    protected ShiftList doInBackground() throws Exception {
      /*  
        SolverFactory<ShiftList> solverFactory = SolverFactory.createFromXmlResource(
                "main/resources/Configuration/config.xml");
        solver = solverFactory.buildSolver();
        */

      
        unsolvedShiftList = new ShiftList();
        ConsultantList.addShiftTargets(unsolvedShiftList);
 //       WorkingSolution display = new WorkingSolution();
        
        
        
        
        
        
     shiftScore = new ShiftScore();
       solver.addEventListener(new SolverEventListener<ShiftList>() {
            @Override
            public void bestSolutionChanged(BestSolutionChangedEvent<ShiftList> event) {
//if (event.getNewBestSolution().getScore().isFeasible()) {
     //display.update(event.getNewBestSolution());
     //ShiftScore shiftScore = new ShiftScore();
     shiftScore.setSolution(event.getNewBestSolution());
     shiftScore.setScore(event.getNewBestScore());
     publish(shiftScore);
    
//}
            }
        });
        solvedShiftList = solver.solve(unsolvedShiftList);
        //display.update(solvedShiftList);
        ShiftScore finalShiftScore = new ShiftScore();
        finalShiftScore.setSolution(solvedShiftList);
        finalShiftScore.setScore(solvedShiftList.getScore());
        display.update(finalShiftScore);
  
      
            DisplaySolution d = new DisplaySolution(solvedShiftList, solver, display);


        return solvedShiftList;
    }

   @Override
  protected void process(List<ShiftScore> chunks) {
    display.update(chunks.get(chunks.size()-1));
       System.out.println("Chunks size" + chunks.size());
        
  }

  @Override
  protected void done() {
     /*  display.update(solvedShiftList);
  
      try {
            // setLookAndFeel();
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
   
    DisplaySolution d = new DisplaySolution(solvedShiftList, solver, display);*/
   // new ExportSolution(solvedShiftList);
  }
    public static void main(String[] args) {
      new DatesReader("Resources/Data/Dates.xml");
        new ShiftStructureReader("Resources/Data/Shift_Structure.xml");
        new ConsultantList("Resources/Data/All_Consultants.xml");
                new PinnedShiftsReader("Resources/Data/Pinned_Shifts.xml");

       // new RunRota(new WorkingSolution());
       new WorkingSolution();
    }

    
}

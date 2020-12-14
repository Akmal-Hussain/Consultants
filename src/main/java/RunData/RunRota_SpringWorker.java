/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import main.java.DisplayData.DisplaySolution_SpringWorker;
import main.java.DisplaySolution.WorkingSolution_SpringWorker;
import main.java.ReadData.ConsultantList;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

/**
 *
 * @author dakhussain
 */
public class RunRota_SpringWorker extends SwingWorker<ShiftList, ShiftList> {

    ShiftList solvedShiftList;
    ShiftList unsolvedShiftList;
    Solver<ShiftList> solver;
    WorkingSolution_SpringWorker display;
    
    public RunRota_SpringWorker(WorkingSolution_SpringWorker display) {
    // Build the Solver
    
        this.display = display;
        
        SolverFactory<ShiftList> solverFactory = SolverFactory.createFromXmlResource(
                "main/resources/Configuration/config.xml");
        solver = solverFactory.buildSolver();
        
       

      
        unsolvedShiftList = new ShiftList();
        ConsultantList.addShiftTargets(unsolvedShiftList);
 //       WorkingSolution display = new WorkingSolution();
        
        this.execute();
        
        //Display the result with heat map and constraint matching...
      //  new DisplaySolution(solvedShiftList, solver, display);
 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      new DatesReader("/main/resources/Data/Dates.xml");
        new ShiftStructureReader("/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("/main/resources/Data/All_Consultants.xml");
       // new RunRota_SpringWorker();
       
    }

    @Override
    protected ShiftList doInBackground() throws Exception {
       solver.addEventListener(new SolverEventListener<ShiftList>() {
            @Override
            public void bestSolutionChanged(BestSolutionChangedEvent<ShiftList> event) {
//if (event.getNewBestSolution().getScore().isFeasible()) {
     //display.update(event.getNewBestSolution());
     publish(event.getNewBestSolution());
//}
            }
        });
        solvedShiftList = solver.solve(unsolvedShiftList);
        //display.update(solvedShiftList);
        return solvedShiftList;
    }

   @Override
  protected void process(List<ShiftList> chunks) {
    display.update(chunks.get(chunks.size()-1));
       System.out.println("Chunks size" + chunks.size());
        
  }

  @Override
  protected void done() {
    display.update(solvedShiftList);
    new DisplaySolution_SpringWorker(solvedShiftList, solver, display);
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestingPackage;

import main.java.ReadData.ConsultantList;
import main.java.ReadData.DatesReader;
import main.java.ReadData.PinnedShiftsReader;
import main.java.ReadData.ShiftStructureReader;
import main.java.RunData.ShiftList;
import org.optaplanner.benchmark.api.PlannerBenchmark;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;

/**
 *
 * @author pi
 */
public class RunBench {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory.createFromSolverConfigXmlResource(
        "Configuration/config.xml");
  new DatesReader("Resources/Data/Dates.xml");
        new ShiftStructureReader("Resources/Data/Shift_Structure.xml");
        new ConsultantList("Resources/Data/All_Consultants.xml");
                new PinnedShiftsReader("Resources/Data/Pinned_Shifts.xml");

       
ShiftList dataset1 = new ShiftList();
//CloudBalance dataset2 = ...;
//CloudBalance dataset3 = ...;
PlannerBenchmark benchmark = benchmarkFactory.buildPlannerBenchmark(
        dataset1);
benchmark.benchmarkAndShowReportInBrowser();

    

    }
}
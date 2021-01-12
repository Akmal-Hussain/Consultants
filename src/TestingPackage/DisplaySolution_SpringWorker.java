/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplayData;

import java.util.List;
import java.util.Map;
import main.java.DisplaySolution.WorkingSolution_SpringWorker;
import main.java.RunData.Shift;
import main.java.RunData.ShiftList;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;

/**
 *
 * @author pi
 */
public class DisplaySolution_SpringWorker {

    public DisplaySolution_SpringWorker(ShiftList solvedSolution, Solver<ShiftList> solver, WorkingSolution_SpringWorker display) {

        ScoreDirectorFactory<ShiftList> scoreDirectorFactory = solver.getScoreDirectorFactory();
        ScoreDirector<ShiftList> guiScoreDirector = scoreDirectorFactory.buildScoreDirector();

        for (Shift solved : solvedSolution.getShiftList()) {
            System.out.println("Date: " + solved.getStartDate() + " Shift Type: " + solved.getShiftType()
                    + " Consultant: " + solved.getConsultant());

        }

        // This is a display of the final score
        guiScoreDirector.setWorkingSolution(solvedSolution);
        Score score = guiScoreDirector.calculateScore();
        System.out.println(score);

        // this is a display of the Score broken down into constraints
        for (ConstraintMatchTotal constraintMatchTotal : guiScoreDirector.getConstraintMatchTotals()) {
            String constraintName = constraintMatchTotal.getConstraintName();
            // The score impact of that constraint
            Score scoreTotal = constraintMatchTotal.getScoreTotal();

            for (ConstraintMatch constraintMatch : constraintMatchTotal.getConstraintMatchSet()) {
                List<Object> justificationList = constraintMatch.getJustificationList();
                String constraintname = constraintMatch.getConstraintName();
                Score ruleScore = constraintMatch.getScore();
                System.out.println(constraintName + "\n Score Total " + scoreTotal + "\n Constraint Name " + constraintname + "\n" + ruleScore);
            }
        }

        Map<Object, Indictment> indictmentMap = guiScoreDirector.getIndictmentMap();
        System.out.println("--------------------------------------BREAK-----------------------------------------------");
        for (Shift shift : solvedSolution.getShiftList()) {
            Indictment indictment = indictmentMap.get(shift);
            if (indictment == null) {
                continue;
            }
            // The score impact of that planning entity
            Score scoreTotal = indictment.getScoreTotal();
            System.out.println(shift.getId() + "  " + scoreTotal);
           // if (scoreTotal.equals(HardSoftScore.ZERO)) {
            display.heatMap(shift);
            //}
            for (ConstraintMatch constraintMatch : indictment.getConstraintMatchSet()) {
                String constraintName = constraintMatch.getConstraintName();
                Score shiftScore = constraintMatch.getScore();
                System.out.println(shift.getStartDate() + "\nShiftType" + shift.getShiftType() + "\n Constraint Name: " + constraintName + "\n " + shiftScore);

            }
        }

    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.DisplaySolution;

import main.java.RunData.ShiftList;
import org.optaplanner.core.api.score.Score;

/**
 *
 * @author pi
 */
public class ShiftScore {

        public void setSolution(ShiftList solution) {
            this.solution = solution;
        }

        public void setScore(Score score) {
            this.score = score;
        }
        ShiftList solution;
        Score score;

        public ShiftList getSolution() {
            return solution;
        }

        public Score getScore() {
            return score;
        }
        
        public ShiftScore() {
           
        }
        
        
    }
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.resources.Rules;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;
import main.java.RunData.ShiftList;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

/**
 *
 * @author dakhussain
 */
public class RotaConstraintsJava implements EasyScoreCalculator
<ShiftList> {
    LocalDate d;
    
    
    @Override
    public HardSoftScore calculateScore(ShiftList sltn_) {
        int hardScore = 0;
        int softScore = 1;
        return HardSoftScore.of(hardScore,softScore);
        WeekFields week;
        Locale uk = Locale.UK;
        
    }
    
}

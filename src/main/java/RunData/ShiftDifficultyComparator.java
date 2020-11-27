/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.RunData;

import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 *
 * @author dakhussain
 */
public class ShiftDifficultyComparator implements Comparator<Shift> {
  public int compare(Shift a, Shift b) {
  return new CompareToBuilder()
  .append(a.isHoliday(), b.isHoliday())
  .toComparison();
  }
}
    


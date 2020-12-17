/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ReadData;

import java.time.LocalDate;

/**
 *
 * @author pi
 */
public class PinnedShift {

    String consultantName;
    LocalDate date;
    String shiftType;

   
        public PinnedShift(String consultantName, LocalDate date, String shiftType) {
            this.consultantName = consultantName;
            this.date = date;
            this.shiftType = shiftType;
        }

         public String getConsultantName() {
        return consultantName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

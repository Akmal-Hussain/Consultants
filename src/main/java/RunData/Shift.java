package main.java.RunData;

import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;
import main.java.ReadData.TypeOfWorking;
import java.time.DayOfWeek;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pi
 */
@PlanningEntity(difficultyComparatorClass = ShiftDifficultyComparator.class)
public class Shift {
    int id;

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    private boolean pinned = false;
    
    @PlanningPin
    public boolean isPinned() {
        return pinned;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    LocalDate startDate;
    LocalDate endDate;
    
    
    public LocalDate getEndDate() {
        return endDate;
    }
    boolean weekend;

    public void setNight(boolean night) {
        this.night = night;
    }
    boolean holiday;
    boolean bankHoliday;
    boolean night;

    @PlanningVariable(valueRangeProviderRefs = "consultantRange")
    ConsultantReader consultant;

    String shiftType;

    public LocalDate getStartDate() {
        return startDate;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public boolean isBankHoliday() {
        return bankHoliday;
    }

    public boolean isNight() {
        return night;
    }

    public ConsultantReader getConsultant() {
        return consultant;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setDate(LocalDate startDate) {
        if (startDate.isAfter(DatesReader.getRange()[0].minusDays(1)) && startDate.isBefore(DatesReader.getRange()[1].plusDays(1))
                || startDate.isAfter(DatesReader.getRange()[1].minusDays(1)) && startDate.isBefore(DatesReader.getRange()[0].plusDays(1))) {
            //        ){
            this.startDate = startDate;

        } else {
            System.out.println("Trying to allocate dates outside range");
            System.exit(-1);
        }

        bankHoliday = false;
        for (LocalDate bankholiday : DatesReader.getBankHolidays()) {
            if (startDate.isEqual(bankholiday)) {
                bankHoliday = true;
            }
        }

        weekend = (startDate.getDayOfWeek().getValue() >= DayOfWeek.FRIDAY.getValue()
                && startDate.getDayOfWeek().getValue() <= DayOfWeek.SUNDAY.getValue());

        holiday = false;
        // Iterate through the 2 dimensional array
        for (LocalDate[] schoolHoliday : DatesReader.getSchoolHolidays()) {
            if (startDate.isEqual(schoolHoliday[0]) || startDate.isEqual(schoolHoliday[1]) || (startDate.isAfter(schoolHoliday[0]) && startDate.isBefore(schoolHoliday[1]))) {
                holiday = true;
            }
            if (weekend) {
                if (startDate.plusDays(3).isEqual(schoolHoliday[0]) || startDate.plusDays(3).isEqual(schoolHoliday[1]) || (startDate.plusDays(3).isAfter(schoolHoliday[0]) && startDate.plusDays(3).isBefore(schoolHoliday[1]))) {
                    holiday = true;
                }
            }

        }

    }

    public void setConsultant(ConsultantReader consultant) {
        this.consultant = consultant;
    }

    public void setShiftType(String shiftType) {
        boolean isTrueShift = false;
        for (String weekday : ShiftStructureReader.getWeekdayDay()) {
            if (shiftType.equalsIgnoreCase(weekday)) {
                isTrueShift = true;
                endDate = startDate;
            }
        }
        for (String weekday : ShiftStructureReader.getWeekdayNight()) {
            if (shiftType.equalsIgnoreCase(weekday)) {
                isTrueShift = true;
                endDate = startDate.plusDays(1L);
            }
        }
        for (String weekday : ShiftStructureReader.getWeekend()) {
            if (shiftType.equalsIgnoreCase(weekday)) {
                isTrueShift = true;
                endDate = startDate.plusDays(8-startDate.getDayOfWeek().getValue());
            }
        }
        if (isTrueShift) {
            this.shiftType = shiftType;
        } else {
            System.out.println("Wrong shiftype attempted");
            System.exit(-1);
        }
    }


    @ValueRangeProvider(id = "consultantRange")
    public List<ConsultantReader> getConsultantList() {
        List<ConsultantReader> consultants = new ArrayList<>(ConsultantList.getConsultantList());

        for (Iterator<ConsultantReader> cs = consultants.iterator(); cs.hasNext();) {
            ConsultantReader c = cs.next();
            if (!isWeekend()) {

                if (shiftType.equalsIgnoreCase("NOW")
                        && c.getWeekdays() == TypeOfWorking.Paeds) {
                    cs.remove();
                }
                if (shiftType.equalsIgnoreCase("NeoOnCall")
                        && c.getOnCalls() == TypeOfWorking.Paeds) {
                    cs.remove();
                }
            }
            if (isWeekend()) {
                if (shiftType.equalsIgnoreCase("NOW")
                        && c.getWeekends() == TypeOfWorking.Paeds) {
                    cs.remove();
                }
            }
            /*
            TypeOfWorking type = TypeOfWorking.Both;
            
            if (shiftType.equalsIgnoreCase("NeoOnCall")) {
                type = TypeOfWorking.Neonates;
            } 
            
            if (shiftType.equalsIgnoreCase("PaedOnCall")) {
                    type = TypeOfWorking.Paeds;
                } 
            
            
            boolean contains = Arrays.stream(c.getDaysWorking()).anyMatch(startDate.getDayOfWeek()::equals);
            if (!contains && startDate.getDayOfWeek().getValue() <5 && 
                    type != TypeOfWorking.Both) {
                cs.remove();
            } 
             */   
        }
        return consultants;
    }
}
        
    /*    
        for(ConsultantReader c : cs) {
            if (!isWeekend()) {
                
                if (shiftType.equalsIgnoreCase("NOW") 
                        && c.getWeekdays() == TypeOfWorking.Paeds){
                    cs.remove(c);
                }
                if (shiftType.equalsIgnoreCase("Neo")
                        && c.getOnCalls() == TypeOfWorking.Paeds){
                    cs.remove(c);
                }
            }
            if (isWeekend()) {
                if (shiftType.equalsIgnoreCase("NOW")
                        && c.getWeekends() == TypeOfWorking.Paeds){
                    cs.remove(c);
                }
            }        
                
            
        }
       
        return cs;
    }

}
*/

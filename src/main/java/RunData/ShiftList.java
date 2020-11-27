package main.java.RunData;


import main.java.ReadData.ConsultantList;
import main.java.ReadData.ConsultantReader;
import main.java.ReadData.DatesReader;
import main.java.ReadData.ShiftStructureReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pi
 */
@PlanningSolution
public class ShiftList {

    private List<ConsultantReader> consultants;

    private HardSoftScore score;

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    private List<Shift> shiftList;

    ArrayList<Shift> holidayList = new ArrayList <> ();

    public ShiftList() {
        this.consultants = ConsultantList.getConsultantList();
        shiftList = new ArrayList<>();
        int id = 0;
        Shift shift;
        // LocalDate startDate = DatesReader.getRange()[0];
        //LocalDate endDate = DatesReader.getRange()[1];
        LocalDate startDate = (DatesReader.getRange()[0].isBefore(DatesReader.getRange()[1]))
                ? DatesReader.getRange()[0] : DatesReader.getRange()[1];
        LocalDate endDate = (DatesReader.getRange()[0].isBefore(DatesReader.getRange()[1]))
                ? DatesReader.getRange()[1] : DatesReader.getRange()[0];
        

        do {
            if (startDate.get(WeekFields.of(Locale.UK).weekOfYear()) != 
                    LocalDate.of(endDate.getYear(), 1, 1).get(WeekFields.of(Locale.UK).weekOfYear()) &&
                    startDate.get(WeekFields.of(Locale.UK).weekOfYear()) != 
                    (LocalDate.of(endDate.getYear(), 1, 1).get(WeekFields.of(Locale.UK).weekOfYear()) -1)) {
            if (startDate.getDayOfWeek().getValue() < 5) {
                if(startDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                for (String dayShift : ShiftStructureReader.getWeekdayDay()) {
                    shift = new Shift();
                    shift.setDate(startDate);
                    shift.setNight(false);
                    shift.setId(id++);
                    shift.setShiftType(dayShift);
                    shiftList.add(shift);
                }
                }
                for (String nightShift : ShiftStructureReader.getWeekdayNight()) {
                    shift = new Shift();
                    shift.setDate(startDate);
                    shift.setNight(true);
                     shift.setId(id++);
                    shift.setShiftType(nightShift);
                    if (!shift.isBankHoliday()) {
                        shiftList.add(shift);
                    }
                }
                startDate = startDate.plusDays(1);
            }
            if (startDate.getDayOfWeek().getValue() > 4) {
                for (String weekendShift : ShiftStructureReader.getWeekend()) {
                    shift = new Shift();
                    shift.setDate(startDate);
                    shift.setShiftType(weekendShift);
                     shift.setId(id++);
                    shift.setNight(false);
                    shiftList.add(shift);
                }
                startDate = startDate.plusDays(8 - startDate.getDayOfWeek().getValue());
            }
            }    
        } while (!startDate.isAfter(endDate));
            
        shiftList
                .stream()
                .filter(c -> c.isHoliday())
                .forEach(d -> holidayList.add(d));

    }
        
    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }
   // @ValueRangeProvider(id = "consultantRange")
    @ProblemFactCollectionProperty
    public List<ConsultantReader> getConsultants() {
        return consultants;
    }

    @PlanningEntityCollectionProperty
    public List<Shift> getShiftList() {
        return shiftList;
    }

    public static void main(String[] args) {
        new DatesReader("src/main/resources/Data/Dates.xml");
        new ShiftStructureReader("src/main/resources/Data/Shift_Structure.xml");
        new ConsultantList("src/main/resources/Data/All_Consultants.xml"); 
        ShiftList c = new ShiftList();
                System.out.println(c.getShiftList().get(0).getStartDate().getDayOfWeek());

        System.out.println(c.getShiftList().get(0).getShiftType());
        System.out.println(c.getShiftList().get(0).getEndDate());
        System.out.println(c.holidayList.get(c.holidayList.size() - 1).getStartDate());

    }

    

}
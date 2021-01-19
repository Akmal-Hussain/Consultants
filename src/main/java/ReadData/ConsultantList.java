/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ReadData;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.java.RunData.Shift;
import main.java.RunData.ShiftList;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 *
 * @author dakhussain
 */
public class ConsultantList implements Serializable {

    private static List<ConsultantReader> consultantList = new ArrayList<>();

    public static List<ConsultantReader> getConsultantList() {
        return consultantList;
    }

    public ConsultantList(String fileName) {

        try {
            Builder builder = new Builder();
        //    File xfile = new File(fileName);
            Document doc = builder.build(getClass().getResourceAsStream(fileName));

            Element root = doc.getRootElement();

            Elements consultants = root.getChildElements("Consultant");
            for (Element consultant : consultants) {
                String name = consultant.getFirstChildElement("Name").getValue();
                String file = consultant.getFirstChildElement("FileName").getValue();
                if (!"".equals(name) && !"".equals(file)) {
                    ConsultantReader x = new ConsultantReader(file);
                    consultantList.add(x);
                }
            }

        } catch (ParsingException | IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

    public static void addShiftTargets(ShiftList shiftList) {
        
        double consultantNumberCOW_Weekday =0;
        double consultantNumberCOW_Weekend=0;
        double consultantNumberNOW_Weekday=0;
        double consultantNumberNOW_Weekend=0;
        double consultantNumberOnCalls=0;
        
        double consultantShiftsCarriedCOW_Weekday=0;
        double consultantShiftsCarriedNOW_Weekday=0;
        double consultantShiftsCarriedCOW_Weekend=0;
        double consultantShiftsCarriedNOW_Weekend=0;
        double consultantShiftsCarriedOnCalls=0;
        
        double COW_WeekdayNumber = 0;
        double COW_WeekendNumber = 0;
        double NOW_WeekdayNumber = 0;
        double NOW_WeekendNumber = 0;
        double onCallsNumber = 0;
        
        
        for (Shift shift : shiftList.getShiftList()) {
            if (shift.getShiftType().equals("COW") && shift.getStartDate().getDayOfWeek() ==DayOfWeek.MONDAY) {
                COW_WeekdayNumber++;
            }
            if (shift.getShiftType().equals("NOW") && shift.getStartDate().getDayOfWeek() == DayOfWeek.MONDAY) {
                NOW_WeekdayNumber++;
            }
            if (shift.getShiftType().equals("COW") && shift.getStartDate().getDayOfWeek() == DayOfWeek.FRIDAY) {
                COW_WeekendNumber++;
            }
            if (shift.getShiftType().equals("COW") && shift.getStartDate().getDayOfWeek() == DayOfWeek.FRIDAY) {
                NOW_WeekendNumber++;
            }
            if (shift.getShiftType().equals("PaedOnCall")) {
                onCallsNumber++;
            }
        }
        for (ConsultantReader c : getConsultantList()) {
            double x = c.getFactor();
            
            
            
            if (c.getWeekdays() == TypeOfWorking.Paeds || c.getWeekdays() == TypeOfWorking.Both) {
                consultantNumberCOW_Weekday += x;}
            if (c.getWeekdays() == TypeOfWorking.Neonates || c.getWeekdays() == TypeOfWorking.Both) {
                consultantNumberNOW_Weekday += x;}

             if (c.getWeekends()== TypeOfWorking.Paeds || c.getWeekdays() == TypeOfWorking.Both) {
                consultantNumberCOW_Weekend += x;}
            if (c.getWeekends() == TypeOfWorking.Neonates || c.getWeekdays() == TypeOfWorking.Both) {
                consultantNumberNOW_Weekend += x;}
             
             if (c.getOnCalls() == TypeOfWorking.Both || c.getOnCalls() == TypeOfWorking.Neonates) {
                 consultantNumberOnCalls += x;
             consultantShiftsCarriedOnCalls += c.getBalance().get("OnCalls");
             }
             
             consultantShiftsCarriedCOW_Weekday += c.getBalance().get("COW_Week");
             consultantShiftsCarriedNOW_Weekday += c.getBalance().get("NOW_Week");
             consultantShiftsCarriedCOW_Weekend += c.getBalance().get("COW_Weekend");
             consultantShiftsCarriedNOW_Weekend += c.getBalance().get("NOW_Weekend");
             
        }
            for (Iterator<ConsultantReader> cs = getConsultantList().iterator(); cs.hasNext();) {
            ConsultantReader cons = cs.next();
            
            double partTimeFactor = cons.getFactor();
            
            cons.setCOW_WeekTarget(partTimeFactor * (COW_WeekdayNumber+consultantShiftsCarriedCOW_Weekday)/consultantNumberCOW_Weekday - cons.getBalance().get("COW_Week") );
            cons.setCOW_WeekendTarget(partTimeFactor * (COW_WeekendNumber+consultantShiftsCarriedCOW_Weekend)/consultantNumberCOW_Weekend - cons.getBalance().get("COW_Weekend"));
            cons.setNOW_WeekTarget(partTimeFactor * (NOW_WeekdayNumber+consultantShiftsCarriedNOW_Weekday)/consultantNumberNOW_Weekday - cons.getBalance().get("NOW_Week"));
            cons.setNOW_WeekendTarget(partTimeFactor * (NOW_WeekendNumber+consultantShiftsCarriedNOW_Weekend)/consultantNumberNOW_Weekend - cons.getBalance().get("NOW_Weekend"));
            cons.setOnCallsTarget(partTimeFactor * (onCallsNumber+consultantShiftsCarriedOnCalls)/consultantNumberOnCalls - cons.getBalance().get("OnCalls"));
            

        }
    }

    public static void update(){
        for(ConsultantReader c :consultantList) {
            c.update();
        }
    }

    public static void main(String[] args) {
        new ConsultantList("src/main/resources/Data/All_Consultants.xml");

    }

}

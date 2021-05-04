/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.ReadData;

import java.io.File;
import java.io.FileInputStream;
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
            Document doc = builder.build(new FileInputStream(fileName));

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

        double consultantNumberCOW_Weekday = 0;
        double consultantNumberCOW_Weekend = 0;
        double consultantNumberNOW_Weekday = 0;
        double consultantNumberNOW_Weekend = 0;
        double consultantNumberOnCalls = 0;

        double consultantShiftsCarriedCOW_Weekday = 0;
        double consultantShiftsCarriedNOW_Weekday = 0;
        double consultantShiftsCarriedCOW_Weekend = 0;
        double consultantShiftsCarriedNOW_Weekend = 0;
        double consultantShiftsCarriedOnCalls = 0;

        double COW_WeekdayNumber = 0;
        double COW_WeekendNumber = 0;
        double NOW_WeekdayNumber = 0;
        double NOW_WeekendNumber = 0;
        double onCallsNumber = 0;
        
        for (Shift shift : shiftList.getShiftList()) {
            if (shift.getShiftType().equals("COW") && shift.getStartDate().getDayOfWeek() == DayOfWeek.MONDAY) {
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
        
        System.out.println("Cow weekday totals: "+ COW_WeekdayNumber);
                System.out.println("Now weekday totals: "+ NOW_WeekdayNumber);
                System.out.println("COW weekend totals: "+ COW_WeekendNumber);
                System.out.println("Now weekend totals: "+ NOW_WeekendNumber);
                System.out.println("OnCalls total: "+ onCallsNumber);

        for (ConsultantReader c : getConsultantList()) {
            double x = c.getFactor();

            if (c.getWeekdays() == TypeOfWorking.Paeds || c.getWeekdays() == TypeOfWorking.Neonates) {
                consultantNumberCOW_Weekday += x;
                consultantNumberNOW_Weekday += x;
            }
            if (c.getWeekdays() == TypeOfWorking.Both) {
                consultantNumberCOW_Weekday += x;
                consultantNumberNOW_Weekday += x;
            }
            

            if (c.getWeekends() == TypeOfWorking.Paeds || c.getWeekdays() == TypeOfWorking.Neonates) {
                consultantNumberCOW_Weekend += x;
                consultantNumberNOW_Weekend += x;
            }
            
            if (c.getWeekends() == TypeOfWorking.Both) {
                consultantNumberCOW_Weekend += x;
                consultantNumberNOW_Weekend += x;
            }
            
            if (c.getOnCalls() == TypeOfWorking.Both || c.getOnCalls() == TypeOfWorking.Neonates) {
                consultantNumberOnCalls += x;
                consultantShiftsCarriedOnCalls += c.getBalance().get("OnCalls");
            }

            consultantShiftsCarriedCOW_Weekday += c.getBalance().get("COW_Week");
            consultantShiftsCarriedNOW_Weekday += c.getBalance().get("NOW_Week");
            consultantShiftsCarriedCOW_Weekend += c.getBalance().get("COW_Weekend");
            consultantShiftsCarriedNOW_Weekend += c.getBalance().get("NOW_Weekend");

        }
        double magicWeekday = (COW_WeekdayNumber + NOW_WeekdayNumber+ consultantShiftsCarriedNOW_Weekday + consultantShiftsCarriedCOW_Weekday) / (consultantNumberCOW_Weekday+consultantNumberNOW_Weekday);
        double magicWeekend = (COW_WeekendNumber + NOW_WeekendNumber + consultantShiftsCarriedNOW_Weekend+ consultantShiftsCarriedCOW_Weekend)/  (consultantNumberCOW_Weekend +consultantNumberNOW_Weekend);
        System.out.println("magic weekend"+magicWeekend);
      
        for (Iterator<ConsultantReader> cs = getConsultantList().iterator(); cs.hasNext();) {
            ConsultantReader cons = cs.next();

            double partTimeFactor = cons.getFactor();
            
            if (cons.getWeekdays() == TypeOfWorking.Paeds ) {
                cons.setCOW_WeekTarget(partTimeFactor *2 *magicWeekday - cons.getBalance().get("COW_Week"));
                COW_WeekdayNumber -= (partTimeFactor *2 *magicWeekday - cons.getBalance().get("COW_Week"));
            } else if (cons.getWeekdays() == TypeOfWorking.Neonates) {
                cons.setNOW_WeekTarget(partTimeFactor *2 *magicWeekday - cons.getBalance().get("NOW_Week"));
                NOW_WeekdayNumber -= (partTimeFactor  *2*magicWeekday - cons.getBalance().get("NOW_Week"));
            } 
            
             if (cons.getWeekends() == TypeOfWorking.Paeds ) {
                cons.setCOW_WeekendTarget(partTimeFactor *2 *magicWeekend - cons.getBalance().get("COW_Weekend"));
                COW_WeekendNumber -= (partTimeFactor *2 *magicWeekend - cons.getBalance().get("COW_Weekend"));
            } else if (cons.getWeekends() == TypeOfWorking.Neonates) {
                cons.setNOW_WeekendTarget(partTimeFactor *2 *magicWeekend - cons.getBalance().get("NOW_Weekend"));
                NOW_WeekendNumber -= (partTimeFactor  *2*magicWeekend - cons.getBalance().get("NOW_Weekend"));
            } 
             
        }
        
        
        double COW_WeekdayPercent = (COW_WeekdayNumber+consultantShiftsCarriedCOW_Weekday)/(COW_WeekdayNumber+consultantShiftsCarriedCOW_Weekday+NOW_WeekdayNumber+consultantShiftsCarriedNOW_Weekday);
        double NOW_WeekdayPercent = (NOW_WeekdayNumber+consultantShiftsCarriedNOW_Weekday)/(COW_WeekdayNumber+consultantShiftsCarriedCOW_Weekday+NOW_WeekdayNumber+consultantShiftsCarriedNOW_Weekday);
        double COW_WeekendPercent = (COW_WeekendNumber+consultantShiftsCarriedCOW_Weekend)/(COW_WeekendNumber + NOW_WeekendNumber+ consultantShiftsCarriedCOW_Weekend+ consultantShiftsCarriedNOW_Weekend);
        double NOW_WeekendPercent = (NOW_WeekendNumber+consultantShiftsCarriedNOW_Weekend)/(COW_WeekendNumber + NOW_WeekendNumber+ consultantShiftsCarriedCOW_Weekend+ consultantShiftsCarriedNOW_Weekend);
        System.out.println("Look Here");
        for (Iterator<ConsultantReader> cs = getConsultantList().iterator(); cs.hasNext();) {
            ConsultantReader cons = cs.next();

            double partTimeFactor = cons.getFactor();

            
            if (cons.getWeekdays() == TypeOfWorking.Both ) {
                cons.setCOW_WeekTarget(COW_WeekdayPercent*2*partTimeFactor  *magicWeekday - cons.getBalance().get("COW_Week"));
                cons.setNOW_WeekTarget(NOW_WeekdayPercent*2*partTimeFactor  * magicWeekday - cons.getBalance().get("NOW_Week"));
        }
            if (cons.getWeekends() == TypeOfWorking.Both ) {
                cons.setCOW_WeekendTarget(COW_WeekendPercent*2*partTimeFactor  *magicWeekend - cons.getBalance().get("COW_Weekend"));
                cons.setNOW_WeekendTarget(NOW_WeekendPercent*2*partTimeFactor  * magicWeekend - cons.getBalance().get("NOW_Weekend"));
        }
            cons.setOnCallsTarget(partTimeFactor * (onCallsNumber + consultantShiftsCarriedOnCalls) / consultantNumberOnCalls - cons.getBalance().get("OnCalls"));
            
            
            if (cons.getConsultantName().contains("Jo Spinks")) {
                System.out.println("Registers with " + cons.getConsultantName());
          
            }
            
        }
            
            /*
            if (cons.getWeekdays() == TypeOfWorking.Paeds || cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setCOW_WeekTarget(partTimeFactor * (COW_WeekdayNumber + consultantShiftsCarriedCOW_Weekday) / consultantNumberCOW_Weekday - cons.getBalance().get("COW_Week"));
            } else {cons.setCOW_WeekTarget(0);}

            if (cons.getWeekends() == TypeOfWorking.Paeds || cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setCOW_WeekendTarget(partTimeFactor * (COW_WeekendNumber + consultantShiftsCarriedCOW_Weekend) / consultantNumberCOW_Weekend - cons.getBalance().get("COW_Weekend"));
            } else {cons.setCOW_WeekendTarget(0);}

            if (cons.getWeekdays() == TypeOfWorking.Neonates || cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setNOW_WeekTarget(partTimeFactor * (NOW_WeekdayNumber + consultantShiftsCarriedNOW_Weekday) / consultantNumberNOW_Weekday - cons.getBalance().get("NOW_Week"));
            } else {cons.setNOW_WeekTarget(0);}

            if (cons.getWeekends() == TypeOfWorking.Neonates || cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setNOW_WeekendTarget(partTimeFactor * (NOW_WeekendNumber + consultantShiftsCarriedNOW_Weekend) / consultantNumberNOW_Weekend - cons.getBalance().get("NOW_Weekend"));
            } else {cons.setNOW_WeekendTarget(0);}

            cons.setOnCallsTarget(partTimeFactor * (onCallsNumber + consultantShiftsCarriedOnCalls) / consultantNumberOnCalls - cons.getBalance().get("OnCalls"));

            
            if (cons.getWeekdays() == TypeOfWorking.Paeds ) {
                cons.setCOW_WeekTarget(partTimeFactor * 2 *(COW_WeekdayNumber + NOW_WeekdayNumber+ consultantShiftsCarriedNOW_Weekday + consultantShiftsCarriedCOW_Weekday) / (consultantNumberCOW_Weekday+consultantNumberNOW_Weekday) - cons.getBalance().get("COW_Week"));
            } else if (cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setCOW_WeekTarget(partTimeFactor *(COW_WeekdayNumber + NOW_WeekdayNumber+ consultantShiftsCarriedNOW_Weekday + consultantShiftsCarriedCOW_Weekday) / (consultantNumberCOW_Weekday+consultantNumberNOW_Weekday) - cons.getBalance().get("COW_Week"));
            } else {cons.setCOW_WeekTarget(0);}

         
            if (cons.getWeekdays() == TypeOfWorking.Neonates) {
                cons.setNOW_WeekTarget(partTimeFactor * 2 *(COW_WeekdayNumber + NOW_WeekdayNumber+ consultantShiftsCarriedNOW_Weekday + consultantShiftsCarriedCOW_Weekday) / (consultantNumberCOW_Weekday+consultantNumberNOW_Weekday) - cons.getBalance().get("NOW_Week"));
            } else  if (cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setNOW_WeekTarget(partTimeFactor *(COW_WeekdayNumber + NOW_WeekdayNumber+ consultantShiftsCarriedNOW_Weekday + consultantShiftsCarriedCOW_Weekday) / (consultantNumberCOW_Weekday+consultantNumberNOW_Weekday) - cons.getBalance().get("NOW_Week"));
            } else {cons.setNOW_WeekTarget(0);}

           if (cons.getWeekends() == TypeOfWorking.Paeds) {
                cons.setCOW_WeekendTarget(partTimeFactor * 2 *(COW_WeekendNumber + NOW_WeekendNumber + consultantShiftsCarriedNOW_Weekend + consultantShiftsCarriedCOW_Weekend) / (consultantNumberCOW_Weekend +consultantNumberNOW_Weekend)- cons.getBalance().get("COW_Weekend"));
            } else if (cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setCOW_WeekendTarget(partTimeFactor * (COW_WeekendNumber + NOW_WeekendNumber + consultantShiftsCarriedNOW_Weekend + consultantShiftsCarriedCOW_Weekend) / (consultantNumberCOW_Weekend +consultantNumberNOW_Weekend)- cons.getBalance().get("COW_Weekend"));
            } else {cons.setCOW_WeekendTarget(0);}
            
            if (cons.getWeekends() == TypeOfWorking.Neonates) {
                cons.setNOW_WeekendTarget(partTimeFactor * 2 *(COW_WeekendNumber + NOW_WeekendNumber + consultantShiftsCarriedNOW_Weekend + consultantShiftsCarriedCOW_Weekend) / (consultantNumberCOW_Weekend +consultantNumberNOW_Weekend)- cons.getBalance().get("NOW_Weekend"));
            } else if (cons.getWeekdays() == TypeOfWorking.Both) {
                cons.setNOW_WeekendTarget(partTimeFactor *(COW_WeekendNumber + NOW_WeekendNumber + consultantShiftsCarriedNOW_Weekend + consultantShiftsCarriedCOW_Weekend) / (consultantNumberCOW_Weekend +consultantNumberNOW_Weekend)- cons.getBalance().get("NOW_Weekend"));
            } else {cons.setNOW_WeekendTarget(0);}

            cons.setOnCallsTarget(partTimeFactor * (onCallsNumber + consultantShiftsCarriedOnCalls) / consultantNumberOnCalls - cons.getBalance().get("OnCalls"));
            
        }*/
    }

    public static void update() {
        for (ConsultantReader c : consultantList) {
            c.update();
        }
    }

    public static void main(String[] args) {
        new ConsultantList("Resources/Data/All_Consultants.xml");

    }

}

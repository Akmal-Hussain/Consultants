package main.java.ReadData;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pi
 */
public class ShiftStructureReader implements Serializable{

    static String[] WeekdayDay;
    static String[] WeekdayNight;
    static String[] Weekend;

 

    public ShiftStructureReader(String filename) {
        try {
            Builder builder = new Builder();
            //File file = new File(filename);
            Document doc = builder.build(new FileInputStream(filename));

            Element root = doc.getRootElement();

            Element weekdays = root.getFirstChildElement("WeekDays");
            WeekdayDay = getShifts(weekdays, "Days");
            WeekdayNight = getShifts(weekdays, "Nights");

            Weekend = getShifts(root, "WeekEnds");

        } catch (ParsingException | IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    String[] getShifts(Element e,String shift) {
        Element sepshift = e.getFirstChildElement(shift);
        Elements sepshifts = sepshift.getChildElements();
        String [] shifts = new String [sepshifts.size()]; 
        int x=0;
        for (Element shif : sepshifts) {
            shifts[x] = shif.getValue();
            x++;
        }
        return shifts;
    }
    
       public static String[] getWeekdayDay() {
        return WeekdayDay;
    }

    public static String[] getWeekdayNight() {
        return WeekdayNight;
    }

    public static String[] getWeekend() {
        return Weekend;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ShiftStructureReader("Resources/Data/Shift_Structure.xml");
    }

}

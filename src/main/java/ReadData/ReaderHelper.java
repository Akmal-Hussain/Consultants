package main.java.ReadData;


import java.time.LocalDate;
import nu.xom.Element;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pi
 */
public class ReaderHelper {

    public static LocalDate getDate(Element e) {
        int day = Integer.parseInt(e.getFirstChildElement("Day").getValue());
        int month = Integer.parseInt(e.getFirstChildElement("Month").getValue());
        int year = Integer.parseInt(e.getFirstChildElement("Year").getValue());
        return LocalDate.of(year, month, day);
    }
    
    public static Element setDate(LocalDate d,String name) {
        Element root = new Element(name);
        Element day = new Element("Day");
        day.appendChild(""+d.getDayOfMonth());
        Element month = new Element("Month");
        month.appendChild(""+d.getMonthValue());
        Element year = new Element("Year");
        year.appendChild(""+d.getYear());
        root.appendChild(day);
        root.appendChild(month);
        root.appendChild(year);
        return root;
    }
    
    

}

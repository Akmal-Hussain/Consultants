package main.java.ReadData;


import java.time.LocalDate;
import nu.xom.Element;
import nu.xom.Elements;

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

    static LocalDate getDate(Element e) {
        int day = Integer.parseInt(e.getFirstChildElement("Day").getValue());
        int month = Integer.parseInt(e.getFirstChildElement("Month").getValue());
        int year = Integer.parseInt(e.getFirstChildElement("Year").getValue());
        return LocalDate.of(year, month, day);
    }
    
    

}

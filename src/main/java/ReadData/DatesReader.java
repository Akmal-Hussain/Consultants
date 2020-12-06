package main.java.ReadData;


import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
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
public class DatesReader implements Serializable{

  
    static LocalDate [] range = new LocalDate[2];
    static LocalDate [] bankHolidays;
    static LocalDate [][] schoolHolidays;
    
    public DatesReader(String filename){
        try {
            Builder builder = new Builder();
            //File file = new File(filename);
            Document doc = builder.build(getClass().getResourceAsStream(filename));
            
            Element root = doc.getRootElement();
            
            Element elementRange = root.getFirstChildElement("Range");
            range[0] = ReaderHelper.getDate(elementRange.getFirstChildElement("From"));
            range[1] = ReaderHelper.getDate(elementRange.getFirstChildElement("To"));
            
            Element elementBankHolidays = root.getFirstChildElement("BankHolidays");
     
            bankHolidays = new LocalDate[elementBankHolidays.getChildElements("BankHoliday").size()];
            Elements bankHoliday = elementBankHolidays.getChildElements();
            int x = 0;
            for (Element bank: bankHoliday) {
                
                bankHolidays[x] = ReaderHelper.getDate(bank);
                x++;
            }
            
            Element elementSchoolHolidays = root.getFirstChildElement("SchoolHolidays");
            Elements schoolHoliday = elementSchoolHolidays.getChildElements();
            schoolHolidays = new LocalDate[schoolHoliday.size()][2];
            int y = 0;
            for (Element sHoliday : schoolHoliday) {
                
                schoolHolidays[y][0] = ReaderHelper.getDate(sHoliday.getFirstChildElement("From"));
                schoolHolidays[y][1] = ReaderHelper.getDate(sHoliday.getFirstChildElement("To"));
                y++;
            }
        } catch (ParsingException | IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
        public static LocalDate[] getRange() {
        return range;
    }

    public static LocalDate[] getBankHolidays() {
        return bankHolidays;
    }

    public static LocalDate[][] getSchoolHolidays() {
        return schoolHolidays;
    }
}

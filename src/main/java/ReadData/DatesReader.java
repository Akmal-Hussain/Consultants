package main.java.ReadData;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

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
    static String filename;
    
    public DatesReader(String filename){
        try {
            this.filename = filename;
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
    
    public static void updateDatesReader() {
        Element Root = new Element("Dates");
        
        Element rangE = new Element("Range");
        Element from = ReaderHelper.setDate(getRange()[0], "From");
        Element to = ReaderHelper.setDate(getRange()[1], "To");
        rangE.appendChild(from);
        rangE.appendChild(to);
        
               
        Element bankholidays = new Element("BankHolidays");
            for (LocalDate d :bankHolidays) {
              Element bankholiday = ReaderHelper.setDate(d,"BankHoliday");
              bankholidays.appendChild(bankholiday);
            }
        
        Element schoolholidays = new Element("SchoolHolidays");
        for (LocalDate [] d :schoolHolidays) {
              Element schoolholiday = new Element("SchoolHoliday");
              Element fro = ReaderHelper.setDate(d[0],"From");
              Element too = ReaderHelper.setDate(d[1],"To");
              schoolholiday.appendChild(fro);
              schoolholiday.appendChild(too);
              schoolholidays.appendChild(schoolholiday);
            }
        
        Root.appendChild(rangE);
        Root.appendChild(bankholidays);
        Root.appendChild(schoolholidays);
        
         Document doc = new Document(Root);
  System.out.println(doc.toXML()); 
        try {
             FileOutputStream fileOutputStream = new FileOutputStream ("src"+filename);
Serializer serializer = new Serializer(fileOutputStream, "UTF-8");
serializer.setIndent(4);
serializer.write(doc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
 

        
    }
}

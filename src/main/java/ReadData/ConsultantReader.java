package main.java.ReadData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nu.xom.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pi
 */
public class ConsultantReader implements Serializable {

    String consultantName;
    String filename;

    LocalDate[][] leaveDatesArray;
    List<LocalDate[]> leaveDatesList;
    List <DayOfWeek> daysWorkingList;
    List<LocalDate> leaveDates = new ArrayList<>();
    DayOfWeek[] daysWorking;
    HashMap<String, Double> balance = new HashMap<>();
    
    double COW_WeekTarget;
    double NOW_WeekTarget;
    double COW_WeekendTarget;
    double NOW_WeekendTarget;
    double onCallsTarget;

    
    
    TypeOfWorking weekdays;
    TypeOfWorking weekends;
    TypeOfWorking onCalls;

   
    public ConsultantReader(String fileName) {
        try {
            Builder builder = new Builder();
           // File xfile = new File(fileName);
                       this.filename= fileName;

            Document doc = builder.build(new FileInputStream(filename));
            Element root = doc.getRootElement();

            Element name = root.getFirstChildElement("Name");
            consultantName = name.getValue();

            Element leave = root.getFirstChildElement("Dates_Of_Leave");
            Elements blocks = leave.getChildElements("Leave_Block");

            //populate array of Leave Dates
            leaveDatesArray = new LocalDate[blocks.size()][2];
            leaveDatesList = new ArrayList<>();
            
            int y = 0;
            for (Element block : blocks) {
                String date = "From";
                LocalDate[] dateBlock = new LocalDate[2];
                for (int x = 0; x < 2; x++) {
                    if (x == 1) {
                        date = "To";
                    }
                    Element theDate = block.getFirstChildElement(date);
                    leaveDatesArray[y][x] = ReaderHelper.getDate(theDate);
                    dateBlock[x] = ReaderHelper.getDate(theDate);
                }
                y++;
                leaveDatesList.add(dateBlock);
            }
            //          LocalDate localDate = leaveDatesArray[0][0];
            //          List<LocalDate> dates leaveDates.add(localDate);
            
            LocalDate localDate;
            for (int x = 0; x < y; x++) {
                localDate = leaveDatesArray[x][0];
                while (!localDate.isEqual(leaveDatesArray[x][1])) {
                    leaveDates.add(localDate);
                    localDate = localDate.plusDays(1L);
                }
                leaveDates.add(localDate);
            }

            Element timeWorking = root.getFirstChildElement("FullOrPartTime");
            String fullORPartTime = timeWorking.getValue();
    //        System.out.println(timeWorking.getValue());
            if (fullORPartTime.contains("Full")) {
                fullOrPartTime = FullOrPartTime.Full;
            } else {
                fullOrPartTime = FullOrPartTime.PartTime;
            }
            factor = 1D;
            if (fullOrPartTime.equals(FullOrPartTime.PartTime)) {
                Element factoR = timeWorking.getFirstChildElement("Factor");
                factor =Double.valueOf(factoR.getValue());
            }
            // populate array of the Working Days of the week.
            Elements daysOfWeekWorking = timeWorking.getFirstChildElement("DaysWorking")
                    .getChildElements("WeekDay");
            daysWorking = new DayOfWeek[daysOfWeekWorking.size()];
            int z = 0;
            for (Element weekday : daysOfWeekWorking) {
                daysWorking[z] = DayOfWeek.valueOf(weekday.getValue().toUpperCase());
                z++;
            }
            
            Arrays.sort(daysWorking);
            daysWorkingList = new ArrayList<>(Arrays.asList(daysWorking));
            // COW or NOW and OnCalls
            Element typeOfWorking = root.getFirstChildElement("Types_Of_Work");

            // Type of Working During the Week and Weekends         
            String[] times = {"Week_Work", "Weekend_Work", "OnCalls"};
            for (String time : times) {
                Elements weekWork = typeOfWorking.getFirstChildElement(time).getChildElements();
                boolean weekCOW = false;
                boolean weekNOW = false;

                for (Element week : weekWork) {
                    if (week.getValue().equals("COW") || week.getValue().equals("Paed")) {
                        weekCOW = true;
                    }
                    if (week.getValue().equals("NOW") || week.getValue().equals("Neo")) {
                        weekNOW = true;
                    }
                }
                TypeOfWorking temp;

                if (weekCOW && weekNOW) {
                    temp = TypeOfWorking.Both;
                } else {
                    if (weekCOW) {
                        temp = TypeOfWorking.Paeds;
                    } else {
                        temp = TypeOfWorking.Neonates;
                    }
                }
                switch (time) {
                    case ("Week_Work"):
                        weekdays = temp;
                        break;
                    case ("Weekend_Work"):
                        weekends = temp;
                        break;
                    case ("OnCalls"):
                        onCalls = temp;
                        break;
                }
            }

            Element balAnce = root.getFirstChildElement("Balance_Last_Rota");
            Elements balances = balAnce.getChildElements();
            for (Element type : balances) {
                balance.put(type.getLocalName(), Double.valueOf(type.getValue()));
            }
           
        } catch (ParsingException | IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }
     public DayOfWeek[] getDaysWorking() {
        return daysWorking;
    }

    public TypeOfWorking getWeekdays() {
        return weekdays;
    }
    
    
    public void setWeekdays(TypeOfWorking t) {
        weekdays = t;
    }

    public TypeOfWorking getWeekends() {
        return weekends;
    }
    
    public void setWeekends(TypeOfWorking t) {
        weekends = t;
    }
    

    public TypeOfWorking getOnCalls() {
        return onCalls;
    }
    
    public void setOnCalls(TypeOfWorking t) {
        onCalls = t;
    }
    
    

    public HashMap<String, Double> getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return consultantName.trim();
    }
    public void setCOW_WeekTarget(double COW_WeekTarget) {
        this.COW_WeekTarget = COW_WeekTarget;
    }

    public void setNOW_WeekTarget(double NOW_WeekTarget) {
        this.NOW_WeekTarget = NOW_WeekTarget;
    }

    public void setCOW_WeekendTarget(double COW_WeekendTarget) {
        this.COW_WeekendTarget = COW_WeekendTarget;
    }

    public void setNOW_WeekendTarget(double NOW_WeekendTarget) {
        this.NOW_WeekendTarget = NOW_WeekendTarget;
    }

    public void setOnCallsTarget(double onCallsTarget) {
        this.onCallsTarget = onCallsTarget;
    }

    public double getCOW_WeekTarget() {
        return COW_WeekTarget;
    }

    public double getNOW_WeekTarget() {
        return NOW_WeekTarget;
    }

    public double getCOW_WeekendTarget() {
        return COW_WeekendTarget;
    }

    public double getNOW_WeekendTarget() {
        return NOW_WeekendTarget;
    }

    public double getOnCallsTarget() {
        return onCallsTarget;
    }
    
public String getConsultantName() {
        return consultantName;
    }
    
    
    public List<LocalDate[]> getLeaveDatesList(){
        return leaveDatesList;
    }

    public LocalDate[][] getLeaveDatesArray() {
        return leaveDatesArray;
    }
    FullOrPartTime fullOrPartTime;
    
    Double factor;
    
    public double getFactor(){
        return factor;
    }
    
    public void setFactor(Double d){
        factor = d;
    }
    
    public FullOrPartTime getFullOrPartTime() {
        return fullOrPartTime;
    }
    
    public void setFullOrPartTime(FullOrPartTime t) {
        fullOrPartTime = t;
    }
    
    

    public List<DayOfWeek> getDaysWorkingList() {
        return daysWorkingList;
    }
    

    public List<LocalDate> getLeaveDates() {
        return leaveDates;
    }
    
    public void update(){
        Element Root = new Element("Consultant");
        
        Element name = new Element("Name");
        name.appendChild(consultantName);
        
        Element datesOfLeave = new Element("Dates_Of_Leave");
        leaveDates.clear();
        for (LocalDate[]d:leaveDatesList){
            Element leaveBlock = new Element("Leave_Block");
            Element fro = ReaderHelper.setDate(d[0],"From");
            Element too = ReaderHelper.setDate(d[1],"To");
            
            
            LocalDate localDate = d[0];
      
                while (!localDate.isEqual(d[1])) {
                    leaveDates.add(localDate);
                    localDate = localDate.plusDays(1L);
                }
                leaveDates.add(localDate);
            

            
            leaveBlock.appendChild(fro);
            leaveBlock.appendChild(too);
            datesOfLeave.appendChild(leaveBlock);
        }
        
        Element fullOrPart = new Element("FullOrPartTime");
        fullOrPart.appendChild(fullOrPartTime.toString());        
        Element factoR = new Element("Factor");
        factoR.appendChild(""+factor);
        Element daysWorking = new Element("DaysWorking");
        for (DayOfWeek d: daysWorkingList) {
            Element weekDay = new Element("WeekDay");
            weekDay.appendChild(d.toString());
            daysWorking.appendChild(weekDay);
        }
        fullOrPart.appendChild(factoR);
        fullOrPart.appendChild(daysWorking);
        
        Element typesOfWork = new Element("Types_Of_Work");
        Element weekDayWork = new Element("Week_Work");
        if (weekdays == TypeOfWorking.Both) {
            Element type1 = new Element("Type");
            Element type2 = new Element("Type");
            type1.appendChild("COW");
            type2.appendChild("NOW");
            weekDayWork.appendChild(type1);
            weekDayWork.appendChild(type2);
        } else {
            Element type = new Element("Type");
            if (weekdays == TypeOfWorking.Paeds) {
                type.appendChild("COW");
            } else {
                type.appendChild("NOW");
            }
            weekDayWork.appendChild(type);
        }
        Element weekEndWork = new Element("Weekend_Work");
        if (weekends == TypeOfWorking.Both) {
            Element type1 = new Element("Type");
            Element type2 = new Element("Type");
            type1.appendChild("COW");
            type2.appendChild("NOW");
            weekEndWork.appendChild(type1);
            weekEndWork.appendChild(type2);
        } else {
            Element type = new Element("Type");
            if (weekends == TypeOfWorking.Paeds) {
                type.appendChild("COW");
            } else {
                type.appendChild("NOW");
            }
            weekEndWork.appendChild(type);
        }
        Element onCallWork = new Element("OnCalls");
        if (onCalls == TypeOfWorking.Both) {
            Element type1 = new Element("Type");
            Element type2 = new Element("Type");
            type1.appendChild("Paed");
            type2.appendChild("Neo");
            onCallWork.appendChild(type1);
            onCallWork.appendChild(type2);
        } else {
            Element type = new Element("Type");
            if (onCalls == TypeOfWorking.Paeds) {
                type.appendChild("Paed");
            } else {
                type.appendChild("Neo");
            }
            onCallWork.appendChild(type);
        }
        typesOfWork.appendChild(weekDayWork);
        typesOfWork.appendChild(weekEndWork);
        typesOfWork.appendChild(onCallWork);
        
        Element balanceLastRota = new Element("Balance_Last_Rota");
        for (Map.Entry<String,Double> entry :balance.entrySet()) {
            Element balancing = new Element(entry.getKey());
            balancing.appendChild(""+entry.getValue());
            balanceLastRota.appendChild(balancing);            
        }
       
        Root.appendChild(name);
        Root.appendChild(datesOfLeave);
        Root.appendChild(fullOrPart);
        Root.appendChild(typesOfWork);
        Root.appendChild(balanceLastRota);
        
         Document doc = new Document(Root);
  System.out.println(doc.toXML());
        System.out.println(filename);
        try {
             FileOutputStream fileOutputStream = new FileOutputStream (filename);
Serializer serializer = new Serializer(fileOutputStream, "UTF-8");
serializer.setIndent(4);
serializer.write(doc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
 

        
    
    }

    public static void main(String[] args) {
        ConsultantReader c = new ConsultantReader("Resources/Data/Elspeth_Brooker.xml");
        new DatesReader("Resources/Data/Dates.xml");
        if (c.getFullOrPartTime() ==FullOrPartTime.PartTime) {
            System.out.println("Part");
        }
        System.out.println(c.getWeekdays());
        for (DayOfWeek d: c.getDaysWorking()) {
        System.out.println(d);
        }
        System.out.println(Arrays.asList(c.getDaysWorking()).contains(DayOfWeek.TUESDAY));
    }
}




/*
enum Consultants {
ANN("Ann",			SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
ASHA("Asha", 		SkillTypes.PAEDonly, 	OnCallType.PAEDandorNEO), 
CLAIRE("Claire", 	SkillTypes.PAEDonly, 	OnCallType.PAEDonly ), 
RAVI("Ravi", 		SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
AK("Ak", 			SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
CHANDAN("Chandan", 	SkillTypes.PAEDnREG, 	OnCallType.PAEDandorNEO ),
SATHIYA("Sathiya", 	SkillTypes.PAEDnREG, 	OnCallType.PAEDandorNEO ),
JO("Jo", 			SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
PETER("Peter", 		SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
BALAJI("Balaji", 	SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
HELEN("Helen", 		SkillTypes.PAEDnREG, 	OnCallType.PAEDandorNEO ), 
KEMY("Kemy", 		SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ), 
NICKY("Nicky", 		SkillTypes.PAEDnNEO, 	OnCallType.PAEDandorNEO ),
SUE("Sue", 			SkillTypes.PAEDonly, 	OnCallType.PAEDonly );
	



	private String code;
	private SkillTypes skillType;
	private OnCallType onCallType;
	
	
	private Consultants(String code, SkillTypes skillType, OnCallType onCallType) {
		this.code = code;
		this.skillType = skillType;
		this.onCallType = onCallType;
	}
	
	public String getCode() {
	return code;
	}
	
	public SkillTypes getSkillType() {
	return skillType;
	}
	
	public OnCallType getOnCallType() {
		return onCallType;
	}
	
}

// PAEDnNEO,PAEDnREG,PAEDonly;
// PAEDandorNEO, PAEDonly;

*/

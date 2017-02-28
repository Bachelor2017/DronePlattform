package droneplatform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * 
 */
public class FaultHandler {
    
    private ArrayList<String> faultList;
    int x = 0;
    
    public FaultHandler(){
         faultList = new ArrayList<>();
        fillList();
        testFault();
    }
    
    public void fillList(){
        addFaultToList("");
        addFaultToList("");
         addFaultToList("");
        addFaultToList("");
         addFaultToList("");
        addFaultToList("");
         addFaultToList("");
        addFaultToList("");
         addFaultToList("");
        addFaultToList("");
         addFaultToList("");
        addFaultToList("");
         addFaultToList("");
        addFaultToList("");
    }
    
    public void testPrint(){
        for(int i = 0; i < faultList.size(); i++){
            System.out.println(faultList.get(i));
        }
    }
    
    public void addFaultToList(String command){
        String time = "";
        String fault="";
        if(command =="")
        {
            fault = "";
        }
        else
        {
             fault = command;
        }
       
        faultList.add(fault);     
    }
    
     private String getTimeStamp() {
        String time = "";
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            time = " Time: " + sdf.format(date);
        } catch (NullPointerException e) {
        }
        return time;
    }
    
    public ArrayList getFaultList(){
        return faultList;
    }
   
    public String lastFaultMessage(){
        int length = faultList.size();
        String faultString = faultList.get(length - 1);
        return faultString;
    }
   
    
    public void addFault()
    {
        addFaultToList("Error " + x);
        x++;
    }
    
    
     public void testFault() {
        
            TimerTask tTask = new TimerTask() {
                public void run() {
                   
             addFault();
                }
            };

            Timer timer = new java.util.Timer();
            timer.scheduleAtFixedRate(tTask, 1000, 1000);
        } 
       
    
    
}

package droneplatform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class FaultHandler {
    
    private ArrayList<String> faultList;
    
    public FaultHandler(){
         faultList = new ArrayList<>();
        fillList();
    }
    
    public void fillList(){
        addFaultToList("Error 1");
        addFaultToList("Error 2");
        addFaultToList("Error 3");
        addFaultToList("Error 4");
        addFaultToList("Error 5");
        addFaultToList("Error 6");
        addFaultToList("Error 7");
        addFaultToList("Error 8");
        addFaultToList("Error 9");
        addFaultToList("Error 10");
        addFaultToList("Error 11");
        addFaultToList("Error 12");
        addFaultToList("Error 13");
        addFaultToList("Error 14");
        addFaultToList("Error 15");
        addFaultToList("Error 16");
        addFaultToList("Error 17");
        addFaultToList("Error 18");
        addFaultToList("Error 19");
        addFaultToList("Error 20");
        addFaultToList("Error 21");
        addFaultToList("Error 28");
        addFaultToList("Error 30");
        addFaultToList("Error 32");
        addFaultToList("Error 34");
        addFaultToList("Error 36");
        addFaultToList("Error 38");
        addFaultToList("Error 46");
        addFaultToList("Error 48");
        addFaultToList("Error 50");
        addFaultToList("Error 52");
    }
    
    public void testPrint(){
        for(int i = 0; i < faultList.size(); i++){
            System.out.println(faultList.get(i));
        }
    }
    
    public void addFaultToList(String command){
        String time = "";
        
        String fault = "Command: " + command + getTimeStamp();
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
    
    private ArrayList getFaultList(){
        return faultList;
    }
    
    public String[] guiFaultList(){
        String[] faultListGUI = new String[10];
        
        for(int i = 0; i < 10; i++){
            faultListGUI[i] = faultList.get(faultList.size() - 1 - i);
        } 
        return faultListGUI;
    }
    
    public void testing(){
        for(int i = 0; i < 10; i++){
            String[] test = guiFaultList();
            System.out.println(test[i]);
        }
    }
    
}

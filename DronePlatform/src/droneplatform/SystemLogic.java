package droneplatform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class does all the logic and calculations
 *
 */



//DENNE KLASSEN MÅ RYDDEST I. SAMME SOM MED FAULTLOGIC. SLÅ SAMMEN CASE
public class SystemLogic implements Runnable {

    ///FRA eventStates
    private ArrayList<Event> differentEventStates;
    private ArrayList<Event> events;
    private ArrayList<String> eventList;

    int test = 1;

    boolean calibrated = false;
    int oldState = 100;
    int newState = 0;
    //time
    int totalTime = 10;
    int totalTimeUsed = 0;
    int totalTimeLeft = 10;
    int cycleTimeUsed = 0;
    int cyclustimeLeft = 0;
    int cycleTime = 0;
    int totalPercentageCompleted = 0;
    int cyclusPercentageCompleted = 0;

    TimerTask tTask;
    java.util.Timer timer;
    boolean runState = true;

    private Thread t;
    private DataHandler dataHandler;
    private byte[] dataFromTeensy;
    private byte[] datatoTeensy;
    private int caseScenario;
    private Semaphore semaphore;
    private boolean platformMode = false;

    boolean value = false;

    public SystemLogic(DataHandler dh, Semaphore semaphore) {
        this.dataHandler = dh;
        this.semaphore = semaphore;

        caseScenario = 500;

        //FRA EVENTSTATES
        eventList = new ArrayList<>();
        events = new ArrayList<>();
        differentEventStates = new ArrayList<>();
        addingEventStates();
        Event event12 = new Event(8, "System powered on");
        events.add(event12);
        runTimer();
        ///////
    }

    /**
     * start a new thread containing the logic
     */
    public void start() {
        t = new Thread(this, "controller thread");
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                platformMode = dataHandler.getPlatformMode();
                dataFromTeensy = dataHandler.getDataFromTeensy();
                datatoTeensy = dataHandler.getDataToTeensy();
                semaphore.release();
             
                if (!platformMode) {

                    int readData = dataFromTeensy[8];
                    newState = readData;
                    switchCalibrationCases(newState);
                } else if (platformMode) {
                    int readData = dataFromTeensy[6];
                    newState = readData;
                    switchCases(newState);
                }

                caseScenario = newState;
              
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemLogic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * The switch cases. each case representing a event.
     *
     * @param caseNumber
     * @throws InterruptedException
     */
    protected void switchCases(int caseNumber) throws InterruptedException {
        int number = caseNumber;
        switch (number) {
            case (0):

                if (oldState != newState) {
                    totalTime = 120;
                    totalTimeLeft = 120;
                    caseLogic(25, totalTimeLeft, 4);

                    oldState = newState;//setting the event time to 13, and time used to 0
                }

                break;
            case (6):      //Calibrating slider
                if (oldState != newState) {
                    totalTime = 120;
                    totalTimeLeft = 120;
                    caseLogic(25, totalTimeLeft, 5);

                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (10):       //Calibrating Lift
                if (oldState != newState) {
                    //totalTimeLeft = totalTimeLeft - cycleTime;
                    caseLogic(20, 85, 6);

                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (11):        //Calibrating arm
                if (oldState != newState) {
                    totalTimeLeft = totalTimeLeft - cycleTime;
                    caseLogic(4, 50, 7);
                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (13):
                if (oldState != newState) {
                    totalTimeLeft = totalTimeLeft - cycleTime;
                    caseLogic(5, 10, 8);
                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (14):
                if (oldState != newState) {
                    totalTimeLeft = totalTimeLeft - cycleTime;
                    case4(4, totalTimeLeft);
                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (15):
                if (oldState != newState) {
                    totalTimeLeft = totalTimeLeft - cycleTime;
                    case5(5, totalTimeLeft);
                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (17):
                if (oldState != newState) {
                    totalTimeLeft = totalTimeLeft - cycleTime;
                    case6(10, totalTimeLeft);
                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
        }
    }

    /**
     * The switch cases. each case representing a event.
     *
     * @param caseNumber
     * @throws InterruptedException
     */
    protected void switchCalibrationCases(int caseNumber) throws InterruptedException {
        int number = caseNumber;
        switch (number) {
            case (0):

                if (oldState != newState) {
                    // if (runState) {
                    totalTime = 40;
                    totalTimeLeft = 40;
                    cyclustimeLeft = 0;
                    totalTimeUsed = 0;
                    cycleTimeUsed = 0;

                    //caseLogic(1, totalTimeLeft, 1);

                    oldState = newState;//setting the event time to 13, and time left to total time
                }

                break;
            case (2):      //Calibrating slider
                //(runState) {
                if (oldState != newState) {
                  
                    totalTimeLeft = totalTimeLeft;
                    caseLogic(15, totalTimeLeft, 1);

                    oldState = newState;//setting the event time to 13, and time left to total time
                }
                break;
            case (4):       //Calibrating Lift
                if (oldState != newState) {
                   // totalTime = 40;
                    totalTimeLeft = totalTimeLeft- cycleTime;
                    caseLogic(25, totalTimeLeft, 2);

                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
            case (6):        //Calibrating arm
                if (oldState != newState) {
                   
                   caseLogic(0,0, 3);

                    oldState = newState;//setting the event time to 13, and time used to 0
                }
                break;
          

               
        }
    }

    /**
     * case: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void caseLogic(int cTime, int totTimeLeft, int x) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        System.out.println("test case: " + x);
        //  System.out.println(differentEventStates.get(0).getEventName());
        events.add(differentEventStates.get(x));

    }

    /**
     * case 1: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void case1(int cTime, int totTimeLeft) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        System.out.println("test case 1");
        //  System.out.println(differentEventStates.get(0).getEventName());
        events.add(differentEventStates.get(0));

    }

    /**
     * case 2: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void case2(int cTime, int totTimeLeft) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        System.out.println("test case 2");
        //   System.out.println(differentEventStates.get(1).getEventName());
        events.add(differentEventStates.get(1));

    }

    /**
     * case 3: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void case3(int cTime, int totTimeLeft) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        System.out.println("test case 3");
        //   System.out.println(differentEventStates.get(2).getEventName());
        events.add(differentEventStates.get(2));

    }

    /**
     * case 4: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void case4(int cTime, int totTimeLeft) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        System.out.println("test case 4");
        //  System.out.println(differentEventStates.get(3).getEventName());
        events.add(differentEventStates.get(3));

    }

    /**
     * case 5: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void case5(int cTime, int totTimeLeft) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        //    System.out.println("test case 5");
        //    System.out.println(differentEventStates.get(4).getEventName());
        events.add(differentEventStates.get(4));
    }

    /**
     * case 6: setting the total time left to the total time value. Setting the
     * cycletime used to 0 to state that a new cycle is starting
     *
     * @param cTime setting the cycle time of the cycle
     * @param totTimeLeft setting the total time left in case the earlier
     * prosess is finished before planed. This also updates the progreessbar in
     * GUI
     * @throws InterruptedException
     */
    public void case6(int cTime, int totTimeLeft) throws InterruptedException {
        cycleTimeUsed = 1;
        totalTimeLeft = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        //    System.out.println("test case 6");
        //    System.out.println(differentEventStates.get(4).getEventName());
        events.add(differentEventStates.get(4));
    }

    /**
     * increments the totalTimeused and cycletimeused by one each 1000ms. does
     * the calculations for the percentage and timeleft for both cycle and total
     * time
     */
    public void runTimer() {

        tTask = new TimerTask() {
            public void run() {

                try {
                    //  if ((caseScenario != 0) && (platformMode == true)) {
                    if ((caseScenario != 0)) {
                        calculateCyclusTimeLeft();
                        calculateTotalTimeLeft();
                        calculateTotalPercentageCompleted();
                        calculateCyclusPercentageCompleted();

                        totalTimeUsed++;
                        cycleTimeUsed++;

                    } else {
                        totalTimeLeft = totalTime;
                        cyclustimeLeft = 0;
                        totalTimeUsed = 0;
                        cycleTimeUsed = 0;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(SystemLogic.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 1000);
    }

    /**
     * get the name of the last event added to the events list
     *
     * @return the eventmae as a string
     */
    public String getLastEventState() {
        int eventListSize = events.size();
        String eventName = "";
        if (eventListSize == 0) {
            eventName = events.get(eventListSize).getEventName();
        } else {
            eventName = events.get(eventListSize - 1).getEventName();
        }

        return eventName;

    }

    /**
     * get the time used in the complete battery change
     *
     * @return totalTimeUsed (int)
     */
    public int getTotalTimeUsed() {
        return totalTimeUsed;
    }

    /**
     * get the time used in the cycle
     *
     * @return cycleTimeUsed (int)
     */
    public int getCycleTimeUsed() {
        return cycleTimeUsed;
    }

    /**
     * get the time left of the cyclus
     *
     * @return cyclustimeLeft (int)
     */
    public int getCycleTimeRemaining() {
        return cyclustimeLeft;
    }

    /**
     * get the time left of the total battery change
     *
     * @return totalTimeLeft (int)
     */
    public int getTotalTimeRemaining() {
        return totalTimeLeft;
    }

    /**
     * calculates the time left of the battery change
     *
     * @throws InterruptedException
     */
    public void calculateTotalTimeLeft() throws InterruptedException {
        //System.out.println("Total time left : " + totalTimeLeft);
        if (totalTimeLeft > 0) {
            totalTimeLeft = totalTimeLeft - 1;

        } else {
            // caseScenario = caseScenario + 1;
            totalTimeLeft = 0;
            // switchCases(caseScenario);

        }
        ;
    }

    /**
     * calculates the time left of the cycle to be completed
     */
    public void calculateCyclusTimeLeft() {
        //System.out.println("Cyclus left : " + cyclustimeLeft);
        if (cyclustimeLeft > 0) {
            cyclustimeLeft = cyclustimeLeft - 1;
        } else {
            //CyclustimeLeft = getLastEventTimeSyclus();
            cyclustimeLeft = 0;
        }

    }

    /**
     * calculates the percentage completed for the total batteryChange. updates
     * the totalPercentageCompleted int. does the calculations as a float to get
     * descimals, and casts it back to int
     */
    public void calculateTotalPercentageCompleted() {
        float calculatedPercentage = ((float) totalTime - (float) totalTimeLeft) / (float) totalTime * 100;
        totalPercentageCompleted = (int) calculatedPercentage;
    }

    /**
     * calculates the percentage completed for each cycle. updates the
     * cyclusPercentageCompleted int. does the calculations as a float to get
     * descimals, and casts it back to int
     */
    public void calculateCyclusPercentageCompleted() {
        float calculatedPercentage = (float) cycleTimeUsed / (float) cycleTime * 100;
        cyclusPercentageCompleted = (int) calculatedPercentage;

    }

    /**
     * returns the percentage of the cyclus completed
     *
     * @return an integer cyclusPercentageCompleted for amount completed
     */
    public int getCyclusPercentageCompleted() {
        return cyclusPercentageCompleted;
    }

    /**
     * returns the percentage of the total change completed
     *
     * @return an integer totalPercentageCompleted for amount completed
     */
    public int getTotalPercentageCompleted() {
        return totalPercentageCompleted;
    }

    ///////////////////////////////////////////////////////////////////////
    ////////////////////Needed to add the events///////////////////////////
    public void addEventToList(String command) {
        String time = "";
        String fault = "";
        if (command == "") {
            fault = "";
        } else {
            fault = "Command: " + command;
        }

        eventList.add(fault);
    }

    /**
     * getting the timestamp of the
     *
     * @return the timestamp in format HH:mm:ss
     */
    public String getTimeStamp() {
        String time = "";
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            time = " Time: " + sdf.format(date);
        } catch (NullPointerException e) {
        }
        return time;

    }

    /**
     * setting up the different events. Adding an event with name
     */
    public void addingEventStates() {
        Event event1 = new Event(1, "Calibrating arm");
        Event event2 = new Event(2, "Calibrating lift");
        Event event3 = new Event(3, "Calibrating slider");
        Event event4 = new Event(4, "Calibration Complete");
        Event event5 = new Event(5, "idloe pos, Awaits signal from drone");
        Event event6 = new Event(6, "Retreiving battery from chargingstation");
        Event event7 = new Event(7, "Battery retreived, going to idle pos");
        Event event8 = new Event(8, "await signal from drone");
        Event event9 = new Event(9, "Signal received, running lift up for batterychange");
        Event event10 = new Event(10, "Starting drone detection");
        Event event11 = new Event(11, "");
        Event event12 = new Event(12, "Locate new battery for change");

        differentEventStates.add(event1);
        differentEventStates.add(event2);
        differentEventStates.add(event3);
        differentEventStates.add(event4);
        differentEventStates.add(event5);
        differentEventStates.add(event6);
        differentEventStates.add(event7);
        differentEventStates.add(event8);
        differentEventStates.add(event9);
        differentEventStates.add(event10);
        differentEventStates.add(event11);
        differentEventStates.add(event12);

    }

}

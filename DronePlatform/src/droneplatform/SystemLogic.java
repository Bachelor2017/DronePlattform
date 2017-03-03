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
public class SystemLogic implements Runnable {

    ///FRA eventStates
    private ArrayList<Event> differentEventStates;
    private ArrayList<Event> events;
    private ArrayList<String> eventList;

    //time
    int totalTime = 100;
    int totalTimeUsed = 0;
    int totalTimeLeft = 100;
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
        Event event12 = new Event(8, "Awaits drone");
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
                semaphore.release();
if(platformMode)
{
                if (caseScenario != dataFromTeensy[1]) {
                    caseScenario = dataFromTeensy[1];
                    switchCases(caseScenario);
                } else {
                    runState = false;

                }
            } 
else
{
    switchCases(0);
}
            }catch (InterruptedException ex) {
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
                // if (runState) {
                totalTimeLeft = totalTime;
                cyclustimeLeft = 0;
                totalTimeUsed = 0;
                cycleTimeUsed = 0;
                // }

                break;
            case (1):
                //(runState) {
                case1(13, 0);            //setting the event time to 13, and time used to 0
                // }
                break;
            case (2):
                // if (runState) {
                case2(22, 90);           //setting the event time to 13, and time used to 0
                // }
                break;
            case (3):
                case3(5, 80);            //setting the event time to 13, and time used to 0

                break;
            case (4):
                case4(10, 70);           //setting the event time to 13, and time used to 0

                break;
            case (5):
                case5(6, 60);            //setting the event time to 13, and time used to 0

                break;
            case (6):
                case6(15, 30);           //setting the event time to 13, and time used to 0

                break;
        }
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
        totalTimeLeft = totalTime;
        cycleTimeUsed = 1;
        totalTimeUsed = totTimeLeft;
        cycleTime = cTime;
        cyclustimeLeft = cycleTime;
        //  System.out.println("test case 1");
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
        //   System.out.println("test case 2");
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
        //   System.out.println("test case 3");
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
        //  System.out.println("test case 4");
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
                    if (caseScenario != 0) {
                        calculateCyclusTimeLeft();
                        calculateTotalTimeLeft();
                        calculateTotalPercentageCompleted();
                        calculateCyclusPercentageCompleted();

                        totalTimeUsed++;
                        cycleTimeUsed++;

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
        return events.get(eventListSize - 1).getEventName();

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
    * setting up the different events. 
    * Adding an event with name
    */

    public void addingEventStates() {

        Event event1 = new Event(1, "Signal from drone received");
        Event event2 = new Event(2, "Conveyorbelt started");
        Event event3 = new Event(3, "Lift going up");
        Event event4 = new Event(4, "Drone location search");
        Event event5 = new Event(5, "Location found, relocating drone");
        Event event6 = new Event(6, "Detaching battery");
        Event event7 = new Event(7, "retreiving battery to docking");
        Event event8 = new Event(8, "Changing battery");
        Event event9 = new Event(9, "Battery change successful");
        Event event10 = new Event(10, "Drone battery change successful");
        Event event11 = new Event(11, "Placing old battery to dockingstation");
        Event event12 = new Event(12, "idle");
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
    }

}

//////////////////////////////////////////////////////////////////////////////
///////////ting som er kommentert bort for å se om de ikke er i bruk

 /**
     * fills a blank list of event string
     */

/*public void fillList() {
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");
        addEventToList("");

    }*/





/* public void settEventState(int x) {
        events.add(differentEventStates.get(x));
    }*/
/**
 *
 */
/*
    public void testEvent() {

        TimerTask tTask = new TimerTask() {
            public void run() {
                if (y < differentEventStates.size()) {
                    addEvent();
                    events.add(differentEventStates.get(y));
                    y++;
                } else {
                    y = 0;
                }
            }
        };

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 4000);
    }
 */
/**
 * gets the last event added to the list
 *
 * @return the integer of the possition of the last event
 */
/* public int getLastEventTimeSyclus() {
        int eventListSize = events.size();
        return events.get(eventListSize - 1).getTimeSyclusOfEvent();
    }*/
/**
 * adding the event to list
 *
 * @param event
 */
/*   public void addEventToList(Event event) {
        events.add(event);
    }*/
 /*  public void addEvent() {
        addEventToList("Event " + totalTimeUsed);
        totalTimeUsed++;
    }
 */
 /*  public ArrayList<Event> returnEventList() {
        return events;
    }
 */
 /*  public ArrayList getEventList() {

        ArrayList<String> tmpEventList = new ArrayList<>();
        for (int x = 0; x < events.size(); x++) {
            tmpEventList.add(events.get(x).getEventName() + "   : " + getTimeStamp());
        }
        return tmpEventList;
    }*/

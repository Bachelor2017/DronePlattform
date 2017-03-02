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
    int totalTimeUsed = 0;
    int y = 0;
    int eventValue = 0;
    int timeLeft = 100;
    int timeLeftCyclus = 0;
    TimerTask tTask;
    java.util.Timer timer;
    boolean runState = true;
    ///////////

    private int xValue;
    private int zValue;
    private int yValue;
    private Thread t;
    private DataHandler dataHandler;
    private byte[] dataFromTeensy;
    private int caseScenario;
    private Semaphore semaphore;
    private EventStates eventStates;
    boolean value = false;

    public SystemLogic(DataHandler dh, Semaphore semaphore) {
        this.dataHandler = dh;
        this.semaphore = semaphore;
        this.eventStates = eventStates;
        caseScenario = 500;

        //FRA EVENTSTATES
        eventList = new ArrayList<>();
        fillList();
      //  testEvent();

        events = new ArrayList<>();
        differentEventStates = new ArrayList<>();
        addingEventStates();
        Event event12 = new Event(8, "idle", 1);
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
                dataFromTeensy = dataHandler.getDataFromTeensy();
                semaphore.release();

                if (caseScenario != dataFromTeensy[1]) {
                    caseScenario = dataFromTeensy[1];
                    switchCases(caseScenario);
                    //t.sleep(2000);
                } else {
                    runState = false;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemLogic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * incrementing the X value with the timer incrementation. used as test for
     * the progressbar in GUI
     */
    public void runTimer() {

        tTask = new TimerTask() {
            public void run() {

                try {
                    calculateTimeLeft();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SystemLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
                totalTimeUsed++;
            }

        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 1000);
    }

    protected void switchCases(int caseNumber) throws InterruptedException {
        int number = caseNumber;
        switch (number) {
            case (0):
                if (runState) {

                    case1();
                }

                break;
            case (1):
                //if (runState) {

                    case2();
               // }
                break;
            case (2):
               // if (runState) {

                    case3();
              // }
                break;
            case (3):
                case4();

                break;
        }
    }

     public void case1() throws InterruptedException {
        timeLeft = 0;
        System.out.println("test case 1");
        System.out.println(differentEventStates.get(0).getEventName());
        events.add(differentEventStates.get(0));

    }
    
    
    
    
    public void case2() throws InterruptedException {
        timeLeft = 5;
        totalTimeUsed=0;
        System.out.println("test case 2");
        System.out.println(differentEventStates.get(1).getEventName());
        events.add(differentEventStates.get(1));

    }

    public void case3() throws InterruptedException {
        timeLeft = 3;
        System.out.println("test case 3");
        System.out.println(differentEventStates.get(2).getEventName());
        events.add(differentEventStates.get(2));

    }

    public void case4() throws InterruptedException {
        timeLeft = 10;
        System.out.println("test case 4");
        System.out.println(differentEventStates.get(3).getEventName());
        events.add(differentEventStates.get(3));

    }

    public void case5() throws InterruptedException {
        System.out.println("test case 3");
        System.out.println(differentEventStates.get(4).getEventName());
        events.add(differentEventStates.get(4));
    }

//////////////////ALT UNDER HENTET FRA EVENTSTATES
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
     * adding the event to list
     *
     * @param event
     */
    public void addEventToList(Event event) {
        events.add(event);
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

    public void addEvent() {
        addEventToList("Event " + totalTimeUsed);
        totalTimeUsed++;
    }

    /**
     *
     */
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

    public ArrayList<Event> returnEventList() {
        return events;
    }

    public ArrayList getEventList() {

        ArrayList<String> tmpEventList = new ArrayList<>();
        for (int x = 0; x < events.size(); x++) {
            tmpEventList.add(events.get(x).getEventName() + "   : " + getTimeStamp());
        }
        return tmpEventList;
    }

    public String getLastEventState() {
        int eventListSize = events.size();
        return events.get(eventListSize - 1).getEventName();

    }

    public int getTotalTimeUsed() {
        return totalTimeUsed;
    }

    /**
     * Setting the docking status of the station If battery is docked, the timer
     * starts running. of the battery is out of docking, the timer pstops and
     * resets
     *
     * @param value boolean value , true if battery is in docking, and false if
     * station is empty
     */
    public int timeLeftOfBatteryChange() {
        return timeLeft;
    }

    public void calculateTimeLeft() throws InterruptedException {
        System.out.println("time left : " + timeLeft);
        if (timeLeft > 0) {
            timeLeft = timeLeft - 1;

        } else {
            caseScenario = caseScenario + 1;
            timeLeft = 0;
            switchCases(2);

        }
        ;
    }

    public int calculateTimeLeftSyclus() {

        if (timeLeftCyclus > 0) {
            timeLeftCyclus = timeLeftCyclus - 1;
        } else {
            timeLeftCyclus = getLastEventTimeSyclus();
        }
        return timeLeftCyclus;
    }

    public void settEventState(int x) {
        events.add(differentEventStates.get(x));
    }

    public int getLastEventTimeSyclus() {
        int eventListSize = events.size();
        return events.get(eventListSize - 1).getTimeSyclusOfEvent();
    }

    public void fillList() {
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

    }

    public void addingEventStates() {
       
        Event event1 = new Event(1, "waiting for drone", 2);
        Event event2 = new Event(2, "Signal from drone received", 2);
        Event event3 = new Event(3, "Conveyorbelt started", 1);
        Event event4 = new Event(4, "Lift going up", 5);
        Event event5 = new Event(5, "Conveyorbelt stopped", 6);
        Event event6 = new Event(6, "Drone location search", 2);
        Event event7 = new Event(7, "Location found, relocating drone", 1);
        Event event8 = new Event(8, "Changing battery", 4);
        Event event9 = new Event(9, "Battery change successful", 1);
        Event event10 = new Event(10, "Drone battery change successful", 1);
        Event event11 = new Event(11, "Placing old battery to dockingstation", 5);
         Event event12 = new Event(12, "idle", 2);
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

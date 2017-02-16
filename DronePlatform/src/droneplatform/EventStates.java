/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author Jørgen
 */
public class EventStates {

    private ArrayList<Event> differentEventStates;
    private ArrayList<Event> events;
    private ArrayList<String> eventList;
    int x = 0;
    int y = 0;
    int eventValue = 0;
    int timeLeft = 100;
    int timeLeftCyclus = 0;
    TimerTask tTask;
    java.util.Timer timer;

    public EventStates() {
        eventList = new ArrayList<>();
        fillList();
        testEvent();

        events = new ArrayList<>();
        differentEventStates = new ArrayList<>();
        addingEventStates();
        Event event12 = new Event(8, "idle", 1);
        events.add(event12);
        runTimer();

    }

    public void addEventToList(String command) {
        String time = "";
        String fault = "";
        if (command == "") {
            fault = "";
        } else {
            fault = "Command: " + command + getTimeStamp();
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

    /**
     *
     * @return
     */
    public String[] guiEventList() {
        String[] eventListGUI = new String[10];

        for (int i = 0; i < 10; i++) {
            eventListGUI[i] = eventList.get(eventList.size() - 1 - i);
        }
        return eventListGUI;
    }

    public void addEvent() {
        addEventToList("Event " + x);
        x++;
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

    public int testGetXValue() {
        return x;
    }

    /**
     * incrementing the X value with the timer incrementation. used as test for
     * the progressbar in GUI
     */
    public void runTimer() {

        tTask = new TimerTask() {
            public void run() {
                calculateTimeLeft();

                calculateTimeLeftSyclus();
                x++;
            }

        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tTask, 1000, 500);
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
        Event event1 = new Event(1, "Signal om drone plassert motatt", 2);
        Event event2 = new Event(2, "Rullebånd startet", 1);
        Event event3 = new Event(3, "Lift på vei opp", 5);
        Event event4 = new Event(4, "RulleBånd stoppet,drone på ende", 6);
        Event event5 = new Event(5, "Robot søker drone", 2);
        Event event6 = new Event(6, "droneplassering mottat, rettet opp drone", 1);
        Event event7 = new Event(7, "Skifter batter", 4);
        Event event8 = new Event(8, "Batteri av", 1);
        Event event9 = new Event(8, "Nytt batteri plassert", 8);
        Event event10 = new Event(8, "Drone batteriskifter utført", 1);
        Event event11 = new Event(8, "Plasserer batteri til dokking", 5);
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

    public void calculateTimeLeft() {
        if (timeLeft > 0) {
            timeLeft = timeLeft - 1;
        } else {
            timer.cancel();
            timer.purge();
            timeLeft = 0;

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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package droneplatform;

import java.util.ArrayList;

/**
 *
 * @author Jørgen
 */
public class EventStates {
    private ArrayList<Event> events;
    
    public EventStates()
    {
        events = new ArrayList<>();
        this.createEvents();
    }
    
    
    
    public void createEvents()
    {
        Event event1 = new Event(1, "StartPossition",0,0,0);
        Event event2 = new Event(2, "Drone at position",0,0,0);
        Event event3 = new Event(3, "Move lift",0,0,0);
        Event event4 = new Event(4, "event 1",0,0,0);
        Event event5 = new Event(5, "event 1",0,0,0);
        
    }
    
}

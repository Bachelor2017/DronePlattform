package droneplatform;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * 
 */
public class BatteryStationLogic {

    private ArrayList<BatteryStation> batteries;
    private BatteryStation battery;

    public BatteryStationLogic() {
        batteries = new ArrayList<>();
        fillList();
    }

    private void fillList() {
        for (int i = 0; i < 16; i++) {
            battery = new BatteryStation();
            batteries.add(battery);
        }
    }

    public ArrayList getArrayListBatteries() {
        return batteries;
    }

    public int getBatteriesLevel(int x) {
        return batteries.get(x).getBatteryLevel();
    }

    public void setBatteriesLevel(int x, int value) {
        batteries.get(x).setBatteryLevel(value);
    }

    public int getBatteriesStatus(int x) {
        return batteries.get(x).getBatteryStatus();
    }

    public void setBatteriesStatus(int x, int value) {
        batteries.get(x).setBatteryStatus(value);
    }

    public void setBatteriesDocking(int x, boolean value) {
        batteries.get(x).setDocked(value);
    }

    public boolean isBatteriesDocked(int x) {
        return batteries.get(x).isDocked();
    }

    public void test() {
        batteries.get(2).setBatteryLevel(20);
        batteries.get(4).setBatteryLevel(40);
        batteries.get(6).setBatteryLevel(60);
        batteries.get(8).setBatteryLevel(80);
        batteries.get(10).setBatteryLevel(100);
        for (int i = 0; i < 16; i++) {
            System.out.println("Bat Nr: " + i + " " + batteries.get(i).getBatteryLevel());
        }
    }
}

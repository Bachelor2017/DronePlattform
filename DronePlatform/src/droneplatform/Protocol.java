
package droneplatform;

/**
 * Defines the protocols used in the project,
 * Batteristatus, Commands,Robotdiretions and errormessages
 * @author Jørgen
 */
public enum Protocol {
    
    BATTERYSTATUS(0),
    COMMANDS(1),
    ROBOTDIRECTION(2),
    ERRORMESSAGES(3);
 
    private int value;
       
    private Protocol(int value) {
        this.value = value;
    }
        
    public int getValue() {
        return this.value;
    }
        
    public enum batteriStatus {
        OK(0),
        ERROR(1);
        
        
        private int value;
        
        private batteriStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
    }
    
    public enum robotDirections {
        XVALUE(0),
        YVALUE(1),
        ZVALUE(2);
        
        private int value;
        
        private robotDirections(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
    }
    
       public enum errorMessages {
        BLABLA(0),
        BLABLA2(1),
        BLABLA1(2);
        
        private int value;
        
        private errorMessages(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return this.value;
        }
    }
}

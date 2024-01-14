import java.time.LocalTime;

public class BankCustomer {
    int unit; 
    int processingTime; 
    LocalTime arrival;

    // Constructor: initializes customer's arrival time, processing time, and unit
    public BankCustomer(String arrival, int processingTime, int unit) {
        this.arrival = LocalTime.parse(arrival);
        this.processingTime = processingTime;
        this.unit = unit;
    }

    // Returns customer's arrival time
    public LocalTime getArrival() {
        return arrival;
    }
    
    // Sets customer's arrival time
    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }
    
    // Returns customer's processing time
    public int getProcessingTime() {
        return processingTime;
    }
    
    // Sets customer's processing time
    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }
    
    // Returns customer's unit
    public int getUnit() {
        return unit;
    }
    
    // Sets customer's unit
    public void setUnit(int unit) {
        this.unit = unit;
    }
}

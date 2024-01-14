import java.time.LocalTime;

public class BankClerk {
    LocalTime availableTime; // Clerk's availability time

    // Constructor: initializes clerk's available time from a string
    public BankClerk(String availableTime) {
        this.availableTime = LocalTime.parse(availableTime);
    }

    // Returns clerk's available time
    public LocalTime getAvailableTime() {
        return availableTime;
    }

    // Sets clerk's available time
    public void setAvailableTime(LocalTime availableTime) {
        this.availableTime = availableTime;
    }
}

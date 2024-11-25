import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {
    private final int id;
    private final String title;
    private final String description;
    private final LocalDate date;
    private final LocalTime time;
    private int eventIDCounter = 1;

    public Event(String title, String description, LocalDate date, LocalTime time) {
        this.id = eventIDCounter++;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return date.atTime(time);
    }

}

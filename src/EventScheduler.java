import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventScheduler {
    private final Scanner scan = new Scanner(System.in);
    private List<Event> events = new ArrayList<>();


    public void addEvent() {
        System.out.println("Add title event!");
        String title = scan.nextLine();

        System.out.println("Add description!");
        String description = scan.nextLine();

        System.out.println("Add date!(in yyyy-MM-dd format)");
        String dateInput = scan.nextLine();
        LocalDate dateEvent = LocalDate.parse(dateInput);

        System.out.println("Add time!(in HH:mm format)");
        String timeInput = scan.nextLine();
        LocalTime timeEvent = LocalTime.parse(timeInput);

        events.add(new Event(title, description, dateEvent, timeEvent));
    }

    public void getAllEvent() {
        for (Event event : events) {
            System.out.println(event);
        }
    }

    private boolean checkList() {
        return events != null && !events.isEmpty();
    }

    public void removeEvent() {
        System.out.println("Write the title of the event you want to delete.");
        String title = scan.nextLine();
        Event deletedEvent = null;
        if (!events.isEmpty()) {
            for (Event event : events) {
                if (event.getTitle().equalsIgnoreCase(title)) {
                    System.out.println("Do you want to delete? Yes/No");
                    String choice = scan.nextLine();
                    if (choice.equalsIgnoreCase("yes")) {
                        deletedEvent = event;
                        System.out.println("Event successfully deleted!");
                    }
                }
            }
            events.remove(deletedEvent);
        }
    }

    public void findEventsByDate() {
        System.out.println("Enter a specific date (in yyyy-MM-dd format):");
        String dateInput = scan.nextLine();
        LocalDate dateEvent = LocalDate.parse(dateInput);

        if (checkList()) {
            for (Event event : events) {
                LocalDateTime eventDateTime = event.getDateTime();
                if (eventDateTime.toLocalDate().equals(dateEvent)) {
                    System.out.println("ID -> " + event.getId());
                    System.out.println("Title -> " + event.getTitle());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                    String formattedTime = eventDateTime.toLocalTime().format(formatter);
                    System.out.println("Time -> " + formattedTime);
                }
            }
        }
    }

    public void listUpcomingEvents() {
        if (checkList()) {
            Collections.sort(events, Comparator.comparing(Event::getDateTime));

            LocalDateTime now = LocalDateTime.now();

            for (Event event : events) {
                LocalDateTime eventDateTime = event.getDateTime();
                System.out.println("ID -> " + event.getId());
                System.out.println("Title -> " + event.getTitle());
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a");
                String formattedTime = eventDateTime.format(dateTimeFormatter);
                System.out.println("When -> " + formattedTime);
                if (eventDateTime.isAfter(now)) {
                    Duration duration = Duration.between(now, eventDateTime);
                    long day = duration.toDays();
                    long hours = duration.toHours();
                    long minutes = duration.toMinutes() % 60;
                    System.out.println("Time remaining -> " + day + " day, " + hours + "hours and " + minutes + " minutes.");
                } else {
                    System.out.println("This event has passed.");
                }
            }
        }
    }

    public void mainMethod() {
        try {
            while (true) {
                System.out.println("1. Add Event");
                System.out.println("2. List Upcoming Events");
                System.out.println("3. Remove Event");
                System.out.println("4. Find Events by Date");
                System.out.println("5. Exit");
                int choice = scan.nextInt();
                scan.nextLine();
                switch (choice) {
                    case 1:
                        addEvent();
                        break;
                    case 2:
                        listUpcomingEvents();
                        break;
                    case 3:
                        removeEvent();
                        break;
                    case 4:
                        findEventsByDate();
                        break;
                    case 5:
                        System.out.println("Exiting the program...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please choose a valid option.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid integer.");
            scan.nextLine();
        }
    }
}
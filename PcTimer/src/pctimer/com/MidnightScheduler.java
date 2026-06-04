package pctimer.com;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MidnightScheduler {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Calculate time remaining until next midnight
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime nextMidnight = now.toLocalDate().plusDays(1).atStartOfDay();
        long initialDelay = Duration.between(now, nextMidnight).toMillis();

        // Run every 24 hours (86,400,000 milliseconds)
        long period = TimeUnit.DAYS.toMillis(1); 

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Midnight task executing!");
            // Insert your execution code here
        }, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}

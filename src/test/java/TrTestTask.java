import me.tr.trtasks.scheduler.TrStaticTaskScheduler;
import me.tr.trtasks.tasks.timer.TrTimerTask;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.time.TrTimeUnit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TrTestTask {


    public static void main(String[] args) {
        TrTimerTask task = new TrTimerTask();

        task.getOptions().setUnit(TrTimeUnit.NANOSECONDS);

        List<String> lines = task.execute(
                () -> {
                    try {
                        BufferedReader fr = new BufferedReader(new FileReader("C:\\Programming\\Java\\Libraries\\TrTasks\\build.gradle"));
                        List<String> internalLines = fr.lines().toList();
                        internalLines.forEach(System.out::println);
                        return internalLines;
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        TrStaticTaskScheduler.syncTimer(() -> System.out.println("Hello world!"), new TrTime(5, TrTimeUnit.SECONDS));
    }

}

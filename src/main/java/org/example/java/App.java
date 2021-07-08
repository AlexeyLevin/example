package org.example.java;

import org.example.java.feature.Execute;
import org.example.java.feature.Result;
import org.example.java.feature.TaskExecutor;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");
        final TaskExecutor taskExecutor = new TaskExecutor();
        List<Execute> executeList = new ArrayList<>();
        executeList.add(new Execute(1L, 3000, true));
        executeList.add(new Execute(2L, 10000, true));
        executeList.add(new Execute(3L, 3000, true));
        executeList.add(new Execute(4L, 5000, true));
        executeList.add(new Execute(5L, 12000, true));
        executeList.add(new Execute(6L, 2000, true));
        executeList.add(new Execute(7L, 6000, true));
        executeList.add(new Execute(8L, 22000, true));
        executeList.add(new Execute(9L, 1000, false));
        executeList.add(new Execute(10L, 1000, true));

        final List<Result> results = taskExecutor.execute(executeList, true);
        System.out.println(results);

        Thread.sleep(2000L);
        System.out.println("END");
    }
}

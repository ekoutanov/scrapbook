package scrapbook.parallelwordcounter;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public final class ParallelWordCounter {
  private static final class WordCountTask implements Callable<Map<String, Integer>> {
    private final File file;
    
    public WordCountTask(File file) {
      this.file = file;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
      final var contents = new String(Files.readAllBytes(Path.of(file.toURI())));
      return Arrays.stream(contents.toString().split("\\s+"))
          .map(String::toLowerCase)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(__ -> 1)));
    }
  }
  
  public static Map<String, Integer> countAll(File dir, int numThreads) throws InterruptedException, ExecutionException {
    final var executor = Executors.newFixedThreadPool(numThreads);
    try {
      final var futures = Arrays.stream(dir.listFiles())
          .filter(file -> file.getName().endsWith(".txt"))
          .map(WordCountTask::new)
          .map(executor::submit)
          .collect(Collectors.toList());
      
      final var aggregate = new TreeMap<String, Integer>();
      for (var future : futures) {
        final var counts = future.get();
        for (var count : counts.entrySet()) {
          aggregate.compute(count.getKey(), (__, existingValue) -> {
            return existingValue != null ? existingValue + count.getValue() : count.getValue();
          });
        }
      }
      return aggregate;
    } finally {
      executor.shutdown();
    }
  }
  
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    if (args.length != 1) {
      System.err.format("Usage: %s <path>\n", ParallelWordCounter.class.getSimpleName());
      System.exit(1);
      return;
    }
    
    final var dir = new File(args[0]);
    final var numThreads = Runtime.getRuntime().availableProcessors();
    for (var count : countAll(dir, numThreads).entrySet()) {
      System.out.format("%10s: %2d\n", count.getKey(), count.getValue());
    }
  }
}

package scrapbook.parallelwordcounter;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public final class ParallelWordCounter {
  private static final class WordCountTask implements Callable<Map<String, Integer>> {
    private final File file;
    
    WordCountTask(File file) {
      this.file = file;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
      final var counts = new HashMap<String, Integer>();
      try (var reader = new BufferedReader(new FileReader(file))) {
        String line = null;
        while ((line = reader.readLine()) != null) {
          final var words = line.toLowerCase().split("\\s+");
          for (var word : words) {
            counts.compute(word, (__, existingCount) -> {
              return existingCount != null ? existingCount + 1 : 1;
            });
          }
        }
      }
      return counts;
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
      
      final var collected = new ArrayList<Map<String, Integer>>(futures.size());
      for (var future : futures) {
        collected.add(future.get());
      }
      
      return collected.stream()
          .flatMap(map -> map.entrySet().stream())
          .collect(Collectors.groupingBy(wordAndCount -> wordAndCount.getKey(), 
                                         TreeMap::new, 
                                         Collectors.summingInt(wordAndCount -> wordAndCount.getValue())));
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

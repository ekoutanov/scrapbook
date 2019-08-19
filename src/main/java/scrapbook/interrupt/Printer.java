package scrapbook.interrupt;

public class Printer extends Thread {
  private volatile boolean running = true;
  
  public Printer() {
    super("Printer");
    start();
  }
  
  @Override
  public void run() {
    while (running) {
      try {
        doSomethingInBackground();
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }
    }
  }
  
  private void doSomethingInBackground() throws InterruptedException {
    System.out.println("Hi there");
    
    Thread.sleep(5000);
  }
  
  public void terminate() {
    running = false;
    this.interrupt();
  }
}

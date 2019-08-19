package scrapbook.interrupt;

public class RunPrinter {
  public static void main(String[] args) throws InterruptedException {
    final var printer = new Printer();
    Thread.sleep(2000);
    System.out.println("Terminating");
    printer.terminate();
  }
}

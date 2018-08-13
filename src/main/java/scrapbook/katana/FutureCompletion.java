package scrapbook.katana;

import java.util.function.*;

public final class FutureCompletion<T> implements BiConsumer<T, Throwable> {
  T value;
  Throwable error;
  
  @Override
  public void accept(T value, Throwable error) {
    this.value = value;
    this.error = error;
  }
}
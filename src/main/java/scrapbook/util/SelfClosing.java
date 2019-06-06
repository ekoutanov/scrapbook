package scrapbook.util;

import java.util.function.*;

public final class SelfClosing<R> implements AutoCloseable {
  private final R resource;
  
  private final Consumer<? super R> closer;
  
  private SelfClosing(R resource, Consumer<? super R> closer) {
    this.resource = resource;
    this.closer = closer;
  }
  
  public R get() {
    return resource;
  }
  
  public static <R> SelfClosing<R> of(R resource, Consumer<? super R> closer) {
    return new SelfClosing<>(resource, closer);
  }

  @Override
  public void close() {
    closer.accept(resource);
  }
}

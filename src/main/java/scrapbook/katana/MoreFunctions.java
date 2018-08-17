package scrapbook.katana;

import com.obsidiandynamics.func.Exceptions.*;

public final class MoreFunctions {
  public static <X extends Throwable> ExceptionWrapper<X> reWrap(Class<? extends Throwable> containerExceptionType, 
                                                                 ExceptionWrapper<X> wrapper) {
    return e -> wrapper.apply(unwrapException(containerExceptionType, e));
  }
  
  /**
   *  Copy of {@link MoreFunctions#unwrapException(Class, Throwable)}.
   *  
   *  @param containerExceptionType
   *  @param throwable
   *  @return
   */
  private static Throwable unwrapException(Class<? extends Throwable> containerExceptionType, Throwable throwable) {
    if (containerExceptionType.isInstance(throwable)) {
      return throwable.getCause();
    } else {
      return throwable;
    }
  }
}

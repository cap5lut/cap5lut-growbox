package net.cap5lut.growbox;

import java.util.concurrent.CompletionException;
import java.util.function.Function;

public final class ExceptionHandler {
    public static <T, E extends Throwable> Function<Throwable, T> handle(
            Class<E> exceptionType, Function<E, T> handler,
            Function<Throwable, T> defaultHandler) {
        return ex -> {
            if (exceptionType.isInstance(ex)) {
                return handler.apply(exceptionType.cast(ex));
            } else if (ex instanceof CompletionException && exceptionType.isInstance(ex.getCause())) {
                return handler.apply(exceptionType.cast(ex.getCause()));
            }

            return defaultHandler.apply(ex);
        };
    }

    public static <T, E0 extends Throwable, E1 extends Throwable> Function<Throwable, T> handle(
            Class<E0> exceptionType0, Function<E0, T> handler0,
            Class<E1> exceptionType1, Function<E1, T> handler1,
            Function<Throwable, T> defaultHandler) {
        return ex -> {
            if (exceptionType0.isInstance(ex)) {
                return handler0.apply(exceptionType0.cast(ex));
            } else if (ex instanceof CompletionException && exceptionType0.isInstance(ex.getCause())) {
                return handler0.apply(exceptionType0.cast(ex.getCause()));
            } if (exceptionType1.isInstance(ex)) {
                return handler1.apply(exceptionType1.cast(ex));
            } else if (ex instanceof CompletionException && exceptionType1.isInstance(ex.getCause())) {
                return handler1.apply(exceptionType1.cast(ex.getCause()));
            }

            return defaultHandler.apply(ex);
        };
    }

    private ExceptionHandler() {
        throw new UnsupportedOperationException();
    }
}

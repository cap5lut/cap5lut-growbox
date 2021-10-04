package net.cap5lut.growbox;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureEx<T> extends CompletableFuture<T> {
    @SuppressWarnings("unchecked")
    @Override
    public <U> CompletableFutureEx<U> thenApply(Function<? super T, ? extends U> fn) {
        return (CompletableFutureEx<U>) super.thenApply(fn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> CompletableFutureEx<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return (CompletableFutureEx<U>) super.thenApplyAsync(fn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> CompletableFutureEx<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return (CompletableFutureEx<U>) super.thenApplyAsync(fn, executor);
    }

    @Override
    public CompletableFutureEx<Void> thenAccept(Consumer<? super T> action) {
        return (CompletableFutureEx<Void>) super.thenAccept(action);
    }

    @Override
    public CompletableFutureEx<Void> thenAcceptAsync(Consumer<? super T> action) {
        return (CompletableFutureEx<Void>) super.thenAcceptAsync(action);
    }

    @Override
    public CompletableFutureEx<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return (CompletableFutureEx<Void>) super.thenAcceptAsync(action, executor);
    }

    @Override
    public CompletableFutureEx<Void> thenRun(Runnable action) {
        return (CompletableFutureEx<Void>) super.thenRun(action);
    }

    @Override
    public CompletableFutureEx<Void> thenRunAsync(Runnable action) {
        return (CompletableFutureEx<Void>) super.thenRunAsync(action);
    }

    @Override
    public CompletableFutureEx<Void> thenRunAsync(Runnable action, Executor executor) {
        return (CompletableFutureEx<Void>) super.thenRunAsync(action, executor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U, V> CompletableFutureEx<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return (CompletableFutureEx<V>) super.thenCombine(other, fn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U, V> CompletableFutureEx<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return (CompletableFutureEx<V>) super.thenCombineAsync(other, fn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U, V> CompletableFutureEx<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return (CompletableFutureEx<V>) super.thenCombineAsync(other, fn, executor);
    }

    @Override
    public <U> CompletableFutureEx<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return (CompletableFutureEx<Void>) super.thenAcceptBoth(other, action);
    }

    @Override
    public <U> CompletableFutureEx<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return (CompletableFutureEx<Void>) super.thenAcceptBothAsync(other, action);
    }

    @Override
    public <U> CompletableFutureEx<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return (CompletableFutureEx<Void>) super.thenAcceptBothAsync(other, action, executor);
    }

    @Override
    public CompletableFutureEx<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return (CompletableFutureEx<Void>) super.runAfterBoth(other, action);
    }

    @Override
    public CompletableFutureEx<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return (CompletableFutureEx<Void>) super.runAfterBothAsync(other, action);
    }

    @Override
    public CompletableFutureEx<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return (CompletableFutureEx<Void>) super.runAfterBothAsync(other, action, executor);
    }

    @Override
    public <U> CompletableFutureEx<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return (CompletableFutureEx<U>) super.applyToEither(other, fn);
    }

    @Override
    public <U> CompletableFutureEx<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return (CompletableFutureEx<U>) super.applyToEitherAsync(other, fn);
    }

    @Override
    public <U> CompletableFutureEx<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return (CompletableFutureEx<U>) super.applyToEitherAsync(other, fn, executor);
    }

    @Override
    public CompletableFutureEx<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return (CompletableFutureEx<Void>) super.acceptEither(other, action);
    }

    @Override
    public CompletableFutureEx<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return (CompletableFutureEx<Void>) super.acceptEitherAsync(other, action);
    }

    @Override
    public CompletableFutureEx<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return (CompletableFutureEx<Void>) super.acceptEitherAsync(other, action, executor);
    }

    @Override
    public CompletableFutureEx<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return (CompletableFutureEx<Void>) super.runAfterEither(other, action);
    }

    @Override
    public CompletableFutureEx<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return (CompletableFutureEx<Void>) super.runAfterEitherAsync(other, action);
    }

    @Override
    public CompletableFutureEx<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return (CompletableFutureEx<Void>) super.runAfterEitherAsync(other, action, executor);
    }

    @Override
    public <U> CompletableFutureEx<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return (CompletableFutureEx<U>) super.thenCompose(fn);
    }

    @Override
    public <U> CompletableFutureEx<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return (CompletableFutureEx<U>) super.thenComposeAsync(fn);
    }

    @Override
    public <U> CompletableFutureEx<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return (CompletableFutureEx<U>) super.thenComposeAsync(fn, executor);
    }

    @Override
    public CompletableFutureEx<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return (CompletableFutureEx<T>) super.whenComplete(action);
    }

    @Override
    public CompletableFutureEx<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return (CompletableFutureEx<T>) super.whenCompleteAsync(action);
    }

    @Override
    public CompletableFutureEx<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return (CompletableFutureEx<T>) super.whenCompleteAsync(action, executor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> CompletableFutureEx<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return (CompletableFutureEx<U>) super.handle(fn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> CompletableFutureEx<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return (CompletableFutureEx<U>) super.handleAsync(fn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> CompletableFutureEx<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return (CompletableFutureEx<U>) super.handleAsync(fn, executor);
    }

    @Override
    public CompletableFutureEx<T> toCompletableFuture() {
        return (CompletableFutureEx<T>) super.toCompletableFuture();
    }

    @Override
    public CompletableFutureEx<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return (CompletableFutureEx<T>) super.exceptionally(fn);
    }

    @Override
    public CompletableFutureEx<T> exceptionallyAsync(Function<Throwable, ? extends T> fn) {
        return (CompletableFutureEx<T>) super.exceptionallyAsync(fn);
    }

    @Override
    public CompletableFutureEx<T> exceptionallyAsync(Function<Throwable, ? extends T> fn, Executor executor) {
        return (CompletableFutureEx<T>) super.exceptionallyAsync(fn, executor);
    }

    @Override
    public CompletableFutureEx<T> exceptionallyCompose(Function<Throwable, ? extends CompletionStage<T>> fn) {
        return (CompletableFutureEx<T>) super.exceptionallyCompose(fn);
    }

    @Override
    public CompletableFutureEx<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn) {
        return (CompletableFutureEx<T>) super.exceptionallyComposeAsync(fn);
    }

    @Override
    public CompletableFutureEx<T> exceptionallyComposeAsync(Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor) {
        return (CompletableFutureEx<T>) super.exceptionallyComposeAsync(fn, executor);
    }

    @Override
    public <U> CompletableFutureEx<U> newIncompleteFuture() {
        return new CompletableFutureEx<>();
    }

    @Override
    public CompletableFutureEx<T> completeAsync(Supplier<? extends T> supplier, Executor executor) {
        return (CompletableFutureEx<T>) super.completeAsync(supplier, executor);
    }

    @Override
    public CompletableFutureEx<T> completeAsync(Supplier<? extends T> supplier) {
        return (CompletableFutureEx<T>) super.completeAsync(supplier);
    }

    @Override
    public CompletableFutureEx<T> orTimeout(long timeout, TimeUnit unit) {
        return (CompletableFutureEx<T>) super.orTimeout(timeout, unit);
    }

    @Override
    public CompletableFutureEx<T> completeOnTimeout(T value, long timeout, TimeUnit unit) {
        return (CompletableFutureEx<T>) super.completeOnTimeout(value, timeout, unit);
    }
}

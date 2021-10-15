package net.cap5lut.growbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.requireNonNull;

public class Example {
    public static void main(String[] args) {
        new Application();
    }

    static class Application {
        private final ExecutorService executor;

        Application() {
            new Service(this);
            executor = Executors.newSingleThreadExecutor();
        }
    }

    static class Service {
        Service(Application application) {
            application.executor.submit((Runnable) System.out::println); // 1
            requireNonNull(application.executor).submit((Runnable) System.out::println); // 2
        }
    }
}

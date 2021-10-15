package net.cap5lut.growbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ShutdownManager {
    private final Logger logger = LogManager.getLogger();
    private final Deque<Runnable> hooks = new ConcurrentLinkedDeque<>();

    public ShutdownManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::executeHooks, "application-shutdown-hook"));
    }

    public void add(Runnable hook) {
        hooks.addLast(hook);
    }

    protected void executeHooks() {
        logger.atInfo().log("Shutting down");
        while (!hooks.isEmpty()) {
            final var hook = hooks.pollFirst();
            if (hook != null) {
                try {
                    hook.run();
                } catch (Throwable error) {
                    logger.atError().log("Error on shutdown hook", error);
                }
            }
        }
    }

    public void shutdown() {
        executeHooks();
        System.exit(0);
    }

    public void shutdown(Logger logger, String message, Object... arguments) {
        logger.atFatal().log(message, arguments);
        executeHooks();
        System.exit(1);
    }
}

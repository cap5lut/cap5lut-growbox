package net.cap5lut.growbox;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Broadcaster {
    private static final Logger logger = LogManager.getLogger();

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Set<WsContext> contexts = new HashSet<>();

    public void register(Application application) {
        application.getWebserver().ws("/api/v1", ws -> {
            ws.onConnect(this::onConnect);
            ws.onClose(this::onDisconnect);
            ws.onError(this::onError);
            ws.onMessage(this::onMessage);
        });
    }

    private void onConnect(WsConnectContext context) {
        logger.info("Connect {client={}}", context.session.getRemoteAddress());
        lock.writeLock().lock();
        try {
            contexts.add(context);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void onDisconnect(WsCloseContext context) {
        logger.info("Disconnect {client={}}", context.session.getRemoteAddress());
        lock.writeLock().lock();
        try {
            contexts.remove(context);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void onError(WsErrorContext context) {
        logger.info("Error {client={}}", context.session.getRemoteAddress(), context.error());
        context.session.close();
    }

    private void onMessage(WsMessageContext context) {
        logger.info("Message {client={}, message={})", context.session.getRemoteAddress(), context.message());
    }

    public void send(Object data) {
        logger.info("Broadcasting {data={}}", data);

        final WsContext[] contexts;
        lock.readLock().lock();
        try {
            contexts = this.contexts.toArray(WsContext[]::new);
        } finally {
            lock.readLock().unlock();
        }

        for (final var context: contexts) {
            context.send(data);
        }
    }
}

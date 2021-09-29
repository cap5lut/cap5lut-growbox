package net.cap5lut.growbox;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;

public class WebSocketController extends Controller {
    protected WebSocketController(Application app) {
        super(app);
        app.webserver.ws("/ws", ws -> {
            ws.onConnect(this::onConnect);
            ws.onClose(this::onDisconnect);
            ws.onError(this::onError);
        });
    }

    protected void onConnect(WsConnectContext context) {

    }

    protected void onDisconnect(WsCloseContext context) {

    }

    protected void onError(WsErrorContext context) {

    }
}

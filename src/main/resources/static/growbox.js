class EventBus {
    #listeners = [];

    dispatch(event) {
        this.#listeners.forEach(listener => listener(event));
    }

    subscribe(listener) {
        this.#listeners.push(listener);
        return new EventSubscription(listener);
    }

    unsubscribe(listener) {
        let index = this.#listeners.indexOf(listener);
        if (index >= 0) {
            this.#listeners.splice(index, 1);
        }
    }
}

class WsClient {
    #url;
    #ws = null;

    #onConnect = new EventBus();
    #onDisconnect = new EventBus();
    #onError = new EventBus();
    #onMessage = new EventBus();

    constructor(path) {
        this.#url = (location.protocol === "http:" ? "ws:" : "wss:") + "//" + location.host + path;
    }

    get onConnect() {
        return this.#onConnect;
    }

    get onDisconnect() {
        return this.#onDisconnect;
    }

    get onError() {
        return this.#onError;
    }

    get onMessage() {
        return this.#onMessage;
    }

    connect() {
        if (this.#ws == null) {
            this.#ws = new WebSocket(this.#url);
            this.#ws.addEventListener("open", e => {
                this.#onConnect.dispatch({client: this});
            });
            this.#ws.addEventListener("close", e => {
                this.#onDisconnect.dispatch({client: this});
            });
            this.#ws.addEventListener("error", e => {
                this.#onError.dispatch({client: this, error: e});
                this.disconnect();
            })
            this.#ws.addEventListener("message", e => {
                this.#onMessage.dispatch({client: this, message: JSON.parse(e.data)});
            })
        }
    }

    disconnect() {
        if (this.#ws != null) {
            this.#ws.disconnect();
            this.#ws = null;
        }
    }

    send(data) {
        if (this.#ws != null) {
            this.#ws.send(JSON.stringify(data));
        } else {
            console.error("could not send data", data);
        }
    }
}
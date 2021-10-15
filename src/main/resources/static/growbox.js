class c5Event {

}

class c5WebSocket {
    #url;
    #protocols;
    #ws;

    #onConnect = new c5Event();
    #onDisconnect = new c5Event();
    #onError = new c5Event();
    #onMessage = new c5Event();

    constructor(path, protocols) {
        this.#url = (location.protocol === "http:" ? "ws:" : "wss:") +
            location.host + (path.startsWith("/") ? "" : "/") + path
        this.#protocols = protocols;
    }

    get url() {
        return this.#url;
    }

    set url(value) {
        this.#url = value;
    }

    get protocols() {
        return this.#protocols;
    }

    set protocols(value) {
        this.#protocols = value;
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
        if (!this.#ws) {
            this.#ws = new WebSocket(this.#url, this.#protocols);
            this.#ws.addEventListener("open", e => {

            });
        }
    }

    disconnect() {
        if (this.#ws) {
            this.#ws.close();
            this.#ws = null;
        }
    }

    send(message) {

    }
}
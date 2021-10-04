DOMTokenList.prototype.set = function (token, add) {
    if (add) {
        this.add(token);
    } else {
        this.remove(token);
    }
}

class EventBus {
    #listeners = [];

    dispatch(event) {
        this.#listeners.forEach(listener => listener(event));
    }

    listen(listener) {
        this.#listeners.push(listener);
    }
}

const state = (component, name, defaults) => {
    let state = localStorage.getItem(name);
    state = state ? JSON.parse(state) : defaults;
    return new Proxy(state, {
        set(object, property, value) {
            if (object[property] !== value) {
                object[property] = value;
                localStorage.setItem(name, JSON.stringify(object));
                component.$forceUpdate();
            }
        }
    });
}

const app = (() => {
    class Application {
        #events;
        #vue;
        #ws;

        constructor() {
            this.#events = new EventBus();
            this.#vue = Vue.createApp({});
            this.#ws = new WebSocket(location.origin.replace(/^http/, "ws") + "/api/v1");
            this.#ws.addEventListener("message", e => this.#events.dispatch(e));
            window.addEventListener("load", () => {
                this.#vue.mount("body")
            });
        }

        get events() {
            return this.#events;
        }

        get vue() {
            return this.#vue;
        }

        get ws() {
            return this.#ws;
        }
    }
    return new Application();
})();
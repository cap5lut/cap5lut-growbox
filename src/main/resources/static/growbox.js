const growbox = (() => {
    class gbEventBus {
        #listeners = [];

        dispatch(event) {
            this.#listeners.forEach(entry => entry.listener(event, entry.subscription));
        }

        subscribe(listener) {
            const subscription = new gbEventSubscription(() => {
                let index = -1;
                for (let i = 0; i < this.#listeners.length; i++) {
                    if (this.#listeners[i].listener === listener) {
                        this.#listeners.splice(i, 1);
                        break;
                    }
                }
            });
            this.#listeners.push({listener: listener, subscription: subscription});
            return subscription;
        }
    }

    class gbEventSubscription {
        #cancelAction;

        constructor(cancelAction) {
            this.#cancelAction = cancelAction;
        }

        cancel() {
            if (this.#cancelAction) {
                this.#cancelAction();
                this.#cancelAction = null;
            }
        }
    }

    const eventBus = new gbEventBus();

    return class Growbox {
        static get events() {
            return eventBus;
        }
    };
})();
package model;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Collector extends AbstractActor {
    //#printer-messages
    static public Props props() {
        return Props.create(Collector.class, () -> new Collector());
    }

    //#printer-messages
    static public class Greeting {
        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }
    //#printer-messages

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public Collector() {
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Greeting.class, greeting -> {
                    log.info(greeting.message);
                })
                .build();
    }
//#printer-messages
}


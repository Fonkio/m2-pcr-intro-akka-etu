package m2dl.pcr.akka.stringservices;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Recep extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Recep() {

    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof String) {
            log.info((String) msg);
        } else {
            unhandled(msg);
        }
    }


}
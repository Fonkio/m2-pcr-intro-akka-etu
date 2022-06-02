package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class It extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef to;
    private ActorRef dest;

    public It(ActorRef to, ActorRef dest) {
        this.to = to;
        this.dest = dest;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof String) {
            ParamDestinataire paramDestinataire = new ParamDestinataire((String) msg, dest);
            to.tell(paramDestinataire, null);
        } else {
            unhandled(msg);
        }
    }


}
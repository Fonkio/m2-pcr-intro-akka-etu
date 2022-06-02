package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class EratostheneActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef twoActorRef;
    private Integer i = 2;
    private Integer n;

    public EratostheneActor() {
        twoActorRef = getContext().actorOf(Props.create(FilterActor.class, 2), "filter-2-actor");
    }


    Procedure<Object> loop = new Procedure<Object>() {
        public void apply(Object n) throws Exception {
            if (n instanceof Integer) {
                Integer nInt = (Integer) n;
                if (i <= nInt) {
                    twoActorRef.tell(i, getContext().self());
                    i++;
                    getContext().become(wait);
                } else {
                    //TODO stop
                }
            } else {
                unhandled(n);
            }
        }
    };

    Procedure<Object> wait = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            getContext().become(loop);
            getContext().self().tell(msg, null);
        }
    };

    @Override
    public void onReceive(Object n) throws Exception {
        loop.apply(n);
    }


}

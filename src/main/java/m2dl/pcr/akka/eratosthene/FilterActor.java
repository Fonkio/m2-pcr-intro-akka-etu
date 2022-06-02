package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import m2dl.pcr.akka.helloworld4.HelloActor;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;


public class FilterActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private ActorRef nextActorRef;
    private Integer p;
    private Queue<Integer> queue;

    public FilterActor(Integer p) {
        this.p = p;
        this.queue = new SynchronousQueue<>();
    }

    Procedure<Object> next = new Procedure<Object>() {
        public void apply(Object x) throws Exception {
            if (x instanceof Integer) {
                Integer xInt = (Integer) x;
                if (xInt % p != 0) { //Premier
                    log.info(xInt + " est premier");
                    nextActorRef = getContext().actorOf(Props.create(FilterActor.class, xInt), "filter-" + xInt + "-actor");
                    getContext().become(nextCreated,false);
                    getContext().sender().tell("ok", null);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> nextCreated = new Procedure<Object>() {
        public void apply(Object x) throws Exception {
            if (x instanceof Integer xInt) {
                if (xInt % p != 0) { //Premier
                    nextActorRef.tell(x, getContext().self());
                    getContext().become(wait);
                    getContext().sender().tell("ok", null);
                }
            } else {
                unhandled(x);
            }
        }
    };

    Procedure<Object> wait = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                if (msg.equals("ok")) {
                    if (queue.isEmpty()) {
                        getContext().become(nextCreated);
                    } else {
                        nextActorRef.tell(queue.remove(), getContext().self());
                    }
                }
            } else if (msg instanceof Integer) {
                queue.add((Integer) msg);
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object msg) throws Exception {
        next.apply(msg);
    }


}

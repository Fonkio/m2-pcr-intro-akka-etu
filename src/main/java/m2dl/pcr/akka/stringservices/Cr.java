package m2dl.pcr.akka.stringservices;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Cr extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Cr() {

    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof ParamDestinataire) {
            ParamDestinataire paramDestinataire = (ParamDestinataire) msg;
            paramDestinataire.getActorRef().tell(StringUtils.crypte(paramDestinataire.getMessage()), null);
            log.info("Reception chaine " + msg + " => Crypte()");
        } else {
            unhandled(msg);
        }
    }


}
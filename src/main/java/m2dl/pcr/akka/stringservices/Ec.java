package m2dl.pcr.akka.stringservices;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ec extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Ec() {

    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof ParamDestinataire) {
            ParamDestinataire paramDestinataire = (ParamDestinataire) msg;
            paramDestinataire.getActorRef().tell(StringUtils.ajouteCtrl(paramDestinataire.getMessage()), null);
            log.info("Reception chaine " + msg + " => ErrorControl()");
        } else {
            unhandled(msg);
        }
    }


}
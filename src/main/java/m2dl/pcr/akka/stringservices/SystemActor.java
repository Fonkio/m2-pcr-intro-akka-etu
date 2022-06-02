package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import m2dl.pcr.akka.eratosthene.EratostheneActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main runtime class.
 */
public class SystemActor {

    public static final Logger log = LoggerFactory.getLogger(SystemActor.class);

    public static void main(String... args) throws Exception {

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);

        final ActorRef ecRef = actorSystem.actorOf(Props.create(Ec.class), "ec-actor");
        final ActorRef crRef = actorSystem.actorOf(Props.create(Cr.class), "cr-actor");
        final ActorRef recepRef = actorSystem.actorOf(Props.create(Recep.class), "recep-actor");
        final ActorRef itRef = actorSystem.actorOf(Props.create(It.class, ecRef, recepRef), "it-actor");

        ParamDestinataire paramDestinataire = new ParamDestinataire("coucou", itRef);
        crRef.tell(paramDestinataire,null);

        Thread.sleep(1000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }
}

package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;

import java.io.Serializable;

public class ParamDestinataire implements Serializable {
    private String message;
    private ActorRef actorRef;

    public ParamDestinataire(String message, ActorRef actorRef) {
        this.message = message;
        this.actorRef = actorRef;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    public void setActorRef(ActorRef actorRef) {
        this.actorRef = actorRef;
    }
}

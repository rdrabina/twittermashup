import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

import model.Collector;
import model.MemcachedJava;
import model.Streamer;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("System");

            //#create-actors

            final ActorRef streamerActor =
                    system.actorOf(Streamer.props(), "streamerActor");
        final ActorRef penisActor =
                system.actorOf(MemcachedJava.props(), "penisActor");

            //#create-actors

            //#main-send-messages
            //streamerActor.tell(new Streamer.StreamByKeyword("poland"), ActorRef.noSender());
            penisActor.tell(new MemcachedJava.InsertKey("pe"), ActorRef.noSender());
            penisActor.tell(new MemcachedJava.GetValue("pe"), ActorRef.noSender());
        penisActor.tell(new MemcachedJava.InsertKey("pe"), ActorRef.noSender());
        penisActor.tell(new MemcachedJava.InsertKey("pe"), ActorRef.noSender());
        penisActor.tell(new MemcachedJava.GetValue("pe"), ActorRef.noSender());
        penisActor.tell(new MemcachedJava.DecrementKey("pe"), ActorRef.noSender());
        penisActor.tell(new MemcachedJava.GetValue("pe"), ActorRef.noSender());
        penisActor.tell(new MemcachedJava.ClearData("pe"), ActorRef.noSender());

            //#main-send-messages


        try {
            System.in.read();

        } catch (IOException ioe) {
            system.stop(streamerActor);
        } finally {
            system.terminate();
        }
    }
}

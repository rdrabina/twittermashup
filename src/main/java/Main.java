import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

import model.Collector;
import model.Streamer;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("System");

            //#create-actors

            final ActorRef streamerActor =
                    system.actorOf(Streamer.props(), "streamerActor");

            //#create-actors

            //#main-send-messages
            streamerActor.tell(new Streamer.StreamByKeyword("facebook"), ActorRef.noSender());
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

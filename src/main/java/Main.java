import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

import model.Collector;
import model.Streamer;

public class Main {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("System");
        try {
            //#create-actors

            final ActorRef streamerActor =
                    system.actorOf(Streamer.props(), "streamerActor");

            //#create-actors

            //#main-send-messages
            streamerActor.tell(new Streamer.StreamByKeyword("facebook"), ActorRef.noSender());
            //#main-send-messages

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();

        } catch (IOException ioe) {
        } finally {
            system.terminate();
        }
    }
}

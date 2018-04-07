package model;
import akka.actor.AbstractActor;
import akka.actor.Props;
import java.time.LocalDateTime;
import  java.util.concurrent.ConcurrentLinkedQueue;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;


public class Collector extends AbstractActor {

    static public Props props() {
        return Props.create(Collector.class, () -> new Collector(1));
    }

    //#collector-messages
    static public class AddTweet {
        private static String key;
        private static LocalDateTime timeMarker;
        public AddTweet(String key,LocalDateTime timeMarker){
            this.key=key;
            this.timeMarker = timeMarker;
        }
    }

    static public class UpdateQueue{
        public UpdateQueue(){}
    }
    //#printer-messages



    public Collector(long periodSec) {
        this.periodSec=periodSec;
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(AddTweet.class, tweet -> {
                    queue.add(new TweetInfo(tweet.key,tweet.timeMarker));
                    System.out.println("Adding tweet to query");
                })
                .match(UpdateQueue.class, x -> {

                    while(queue.peek().shouldRemove()){
                        TweetInfo ti = queue.poll();
                        System.out.println("Removing tweet info: "+ti.key);
                    }
                })
                .build();
    }

    private ConcurrentLinkedQueue<TweetInfo> queue = new ConcurrentLinkedQueue<>();

    private long  periodSec;

    private class TweetInfo{

        final private String key;
        final private LocalDateTime timeMarker;

        public TweetInfo(String key,LocalDateTime timeMarker){
            this.key=key;
            this.timeMarker = timeMarker;
        }

        public boolean shouldRemove(){
            LocalDateTime now = LocalDateTime.now();
            long p2 = ChronoUnit.SECONDS.between(timeMarker, now);
            return p2 < periodSec;
        }
    }
}


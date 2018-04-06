package model;

import akka.actor.AbstractActor;
import akka.actor.Props;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MemcachedJava extends AbstractActor  {

    private MemcachedClient mcc;

    public MemcachedJava(){
        try {
            this.mcc = new MemcachedClient(new
                    InetSocketAddress("127.0.0.1", 11211));
        }
        catch (IOException e)
        {
            System.out.println("Some problems occured");
        }

    }
    public static void main(String[] args) throws IOException {


        // Connecting to Memcached server on localhost
        MemcachedClient mcc = new MemcachedClient(new
                InetSocketAddress("127.0.0.1", 11211    ));
        System.out.println("Connection to server sucessfully");

       // System.out.println("add status:" + mcc.add("p", 900, 5).isDone());
        System.out.println("set status:"+mcc.set("count", 900, "5"));

        // Get value from cache
        System.out.println("Get from Cache:"+mcc.get("count"));

        // now increase the stored value
        System.out.println("Increment value:"+mcc.incr("count", 1));
        System.out.println("Increment value:"+mcc.incr("count", 2));

        // now decrease the stored value
       // System.out.println("Decrement value:"+mcc.decr("count", 1));

        // now get the final stored value
        System.out.println("Get from Cache:"+mcc.get("count"));

    }

    static public Props props() {
        return Props.create(MemcachedJava.class, () -> new MemcachedJava());
    }

    static public class InsertKey
    {
        private final String keyword;
        public InsertKey(String keyword) {
            this.keyword=keyword;
        }
    }

    static public class DecrementKey
    {
        private final String keyword;
        public DecrementKey(String keyword) {
            this.keyword=keyword;
        }
    }

    static public class GetValue
    {
        private final String keyword;
        public GetValue(String keyword) {
            this.keyword=keyword;
        }
    }

    static public class ClearData
    {
        private final String keyword;
        public ClearData(String keyword) {
            this.keyword=keyword;
        }
    }



    @Override
    public AbstractActor.Receive createReceive() {
        try {
            MemcachedClient mcc = new MemcachedClient(new
                    InetSocketAddress("127.0.0.1", 11211));

            return receiveBuilder()
                    .match(InsertKey.class, x -> {
                        System.out.println(mcc.get(x.keyword)); // to remove
                        if (mcc.get(x.keyword) == null) {
                            System.out.println("set" +mcc.set(x.keyword, 0, 0));//mcc.set(x.keyword,0,1);
                        }
                        int value = (int) mcc.get(x.keyword);
                        value++;
                        mcc.delete(x.keyword);
                        mcc.set(x.keyword, 0, value);
                    })
                    .match(DecrementKey.class, x -> {
                        int value = (int) mcc.get(x.keyword);
                        value--;
                        mcc.delete(x.keyword);
                        mcc.set(x.keyword, 0, value);
                        if (mcc.get(x.keyword).equals(0)) mcc.delete(x.keyword);
                    })
                    .match(GetValue.class, x -> {
                        System.out.println("get"+mcc.get(x.keyword));//mcc.get(x.keyword);
                    })
                    .match(ClearData.class, x -> {
                        mcc.delete(x.keyword);
                    })
                    .build();
        }
        catch (IOException e)
        {
            System.out.println("Some problems occured");
        }
        return null;
    }

}
import java.util.*;

class Reservation {
    String name;

    public Reservation(String name) {
        this.name = name;
    }
}

class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    public synchronized void add(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation get() {
        return queue.poll();
    }
}

class Processor implements Runnable {
    BookingQueue queue;

    public Processor(BookingQueue q) {
        this.queue = q;
    }

    public void run() {
        Reservation r;
        while ((r = queue.get()) != null) {
            System.out.println(Thread.currentThread().getName() +
                    " processing " + r.name);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("User1"));
        queue.add(new Reservation("User2"));

        Thread t1 = new Thread(new Processor(queue));
        Thread t2 = new Thread(new Processor(queue));

        t1.start();
        t2.start();
    }
}
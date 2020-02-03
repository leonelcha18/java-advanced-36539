package com.educacionit.orders.services;

import java.util.concurrent.atomic.AtomicLong;

public class KeysService {
    // Forma 1
    //private long key;

    // Forma 2
    //private volatile long key;

    // Forma 3
    // private long key;

    private AtomicLong key = new  AtomicLong (0);

    // Forma 1
    /*public synchronized long getNewKey () {

        return key++;
    }*/

    // Forma 2
    /*public long getNewKey () {

        return key++;
    }*/

    // Forma 3
    /*public long getNewKey () {

        synchronized (this) {
            return key++;
        }
    }*/

    public long getNewKey () {

        return key.getAndIncrement();
    }
}
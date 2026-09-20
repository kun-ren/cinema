package com.movie.api.constant;

/**
 * Order status
 */
public final class OrderStatus {

    //Payment fails if not completed within 15 minutes
    public final static long EXPIRATION_TIME = 1000 * 60 * 15;

    //New order awaiting payment
    public final static Integer PAYMENT_WAITING = 0;

    //Payment failed
    public final static Integer PAYMENT_FAILED = 1;

    //Paid
    public final static Integer PAYMENT_SUCCESSFUL = 2;

    //Canceled by an administrator
    public final static Integer COUNTERMAND = 3;

}

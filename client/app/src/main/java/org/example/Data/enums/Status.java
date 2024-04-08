package org.example.Data.enums;

public enum Status {
    Ordered, InProgress, Cooked, Delivered, Completed, Canceled;

    /*
     * Options for the Cook
     */
    public static Status[] cooks = new Status[] { Ordered, InProgress, Cooked };
    /**
     * Options for the Waiter
     */
    public static Status[] waiters = new Status[] { Ordered, Delivered, Completed, Canceled };
}

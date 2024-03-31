package org.example.Data.enums;

public enum Status {
    Ordered, InProgress, Cooked, Delivered, Completed, Canceled;

    public static Status[] cooks = new Status[] { Ordered, InProgress, Cooked };
    public static Status[] waiters = new Status[] { Ordered, Delivered, Completed, Canceled };
}

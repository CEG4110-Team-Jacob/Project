package org.example.Data.enums;

public enum Status {
    Ordered, InProgress, Cooked, Delivered, Canceled;

    public static Status[] cooks = new Status[] { Ordered, InProgress, Cooked };
}

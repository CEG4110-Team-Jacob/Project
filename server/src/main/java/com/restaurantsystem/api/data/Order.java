package com.restaurantsystem.api.data;

import java.util.List;

import com.restaurantsystem.api.shared.enums.Status;

public class Order {
    private int id;
    private List<Item> items;
    private int timeOrdered;
    private int timeCompleted;
    private Status status;
    private int totalPrice;
    private Worker waiter;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTimeOrdered() {
        return timeOrdered;
    }

    public void setTimeOrdered(int timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public int getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(int timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Worker getWaiter() {
        return waiter;
    }

    public void setWaiter(Worker waiter) {
        this.waiter = waiter;
    }

}
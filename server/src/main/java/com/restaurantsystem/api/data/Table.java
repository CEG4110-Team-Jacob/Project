package com.restaurantsystem.api.data;

public class Table {
    private int id;
    private int number;
    private Worker waiter;
    private boolean isOccupied;
    private int numSeats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Worker getWaiter() {
        return waiter;
    }

    public void setWaiter(Worker waiter) {
        this.waiter = waiter;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

}

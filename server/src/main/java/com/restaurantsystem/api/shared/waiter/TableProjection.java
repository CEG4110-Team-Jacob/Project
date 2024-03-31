package com.restaurantsystem.api.shared.waiter;

public interface TableProjection {
    public interface Worker {
        String getFirstName();

        String getLastName();

        int getId();
    }

    int getId();

    int getNumber();

    Worker getWaiter();

    boolean getIsOccupied();

    int getNumSeats();

    int getX();

    int getY();
}

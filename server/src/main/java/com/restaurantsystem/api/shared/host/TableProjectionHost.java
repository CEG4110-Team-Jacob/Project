package com.restaurantsystem.api.shared.host;

public interface TableProjectionHost {
    int getId();

    int getNumber();

    boolean getIsOccupied();

    int getNumSeats();

    int getX();

    int getY();
}

package com.restaurantsystem.api.shared.manager;

import java.util.Set;

public record PostTables(Set<PostTable> tables) {
    /**
     * PostTable
     */
    public record PostTable(int x, int y, int waiter, int number, int numSeats) {
    }
}

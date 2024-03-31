package org.example.Data.controllers;

import java.util.Optional;
import java.util.Set;

import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;

public class Hosts {
    public record GetHostTables(Set<HostTable> tables) {
        public record HostTable(int id, int number, boolean isOccupied, int numSeats, int x, int y) {
        }
    }

    private static GetMethods<GetHostTables> tables = new GetMethods<>("/host/tables", GetHostTables.class);
    private static PostMethods<Integer, Boolean> occupy = new PostMethods<>("/host/occupy", Boolean.class);

    public static Optional<GetHostTables> getTables() {
        return tables.set();
    }

    public static void reset() {
        tables.reset();
    }

    public static Optional<Boolean> occupy(Integer body) {
        return occupy.post(body);
    }
}
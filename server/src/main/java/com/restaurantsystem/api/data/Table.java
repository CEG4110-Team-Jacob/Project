package com.restaurantsystem.api.data;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * A table in a restaurant
 */
@Entity(name = "restaurant_table")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    // The table number staff will see
    private int number;

    /**
     * The waiter who is assigned to the table
     */
    @ManyToOne
    private Worker waiter;
    /**
     * All the orders placed on the table
     */
    @OneToMany
    private Set<Order> orders;
    /**
     * If the table is occupied
     */
    private boolean isOccupied;
    /**
     * The number of seats at the table
     */
    private int numSeats;

    /**
     * The x position of the table
     */
    private int x;
    /**
     * The y position of the table
     */
    private int y;

    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Table() {
    }

    public Table(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Table(int id) {
        this.id = id;
    }

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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

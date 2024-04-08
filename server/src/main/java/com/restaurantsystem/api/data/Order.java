package com.restaurantsystem.api.data;

import java.util.List;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * A restaurant order
 */
@Table(name = "restaurant_order")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * Items the order contains
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_to_items", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    private Date timeOrdered;

    private Date timeCompleted;

    @Column(name = "order_status")
    private Status status;

    /**
     * Total price of all the items
     */
    private int totalPrice;

    /**
     * The waiter who placed the order
     */
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker waiter;

    /**
     * The table where the order was placed
     */
    @ManyToOne
    private com.restaurantsystem.api.data.Table table;

    /**
     * Status of the order
     */
    public enum Status {
        Cooked, Ordered, InProgress, Delivered, Completed, Canceled;

        // The order's status that the cook can change from.
        public static boolean cook(Status in) {
            return in == Cooked || in == InProgress || in == Ordered;
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getTimeOrdered() {
        return timeOrdered;
    }

    public void setTimeOrdered(Date timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public Date getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(Date timeCompleted) {
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

    /**
     * Sets the total price of the order using the items
     */
    public void setTotalPrice() {
        totalPrice = 0;
        for (Item item : items) {
            totalPrice += item.getPrice();
        }
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

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public com.restaurantsystem.api.data.Table getTable() {
        return table;
    }

    public void setTable(com.restaurantsystem.api.data.Table table) {
        this.table = table;
    }

}

package com.restaurantsystem.api.data;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * A restaurant item
 */
@Table(name = "restaurant_item")
@Entity
public class Item {
    /**
     * Generic type of the item (Food/Drink)
     */
    public enum ItemType {
        Beverage, Food
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;

    private String description;
    private ItemType type;
    /** Price in cents */
    private int price;
    private boolean inStock;
    private boolean isActive = true;

    /**
     * The orders the item is in
     */
    @ManyToMany(mappedBy = "items")
    private Set<Order> orders;

    public Item(int id) {
        this.id = id;
    }

    public Item() {
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

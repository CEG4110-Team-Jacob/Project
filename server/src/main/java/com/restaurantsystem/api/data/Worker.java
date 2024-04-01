package com.restaurantsystem.api.data;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * A restaurant worker
 * This could be split later, but it works fine now.
 */
@Entity
public class Worker {
    /**
     * The job of a worker
     */
    public enum Job {
        Waiter, Host, Manager, Cook
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String firstName;

    private String lastName;
    private String username;
    private String passwordHash;
    private int age;
    private Job job;
    private boolean isActive = true;
    /**
     * Token used to use APIs
     */
    private String token;
    @OneToMany(mappedBy = "waiter")
    /**
     * The orders a waiter has taken
     */
    private Set<Order> orders;
    @OneToMany(mappedBy = "waiter")
    /**
     * The tables a waiter serves
     */
    private Set<Table> tables;

    public Worker(int id) {
        this.id = id;
    }

    public Worker() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}

package com.restaurantsystem.api.shared.manager;

import java.util.List;

import com.restaurantsystem.api.data.enums.Job;

/**
 * Worker
 */
record Worker(
        String name,
        int id, int age, Job job) {
}

public record GetWorkers(List<Worker> workers) {

}

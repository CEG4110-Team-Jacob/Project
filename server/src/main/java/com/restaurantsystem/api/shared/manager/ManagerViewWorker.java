package com.restaurantsystem.api.shared.manager;

import com.restaurantsystem.api.data.Worker.Job;

/**
 * Worker
 */
public record ManagerViewWorker(
        String name,
        int id, int age, Job job, int weekHours) {
}
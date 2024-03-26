package com.restaurantsystem.api.shared.manager;

import java.util.List;

import com.restaurantsystem.api.data.Worker.Job;

/**
 * Worker
 */
public record ManagerViewWorker(
                String firstName, String lastName,
                int id, int age, Job job) {
        public record ListWorkers(List<ManagerViewWorker> workers) {
        };
}
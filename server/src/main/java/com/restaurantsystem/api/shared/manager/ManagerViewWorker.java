package com.restaurantsystem.api.shared.manager;

import java.util.List;

import com.restaurantsystem.api.data.Worker.Job;

/**
 * Worker
 */
public record ManagerViewWorker(
                String name,
                int id, int age, Job job, int weekHours) {
        public record ListWorkers(List<ManagerViewWorker> workers) {
        };
}
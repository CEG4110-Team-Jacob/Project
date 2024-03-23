package com.restaurantsystem.api.shared.all;

import com.restaurantsystem.api.data.Worker.Job;

public interface WorkerProjection {
    int getId();

    String getFirstName();

    String getLastName();

    int getAge();

    Job getJob();
}

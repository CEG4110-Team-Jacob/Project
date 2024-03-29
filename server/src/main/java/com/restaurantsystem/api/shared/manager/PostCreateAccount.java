package com.restaurantsystem.api.shared.manager;

import com.restaurantsystem.api.data.Worker.Job;

public record PostCreateAccount(
        String firstName, String lastName,
        int age, Job job, String username, String password) {

}

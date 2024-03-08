package com.restaurantsystem.api.shared.manager;

import com.restaurantsystem.api.data.enums.Job;

public record PostCreateAccount(
        String firstName, String lastName,
        int age, Job job) {
}

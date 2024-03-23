package org.example.Data.records;

import org.example.Data.enums.Job;

public record WorkerDetails(int id, String firstName, String lastName, int age, Job job) {

}

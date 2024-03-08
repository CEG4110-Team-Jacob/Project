package com.restaurantsystem.api.service.interfaces;

import java.util.List;

import com.restaurantsystem.api.shared.manager.GetWorkers;
import com.restaurantsystem.api.shared.manager.PostCreateAccount;

public interface ManagerService {
    public boolean createAccount(PostCreateAccount data);

    public List<GetWorkers> getWorkers();
}

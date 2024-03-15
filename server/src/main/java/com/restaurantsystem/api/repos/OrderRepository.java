package com.restaurantsystem.api.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.restaurantsystem.api.data.Order;
import com.restaurantsystem.api.data.Worker;
import com.restaurantsystem.api.data.Order.Status;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    <T> Optional<T> findById(Integer id, Class<T> type);

    <T> List<T> findAllByStatusInAndWaiter(Collection<Status> statuses, Worker waiter, Class<T> type);

    <T> List<T> findAllByStatusIn(Collection<Status> statuses, Class<T> type);
}

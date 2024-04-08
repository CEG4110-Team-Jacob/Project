package org.example.Data.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.Data.records.Item;
import org.example.Data.utils.GetMethods;
import org.example.Data.utils.PostMethods;
import org.example.Data.controllers.Cooks.CookOrder.ListCookOrders;
import org.example.Data.enums.Status;

/**
 * Cook API Getter
 * Look at documentation at
 * <a
 * href=https://github.com/CEG4110-Team-Jacob/Project/wiki/Server#cook>Documentation</a>
 */
public class Cooks {
    public record CookOrder(List<Item> items, Date timeOrdered, Status status,
            int id) {
        public record ListCookOrders(List<CookOrder> orders) {
        }
    }

    public record PostSetStatus(int orderId, Status status) {
    }

    private static GetMethods<CookOrder.ListCookOrders> orders = new GetMethods<>("/cook/getOrders",
            CookOrder.ListCookOrders.class);

    private static PostMethods<Integer, Boolean> itemDepleted = new PostMethods<>("/cook/itemDepleted", Boolean.class);
    private static PostMethods<Integer, Boolean> itemRestocked = new PostMethods<>("/cook/itemRestocked",
            Boolean.class);

    private static PostMethods<PostSetStatus, Boolean> setStatus = new PostMethods<>("/cook/setStatus", Boolean.class);

    public static Optional<Boolean> setStatus(PostSetStatus body) {
        return setStatus.post(body);
    }

    public static void reset() {
        orders.reset();
    }

    public static Optional<Boolean> itemDepleted(Integer body) {
        return itemDepleted.post(body);
    }

    public static Optional<Boolean> itemRestocked(Integer body) {
        return itemRestocked.post(body);
    }

    public static Optional<ListCookOrders> setOrders() {
        return orders.set();
    }
}

package org.example.Data.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.Data.records.Item;
import org.example.Data.records.Item.ListItems;
import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;
import org.example.Data.controllers.Cooks.CookOrder.ListCookOrders;
import org.example.Data.enums.Status;

public class Cooks {
    public record CookOrder(List<Item> items, Date timeOrdered, Status status,
            int id) {
        public record ListCookOrders(List<CookOrder> orders) {
        }
    }

    public record PostSetStatus(int orderId, Status status) {
    }

    private static GetMethods<Item.ListItems> items = new GetMethods<>("/cook/items", Item.ListItems.class);

    private static GetMethods<CookOrder.ListCookOrders> orders = new GetMethods<>("/cook/getOrders",
            CookOrder.ListCookOrders.class);

    private static PostMethods<Integer, Boolean> cookingOrder = new PostMethods<>("/cook/cookingOrder", Boolean.class);
    private static PostMethods<Integer, Boolean> completeOrder = new PostMethods<>("/cook/completeOrder",
            Boolean.class);
    private static PostMethods<Integer, Boolean> itemDepleted = new PostMethods<>("/cook/itemDepleted", Boolean.class);
    private static PostMethods<Integer, Boolean> itemRestocked = new PostMethods<>("/cook/itemRestocked",
            Boolean.class);

    private static PostMethods<PostSetStatus, Boolean> setStatus = new PostMethods<>("/cook/setStatus", Boolean.class);

    public static Optional<Boolean> setStatus(PostSetStatus body) {
        return setStatus.post(body);
    }

    public static void reset() {
        items.reset();
        orders.reset();
    }

    public static Optional<Boolean> completeOrder(Integer body) {
        return completeOrder.post(body);
    }

    public static Optional<Boolean> cookingOrder(Integer body) {
        return cookingOrder.post(body);
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

    public static Optional<ListCookOrders> getOrders() {
        return orders.get();
    }

    public static Optional<ListItems> setItems() {
        return items.set();
    }

    public static Optional<ListItems> getItems() {
        return items.get();
    }

}

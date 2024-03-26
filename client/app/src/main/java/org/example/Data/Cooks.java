package org.example.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.example.Data.Cooks.CookOrder.ListCookOrders;
import org.example.Data.Utils.GetMethods;
import org.example.Data.Utils.PostMethods;
import org.example.Data.records.Item;
import org.example.Data.records.Item.ListItems;
import org.example.Data.enums.Status;

public class Cooks {
    public record CookOrder(List<Item> items, Date timeOrdered, Status status,
            int id) {
        public record ListCookOrders(List<CookOrder> orders) {
        }
    }

    private static GetMethods<Item.ListItems> items = new GetMethods<>("/cook/items", Item.ListItems.class);

    private static GetMethods<CookOrder.ListCookOrders> orders = new GetMethods<>("/getOrders",
            CookOrder.ListCookOrders.class);

    private static PostMethods<Integer, Boolean> cookingOrder = new PostMethods<>("/cook/cookingOrder", Boolean.class);
    private static PostMethods<Integer, Boolean> completeOrder = new PostMethods<>("/cook/completeOrder",
            Boolean.class);
    private static PostMethods<Integer, Boolean> itemDepleted = new PostMethods<>("/cook/itemDepleted", Boolean.class);
    private static PostMethods<Integer, Boolean> itemRestocked = new PostMethods<>("/cook/itemRestocked",
            Boolean.class);

    public Optional<Boolean> completeOrder(Integer body) {
        return completeOrder.post(body);
    }

    public Optional<Boolean> cookingOrder(Integer body) {
        return cookingOrder.post(body);
    }

    public Optional<Boolean> itemDepleted(Integer body) {
        return itemDepleted.post(body);
    }

    public Optional<Boolean> itemRestocked(Integer body) {
        return itemRestocked.post(body);
    }

    public Optional<ListCookOrders> setOrders() {
        return orders.set();
    }

    public Optional<ListCookOrders> getOrders() {
        return orders.get();
    }

    public Optional<ListItems> setItems() {
        return items.set();
    }

    public Optional<ListItems> getItems() {
        return items.get();
    }

}

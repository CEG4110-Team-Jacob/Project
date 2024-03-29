package org.example.Data;

import org.example.Data.controllers.Cooks;
import org.example.Data.controllers.General;
import org.example.Data.controllers.Managers;
import org.example.Data.controllers.Waiters;

public class Data {

    public static void deleteData() {
        Waiters.reset();
        General.reset();
        Managers.reset();
        Cooks.reset();
    }
}

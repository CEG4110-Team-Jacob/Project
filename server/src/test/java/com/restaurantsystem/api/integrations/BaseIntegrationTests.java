package com.restaurantsystem.api.integrations;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.restaurantsystem.api.DatabasePopulate;
import com.restaurantsystem.api.MainController;
import com.restaurantsystem.api.DatabasePopulate.Login;
import com.restaurantsystem.api.controllers.CookController;
import com.restaurantsystem.api.controllers.HostController;
import com.restaurantsystem.api.controllers.ManagerController;
import com.restaurantsystem.api.controllers.WaiterController;
import com.restaurantsystem.api.service.AuthenticationService;

@SpringBootTest
public class BaseIntegrationTests {
    @Autowired
    protected AuthenticationService authenticationService;
    protected String waiterT;
    protected String hostT;
    protected String managerT;
    protected String cookT;

    @Autowired
    protected MainController mainController;
    @Autowired
    protected HostController hostController;
    @Autowired
    protected CookController cookController;
    @Autowired
    protected WaiterController waiterController;
    @Autowired
    protected ManagerController managerController;

    private String login(Login login) {
        return authenticationService.login(login.username(), login.password()).get();
    }

    @BeforeEach
    public void beforeEach() {
        waiterT = login(DatabasePopulate.Waiter1);
        hostT = login(DatabasePopulate.Host1);
        managerT = login(DatabasePopulate.Manager1);
        cookT = login(DatabasePopulate.Cook1);
    }
}

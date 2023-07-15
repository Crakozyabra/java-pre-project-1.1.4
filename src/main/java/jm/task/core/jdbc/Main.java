package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    private static final List<String> names = List.of("Name1", "Name2", "Name3", "Name4");

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser(names.get(0), "LastName1", (byte) 10);
        System.out.printf("User с именем – %s добавлен в базу данных\n", names.get(0));
        userService.saveUser(names.get(1), "LastName2", (byte) 20);
        System.out.printf("User с именем – %s добавлен в базу данных\n", names.get(1));
        userService.saveUser(names.get(2), "LastName3", (byte) 30);
        System.out.printf("User с именем – %s добавлен в базу данных\n", names.get(2));
        userService.saveUser(names.get(3), "LastName4", (byte) 40);
        System.out.printf("User с именем – %s добавлен в базу данных\n", names.get(3));
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService=new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("First","First",(byte) 20);
        userService.saveUser("Second","Second",(byte) 21);
        userService.saveUser("Third","Third",(byte) 22);
        userService.saveUser("Fourth","Fourth",(byte) 23);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

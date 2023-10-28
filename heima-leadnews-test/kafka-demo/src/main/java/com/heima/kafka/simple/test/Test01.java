package com.heima.kafka.simple.test;


import com.heima.kafka.simple.pojo.User;

import java.util.TreeSet;

/**
 * TODO
 *
 * @author yaoh
 */
public class Test01 {
    public static void main(String[] args) {

        TreeSet<User> users = new TreeSet<>(((o1, o2) -> o1.getAge() > o2.getAge() ? 1 : -1));
        User user1 = new User("a", 1);
        User user2 = new User("b", 2);
        User user3 = new User("c", 3);
        User user4 = new User("d", 4);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        System.out.println(users);
    }
}

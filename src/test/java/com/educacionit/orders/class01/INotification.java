
package com.educacionit.orders.class01;

@FunctionalInterface
public interface INotification {
    void send (String to, String from, String subject, String content);
}
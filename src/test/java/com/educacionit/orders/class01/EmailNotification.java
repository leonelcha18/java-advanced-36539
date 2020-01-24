package com.educacionit.orders.class01;

public class EmailNotification implements INotification {

    public void send(String to, String from, String subject,
                     String content) {

        System.out.println (String.format ("%s sending message [%s][%s] to %s using email !!!", from, subject, content, to));
    }
}

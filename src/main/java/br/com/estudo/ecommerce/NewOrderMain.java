package br.com.estudo.ecommerce;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

       try (var orderDispatcher = new KafkaDispatcher<Order>()){
           try (var emailDispatcher = new KafkaDispatcher<Email>()){
               for(var i = 0; i < 10; i++) {
                   var userId = UUID.randomUUID().toString();
                   var orderId = UUID.randomUUID().toString();
                   var amount = new BigDecimal(Math.random() * 5000 + 1);
                   var order = new Order(userId, orderId, amount);
                   var value = "12345,12345,12345";
                   orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
                   var emailMsg = "Thank you for your order! We're processing your order!";
                   var email = new Email(emailMsg, emailMsg);
                   emailDispatcher.send("ECOMMERCE_NEW_EMAIL", userId, email);
               }
           }

       }

    }

}

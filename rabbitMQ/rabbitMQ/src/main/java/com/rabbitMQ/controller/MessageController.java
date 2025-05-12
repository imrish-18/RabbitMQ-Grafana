package com.rabbitMQ.controller;

import com.rabbitMQ.config.MessageListener;
import com.rabbitMQ.config.MessagePublisher;
import com.rabbitMQ.config.MessageSender;
import com.rabbitMQ.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private  MessageSender sender;

    @Autowired
    private MessagePublisher publisher;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody String msg) {
        sender.send(msg);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reactive")
    public Mono<Void> postMessage(@RequestBody Mono<String> body) {
        return body.flatMap(publisher::send);
    }
    @PostMapping("/{routingKey}")
    public ResponseEntity<String> sendMessage(
            @PathVariable String routingKey,
            @RequestBody String message
    ) {
        publisher.sendMessage(routingKey, message);
        return ResponseEntity.ok("Message sent with routing key: " + routingKey);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<String> sendUser(@RequestBody User user) {
        publisher.sendUser(user, "info");
        return ResponseEntity.ok("User sent to RabbitMQ");
    }
}


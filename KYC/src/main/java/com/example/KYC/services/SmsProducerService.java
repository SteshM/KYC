package com.example.KYC.services;


import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsProducerService {
    private final StreamBridge streamBridge;
    private static final String ORDERS_SENDTEXT_TOPIC = "orders-sendText-topic";

    public void sendSms(String message, String phoneNumber) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message",message);
        jsonObject.addProperty("phoneNumber",phoneNumber);
        log.info("Sending SMS to Kafka topic: {}", jsonObject);

        // Send the message to the Kafka topic using StreamBridge
        streamBridge.send(ORDERS_SENDTEXT_TOPIC, jsonObject.toString());}

}

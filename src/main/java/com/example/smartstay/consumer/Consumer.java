package com.example.smartstay.consumer;

import com.example.smartstay.model.Booking;
import com.example.smartstay.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private PaymentService paymentService = new PaymentService();

    @JmsListener(destination = "consumer")
    public void consumeMessage(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Booking data = mapper.readValue(message, Booking.class);
        try {
            logger.info("Message received === " + data);
            paymentService.calculate(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

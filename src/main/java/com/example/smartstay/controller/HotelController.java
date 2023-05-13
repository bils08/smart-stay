package com.example.smartstay.controller;

import com.example.smartstay.model.Booking;
import com.example.smartstay.model.Payment;
import com.example.smartstay.model.Person;
import com.example.smartstay.model.Room;
import com.example.smartstay.model.handler.ResponseHandler;
import com.example.smartstay.service.BookingService;
import com.example.smartstay.service.PaymentService;
import com.example.smartstay.service.PersonService;
import com.example.smartstay.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RequestMapping("v1/api/hotel")
@RestController
public class HotelController {

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private PersonService personService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;


    @PostMapping("/person")
    public ResponseEntity<Object> savePerson(@RequestBody Person person) {
        try {
            Person data = personService.save(person);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/person")
    public ResponseEntity<Object> findAllPerson() {
        try {
            List<Person> data = personService.findAll();
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/room")
    public ResponseEntity<Object> saveRoom(@RequestBody Room room) {
        try {
            Room data = roomService.save(room);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/room")
    public ResponseEntity<Object> findAllRoom() {
        try {
            List<Room> data = roomService.findAll();
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Object> findByIdRoom(@PathVariable("id") Long id) {
        try {
            Room data = roomService.findById(id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/booking")
    public ResponseEntity<Object> saveBooking(@RequestBody Booking booking) {
        try {
            Booking data = bookingService.save(booking);
            logger.info("data === " + data);
            ObjectMapper mapper = new ObjectMapper();
            String productAsJson = mapper.writeValueAsString(booking);
            jmsTemplate.convertAndSend("consumer", productAsJson);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/booking")
    public ResponseEntity<Object> findAllBooking() {
        try {
            List<Booking> data = bookingService.findAll();
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<Object> findByIdBooking(@PathVariable("id") Long id) {
        try {
            Booking data = bookingService.findById(id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<Object> savePayment(@RequestBody Payment payment) {
        try {
            Payment data = paymentService.save(payment);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/payment")
    public ResponseEntity<Object> findAllPayment() {
        try {
            List<Payment> data = paymentService.findAll();
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> findByIdPayment(@PathVariable("id") Long id) {
        try {
            Optional<Payment> data = paymentService.findById(id);
            logger.info("test == " + data);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("failed", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


}

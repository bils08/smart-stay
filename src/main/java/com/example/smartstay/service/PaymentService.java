package com.example.smartstay.service;

import com.example.smartstay.consumer.Consumer;
import com.example.smartstay.model.Booking;
import com.example.smartstay.model.Payment;
import com.example.smartstay.repository.BookingRepository;
import com.example.smartstay.repository.PaymentRepository;
import com.example.smartstay.utils.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private Payment payment = new Payment();

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        Optional<Payment> data = paymentRepository.findById(id);
        logger.info("data payment === " + data);
        return paymentRepository.findById(id);
    }

    public void calculate(Booking booking) {
        Booking booked = bookingService.findById(booking.getId());
        float price = booked.getRoom().getPrice();
        long daysStay = days(booked.getCheckIn(), booked.getCheckOut());
        logger.info("stay === " + daysStay);
        float total = price * daysStay;
        payment.setBooking(booked);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTotal(total);
        logger.info("payment === " + payment);
        paymentRepository.save(payment);
    }

    public long days(long checkIn, long checkOut) {
        Date dateIn = new Date(checkIn);
        Date dateOut = new Date(checkOut);
        long timeDiff = Math.abs(dateIn.getTime() - dateOut.getTime());
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        return daysDiff;
    }
}

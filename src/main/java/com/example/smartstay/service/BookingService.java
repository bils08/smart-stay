package com.example.smartstay.service;

import com.example.smartstay.controller.HotelController;
import com.example.smartstay.model.Booking;
import com.example.smartstay.model.Room;
import com.example.smartstay.repository.BookingRepository;
import com.example.smartstay.repository.RoomRepository;
import com.example.smartstay.utils.BookingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Booking save(Booking booking) {
        Room bookedRoom = new Room();
        Long room_id = booking.getRoom().getId();
        bookedRoom = roomRepository.findById(room_id).get();
        bookedRoom.setStatus(BookingStatus.BOOKED);
        roomRepository.save(bookedRoom);
        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        logger.info("id === " + id);
        Optional<Booking> data = bookingRepository.findById(id);
        logger.info("booking data === " + data);
        return bookingRepository.findById(id).get();
    }
}

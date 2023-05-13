package com.example.smartstay.model;

import com.example.smartstay.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Booking booking;

    @Column(name = "total")
    private float total;

    @Column(name = "status")
    private PaymentStatus status;
}

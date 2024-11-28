package org.rostislav.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.rostislav.models.Rental;
import org.rostislav.models.RentalWithNames;
import org.rostislav.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public Rental getRentalById(int id) {
        return rentalRepository.getRentalById(id);
    }

    public Collection<Rental> getAllRentals() {
        return rentalRepository.getAllRentals();
    }

    public void addRental(int userId, int carId, LocalDate pickupDate, LocalDate dropoffDate, BigDecimal totalPrice, String status) {
        rentalRepository.addRental(new Rental(userId, carId, pickupDate, dropoffDate, totalPrice, status));
    }

    public void removeRental(int id) {
        rentalRepository.deleteRental(id);
    }

    public void updateRental(Rental rental) {
        rentalRepository.updateRental(rental);
    }

    public Collection<RentalWithNames> getAllRentalsWithNames() {
        return rentalRepository.getAllRentalsWithNames();
    }

    public RentalWithNames getRentalWithNames(int rentalId) {
        return rentalRepository.getRentalWithNames(rentalId);
    }
}
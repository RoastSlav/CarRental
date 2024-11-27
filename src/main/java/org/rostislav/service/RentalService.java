package org.rostislav.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import org.rostislav.models.Rental;
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

    public void addRental(Rental rental) {
        rentalRepository.addRental(rental);
    }

    public void addRental(int userId, int vehicleId, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice, String status) {
        rentalRepository.addRental(new Rental(userId, vehicleId, startDate, endDate, totalPrice, status));
    }

    public void removeRental(int id) {
        rentalRepository.deleteRental(id);
    }

    public void updateRental(Rental rental) {
        rentalRepository.updateRental(rental);
    }

    public void updateRental(int id, int userId, int vehicleId, LocalDate startDate, LocalDate endDate, BigDecimal totalPrice, String status) {
        rentalRepository.updateRental(new Rental(id, userId, vehicleId, startDate, endDate, totalPrice, status));
    }

    public void updateRentalStatus(int id, String status) {
        rentalRepository.updateRentalStatus(id, status);
    }
}

package com.kgentry.model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkIndate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkIndate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkIndate = checkIndate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return String.format("customer: %s %s has a reservation for room %s from %s to %s", customer.getFirstName(),
                customer.getLastName(), room.getRoomNumber(), checkIndate, checkOutDate);
    }


}

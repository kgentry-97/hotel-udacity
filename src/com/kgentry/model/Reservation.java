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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckIndate() {
        return checkIndate;
    }

    public void setCheckIndate(Date checkIndate) {
        this.checkIndate = checkIndate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return String.format("customer: %s %s has a reservation for room %s from %s to %s", customer.getFirstName(),
                customer.getLastName(), room.getRoomNumber(), checkIndate, checkOutDate);
    }


}

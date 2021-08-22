package com.kgentry.api;

import com.kgentry.model.Customer;
import com.kgentry.model.IRoom;
import com.kgentry.model.Reservation;
import com.kgentry.service.CustomerService;
import com.kgentry.service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource;
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private HotelResource(){}

    public static HotelResource getInstance(){
        if(hotelResource == null){
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
       customerService.addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomNumber){
        return null;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return null;
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail){
        return reservationService.getCustomerReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return null;
    }
}

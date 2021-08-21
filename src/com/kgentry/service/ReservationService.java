package com.kgentry.service;

import com.kgentry.model.Customer;
import com.kgentry.model.IRoom;
import com.kgentry.model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class ReservationService {

    private HashMap<String, Reservation> reservations = new HashMap<>();
    private static ReservationService reservationService;
    private HashMap<String, IRoom> rooms = new HashMap<>();

    private ReservationService(){}

    public static ReservationService getInstance(){
        if(reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addRoom(IRoom room){
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkinDate, Date checkOutDate){
        return null;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return null;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        return ;
    }

    public void printAllReservations(){

    }

    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

}

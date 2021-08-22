package com.kgentry.service;

import com.kgentry.model.Customer;
import com.kgentry.model.IRoom;
import com.kgentry.model.Reservation;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class ReservationService {

    Collection<Reservation> reservations = new LinkedList<>();
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
        Collection<Reservation> foundResvations = new LinkedList<>();
        for(Reservation reservation: reservations){
            if(reservation.getCustomer().equals(customer)){
                foundResvations.add(reservation);
            }
        }
        return foundResvations;
    }

    public void printAllReservations(){
        Collection<Reservation> reservations = reservationService.getReservations();
        if(!reservations.isEmpty()){
            reservations.forEach(System.out::println);
        }
        else {
            System.out.println("no reservations");
        }
    }

    public Collection<IRoom> getAllRooms(){
        return rooms.values();
    }

}

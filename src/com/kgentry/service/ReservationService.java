package com.kgentry.service;

import com.kgentry.model.Customer;
import com.kgentry.model.IRoom;
import com.kgentry.model.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

;


public class ReservationService {
    private static final ReservationService reservationService = new ReservationService();
    private final Collection<Reservation> reservations = new HashSet<>();
    private static final Collection<IRoom> rooms = new HashSet<>();

    private ReservationService() {
    }

    public synchronized static ReservationService getInstance() {
        return reservationService;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations.addAll(reservations);
    }

    public void addRoom(IRoom room){
        rooms.add(room);
    }

    public IRoom getARoom(String roomId){
        IRoom foundRoom = null;
        for (IRoom room: rooms) {
            if(room.getRoomNumber().equals(roomId)){
                foundRoom = room;
            }
        }
        return foundRoom;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> newReservation = new HashSet<>();
        newReservation.add(reservation);
        setReservations(newReservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRooms = new ArrayList<>(rooms);
        Collection<Reservation> allReservation = getReservations();
        for(Reservation reservation: allReservation){
            if(checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckIndate())){
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        Collection<Reservation> foundReservations = new LinkedList<>();
        for(Reservation reservation: reservations){
            if(reservation.getCustomer().equals(customer)){
                foundReservations.add(reservation);
            }
        }
        return foundReservations;
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
        return rooms;
    }

}

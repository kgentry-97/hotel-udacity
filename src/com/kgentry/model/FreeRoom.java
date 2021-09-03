package com.kgentry.model;

public class FreeRoom extends Room{


    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString(){
        return String.format("Room: %s has a %s bed that is free", getRoomNumber(), getRoomType());
    }


}

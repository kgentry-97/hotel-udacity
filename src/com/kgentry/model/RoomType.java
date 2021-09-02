package com.kgentry.model;

import java.util.HashMap;
import java.util.Map;

public enum RoomType {
    SINGLE(1), DOUBLE(2);

    private static final Map<Integer, RoomType> BY_NUMBER = new HashMap<>();

    static {
        for (RoomType roomType: values()){
            BY_NUMBER.put(roomType.number, roomType);
        }
    }

    public final int number;

    RoomType(int number){
        this.number = number;
    }

    public static RoomType valueOfNumber(int number){
        return BY_NUMBER.get(number);
    }

}

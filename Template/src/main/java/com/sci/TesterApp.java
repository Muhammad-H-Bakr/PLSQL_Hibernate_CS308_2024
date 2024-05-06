package com.sci;

import com.sci.dao.DBConfig;
import com.sci.dao.DBReservation;
import com.sci.models.*;
import java.util.List;

public class TesterApp {
    public static void main(String[] args) {
        DBReservation roomType = new DBReservation();
        List<Reservation> rooms = roomType.get();
        for (Reservation room : rooms) {
            System.out.println(room);
        }
        DBConfig.shutdown();
    }
}

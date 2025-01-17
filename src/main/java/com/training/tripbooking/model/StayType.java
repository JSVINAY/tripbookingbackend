package com.training.tripbooking.model;

public enum StayType {
    HOTEL, RESORT, HOSTEL, VILLA;

    public static boolean isValidStayType(String stayType) {
        for (StayType type : StayType.values()) {
            if (type.name().equalsIgnoreCase(stayType)) {
                return true;
            }
        }
        return false;
    }
}

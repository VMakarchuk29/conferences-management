package com.conference.demo.entities.enums;

public enum OfferStatus {
    IN_PROCESS, DENIED, ACCEPT;

    @Override
    public String toString() {
        return name();
    }
}

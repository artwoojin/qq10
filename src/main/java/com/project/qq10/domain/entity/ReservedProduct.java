package com.project.qq10.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("RESERVED")
@NoArgsConstructor
//@SuperBuilder
public class ReservedProduct extends Product{
    @Column(name = "reservation_start")
    private LocalDateTime reservationStart;

    @Column(name = "reservation_end")
    private LocalDateTime reservationEnd;

    public void updateReservationTimes(LocalDateTime reservationStart, LocalDateTime reservationEnd) {
        if (reservationStart != null && reservationEnd != null) {
            this.reservationStart = reservationStart;
            this.reservationEnd = reservationEnd;
        }
    }

    public boolean isReservationActive() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(reservationStart) && now.isBefore(reservationEnd);
    }
}

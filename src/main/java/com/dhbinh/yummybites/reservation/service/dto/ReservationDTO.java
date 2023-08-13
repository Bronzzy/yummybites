package com.dhbinh.yummybites.reservation.service.dto;

import com.dhbinh.yummybites.base.exception.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {

    private Long id;

    @FutureOrPresent(message = ErrorMessage.RESERVATION_DATE_MUST_BE_IN_FUTURE)
    @NotNull(message = ErrorMessage.RESERVATION_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationDate;

    @NotBlank(message = ErrorMessage.NAME_NULL_OR_BLANK)
    private String name;

    @Min(value = 1, message = ErrorMessage.GUESTS_NUMBER_LESS_THAN_ONE)
    private Integer numberOfGuests;

    private String note;
}

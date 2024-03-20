package com.BKHOSTEL.BKHOSTEL.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private Date timestamp;
    private String status;
    private String message;

}

package com.uca.parcialfinalncapas.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ErrorResponse {
    private Object message;
    private int status;
    private LocalDate time;
    private String uri;
}

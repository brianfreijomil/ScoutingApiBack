package com.microservice.user.http.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseApi<T> {

    private T body;
    private HttpStatus status;
    private String message;

}

package com.weiz.spb.services.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageCode implements Serializable {

    protected int code;
    protected String message;

    public MessageCode(HttpStatus status) {

        this.code = status.value();
        this.message = status.getReasonPhrase();
    }
}

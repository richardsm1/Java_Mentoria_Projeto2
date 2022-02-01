package com.mentorias.login.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ErroPadrao {

    private LocalDateTime date_time;
    private String url;
    private int status;
    private String error;
    private String msg;
}

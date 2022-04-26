package com.example.vaadinapp.domain;

import lombok.Data;

@Data
public class Mielogram {
    private String cellType;
    private Long total;
    private String percent;

    public Mielogram(String cellType, Long total) {
        this.cellType = cellType;
        this.total = total;
    }
}

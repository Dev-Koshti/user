package com.example.User.maintainancemode.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MaintenanceDTO implements Serializable {

    private boolean in_maintenance;

    public MaintenanceDTO(boolean in_maintenance) {
        this.in_maintenance = in_maintenance;
    }
}

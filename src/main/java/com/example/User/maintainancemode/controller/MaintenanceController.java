package com.example.User.maintainancemode.controller;

import com.example.User.constant.APIRequestURL;
import com.example.User.maintainancemode.handler.MaintainanceModeHandler;
import com.example.User.maintainancemode.model.request.MaintenanceModeONRequest;
import com.example.User.maintainancemode.model.request.MaintenanceModeOffRequest;
import com.example.User.maintainancemode.model.request.RetriveMaintenanceModeRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MaintenanceController {

    public static final String API_MAINTENANCE_URI = APIRequestURL
            .AUTH_GET_API_MAINTENANCE_URI;

    @Autowired
    private MaintainanceModeHandler maintainanceModeHandler;

    @GetMapping(API_MAINTENANCE_URI)
    public ResponseEntity<JsonNode> retreiveInMaintenance(@RequestHeader HttpHeaders headers, @ModelAttribute RetriveMaintenanceModeRequest retriveMaintenanceModeRequest) {
        return maintainanceModeHandler.retreiveInMaintenance(headers, retriveMaintenanceModeRequest);
    }

    @PutMapping(APIRequestURL.AUTH_MAINTENANCE_MODE_ON)
    public ResponseEntity<JsonNode> maintenanceModeOn(@RequestHeader HttpHeaders headers, @RequestBody MaintenanceModeONRequest maintenanceModeONRequest) {
        return maintainanceModeHandler.maintenanceModeON(headers, maintenanceModeONRequest);
    }

    @PutMapping(APIRequestURL.AUTH_MAINTENANCE_MODE_OFF)
    public ResponseEntity<JsonNode> maintenanceModeOff(@RequestHeader HttpHeaders headers, @RequestBody MaintenanceModeOffRequest maintenanceModeOFFRequest) {
        return maintainanceModeHandler.maintenanceModeOFF(headers, maintenanceModeOFFRequest);
    }
}

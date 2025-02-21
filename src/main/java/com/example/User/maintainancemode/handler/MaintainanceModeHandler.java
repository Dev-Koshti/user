package com.example.User.maintainancemode.handler;

import com.example.User.api.common.commonresponse.CommonAPIDataResponse;
import com.example.User.helper.HeaderProcessingHelper;
import com.example.User.maintainancemode.model.MaintenanceDTO;
import com.example.User.maintainancemode.model.request.MaintenanceModeONRequest;
import com.example.User.maintainancemode.model.request.MaintenanceModeOffRequest;
import com.example.User.maintainancemode.model.request.RetriveMaintenanceModeRequest;
import com.example.User.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MaintainanceModeHandler {

    private final ApplicationEventPublisher eventPublisher;

    private final ApplicationAvailability availability;

    public MaintainanceModeHandler(ApplicationEventPublisher eventPublisher, ApplicationAvailability availability) {
        this.eventPublisher = eventPublisher;
        this.availability = availability;
    }

    public ResponseEntity<JsonNode> retreiveInMaintenance(HttpHeaders headers, RetriveMaintenanceModeRequest retriveMaintenanceModeRequest) {
        HeaderProcessingHelper.setRequestHeaders(retriveMaintenanceModeRequest, headers);
        if (retriveMaintenanceModeRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }

        var lastChangeEvent = availability.getLastChangeEvent(ReadinessState.class);
        MaintenanceDTO maintenanceDTO = new MaintenanceDTO(lastChangeEvent.getState().equals(ReadinessState.REFUSING_TRAFFIC));

        return new ResponseEntity<>(Utils.generateSuccessResponse(maintenanceDTO), HttpStatus.OK);
    }

    public ResponseEntity<JsonNode> maintenanceModeON(HttpHeaders headers, MaintenanceModeONRequest maintenanceModeONRequest) {
        HeaderProcessingHelper.setRequestHeaders(maintenanceModeONRequest, headers);

        if (maintenanceModeONRequest.checkBadRequest()) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BAD_REQUEST"), HttpStatus.OK);
        }

        CommonAPIDataResponse commonAPIDataResponse = new CommonAPIDataResponse();

        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.REFUSING_TRAFFIC);

        commonAPIDataResponse.setMessage("MAINTENANCE_MODE_IS_ON_SUCCESSFULLY");

        return Utils.getJsonNodeResponseEntity(commonAPIDataResponse);
    }

    public ResponseEntity<JsonNode> maintenanceModeOFF(HttpHeaders headers, MaintenanceModeOffRequest maintenanceModeOffRequest) {
        HeaderProcessingHelper.setRequestHeaders(maintenanceModeOffRequest, headers);

        CommonAPIDataResponse commonAPIDataResponse = new CommonAPIDataResponse();

        AvailabilityChangeEvent.publish(eventPublisher, this, ReadinessState.ACCEPTING_TRAFFIC);

        commonAPIDataResponse.setMessage("MAINTENANCE_MODE_IS_OFF_SUCCESSFULLY");

        return Utils.getJsonNodeResponseEntity(commonAPIDataResponse);
    }

}

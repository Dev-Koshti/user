package com.example.User.dummes;

import com.example.User.api.common.commonrequest.CommonAPIDataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveTicketBarcodeDetailsRequest extends CommonAPIDataRequest {

    @JsonProperty("bus_ticket_barcode")
    private String busTicketBarcode;

    @JsonProperty("trade_fair_ticket_barcode")
    private String tradeFairTicketBarcode;

    @JsonProperty("bus_ticket_barcode_scanned_date")
    private Long busTicketBarcodeScannedDate;

    @JsonProperty("trade_fair_ticket_barcode_scanned_date")
    private Long tradeFairTicketBarcodeScannedDate;

    @JsonProperty("trade_fair_ticket_barcode_valid")
    private Boolean tradeFairTicketBarcodeValid;

    @JsonProperty("bus_ticket_barcode_valid")
    private Boolean busTicketBarcodeValid;

    @JsonProperty("booking_master_id")
    private String bookingMasterId;

    @JsonProperty("scanned_by")
    private String scannedBy;

    @JsonProperty("booking_number")
    private String bookingNumber;

    @JsonProperty("booking_qr_string")
    private String bookingQrString;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("user_ticket_generator_id")
    private String userTicketGeneratorId;
}

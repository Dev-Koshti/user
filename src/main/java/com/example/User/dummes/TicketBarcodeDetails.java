package com.example.User.dummes;

import com.example.User.database.CommonFieldModel;
import com.example.User.utils.Utils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Document(collection = "booking_barcode_details")
public class TicketBarcodeDetails extends CommonFieldModel {

    @Field("_id")
    @Builder.Default
    private String id = Utils.generateUUID();

    @Field("bus_ticket_barcode")
    private String busTicketBarcode;

    @Field("trade_fair_ticket_barcode")
    private String tradeFairTicketBarcode;

    @Field("bus_ticket_barcode_scanned_date")
    private Long busTicketBarcodeScannedDate;

    @Field("trade_fair_ticket_barcode_scanned_date")
    private Long tradeFairTicketBarcodeScannedDate;

    @Field("trade_fair_ticket_barcode_valid")
    private Boolean tradeFairTicketBarcodeValid;

    @Field("bus_ticket_barcode_valid")
    private Boolean busTicketBarcodeValid;

    @Field("booking_master_id")
    private String bookingMasterId;

    @Field("scanned_by")
    private String scannedBy;

    @Field("booking_number")
    private String bookingNumber;

    @Field("booking_qr_string")
    private String bookingQrString;

    @Field("user_ticket_generator_id")
    private String userTicketGeneratorId;

    public TicketBarcodeDetails setTicketBarcode(SaveTicketBarcodeDetailsRequest request) {
        return TicketBarcodeDetails.builder()
                .busTicketBarcode(request.getBusTicketBarcode())
                .tradeFairTicketBarcode(request.getTradeFairTicketBarcode())
                .busTicketBarcodeScannedDate(request.getBusTicketBarcodeScannedDate())
                .tradeFairTicketBarcodeScannedDate(request.getTradeFairTicketBarcodeScannedDate())
                .tradeFairTicketBarcodeValid(request.getTradeFairTicketBarcodeValid())
                .busTicketBarcodeValid(request.getBusTicketBarcodeValid())
                .bookingMasterId(request.getBookingMasterId())
                .bookingNumber(request.getBookingNumber())
                .bookingQrString(request.getBookingQrString())
                .userTicketGeneratorId(request.getUserTicketGeneratorId())
                .isActive(request.getIsActive())
                .scannedBy(request.getScannedBy())
                .createdBy(request.getToken_user_id())
                .createdDate(Instant.now().getEpochSecond())
                .companyId(request.getCompany_id())
                .userTicketGeneratorId(request.getUserTicketGeneratorId())
                .build();
    }

}

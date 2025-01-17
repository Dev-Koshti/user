package com.example.User.dummes;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Component
public class BulkDataService {
    private final MongoTemplate mongoTemplate;

    public void saveRandomData(int totalRecords) {
        List<TicketBarcodeDetails> batch = new ArrayList<>();
        int batchSize = 1000; // Batch size for bulk inserts

        for (int i = 0; i < totalRecords; i++) {
            SaveTicketBarcodeDetailsRequest barcodeRequest = generateRandomData();
            batch.add(new TicketBarcodeDetails().setTicketBarcode(barcodeRequest));

            // Insert the batch when the size reaches the batchSize or when it's the last record
            if (batch.size() == batchSize || i == totalRecords - 1) {
                try {
                    mongoTemplate.insertAll(batch);
                    System.out.println("Inserted batch of size: " + batch.size());
                    batch.clear(); // Clear the batch after insertion
                } catch (Exception e) {
                    System.err.println("Error inserting batch: " + e.getMessage());
                }
            }
        }

        System.out.println("All data inserted successfully!");
    }

    private SaveTicketBarcodeDetailsRequest generateRandomData() {
        SaveTicketBarcodeDetailsRequest barcodeRequest = new SaveTicketBarcodeDetailsRequest();

        // Generate random data
        String[] barcodes = {
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        };

        Integer totalSeat = ThreadLocalRandom.current().nextInt(1, 51); // Random seats between 1 and 50
        Integer totalAdultQuantity = ThreadLocalRandom.current().nextInt(1, 11); // Random adults between 1 and 10
        Integer totalChildQuantity = ThreadLocalRandom.current().nextInt(0, 6); // Random children between 0 and 5

        barcodeRequest.setBookingMasterId(UUID.randomUUID().toString());
        barcodeRequest.setBusTicketBarcode((!Objects.isNull(totalSeat) && totalSeat >= 1) ? barcodes[0] : null);
        barcodeRequest.setTradeFairTicketBarcode(
                (!Objects.isNull(totalAdultQuantity) && totalAdultQuantity >= 1)
                        || (!Objects.isNull(totalChildQuantity) && totalChildQuantity >= 1)
                        ? barcodes[1]
                        : null
        );
        barcodeRequest.setBusTicketBarcodeValid(false);
        barcodeRequest.setTradeFairTicketBarcodeValid(false);
        barcodeRequest.setCompany_id(UUID.randomUUID().toString());
        barcodeRequest.setIsActive(ThreadLocalRandom.current().nextBoolean());
        barcodeRequest.setToken_user_id(UUID.randomUUID().toString());
        barcodeRequest.setBookingNumber("BOOK-" + ThreadLocalRandom.current().nextInt(100000, 999999));
        barcodeRequest.setBookingQrString(generateUniqueQRString(barcodeRequest.getBookingNumber()));
        barcodeRequest.setUserTicketGeneratorId(UUID.randomUUID().toString());

        return barcodeRequest;
    }

    private String generateUniqueQRString(String ticketNumber) {
        long timestamp = System.currentTimeMillis();
        String randomUUID = UUID.randomUUID().toString();
        return ticketNumber + "-" + timestamp + "-" + randomUUID;
    }
}

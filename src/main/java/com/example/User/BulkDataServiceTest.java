package com.example.User;

import com.example.User.dummes.BulkDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BulkDataServiceTest implements CommandLineRunner {

    @Autowired
    private BulkDataService bulkDataService;

    public static void main(String[] args) {
        SpringApplication.run(BulkDataServiceTest.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting bulk data insertion...");
        bulkDataService.saveRandomData(100000); // Save 1 lakh records
        System.out.println("Bulk data insertion completed.");
    }
}

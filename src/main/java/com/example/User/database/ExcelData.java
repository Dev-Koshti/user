package com.example.User.database;

import com.example.User.utils.Utils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "excel_data")
public class ExcelData {
    @Id
    private String id = Utils.generateUUID();
    private String field1;
    private double field2;
    private Instant createdDate;
}

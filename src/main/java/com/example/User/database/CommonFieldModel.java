package com.example.User.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommonFieldModel {


    @Field("is_active")
    @Builder.Default
    public Boolean isActive = true;

    @Field("created_date")
    @Builder.Default
    public Long createdDate = Instant.now().getEpochSecond();//System.currentTimeMillis() / 1000L;

    @Field("created_by")
    public String createdBy = null;

    @Field("updated_date")
    @Builder.Default
    public Long updatedDate = null;

    @Field("updated_by")
    public String updatedBy = null;
}
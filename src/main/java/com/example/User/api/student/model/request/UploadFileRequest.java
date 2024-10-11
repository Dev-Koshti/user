package com.example.User.api.student.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadFileRequest {

    @JsonProperty("file")
    private MultipartFile file;

    @JsonProperty("description")
    private String description;

    @JsonProperty("uploaded_by")
    private String uploadedBy;

    @JsonProperty("timestamp")
    private String timestamp;

    public Boolean checkBadRequest() {
        // Check if the file is null or empty
        return this.getFile() == null || this.getFile().isEmpty();
    }
}

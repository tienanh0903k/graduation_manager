package com.example.graduate.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseObject {
    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("data")
    private Object data;

    public static ResponseObject success(String message, Object data) {
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }

    public static ResponseObject error(String message, HttpStatus status) {
        return ResponseObject.builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }

}

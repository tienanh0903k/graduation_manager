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
public class ResponseObject<T> {
    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("data")
    private Object data;

    public static <T> ResponseObject<T> success(String message, T data) {
        return ResponseObject.<T>builder()
                .status(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseObject<T> error(String message, HttpStatus status) {
        return ResponseObject.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }

}

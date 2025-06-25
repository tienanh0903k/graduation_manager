package com.example.graduate.response;


public class Response<T> {

    private String message;
    private T data;
    private String status;

    // Constructor
    public Response(String message, T data) {
        this.message = message;
        this.data = data;
        this.status = "success";
    }

    // Getter and Setter methods
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Phương thức giúp dễ dàng tạo ra đối tượng Response
    public static <T> Response<T> success(String message, T data) {
        return new Response<>(message, data);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(message, null);
    }
}

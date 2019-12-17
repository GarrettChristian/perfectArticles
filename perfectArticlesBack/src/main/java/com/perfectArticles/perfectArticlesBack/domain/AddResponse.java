package com.perfectArticles.perfectArticlesBack.domain;

public class AddResponse {

    private StringBuilder message = new StringBuilder("Success");

    private Boolean success = true;

    public String getMessage() {
        return message.toString();
    }

    public AddResponse setMessage(String message) {
        this.message = new StringBuilder(message);
        return this;
    }

    public AddResponse updateMessage(String message) {
        this.message.append(", " + message);
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public AddResponse setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
}

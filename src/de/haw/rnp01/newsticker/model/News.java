package de.haw.rnp01.newsticker.model;

/**
 * Created by daniel on 21.03.2016.
 */
public class News {
    private String type;
    private String message;

    public News(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "News{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

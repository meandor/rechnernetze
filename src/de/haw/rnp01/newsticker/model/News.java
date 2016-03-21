package de.haw.rnp01.newsticker.model;

/**
 * Created by daniel on 21.03.2016.
 */
public class News {
    private String type;
    private String message;

    /**
     * Constructs a new News model
     * @param type the type of the news as String
     * @param message actual message of the news
     */
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

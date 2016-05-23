package de.haw.rnp.chat.model;

/**
 * This class represents a message. Every message has a content and a type.
 */
public class Message {
    private String type;
    private String content;

    /**
     * Constructs a new content model.
     * @param type the type of the news as String
     * @param content actual content of the news
     */
    public Message(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return type + ": " + content;
    }
}

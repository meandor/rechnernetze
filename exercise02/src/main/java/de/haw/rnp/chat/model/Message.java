package de.haw.rnp.chat.model;

/**
 * This class represents a message.
 */
public class Message {
    private String text;

    /**
     * Constructs a new content model.
     * @param text the type of the news as String
     */
    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String content) {
        this.text = content;
    }

    @Override
    public String toString() {
        return text;
    }
}

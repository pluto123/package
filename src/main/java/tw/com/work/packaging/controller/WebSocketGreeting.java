package tw.com.work.packaging.controller;

public class WebSocketGreeting {
    private String content;

    public WebSocketGreeting() {
    }

    public WebSocketGreeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

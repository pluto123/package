package tw.com.work.packaging.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public WebSocketGreeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new WebSocketGreeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
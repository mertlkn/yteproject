package yteproject.application.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import yteproject.application.dtos.Greeting;
import yteproject.application.dtos.HelloMessage;

@Controller
@RequiredArgsConstructor
public class WsController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting endPoint(HelloMessage helloMessage) {
        System.out.println("entered");

        return new Greeting(
                "Event Name: "+HtmlUtils.htmlEscape(helloMessage.getEventName()) +
                " Name: "+HtmlUtils.htmlEscape(helloMessage.getName()) +
                " Surname: "+HtmlUtils.htmlEscape(helloMessage.getSurname()) +
                " TC Kimlik No: "+HtmlUtils.htmlEscape(helloMessage.getTcKimlikNo())
        );
    }
}

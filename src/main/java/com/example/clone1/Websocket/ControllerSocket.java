package com.example.clone1.Websocket;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ControllerSocket {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greetting greeting(Messagerr message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Thread.sleep(1000); // simulated delay

        // Lấy IP đã lưu trong Interceptor
        String clientIp = (String) headerAccessor.getSessionAttributes().get("client-ip");

        // Lấy thời gian hiện tại
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Xây dựng dữ liệu trả về theo định dạng yêu cầu
        String formattedMessage = "ip:" + clientIp + ",Name:" + message.getName() + ",Content:" + message.getContent()
                + ",time:" + timestamp;

        return new Greetting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "! Your IP is: " + clientIp);
    }
}

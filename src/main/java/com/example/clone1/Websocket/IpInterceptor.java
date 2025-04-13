package com.example.clone1.Websocket;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.InetSocketAddress;
import java.util.Map;

public class IpInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(IpInterceptor.class);

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2,
            @Nullable Exception arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String ip = servletRequest.getServletRequest().getRemoteAddr();
            logger.info("Client IP: {}", ip);
            attributes.put("client-ip", ip); // Lưu IP vào session attributes
        }
        return true;
        // TODO Auto-generated method stub
    }
}

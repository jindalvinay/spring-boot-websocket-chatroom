package com.cfmonkey.udacity.chat.controller;

import com.alibaba.fastjson.JSON;
import com.cfmonkey.udacity.chat.model.Message;
import org.apache.tomcat.websocket.pojo.PojoMessageHandlerWholeBase;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
//@ServerEndpoint("/chat")
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static HashMap<String, String> users = new HashMap<>();

    private static void sendMessageToAll(String msg) {
        //TODO-DONE: add send message method.
        onlineSessions.forEach((id, session) -> {
            try{
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        //TODO: add on open connection.
        System.out.println("Inside onOpen sessionId : "+session.getId());

        //check if username already exists
        if(users.containsValue(username)){
            System.out.println("sessionId : "+ session.getId()+" username: " + username + " already exists.");
            onClose(session, "");

        } else {
            onlineSessions.put(session.getId(), session);
            users.put(session.getId(), username);
            sendMessageToAll(Message.strToJson("chatbot", username + " joined", "SPEAK", onlineSessions.size()));
        }
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //TODO-DONE: add send message.
        System.out.println("Inside onMessage sessionId : " + session.getId() + " json: " + jsonStr);
        Message message = JSON.parseObject(jsonStr, Message.class);
        sendMessageToAll(Message.strToJson(message.getUsername(), message.getMsg(), "SPEAK", onlineSessions.size()));
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        //TODO: add close connection.
        System.out.println("Inside onClose sessionId : "+session.getId());

        onlineSessions.remove(session.getId());
        users.remove(session.getId());
        if(username.length() > 0)
            sendMessageToAll(Message.strToJson("chatbot", username + " left", "SPEAK", onlineSessions.size()));
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Inside onError sessionId : "+session.getId());
        error.printStackTrace();
    }

}

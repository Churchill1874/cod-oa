package com.ent.codoa.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {

/*    @Autowired
    private PrivateChatService privateChatService;


    //广播发送功能 适用于系统公告 大厅消息等
    @MessageMapping("/system/notice") // 接收前端发送的消息（"/app/chat"）
    @SendTo("/topic/messages") // 广播到 "/topic/messages"
    public String handleChatMessage(String message) {
        log.info("收到消息: {}", message);
        JSONObject jsonObject = JSONUtil.parseObj(message);
        log.info("收到解析后的数据:{}", JSONUtil.toJsonStr(jsonObject));
        return "服务器返回: " + message;
    }


    //聊提室聊天发送功能


    //私聊发送功能
    @MessageMapping("/chat/private")
    public void sendPrivateMessage(@Payload PrivateChatSendReq req, Principal principal) {
        req.setSendAccount(principal.getName());
        log.info("{}发送了聊天私信:{}", req.getSendAccount(), req.getContent());

        PrivateChat privateChat = BeanUtil.toBean(req, PrivateChat.class);
        privateChatService.add(privateChat);
    }*/

}


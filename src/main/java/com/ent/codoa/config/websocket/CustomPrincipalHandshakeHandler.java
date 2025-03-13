package com.ent.codoa.config.websocket;


import com.ent.codoa.service.EhcacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
@Slf4j
public class CustomPrincipalHandshakeHandler extends DefaultHandshakeHandler {
    private final EhcacheService ehcacheService;

    public CustomPrincipalHandshakeHandler(EhcacheService ehcacheService) {
        this.ehcacheService = ehcacheService;
    }

/*    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 从 attributes 中取出 Token
        String token = (String) attributes.get("token-id");

        // 如果没有 token，可返回 null 或做其它处理
        if (token == null) {
            return null;
        }

        PlayerTokenResp playerTokenResp =  ehcacheService.playerTokenCache().get(token);
        if (playerTokenResp == null){
            return null;
        }

        // 用 token 构造自定义 Principal
        return new StompPrincipal(playerTokenResp.getAccount());
    }*/

}

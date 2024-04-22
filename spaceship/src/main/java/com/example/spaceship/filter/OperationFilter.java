package com.example.spaceship.filter;

import com.example.spaceship.core.IoC;
import com.example.spaceship.dto.UserContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Order(1)
public class OperationFilter implements Filter {
    private static final int GAME_ID_MATCHER_GROUP = 1;
    private static final int PLAYER_ID_MATCHING_GROUP = 2;
    private static final Pattern OPERATION_URI_PATTERN = Pattern.compile("/games/(\\d+)/players/([^/]+)/operations");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var requestUri = httpRequest.getRequestURI();
        var matcher = OPERATION_URI_PATTERN.matcher(requestUri);
        if (matcher.find()) {
            //user context is taken from the scope of the user
            var userContext = IoC.<UserContext>resolve("UserContext");
            var gameId = matcher.group(GAME_ID_MATCHER_GROUP);
            var playerId = matcher.group(PLAYER_ID_MATCHING_GROUP);

            if (userContextNotMatching(userContext, gameId, playerId)) {
                var httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter()
                        .write("user=%s don't have access to perform operation for game =%s".formatted(userContext.username(), gameId));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static boolean userContextNotMatching(UserContext userContext, String gameId, String playerId) {
        return !userContext.gameId().equals(gameId) || !playerId.equals(userContext.username());
    }
}

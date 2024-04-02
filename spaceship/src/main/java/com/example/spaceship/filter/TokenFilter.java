package com.example.spaceship.filter;

import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.dto.UserContext;
import com.example.spaceship.service.AccessTokenService;
import com.example.spaceship.utils.TokenUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TokenFilter implements Filter {
    private final AccessTokenService accessTokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        var authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            var decodedToken = accessTokenService.verify(TokenUtils.withoutBearer(authorizationHeader));
            var userContext = accessTokenService.getPayload(decodedToken.getToken(), UserContext.class);
            IoC.<RegisterDependencyCommand>resolve("IoC.Register", "UserContext",
                    (Function<Object[], Object>) args -> userContext).execute();

        } catch (Exception exception) {
            log.warn(exception.getMessage());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("incorrect authorization header");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

package com.example.spaceship.filter;

import com.example.spaceship.IoCAdapterInterceptor;
import com.example.spaceship.core.IoC;
import com.example.spaceship.dto.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationFilterTest {
    private static final String MATCHING_URL = "/games/1/players/username/operations";
    private static final String MATCHING_GAME_ID = "1";
    private static final String MATCHING_PLAYER_ID = "username";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private OperationFilter operationFilter;

    @Test
    @SneakyThrows
    void shouldGoFurtherWhenNotMatching() {
        when(request.getRequestURI()).thenReturn("/not/this/path");

        operationFilter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @SneakyThrows
    void shouldGoFurtherWhenMatches() {
        when(request.getRequestURI()).thenReturn(MATCHING_URL);
        var matchingUserContext = new UserContext(MATCHING_PLAYER_ID, MATCHING_GAME_ID);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.<UserContext>resolve("UserContext")).thenReturn(matchingUserContext);
            operationFilter.doFilter(request, response, filterChain);
        }

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @SneakyThrows
    void shouldRespondWhenUsernameNotMatching() {
        when(request.getRequestURI()).thenReturn(MATCHING_URL);
        var notMatchingUsername = new UserContext("not_matching_username", MATCHING_GAME_ID);
        var writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.<UserContext>resolve("UserContext")).thenReturn(notMatchingUsername);
            operationFilter.doFilter(request, response, filterChain);
        }
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer)
                .write("user=%s don't have access to perform operation for game =%s".formatted(notMatchingUsername.username(), MATCHING_GAME_ID));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @SneakyThrows
    void shouldRespondWhenGameNotMatching() {
        when(request.getRequestURI()).thenReturn(MATCHING_URL);
        var notMatchingGameId = new UserContext(MATCHING_PLAYER_ID, "not_matching_game_id");
        var writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.<UserContext>resolve("UserContext")).thenReturn(notMatchingGameId);
            operationFilter.doFilter(request, response, filterChain);
        }
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(writer)
                .write("user=%s don't have access to perform operation for game =%s".formatted(MATCHING_PLAYER_ID, MATCHING_GAME_ID));
        verify(filterChain).doFilter(request, response);
    }

}

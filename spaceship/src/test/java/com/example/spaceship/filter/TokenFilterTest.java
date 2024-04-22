package com.example.spaceship.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.spaceship.command.ioc.RegisterDependencyCommand;
import com.example.spaceship.command.scope.SetCurrentScopeCommand;
import com.example.spaceship.core.IoC;
import com.example.spaceship.dto.UserContext;
import com.example.spaceship.model.core.Scope;
import com.example.spaceship.service.AccessTokenService;
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
import org.springframework.http.HttpHeaders;

import java.io.PrintWriter;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenFilterTest {
    @Mock
    private AccessTokenService accessTokenService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private TokenFilter tokenFilter;

    @Test
    @SneakyThrows
    void shouldReturnUnAuthorizedWhenNotVerified() {
        var unverifiedBearerToken = "Bearer unverified";
        var unverifiedToken = unverifiedBearerToken.replaceFirst("Bearer ", "");
        var writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(unverifiedBearerToken);
        when(accessTokenService.verify(unverifiedToken)).thenThrow(RuntimeException.class);

        tokenFilter.doFilter(request, response, filterChain);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("incorrect authorization header");
        verifyNoMoreInteractions(filterChain);
    }

    @Test
    @SneakyThrows
    void shouldSetContextWhenVerified() {
        var bearerToken = "Bearer verified";
        var verifiedToken = "verified";
        var decodedJwt = Mockito.mock(DecodedJWT.class);
        var registerCommand = Mockito.mock(RegisterDependencyCommand.class);
        var scopeRegisterCommand = Mockito.mock(SetCurrentScopeCommand.class);
        var userContext = new UserContext("username", "gameId");
        var scopeId = userContext.username() + ":" + userContext.gameId();
        var emptyScope = new Scope(scopeId, Map.of());

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(bearerToken);
        when(accessTokenService.verify(verifiedToken)).thenReturn(decodedJwt);
        when(decodedJwt.getToken()).thenReturn(verifiedToken);
        when(accessTokenService.getPayload(decodedJwt.getToken(), UserContext.class)).thenReturn(userContext);

        try (MockedStatic<IoC> ioC = mockStatic(IoC.class)) {
            ioC.when(() -> IoC.resolve(eq("IoC.Register"), eq("UserContext"), isA(Function.class))).thenReturn(registerCommand);
            ioC.when(() -> IoC.resolve("IoC.Scope.Create.Empty", scopeId)).thenReturn(emptyScope);
            ioC.when(() -> IoC.resolve("IoC.Scope.Current.Set", emptyScope)).thenReturn(scopeRegisterCommand);
            tokenFilter.doFilter(request, response, filterChain);
            verify(scopeRegisterCommand).execute();
        }

        verify(filterChain).doFilter(request, response);
        verify(registerCommand).execute();
    }
}

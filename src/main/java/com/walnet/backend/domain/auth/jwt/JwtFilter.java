package com.walnet.backend.domain.auth.jwt;

import com.walnet.backend.global.exception.AccessTokenExpiredException;
import com.walnet.backend.global.exception.BadRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String email = jwtProvider.extractEmailFromAccessToken(token);

                // 인증 객체 생성 및 SecurityContext 설정
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (AccessTokenExpiredException | BadRequestException e) {
                setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "ACCESS_TOKEN_ERROR", e.getMessage());
                return; // 이후 필터 중단 (컨트롤러로 안 넘어감)
            } catch (Exception e) {
                setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED", "유효하지 않은 토큰입니다.");
                return;
            }
        }

        filterChain.doFilter(request, response); // 토큰 없거나 정상 인증된 경우
    }

    private void setErrorResponse(HttpServletResponse response, int status, String title, String detail) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format("""
            {
              "type": "about:blank",
              "title": "%s",
              "status": %d,
              "detail": "%s"
            }
        """, title, status, detail);

        response.getWriter().write(json);
    }
}
package by.slotify.core.security;

import by.slotify.core.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String username = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";
        String method = request.getMethod();
        String uri = request.getRequestURI();
        
        log.warn("Access denied for user: {} to {} {}", username, method, uri);

        // Определяем требуемую роль на основе URL и метода
        String requiredRole = determineRequiredRole(uri, method);
        String message = "У вас нет прав доступа для выполнения данной операции.";
        if (requiredRole != null) {
            message += " Требуется роль: " + requiredRole + ".";
        } else {
            message += " Обратитесь к администратору для получения необходимых прав доступа.";
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpServletResponse.SC_FORBIDDEN)
                .error("Access Denied")
                .message(message)
                .path(uri)
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private String determineRequiredRole(String uri, String method) {
        // Административные эндпоинты
        if (uri.contains("/api/admin/")) {
            return "ADMIN";
        }
        
        // Пользовательские эндпоинты
        if (uri.contains("/api/user/")) {
            return "USER";
        }
        
        // Операции создания/обновления/удаления требуют роль ADMIN
        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
            if (uri.contains("/api/meetings") || 
                uri.contains("/api/time-slots") ||
                uri.contains("/api/meeting-types") ||
                uri.contains("/api/locations") ||
                uri.contains("/api/participation-types")) {
                return "ADMIN";
            }
        }
        
        // Создание заявки требует роль USER
        if (method.equals("POST") && uri.contains("/api/requests")) {
            return "USER";
        }
        
        return null;
    }
}


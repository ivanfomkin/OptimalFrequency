package ru.nntu.its.at.ifomkin.optimalfrequency.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@UtilityClass
public class LogUtil {
    public String getUserIP() {
        HttpServletRequest request = getRequest();
        return request.getRemoteAddr();
    }

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes(), "Запрос не может равняться null"))
                .getRequest();
    }
}

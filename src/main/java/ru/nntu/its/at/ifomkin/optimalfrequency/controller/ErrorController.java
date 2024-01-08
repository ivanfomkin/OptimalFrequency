package ru.nntu.its.at.ifomkin.optimalfrequency.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.OptimalMileageRequest;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.AcceptableLevelsOfFailureFreeOperationUtil;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public String errorMessage(Exception exception, Model model) {
        model.addAttribute("acceptableLevels", AcceptableLevelsOfFailureFreeOperationUtil.ACCEPTABLE_VALUES);
        model.addAttribute("errorMessage", exception.getMessage());
        model.addAttribute("request", new OptimalMileageRequest());
        return "index";
    }
}

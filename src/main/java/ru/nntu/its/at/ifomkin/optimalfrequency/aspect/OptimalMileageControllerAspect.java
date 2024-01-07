package ru.nntu.its.at.ifomkin.optimalfrequency.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.OptimalMileageRequest;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.FileUtil;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.LogUtil;

@Slf4j
@Aspect
@Component
public class OptimalMileageControllerAspect {
    @Pointcut(value = "execution(* ru.nntu.its.at.ifomkin.optimalfrequency.controller.OptimalMileageController.calculateOptimalMileage(ru.nntu.its.at.ifomkin.optimalfrequency.model.OptimalMileageRequest, org.springframework.ui.Model)) && args(request, model)", argNames = "request, model")
    public void calculateOptimalMileagePointcut(OptimalMileageRequest request, Model model) {
    }

    @Before(value = "calculateOptimalMileagePointcut(request, model)", argNames = "request,model")
    public void beforeGenerateData(OptimalMileageRequest request, Model model) {
        log.info("Запрос на вычисление оптимальной периодичности с IP {}. Запрос: {}. ", LogUtil.getUserIP(), request);
        log.debug("Содержимое файла: {}", FileUtil.getMultipartStringContent(request.getFile()));
    }
}

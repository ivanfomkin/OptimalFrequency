package ru.nntu.its.at.ifomkin.optimalfrequency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Dataset;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.OptimalMileageRequest;
import ru.nntu.its.at.ifomkin.optimalfrequency.service.InputFileService;
import ru.nntu.its.at.ifomkin.optimalfrequency.util.AcceptableLevelsOfFailureFreeOperationUtils;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OptimalMileageController {
    private final InputFileService inputFileService;

    @ModelAttribute("acceptableLevels")
    public List<Double> acceptableLevels() {
        return AcceptableLevelsOfFailureFreeOperationUtils.ACCEPTABLE_VALUES;
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("request", new OptimalMileageRequest());
        return "index";
    }

    @PostMapping
    public String calculateOptimalMileage(OptimalMileageRequest request, Model model) {
        Dataset dataset = inputFileService.handleInputFile(request);
        model.addAttribute("response", dataset);
        model.addAttribute("acceptableLevel", request.getAcceptableLevelOfFailureFreeOperation());
        return "result";
    }
}

package ru.nntu.its.at.ifomkin.optimalfrequency.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nntu.its.at.ifomkin.optimalfrequency.factory.DataFactory;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.Dataset;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.OptimalMileageRequest;
import ru.nntu.its.at.ifomkin.optimalfrequency.model.filedata.InputData;

@Service
@RequiredArgsConstructor
public class InputFileService {
    private final DatasetService datasetService;

    public Dataset handleInputFile(OptimalMileageRequest request) {
        InputData inputData = DataFactory.getData(request.getFile());
        double[] inputArray = inputData.getData();
        return datasetService.calculateOptimalMileage(inputArray, request.getAcceptableLevelOfFailureFreeOperation(), request.getSignificanceLevel());
    }
}

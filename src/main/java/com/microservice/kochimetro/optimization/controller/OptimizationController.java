package com.microservice.kochimetro.optimization.controller;

import com.microservice.kochimetro.depot.entity.DepotResource;
import com.microservice.kochimetro.depot.repository.DepotResourceRepository;
import com.microservice.kochimetro.optimization.dto.request.OptimizationRequest;
import com.microservice.kochimetro.optimization.dto.response.OptimizationResponse;
import com.microservice.kochimetro.optimization.model.TrainData;
import com.microservice.kochimetro.optimization.orTools.solver.BasicTrainSelectionSolver;
import com.microservice.kochimetro.optimization.service.TrainDataBuilder;
import com.microservice.kochimetro.train.entity.enums.Depot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/optimization")
@RequiredArgsConstructor
public class OptimizationController {

    private final TrainDataBuilder trainDataBuilder;
    private final DepotResourceRepository depotResourceRepository;
    private final BasicTrainSelectionSolver solver;

    @PostMapping("/run")
    public OptimizationResponse optimize(
            @RequestBody OptimizationRequest request
    ) {

        List<TrainData> trainData =
                trainDataBuilder.buildContext();

        Map<Depot, DepotResource> depotResourceMap =
                depotResourceRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(
                                DepotResource::getDepot,
                                Function.identity()
                        ));

        return solver.solve(
                request,
                trainData,
                depotResourceMap
        );
    }
}

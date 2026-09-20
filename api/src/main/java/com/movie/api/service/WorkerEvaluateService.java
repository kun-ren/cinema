package com.movie.api.service;

import com.movie.api.model.entity.WorkerEvaluate;

import java.util.List;

public interface WorkerEvaluateService {

    void save(WorkerEvaluate workerEvaluate);

    void deleteById(String id);

    //Delete all reviews for this support worker
    void deleteAllByWid(String wid);

    //List all reviews by support worker ID
    List<WorkerEvaluate> findByWorkerId(String wid);

}

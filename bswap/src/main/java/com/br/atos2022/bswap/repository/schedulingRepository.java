package com.br.atos2022.bswap.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.atos2022.bswap.model.schedulingModel;

@Repository
public interface schedulingRepository extends CrudRepository<schedulingModel,Integer> {
    

    List<schedulingModel>findByDateGreaterThanEqualAndDateLessThanEqual(Timestamp startDate, Timestamp endDate);
    List<schedulingModel>findAll();

    Optional<schedulingModel>findBySchedID(Integer schedID);
}

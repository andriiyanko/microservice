package com.example.andy.configurationservice.persistence.dao.repositories;

import com.example.andy.configurationservice.persistence.model.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ConfigurationRepository extends CrudRepository<Configuration, Integer> {
    Optional<Configuration> findConfigurationBySerialNumber(String serialNumber);
}

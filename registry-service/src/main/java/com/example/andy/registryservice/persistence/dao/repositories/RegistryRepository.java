package com.example.andy.registryservice.persistence.dao.repositories;

import com.example.andy.registryservice.persistence.model.Registry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RegistryRepository extends CrudRepository<Registry, Integer> {
    List<Registry> findRegistryByVendorContainingIgnoreCase(String vendor);
    List<Registry> findRegistryByModelContainingIgnoreCase(String model);
}

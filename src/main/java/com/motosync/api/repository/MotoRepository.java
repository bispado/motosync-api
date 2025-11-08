package com.motosync.api.repository;

import com.motosync.api.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {

    Optional<Moto> findByPlaca(String placa);

    Optional<Moto> findByChassi(String chassi);
}


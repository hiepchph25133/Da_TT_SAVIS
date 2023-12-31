package com.example.demo.repository;

import com.example.demo.entity.CoAo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CoAoRepository extends JpaRepository<CoAo, UUID> {
    CoAo getCoAoById(UUID id);
}

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Journey;

@Repository
public interface IJourneyRepository extends JpaRepository<Journey, Long> {

}

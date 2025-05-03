package com.uhdi_apa.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uhdi_apa.hospital.model.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}

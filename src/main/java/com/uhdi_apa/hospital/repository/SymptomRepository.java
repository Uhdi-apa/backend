package com.uhdi_apa.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uhdi_apa.hospital.model.Hospital;
import com.uhdi_apa.hospital.model.Symptom;
import com.uhdi_apa.hospital.model.SymptomDepartment;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
	Optional<Symptom> findByKeyword(String keyword);
}

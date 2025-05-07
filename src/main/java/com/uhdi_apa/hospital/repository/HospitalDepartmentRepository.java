package com.uhdi_apa.hospital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uhdi_apa.hospital.model.Department;
import com.uhdi_apa.hospital.model.Hospital;
import com.uhdi_apa.hospital.model.HospitalDepartment;

@Repository
public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
	Optional<List<HospitalDepartment>> findByDepartmentIn(List<Department> departments);
}

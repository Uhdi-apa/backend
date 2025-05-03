package com.uhdi_apa.hospital.model;

import java.util.ArrayList;
import java.util.List;

import com.uhdi_apa.base.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@Table(name = "Symptoms")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Symptom extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String keyword;

	@OneToMany(mappedBy = "symptom")
	private List<SymptomDepartment> symptomDepartments = new ArrayList<>();
}

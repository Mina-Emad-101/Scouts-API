package com.scouts.app.RepoModels;

import java.util.Date;
import java.util.List;

import com.scouts.app.Models.User.Sector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RepoAttendance
 */
@Entity
@Table(name = "attendances", uniqueConstraints = @UniqueConstraint(columnNames = {"created_at", "sector"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepoAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Enumerated(EnumType.STRING)
	private Sector sector;

	// @OneToMany(mappedBy = "attendance")
	// private List<RepoUserAttendance> userAttendances;
}

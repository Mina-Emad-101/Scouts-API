package com.scouts.app.RepoModels;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RepoUserAttendance
 */
@Entity
@Table(name = "user_attendance", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "attendance_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepoUserAttendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
	private RepoUser user;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "attendance_id")
	private RepoAttendance attendance;
}

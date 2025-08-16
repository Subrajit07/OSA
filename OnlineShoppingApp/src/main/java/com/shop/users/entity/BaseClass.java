package com.shop.users.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseClass {
	
	@CreationTimestamp
	private OffsetDateTime createdAt;
	
	@UpdateTimestamp
	private OffsetDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Users createdBy;
	
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private Users updatedBy;
}

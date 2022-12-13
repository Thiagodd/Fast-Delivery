package com.fastdelivery.fastdelivery.domain.repository;

import com.fastdelivery.fastdelivery.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
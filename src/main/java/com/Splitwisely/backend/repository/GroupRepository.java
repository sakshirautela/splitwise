package com.Splitwisely.backend.repository;

import com.Splitwisely.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}

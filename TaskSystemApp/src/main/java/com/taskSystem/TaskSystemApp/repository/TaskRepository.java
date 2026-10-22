package com.taskSystem.TaskSystemApp.repository;

import com.taskSystem.TaskSystemApp.model.Task;

import com.taskSystem.TaskSystemApp.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("""
select t from Task t where t.user =:users AND t.isDeleted=false 
""")
    Page<Task> findActiveTasksByUser(@Param("users") Users user, Pageable pageable);

    @Query("""
select t from Task t where t.id=:id AND t.user=:user AND t.isDeleted=false 
""")
    Optional<Task> findActiveTaskByIdAndUser(@Param("id") Long id, @Param("users")Users user);
}

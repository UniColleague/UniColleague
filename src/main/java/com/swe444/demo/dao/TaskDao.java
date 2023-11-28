package com.swe444.demo.dao;

import com.swe444.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskDao extends JpaRepository<Task, Integer> {

}

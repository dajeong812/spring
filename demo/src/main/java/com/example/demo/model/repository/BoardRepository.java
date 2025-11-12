package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Board; // Board로 변경

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 기본 CRUD 메서드(findAll, findById, save, deleteById 등)는 JpaRepository가 자동 제공
}

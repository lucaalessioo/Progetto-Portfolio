package com.makeup.portfolio.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.makeup.portfolio.model.Works;

@Repository
public interface WorksRepository extends JpaRepository<Works,Long>{
    
}

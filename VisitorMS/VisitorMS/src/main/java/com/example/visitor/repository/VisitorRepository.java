package com.example.visitor.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.visitor.entity.Visitor;

public interface VisitorRepository extends CrudRepository<Visitor,Long>{
    
}

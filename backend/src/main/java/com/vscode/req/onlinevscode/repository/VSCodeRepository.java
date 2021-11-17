package com.vscode.req.onlinevscode.repository;

import com.vscode.req.onlinevscode.model.VSCodeModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VSCodeRepository extends JpaRepository< VSCodeModel, Long >{
    
}

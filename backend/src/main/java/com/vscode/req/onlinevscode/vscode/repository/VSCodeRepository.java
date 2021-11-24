package com.vscode.req.onlinevscode.vscode.repository;

import com.vscode.req.onlinevscode.vscode.model.VSCodeModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VSCodeRepository extends JpaRepository< VSCodeModel, Long >{
    
}

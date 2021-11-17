package com.vscode.req.onlinevscode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vscode_tb")
public class VSCodeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;        // VSCode ID
    String tkn;     // 인증 token
    String URL;     // 접속 URL
}

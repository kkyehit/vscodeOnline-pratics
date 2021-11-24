package com.vscode.req.onlinevscode.vscode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vscode_tb")
@Getter
@Setter
public class VSCodeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;        // VSCode ID
    String tkn;     // 인증 token
    String URL;     // 접속 URL
    Long userId;    // 사용자 ID
}

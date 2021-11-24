package com.vscode.req.onlinevscode.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "user_tb", 
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"user_id"} // 사용자 로그인 ID는 고유값
        )
    })
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;                // 사용자 ID
    @Column(name="user_id")
    String userId;         // 사용자 로그인 ID
    @Column(name="user_pw")
    String password;        // 사용자 비밀번호
}

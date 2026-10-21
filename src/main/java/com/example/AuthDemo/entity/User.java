package com.example.AuthDemo.entity;
import com.example.AuthDemo.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table (name = "tb_user")
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
@Entity 

public class User {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true, length = 255)
    private String email;

    @Column (name ="password_hash", nullable = false, length = 100)
    private String password;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Role role = Role.USER;
}
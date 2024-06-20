package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    private Long dni;

    private String nombre;

    private String apellido;

    @Column(unique = true, nullable = false)
    private String correo;

    private String password;

}

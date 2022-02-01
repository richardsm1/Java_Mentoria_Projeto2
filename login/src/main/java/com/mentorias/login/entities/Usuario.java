package com.mentorias.login.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é necessário")
    @Size(min = 3, max = 99, message = "Nome deve ter de 3 a 99 caracteres")
    private String usuario;

    @NotBlank
    private String senha;

    @NotBlank
    private String funcao;
}

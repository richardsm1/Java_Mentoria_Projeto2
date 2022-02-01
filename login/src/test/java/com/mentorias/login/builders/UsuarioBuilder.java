package com.mentorias.login.builders;

import com.mentorias.login.entities.Usuario;
import com.mentorias.login.enums.Funcoes;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioBuilder {

    private Usuario usuario;

    private UsuarioBuilder() {
    }

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder usuarioBuilder = new UsuarioBuilder();
        usuarioBuilder.usuario = new Usuario();
        usuarioBuilder.usuario.setId(1L);
        usuarioBuilder.usuario.setUsuario("Usuario1");
        usuarioBuilder.usuario.setFuncao(Funcoes.FUNCIONARIO.getFuncao());
        return usuarioBuilder;
    }

    public UsuarioBuilder comId(Long id) {
        this.usuario.setId(id);
        return this;
    }

    public UsuarioBuilder comNome(String nome) {
        this.usuario.setUsuario(nome);
        return this;
    }

    public UsuarioBuilder comSenha(String senha) {
        this.usuario.setSenha(BCrypt.hashpw(senha, BCrypt.gensalt(12)));
        return this;
    }

    public UsuarioBuilder comFuncao(String funcao) {
        this.usuario.setFuncao(funcao);
        return this;
    }

    public Usuario build() {
        return this.usuario;
    }


}

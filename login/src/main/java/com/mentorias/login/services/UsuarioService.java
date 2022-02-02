package com.mentorias.login.services;

import com.mentorias.login.entities.Usuario;
import com.mentorias.login.enums.Funcoes;
import com.mentorias.login.exceptions.*;
import com.mentorias.login.repositories.UsuarioRepositorie;
import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepositorie usuarioRepositorie;
    Set<String> conjuntoFuncoes = new HashSet<>(Arrays.asList("admin", "funcionario"));

    @Autowired
    public UsuarioService(UsuarioRepositorie usuarioRepositorie1) {
        this.usuarioRepositorie = usuarioRepositorie1;
    }

    public Usuario visualizarUsuarioPorId(Long id, HttpServletRequest request) {
        Usuario usuario = obterUsuarioSolicitante(request);

        if (!verificarSeUsuarioLogadoEhAdmin(usuario)) {
            throw new UsuarioSemPermissaoException("Usuário não possui esse nível de acesso");
        }
        return buscarPorId(id);
    }

    public Usuario visualizarUsuarioPorNome(String nome, HttpServletRequest request) {
        Usuario usuario = obterUsuarioSolicitante(request);

        if (!verificarSeUsuarioLogadoEhAdmin(usuario)) {
            throw new UsuarioSemPermissaoException("Usuário não possui esse nível de acesso");
        }
        return buscarPorNomeDeUsuario(nome);
    }


    public Usuario cadastrar(Usuario usuario, HttpServletRequest request) {
        if (usuarioRepositorie.findByUsuario(usuario.getUsuario()).isPresent()) {
            throw new UsuarioJaExisteException("Usuario com nome informado já foi cadastrado");
        }

        if (!conjuntoFuncoes.contains(usuario.getFuncao())) {
            throw new FuncaoInvalidaException("Função inválida");
        }

        if (usuario.getFuncao().equals(Funcoes.ADMIN.getFuncao())) {
            Usuario usuarioSolicitante = obterUsuarioSolicitante(request);
            if (!verificarSeUsuarioLogadoEhAdmin(usuarioSolicitante)) {
                throw new UsuarioSemPermissaoException("Usuário não possui esse nível de acesso");
            }
        }

        usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt(12)));
        return usuarioRepositorie.save(usuario);
    }

    public Usuario atualizar(Usuario usuario, HttpServletRequest request) {
        Usuario usuarioSolicitante = obterUsuarioSolicitante(request);

        if (!verificarSeUsuarioLogadoEhAdmin(usuarioSolicitante)) {
            throw new UsuarioSemPermissaoException("Usuário não possui esse nível de acesso");
        }

        Usuario usuarioAtualizar = buscarPorId(usuario.getId());
        usuarioAtualizar.setUsuario(usuario.getUsuario());
        usuarioAtualizar.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt(12)));
        usuarioAtualizar.setFuncao(usuario.getFuncao());

        return usuarioRepositorie.save(usuarioAtualizar);
    }

    public String remover(Long id, HttpServletRequest request) {
        Usuario usuarioSolicitante = obterUsuarioSolicitante(request);

        if (!verificarSeUsuarioLogadoEhAdmin(usuarioSolicitante)) {
            throw new UsuarioSemPermissaoException("Usuário não possui esse nível de acesso");
        }
        usuarioRepositorie.deleteById(id);
        return "Usuário removido com sucesso";
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepositorie.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario com id " + id + " nao encontrado"));
    }

    public Usuario buscarPorNomeDeUsuario(String usuario) {
        return usuarioRepositorie.findByUsuario(usuario).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario com nome " + usuario + " nao encontrado"));
    }

    public Usuario fazerLogin(String usuario, String senha) {
        Usuario usuarioSalvo = usuarioRepositorie.findByUsuario(usuario).orElseThrow(() -> new AuthenticationFailedException("Falha na autenticação!"));

        if (BCrypt.checkpw(senha, usuarioSalvo.getSenha())) {
            return usuarioSalvo;
        } else throw new CredenciaisInvalidasException("Senha incorreta");
    }

    public boolean verificarSeUsuarioLogadoEhAdmin(Usuario usuario) {
        return usuario.getFuncao().equals(Funcoes.ADMIN.getFuncao());
    }

    public String decodificarCredenciais(HttpServletRequest request) {
        String credenciais = request.getHeader("Authorization");

        if (credenciais == null || !credenciais.contains("Basic ")) {
            throw new CredenciaisInvalidasException("Credenciais invalidas");
        }
        return new String(Base64.decodeBase64(credenciais.substring(6)));
    }

    public String obterNomeUsuarioSolicitante(String credenciais) {
        return credenciais.substring(0, credenciais.indexOf(":"));
    }

    public String obterSenhaUsuarioSolicitante(String credenciais) {
        return credenciais.substring(credenciais.indexOf(":") + 1);
    }

    public Usuario obterUsuarioSolicitante(HttpServletRequest request) {
        String credenciais = decodificarCredenciais(request);
        return fazerLogin(obterNomeUsuarioSolicitante(credenciais), obterSenhaUsuarioSolicitante(credenciais));
    }
}

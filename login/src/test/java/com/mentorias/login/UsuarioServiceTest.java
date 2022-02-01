package com.mentorias.login;

import com.mentorias.login.builders.UsuarioBuilder;
import com.mentorias.login.entities.Usuario;
import com.mentorias.login.enums.Funcoes;
import com.mentorias.login.repositories.UsuarioRepositorie;
import com.mentorias.login.services.UsuarioService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepositorie usuarioRepositorie;

    @Mock
    private HttpServletRequest request;


    @Test
    public void verificarSeUsuarioLogadoEhAdminTest() {
        Usuario usuario = new Usuario(1L, "jao", "123", "admin");
        Assert.assertTrue(usuarioService.verificarSeUsuarioLogadoEhAdmin(usuario));
    }

    @Test
    public void buscarPorIdTest() {
        Usuario usuario = new Usuario(1L, "Usuario1", "123", "admin");
        when(usuarioRepositorie.findById(anyLong())).thenReturn(Optional.of(usuario));
        usuarioService.buscarPorId(1L);
        verify(usuarioRepositorie).findById(1L);
    }


    @Test
    public void buscarPorNomeDeUsuarioTest() {
        String nomeUsuario = "Usuario1";
        Usuario usuario = UsuarioBuilder.umUsuario().comNome(nomeUsuario).build();
        when(usuarioRepositorie.findByUsuario(anyString())).thenReturn(Optional.of(usuario));
        usuarioService.buscarPorNomeDeUsuario(nomeUsuario);
        verify(usuarioRepositorie).findByUsuario(nomeUsuario);
    }

    @Test
    public void fazerLoginTest() {
        String nomeUsuario = "Usuario1";
        Usuario usuario = UsuarioBuilder.umUsuario().comSenha("123").build();
        when(usuarioRepositorie.findByUsuario(nomeUsuario)).thenReturn(Optional.of(usuario));
        Usuario usuarioLogado = usuarioService.fazerLogin(nomeUsuario, "123");
        Assert.assertEquals(usuario, usuarioLogado);
    }


    @Test
    public void decodificarCredenciaisTest() {
        String nomeUsuario = "Usuario1";
        String senhaUsuario = "12345";
        String credencialDecodificada = nomeUsuario + ":" + senhaUsuario;
        String credencialCodificada = Base64.getEncoder().encodeToString((nomeUsuario + ":" + senhaUsuario).getBytes());
        when(request.getHeader("Authorization")).thenReturn("Basic " + credencialCodificada);
        String saida = usuarioService.decodificarCredenciais(request);
        Assert.assertEquals(credencialDecodificada, saida);
    }

    @Test
    public void obterNomeUsuarioSolicitanteTest() {
        String nomeUsuario = "Usuario1";
        String senhaUsuario = "12345";
        String credenciais = nomeUsuario + ":" + senhaUsuario;
        String saida = usuarioService.obterNomeUsuarioSolicitante(credenciais);
        Assert.assertEquals(nomeUsuario, saida);
    }

    @Test
    public void obterSenhaUsuarioSolicitanteTest() {
        String nomeUsuario = "Usuario1";
        String senhaUsuario = "12345";
        String credenciais = nomeUsuario + ":" + senhaUsuario;
        String saida = usuarioService.obterSenhaUsuarioSolicitante(credenciais);
        Assert.assertEquals(senhaUsuario, saida);
    }

    @Test
    public void obterUsuarioSolicitanteTest() {
        String nomeUsuario = "Usuario1";
        String senhaUsuario = "12345";
        String credencialCodificada = Base64.getEncoder().encodeToString((nomeUsuario + ":" + senhaUsuario).getBytes());
        Usuario usuario = UsuarioBuilder.umUsuario().comNome(nomeUsuario).comSenha(senhaUsuario).build();

        when(request.getHeader("Authorization")).thenReturn("Basic " + credencialCodificada);
        when(usuarioRepositorie.findByUsuario(nomeUsuario)).thenReturn(Optional.of(usuario));

        Usuario usuarioSolicitante = usuarioService.obterUsuarioSolicitante(request);
        Assert.assertEquals(usuario, usuarioSolicitante);
    }

    @Test
    public void removerTest() {
        String nomeUsuario = "Usuario1";
        String senhaUsuario = "12345";
        String credencialCodificada = Base64.getEncoder().encodeToString((nomeUsuario + ":" + senhaUsuario).getBytes());
        Usuario usuario = UsuarioBuilder.umUsuario()
                .comNome(nomeUsuario)
                .comSenha(senhaUsuario)
                .comFuncao(Funcoes.ADMIN.getFuncao())
                .build();

        when(request.getHeader("Authorization")).thenReturn("Basic " + credencialCodificada);
        when(usuarioRepositorie.findByUsuario(nomeUsuario)).thenReturn(Optional.of(usuario));

        String saida = usuarioService.remover(1L, request);
        Assert.assertEquals("Usuário removido com sucesso", saida);
    }

    @Test
    public void atualizarTest() {
        Long idUsuarioSolicitante = 1L;
        String nomeUsuarioSolicitante = "Usuario1";
        String senhaUsuarioSolicitante = "12345";
        String credencialCodificadaSolicitante = Base64
                .getEncoder()
                .encodeToString((nomeUsuarioSolicitante + ":" + senhaUsuarioSolicitante).getBytes());
        Long idUsuarioAtualizado = 2L;
        String nomeUsuarioAtualizado = "Usuario2";
        String senhaUsuarioAtualizado = "54321";

        Usuario usuarioSolicitante = UsuarioBuilder.umUsuario()
                .comId(idUsuarioSolicitante)
                .comNome(nomeUsuarioSolicitante)
                .comSenha(senhaUsuarioSolicitante)
                .comFuncao(Funcoes.ADMIN.getFuncao())
                .build();
        Usuario usuarioAtualizado = UsuarioBuilder.umUsuario()
                .comId(idUsuarioAtualizado)
                .comNome(nomeUsuarioAtualizado)
                .comSenha(senhaUsuarioAtualizado)
                .comFuncao(Funcoes.FUNCIONARIO.getFuncao())
                .build();

        when(request.getHeader("Authorization")).thenReturn("Basic " + credencialCodificadaSolicitante);
        when(usuarioRepositorie.findById(idUsuarioAtualizado)).thenReturn(Optional.of(usuarioAtualizado));
        when(usuarioRepositorie.findByUsuario(nomeUsuarioSolicitante)).thenReturn(Optional.of(usuarioSolicitante));
        when(usuarioRepositorie.save(usuarioAtualizado)).thenReturn(usuarioAtualizado);

        Usuario usuarioAtualizadoSalvo = usuarioService.atualizar(usuarioAtualizado, request);
        Assert.assertEquals(usuarioAtualizado, usuarioAtualizadoSalvo);
    }

    @Test
    public void cadastrarTest() {
        Long idUsuarioSolicitante = 1L;
        String nomeUsuarioSolicitante = "Usuario1";
        String senhaUsuarioSolicitante = "12345";
        String credencialCodificadaSolicitante = Base64
                .getEncoder()
                .encodeToString((nomeUsuarioSolicitante + ":" + senhaUsuarioSolicitante).getBytes());
        Long idUsuarioCadastrar = 2L;
        String nomeUsuarioCadastrar = "Usuario2";
        String senhaUsuarioCadastrar = "54321";

        Usuario usuarioSolicitante = UsuarioBuilder.umUsuario()
                .comId(idUsuarioSolicitante)
                .comNome(nomeUsuarioSolicitante)
                .comSenha(senhaUsuarioSolicitante)
                .comFuncao(Funcoes.ADMIN.getFuncao())
                .build();
        Usuario usuarioCadastrar = UsuarioBuilder.umUsuario()
                .comId(idUsuarioCadastrar)
                .comNome(nomeUsuarioCadastrar)
                .comSenha(senhaUsuarioCadastrar)
                .comFuncao(Funcoes.FUNCIONARIO.getFuncao())
                .build();
        when(usuarioRepositorie.findByUsuario(nomeUsuarioCadastrar)).thenReturn(Optional.empty());
        when(usuarioRepositorie.save(usuarioCadastrar)).thenReturn(usuarioCadastrar);

        Usuario usuarioCadastradoSalvo = usuarioService.cadastrar(usuarioCadastrar, request);
        Assert.assertEquals(usuarioCadastrar, usuarioCadastradoSalvo);
    }

    @Test
    public void visualizarUsuarioPorNomeTest() {
        Long idUsuarioSolicitante = 1L;
        String nomeUsuarioSolicitante = "Usuario1";
        String senhaUsuarioSolicitante = "12345";
        String credencialCodificadaSolicitante = Base64
                .getEncoder()
                .encodeToString((nomeUsuarioSolicitante + ":" + senhaUsuarioSolicitante).getBytes());
        Long idUsuarioVisualizar = 2L;
        String nomeUsuarioVisualizar = "Usuario2";
        String senhaUsuarioVisualizar = "54321";

        Usuario usuarioSolicitante = UsuarioBuilder.umUsuario()
                .comId(idUsuarioSolicitante)
                .comNome(nomeUsuarioSolicitante)
                .comSenha(senhaUsuarioSolicitante)
                .comFuncao(Funcoes.ADMIN.getFuncao())
                .build();
        Usuario usuarioVisualizar = UsuarioBuilder.umUsuario()
                .comId(idUsuarioVisualizar)
                .comNome(nomeUsuarioVisualizar)
                .comSenha(senhaUsuarioVisualizar)
                .comFuncao(Funcoes.FUNCIONARIO.getFuncao())
                .build();
        when(request.getHeader("Authorization")).thenReturn("Basic " + credencialCodificadaSolicitante);
        when(usuarioRepositorie.findByUsuario(nomeUsuarioSolicitante)).thenReturn(Optional.of(usuarioSolicitante));
        when(usuarioRepositorie.findByUsuario(nomeUsuarioVisualizar)).thenReturn(Optional.of(usuarioVisualizar));

        Usuario usuarioCadastradoSalvo = usuarioService.visualizarUsuarioPorNome(usuarioVisualizar.getUsuario(), request);
        Assert.assertEquals(usuarioVisualizar, usuarioCadastradoSalvo);
    }
}

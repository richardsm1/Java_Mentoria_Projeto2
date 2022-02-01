package com.mentorias.login.resources;

import com.mentorias.login.entities.Usuario;
import com.mentorias.login.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("apilogin/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> visualizarPorId(@PathVariable("id") Long id, HttpServletRequest request) {
        return ResponseEntity.ok(usuarioService.visualizarUsuarioPorId(id, request));
    }

    @GetMapping()
    public ResponseEntity<Usuario> visualizarPorNome(@RequestParam("nome") String nome, HttpServletRequest request) {
        return ResponseEntity.ok(usuarioService.visualizarUsuarioPorNome(nome, request));
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario, HttpServletRequest request) {
        return ResponseEntity.ok(usuarioService.cadastrar(usuario, request));
    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario, HttpServletRequest request) {
        return ResponseEntity.ok(usuarioService.atualizar(usuario, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable("id") Long id, HttpServletRequest request) {
        return ResponseEntity.ok(usuarioService.remover(id, request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> fazerLogin(@RequestBody Usuario usuario) {
        usuarioService.fazerLogin(usuario.getUsuario(), usuario.getSenha());
        return ResponseEntity.ok("Login completado com sucesso");
    }
}

package br.com.acervo.api.controller;

import br.com.acervo.api.model.livro.Categoria;
import br.com.acervo.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;

import java.util.List;

@RestController
@RequestMapping("categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Endpoint para cadastrar categorias pelo Insomnia
    @PostMapping
    @Transactional
    public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) {
        // Salva o nome enviado (ex: "FANTASIA") na tabela do banco
        var novaCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.ok(novaCategoria);
    }

    // Lista todas as categorias existentes
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        List<Categoria> lista = categoriaRepository.findAll();
        return ResponseEntity.ok(lista);
    }
}

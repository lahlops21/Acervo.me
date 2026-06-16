package br.com.acervo.api.service;

import br.com.acervo.api.model.exemplar.DadosCadastroNovoExemplar;
import br.com.acervo.api.model.exemplar.Exemplar;
import br.com.acervo.api.model.exemplar.StatusLivro;
import br.com.acervo.api.model.livro.Livro;
import br.com.acervo.api.repository.ExemplarRepository;
import br.com.acervo.api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
public class ExemplarService {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private ExemplarRepository exemplarRepository;

    @Transactional
public Exemplar cadastrarNovoExemplar(DadosCadastroNovoExemplar dados) {
    Optional<Livro> livroExistente = livroRepository.findByIsbn(dados.isbn());
    
    Livro livroAlvo;
    
    if (livroExistente.isPresent()) {
        // Cenário A: O livro já existe!
        livroAlvo = livroExistente.get();
    } else {
        // Cenário B: O livro é inédito! Criamos o registro base dele
        livroAlvo = new Livro();
        livroAlvo.setIsbn(dados.isbn());
        livroAlvo.setTitulo(dados.titulo());
        livroAlvo.setEditora(dados.editora());
        livroAlvo.setAnoPublicacao(dados.anoPublicacao());
        livroAlvo.setSinopse(dados.sinopse());
        livroAlvo.setUrlCapa(dados.urlCapa());
        livroAlvo.setQuantidadeExemplares(0); // Garante que nasce em zero antes de somar
    }

    // 🔥 O SEU DESEJO REALIZADO AQUI:
    // Não importa se o livro é velho ou novo, a quantidade dele aumenta em +1!
    livroAlvo.setQuantidadeExemplares(livroAlvo.getQuantidadeExemplares() + 1);
    
    // Salva ou atualiza o livro com a nova quantidade
    livroAlvo = livroRepository.save(livroAlvo);

    // Cria a cópia física (Exemplar) vinculada ao livroAlvo
    Exemplar novoExemplar = new Exemplar();
    novoExemplar.setTombo(dados.tombo());
    novoExemplar.setStatus(StatusLivro.DISPONIVEL);
    novoExemplar.setLivro(livroAlvo); 

    return exemplarRepository.save(novoExemplar);
}
}
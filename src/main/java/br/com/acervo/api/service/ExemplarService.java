package br.com.acervo.api.service;

// import org.springframework.stereotype.Service;

// @Service
public class ExemplarService {

    // @Autowired private LivroRepository livroRepository;
    // @Autowired private ExemplarRepository exemplarRepository;

    // @Transactional
    // public void cadastrarCopiaFisica(DadosCadastroNovoExemplar dados) {
        
    //     // 1. Tenta buscar se o livro já existe pelo ISBN
    //     Livro livro = livroRepository.findByIsbn(dados.isbn())
    //         .orElseGet(() -> {
    //             // Se NÃO existir, a mágica acontece: cria um livro novo do zero!
    //             Livro novoLivro = new Livro();
    //             novoLivro.setIsbn(dados.isbn());
    //             novoLivro.setTitulo(dados.titulo());
    //             novoLivro.setEditora(dados.editora());
    //             novoLivro.setAnoPublicacao(dados.anoPublicacao());
    //             novoLivro.setSinopse(dados.sinopse());
    //             return livroRepository.save(novoLivro); // Salva o livro primeiro!
    //         });

    //     // 2. Agora que temos um livro (seja o velho ou o novo), criamos a cópia física
    //     Exemplar novoExemplar = new Exemplar();
    //     novoExemplar.setTombo(dados.tombo());
    //     novoExemplar.setLivro(livro); // Vincula o exemplar ao livro correspondente
    //     novoExemplar.setStatus(StatusLivro.DISPONIVEL); // Já nasce pronto para empréstimo

    //     exemplarRepository.save(novoExemplar);
    // }
}
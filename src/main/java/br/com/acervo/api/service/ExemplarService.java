package br.com.acervo.api.service;

import br.com.acervo.api.model.autor.Autor;
import br.com.acervo.api.model.exemplar.DadosCadastroNovoExemplar;
import br.com.acervo.api.model.exemplar.DadosDetalhamentoCompletoLivro;
import br.com.acervo.api.model.exemplar.Exemplar;
import br.com.acervo.api.model.livro.Categoria;
import br.com.acervo.api.model.exemplar.StatusLivro;
import br.com.acervo.api.model.livro.Livro;
import br.com.acervo.api.repository.AutorRepository;
import br.com.acervo.api.repository.ExemplarRepository;
import br.com.acervo.api.repository.LivroRepository;
import br.com.acervo.api.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExemplarService {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private AutorRepository autorRepository; 

    @Autowired
    private CategoriaRepository categoriaRepository;

        @Transactional
    public Exemplar cadastrarNovoExemplar(DadosCadastroNovoExemplar dados) {
        Optional<Livro> livroExistente = livroRepository.findByIsbn(dados.isbn());
        
        Livro livroAlvo;
        
        if (livroExistente.isPresent()) {
            // Cenário A: O livro já existe
            livroAlvo = livroExistente.get();
        } else {
            // Cenário B: O livro é novo-> Cria o registro base dele
            livroAlvo = new Livro();
            livroAlvo.setIsbn(dados.isbn());
            livroAlvo.setTitulo(dados.titulo());
            livroAlvo.setEditora(dados.editora());
            livroAlvo.setAnoPublicacao(dados.anoPublicacao());
            livroAlvo.setSinopse(dados.sinopse());
            livroAlvo.setUrlCapa(dados.urlCapa());
            livroAlvo.setQuantidadeExemplares(0); 
            
            //  Converte List<Integer> em List<Categoria> buscando registros reais no banco
            if (dados.categorias() != null && !dados.categorias().isEmpty()) {
                List<Categoria> categoriasCarregadas = new ArrayList<>();
                for (Integer idCategoria : dados.categorias()) {
                    Categoria categoriaReal = categoriaRepository.findById(idCategoria)
                        .orElseThrow(() -> new IllegalArgumentException("Categoria com ID " + idCategoria + " não encontrada no sistema"));
                    categoriasCarregadas.add(categoriaReal);
                }
                livroAlvo.setCategorias(categoriasCarregadas);
            }



            // PROCESSAMENTO INTELIGENTE DOS AUTORES:
            if (dados.autores() != null && !dados.autores().isEmpty()) {
                List<Autor> listaAutoresFinal = dados.autores().stream()
                    .map(autorInput -> {
                        if (autorInput.id() != null) {
                            // Se veio com ID, busca o autor já existente
                            return autorRepository.findById(autorInput.id())
                                .orElseThrow(() -> new IllegalArgumentException("Autor com ID " + autorInput.id() + " não encontrado"));
                        } else {
                            // Se NÃO veio com ID, busca por nome para não duplicar no banco
                            return autorRepository.findByNome(autorInput.nome())
                                .orElseGet(() -> {
                                    // Se o nome também não existir, cria o autor acadêmico/novo do zero
                                    Autor autorNovo = new Autor();
                                    autorNovo.setNome(autorInput.nome());
                                    return autorNovo;
                                });
                        }
                    })
                    .toList();
                
                livroAlvo.setAutores(listaAutoresFinal);
            }
        }

        // A quantidade aumenta em +1 independentemente do cenário
        livroAlvo.setQuantidadeExemplares(livroAlvo.getQuantidadeExemplares() + 1);
        
        // Salva ou atualiza o livro no banco
        livroAlvo = livroRepository.save(livroAlvo);

        // Cria a cópia física (Exemplar) vinculada ao livroAlvo
        Exemplar novoExemplar = new Exemplar();
        novoExemplar.setTombo(dados.tombo());
        novoExemplar.setStatus(StatusLivro.DISPONIVEL);
        novoExemplar.setLivro(livroAlvo); 

        // RETORNO OBRIGATÓRIO: Garante o retorno do tipo Exemplar que resolve o erro da imagem
        return exemplarRepository.save(novoExemplar);
    }


public DadosDetalhamentoCompletoLivro obterDetalhamentoCompleto(Integer idExemplar) {
    var exemplar = exemplarRepository.findById(idExemplar)
            .orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado"));

    var livro = exemplar.getLivro();

    // Busca os nomes dos autores
    List<String> nomesAutores = livro.getAutores().stream()
            .map(Autor::getNome)
            .toList();

    // Correção de atribuição segura: se estiver vazia, cria uma nova lista contendo o aviso
    if (nomesAutores.isEmpty()) {
        nomesAutores = java.util.Collections.singletonList("Autor Desconhecido");
    }

    return new DadosDetalhamentoCompletoLivro(livro, exemplar, nomesAutores);
}
}
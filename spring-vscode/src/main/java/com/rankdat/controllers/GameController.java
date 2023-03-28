package com.rankdat.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rankdat.models.Game;
import com.rankdat.repository.GameRepository;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
public class GameController {

    Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired // Princípio da Injeção de Dependência (> Pesquisar mais Sobre! <)
    GameRepository repository;

    @GetMapping
    public List<Game> returnGames() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game jogo) {
        log.info("cadastrando jogo: " + jogo);

        repository.save(jogo);

        return ResponseEntity.status(HttpStatus.CREATED).body(jogo);
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        log.info("buscando por jogo com id: " + id);

        var jogoEncontrado = repository.findById(id);

        if(jogoEncontrado.isEmpty()) 
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(jogoEncontrado.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
        log.info("deletando jogo com id: " + id);
        var jogoEncontrado = repository.findById(id);

        if(jogoEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        
        repository.delete(jogoEncontrado.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> putGame(@PathVariable Long id, @RequestBody Game jogo) {
        log.info("alterando jogo com id: " + id);
        var jogoEncontrado = repository.findById(id);

        if(jogoEncontrado.isEmpty())
            return ResponseEntity.notFound().build();

        
        jogo.setId(id);

        repository.save(jogo);

        return ResponseEntity.ok(jogo);

    }

}
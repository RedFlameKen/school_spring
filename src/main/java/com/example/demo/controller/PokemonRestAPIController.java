package com.example.demo.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Pokemon;
import com.example.demo.response.APIResponse;
import com.example.demo.service.PokemonRestAPIService;

@RestController
@RequestMapping("/api/v2")
public class PokemonRestAPIController {

    @Autowired
    PokemonRestAPIService pokemonRestAPIService;

    @GetMapping("/lists")
    public ResponseEntity<?> getPokemonList() {
        APIResponse<?> api = new APIResponse<>("Test", 200, pokemonRestAPIService.getPokemonList());
        return ResponseEntity.ok(api);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPokemon(@RequestParam String pokemon) {
        Optional<Pokemon> findPokemon = pokemonRestAPIService.searchPokemon(pokemon);

        if(findPokemon.isEmpty()){
            APIResponse<?> apiResponse = new APIResponse<>("Not found", 404, null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(findPokemon);
    }

    @GetMapping("/search/{pokemon}")
    public ResponseEntity<?> pokemonPath(@PathVariable String pokemon) {
        return this.searchPokemon(pokemon);
    }

    @GetMapping("/request")
    public ResponseEntity<?> pokemonPath(@RequestBody Pokemon pokemon) {
        System.out.println(pokemon.getName());
        System.out.println(pokemon.getCategory());
        return ResponseEntity.ok("sfsdf");
    }


    @DeleteMapping("/pokemon/{pokemonId}")
    public ResponseEntity<?> pokemonDelete(@PathVariable Long pokemonId){
        boolean deleted = pokemonRestAPIService.deletePokemon(pokemonId);
        APIResponse<?> apiResponse;
        if(!deleted){
            apiResponse = new APIResponse<>("Not found", 404, null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new APIResponse<>("Ok", 200, null));
    }

}

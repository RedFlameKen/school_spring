package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

//    @EntityGraph(attributePaths = {"category"})
//    @Override
//    List<Pokemon> findAll();

    Pokemon findByName(String name);

    List<Pokemon> findByNameContainingIgnoreCase(String keyword);


}

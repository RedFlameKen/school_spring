package com.example.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PersonEntity;
import com.example.demo.repository.PersonRepository;
import com.example.demo.request.PersonRequest;
import com.example.demo.response.APIResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("details")
    public ResponseEntity<?> getPersonDetails(){
        // System.out.println("=======" + config.getDatabaseURL());

        List<PersonEntity> getPersonEntities = personRepository.findAll();

        APIResponse<List<PersonEntity>> response = new APIResponse<>(
                "Successfully fetch data",
                200,
                getPersonEntities
        );

        // Map<String, String> data = new HashMap<>();

        return ResponseEntity.ok(response);
    }

    @PostMapping("details")
    public ResponseEntity<?> addPerson(@RequestBody PersonEntity person1){
        
        personRepository.save(person1);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

//    @DeleteMapping("details/{id}")
    @GetMapping("details/{id}")
    public ResponseEntity<?> findPerson(@PathVariable Long id){

        Optional<PersonEntity> findPerson = personRepository.findById(id);

        APIResponse<PersonEntity> response = new APIResponse<>("Successfully found", 200, findPerson.get());


        return ResponseEntity.ok(response);
    }

    @PutMapping("details/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id, @RequestBody PersonRequest person) {
        Optional<PersonEntity> optionalPerson = personRepository.findById(id);

        if(optionalPerson.isPresent()) {
            PersonEntity personToUpdate = optionalPerson.get();
            personToUpdate.setSection(person.getSection());
            personToUpdate.setFirstName(person.getFirstName());
            personToUpdate.setLastName(person.getLastName());
            personRepository.save(personToUpdate);
            APIResponse<?> updatePerson = new APIResponse<>("Successfully Changed", 200, null);
            return ResponseEntity.ok(updatePerson);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id is not found!");

    }

    @GetMapping("test")
    public ResponseEntity<?> getOrders() {
        Map<String, String> orders = new HashMap<>();

        orders.put("ballpen", "Ballpen");

        orders.put("pencil", "Pencil");

        orders.put("notebook", "Notebook");



        return ResponseEntity.ok(orders);
    }


}

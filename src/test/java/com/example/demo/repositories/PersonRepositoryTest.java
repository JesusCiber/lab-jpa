package com.example.demo.repositories;

import com.example.demo.models.Person;
import com.example.demo.reposirories.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository PersonRepository;

    @Test
    @DisplayName("Añadir persona")
    public void  save(){

        Person person5 = new Person("John", 30);
        Person savedPerson = PersonRepository.save(person5);
        Long personId = savedPerson.getId();
        assertNotNull(PersonRepository.findById(personId));
        System.out.println("Saved person: " + savedPerson);

        assertNotNull(PersonRepository.findById(personId));
        assertEquals("John", savedPerson.getName());
        assertEquals(30, savedPerson.getAge());

    }

    @Test
    @DisplayName("Test save and find all persons with spring data jpa")
    public void findAll() {
        List<Person> persons = List.of(
                new Person("Sofia Herrera", 28),
                new Person("Ricardo López", 35),
                new Person("Patricia Ruiz", 40),
                new Person("Marcos Pérez", 26),
                new Person("Carmen Díaz", 33),
                new Person("Eduardo Torres", 22),
                new Person("Laura García", 30),
                new Person("Raúl Martín", 31),
                new Person("Ana Sánchez", 29),
                new Person("Luis Gómez", 34)
        );

        // Save the person to the database
        PersonRepository.saveAll(persons);

        // Retrieve all persons from the database
        List<Person> personsFromDb = PersonRepository.findAll();

        // Verify that the list is not empty and contains the saved persons
        assertFalse(personsFromDb.isEmpty());

        // Print the persons to the console
        for (Person person : personsFromDb) {
            System.out.println(person);
        }

        // Verify that the first person in the list is "Lucas Rodríguez"
        assertEquals("Alice", personsFromDb.getFirst().getName());
        assertEquals(40, personsFromDb.getFirst().getAge());
    }
    @Test
    @DisplayName("Test delete person with spring data jpa")
    public void testDelete() {

        Person newPerson = new Person("John", 30);
        PersonRepository.save(newPerson);

        Long personId = (long)newPerson.getId();

        assertNotNull(PersonRepository.findById(personId));

        System.out.println("Saved person: " + newPerson);

        PersonRepository.delete(newPerson);

        assertFalse(PersonRepository.findById(personId).isPresent());
    }

    @Test
    @DisplayName("Test update person with spring data jpa")
    public void testUpdate() {

        Person person = new Person("Jesus ", 25);
        PersonRepository.save(person);

        Long personId = (long)person.getId();


        assertNotNull(PersonRepository.findById(personId));


        System.out.println("Saved person: " + person);

        Person updatedPerson = PersonRepository.findById(personId).get();


        assertNotNull(updatedPerson);


        updatedPerson.setName("Joan");
        updatedPerson.setAge(35);
        PersonRepository.save(updatedPerson);


        Person updatedPersonDB = PersonRepository.findById(personId).orElse(null);
        assertNotNull(updatedPersonDB);
        assertEquals("Joan ", updatedPersonDB.getName());
        assertEquals(35, updatedPersonDB.getAge());


        System.out.println("Updated person: " + updatedPerson);
    }


}

//package com.example.demo.dao;
//
//import com.example.demo.model.Person;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public interface PersonDao {
//    int insertPerson(UUID id, Person person);
//    default int insertPerson(Person person){
//        UUID id=UUID.randomUUID();
//        return insertPerson(id,person);
//    }
//  public  List<Person> selectAllPeople();
//
//    public Optional<Person> selectPersonById(UUID id);
//
//    public   int deletePersonById(UUID id);
//    public   int updatePersonById(UUID id,Person person);
//}

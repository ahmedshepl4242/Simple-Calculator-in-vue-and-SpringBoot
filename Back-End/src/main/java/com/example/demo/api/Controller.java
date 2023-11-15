package com.example.demo.api;

import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
@RestController
public class Controller {

    private final PersonService service;
    private final Integer[] res = new Integer[4];

    private double result = 0;
    public String s;

    public Integer[] getRes() {
        return res;
    }

    @Autowired

    public Controller(PersonService personService) {
        this.service = personService;
    }

    @GetMapping("/cal")
    public List<String> getAllPeople() {
        return service.getStr();
    }


    @PostMapping("/cal")

    public double cal1(@RequestBody String exp) {
        this.s = exp;
        return service.face(exp);
    }

    @PostMapping("/oneOperand")

    public double cal2(@RequestBody String exp) {

        String sw = exp.substring(exp.length() - 2, exp.length());
        System.out.println(sw);
        String nm = exp.substring(0, exp.length() - 2);
        double num = Double.parseDouble(nm);
        System.out.println(num);
        double reslt = 0;
        if (sw.equalsIgnoreCase("in")) {
            reslt = 1 / num;
        } else if (sw.equalsIgnoreCase("sq")) {

            reslt = Math.pow(num, 2);
        } else if (sw.equalsIgnoreCase("sr")) {
            reslt = Math.sqrt(num);
        } else if (sw.equalsIgnoreCase("pr")) {
            reslt = num/100;
        }


//        switch (sw) {
//            case "in":
//                 reslt =1 / num;
//                 break;
//            case "sq":
//                reslt= Math.pow(num, 2);
//                break;
//            case "sr":
//                reslt= Math.sqrt(num);
//                break;
//        }
        System.out.println(reslt);
        return reslt;

    }
}


//    @GetMapping(path = "{id}")
//    public Person getPersonById(@PathVariable("id") UUID id){
//        return personService.getPersonById(id)
//                .orElse(null);
//    }


//    @PutMapping (path="{id}")
//
//    public void updateById(@PathVariable("id") UUID id,@RequestBody Person person){
//        personService.update(id,person);
//
//    }
//    @DeleteMapping(path="{id}")
//    public void deleteByID(@PathVariable("id") UUID id){
//        personService.deletePerson(id);
//
//    }


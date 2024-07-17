package com.kan.kanMongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kan.kanMongo.service.UserMService;
import com.kan.kanMongo.vo.UserM;

@RestController
@RequestMapping("/muser")
public class UserMController {


    @Autowired
    private UserMService userMService;


//
//    @GetMapping("/{id}")
//    public Optional<YourModel> getById(@PathVariable String id) {
//        return service.getById(id);
//    }
//
    @PostMapping("/create")
    public UserM create(@RequestBody UserM userM) {
        return userMService.save( userM);
    }

    @GetMapping("/getAll")
	 public List<UserM> getAll() {
	      return userMService.getAll();
	  }
//    @PutMapping("/{id}")
//    public YourModel update(@PathVariable String id, @RequestBody YourModel yourModel) {
//        yourModel.setId(id);
//        return service.save(yourModel);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        service.deleteById(id);
//    }
}

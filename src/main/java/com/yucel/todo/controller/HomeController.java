package com.yucel.todo.controller;


import com.yucel.todo.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {


    @GetMapping
    public ResponseEntity< ApiResponse >  homePage(){

        String data="This is home page";
        return  ResponseEntity.ok(new ApiResponse("success",data ));


    }
}

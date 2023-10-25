package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.masai.dto.ChatGPTRequest;
import com.masai.dto.ChatGptResponse;
import com.masai.dto.Data;

@RestController
@CrossOrigin("*")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;
    
    @GetMapping("/chat")
    public ResponseEntity<Data> chat(@RequestParam("prompt") String prompt){
    	
        ChatGPTRequest request=new ChatGPTRequest(model, prompt);
        System.out.println(prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        
        String s=chatGptResponse.getChoices().get(0).getMessage().getContent();
        Data d=new Data(s);
        System.out.println(s);
        
        return new ResponseEntity<Data>(d,HttpStatus.OK);
    }
}

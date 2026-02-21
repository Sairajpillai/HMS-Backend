package com.example.visitor.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.visitor.dto.VisitorDTO;
import com.example.visitor.exception.HMSUserException;
import com.example.visitor.service.VisitorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/visitor")
public class VisitorAPI {

    private final VisitorService visitorService;

    @PostMapping("/saveVisitor")
    public ResponseEntity<VisitorDTO> saveVisitor(@RequestBody VisitorDTO visitorDTO) throws HMSUserException {
        return new ResponseEntity<>(visitorService.saveVisitor(visitorDTO), HttpStatus.CREATED);
    }

}

package com.zuankid.codesharingplatform.controller;

import com.zuankid.codesharingplatform.entity.Code;
import com.zuankid.codesharingplatform.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
public class ApiController {
    @Autowired
    private CodeService codeService;

    //return JSON with the N-th uploaded code snippet.
    @GetMapping("/api/code/{id}")
    public ResponseEntity<Code> getJsonCode(@PathVariable UUID id, HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        boolean isCodeExist = codeService.isCodeExist(id);
        if(!isCodeExist) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Code code = codeService.getCode(id);
        long timeRemain = code.getTime();
        long viewsRemain = code.getViews();
        if(!code.isNoTimeRestriction()){
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            timeRemain -= ChronoUnit.SECONDS.between(code.getDate(), now);
            if(timeRemain <= 0) {
                codeService.deleteCode(id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            code.setTime(timeRemain);
        }
        if(!code.isNoViewsRestriction()){
            if(viewsRemain == 0) {
                codeService.deleteCode(id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            code.setViews(--viewsRemain);
        }
        codeService.updateCode(code);
        return new ResponseEntity<Code>(code,HttpStatus.OK);
    }

    //Post code and ID is increasing automatically
    @PostMapping("/api/code/new")
    public ResponseEntity<Object> addNewCode(@RequestBody Code newCode){
        newCode.setNoTimeRestriction(newCode.getTime()<=0?true:false);
        newCode.setNoViewsRestriction(newCode.getViews()<=0?true:false);
        newCode.setId(UUID.randomUUID());
        newCode.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        Code code = codeService.addCode(newCode);
        return ResponseEntity.ok(String.format("{ \"id\": \"%s\" }", code.getId()));
    }

    //Get 10 codes latest and return JSON
    @GetMapping("/api/code/latest")
    public List<Code> getArrayJsonCodeLatest(HttpServletResponse response){
        response.addHeader("Content-Type", "application/json");
        return codeService.getLatestRestrictedCodes();
    }


}

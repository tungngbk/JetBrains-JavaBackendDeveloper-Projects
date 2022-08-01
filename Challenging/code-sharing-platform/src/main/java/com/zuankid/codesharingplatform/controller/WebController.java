package com.zuankid.codesharingplatform.controller;

import com.zuankid.codesharingplatform.entity.Code;
import com.zuankid.codesharingplatform.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Controller
public class WebController {
    @Autowired
    private CodeService codeService;

    //return HTML that contains the N-th uploaded code snippet.
    @GetMapping("/code/{id}")
    public ModelAndView getHtmlCode(@PathVariable UUID id, HttpServletResponse response) {
        boolean isCodeExist = codeService.isCodeExist(id);
        if(!isCodeExist) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");;
        ModelAndView modelAndView = new ModelAndView("code");
        response.addHeader("Content-Type", "text/html");
        Code code = codeService.getCode(id);
        long timeRemain = code.getTime();
        long viewsRemain = code.getViews();
        if(!code.isNoTimeRestriction()){
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            timeRemain -= ChronoUnit.SECONDS.between(code.getDate(), now);
            if(timeRemain <= 0) {
                codeService.deleteCode(id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
            }
            code.setTime(timeRemain);
        }
        if(!code.isNoViewsRestriction()){
            if(viewsRemain == 0) {
                codeService.deleteCode(id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
            }
            code.setViews(--viewsRemain);
        }
        codeService.updateCode(code);
        modelAndView.addObject("code", code);
        modelAndView.addObject("title","Code");
        return modelAndView;
    }

    //Post code with HTML form
    @GetMapping("/code/new")
    public String addNewCodeWithHTML(){
        return "newcode";
    }

    //Get 10 codes latest and return HTML
    @GetMapping("/code/latest")
    public String getHTMLCodeLatest(HttpServletResponse response, Model model){
        List<Code> list = codeService.getLatestRestrictedCodes();
        model.addAttribute("flag","on");
        model.addAttribute("code",list);
        model.addAttribute("title","Latest");
        return "code";
    }

}

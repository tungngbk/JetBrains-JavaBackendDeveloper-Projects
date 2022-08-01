package com.zuankid.codesharingplatform.service;

import com.zuankid.codesharingplatform.entity.Code;
import com.zuankid.codesharingplatform.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CodeService {
    @Autowired
    private CodeRepository codeRepository;

    public Code getCode(UUID id){
        return codeRepository.findById(id).get();
    }

    public List<Code> getLatestRestrictedCodes(){
        List<Code> list = new ArrayList<>();
        Iterable<Code> all = codeRepository.findAllByOrderByDateAsc();
        all.forEach(list::add);
        List<Code> restrictedList = new ArrayList<>();
        for(Code code : list){
            if (code.isNoViewsRestriction() && code.isNoTimeRestriction()){
                restrictedList.add(code);
            }
        }
        restrictedList = restrictedList.size() > 10 ?
                restrictedList.subList(restrictedList.size() - 10, restrictedList.size())
                : restrictedList;
        Collections.reverse(restrictedList);
        return restrictedList;
    }

    public Code addCode(Code listElement){
        return codeRepository.save(listElement);
    }

    public List<Code> getLatestCodes(){
        return codeRepository.findTop10ByOrderByDateDesc();
    }

    public void deleteCode(UUID id){
        codeRepository.deleteById(id);
    }

    public boolean isCodeExist(UUID id){
        return codeRepository.existsById(id);
    }

    public void updateCode(Code code) {
        codeRepository.save(code);
    }
}

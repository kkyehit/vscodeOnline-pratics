package com.vscode.req.onlinevscode.web;

import com.vscode.req.onlinevscode.service.VSCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vscode")
public class VSCodeController {
    
    @Autowired
    VSCodeService vscodeService;

    @GetMapping("/{id}")
    public String getVsCode(@PathVariable("id") Long id){
        vscodeService.createVSCode(id);
        return "";
    }

    @PostMapping("/{id}")
    public String createVscode(@PathVariable("id") String id){
        return "";
    }
}
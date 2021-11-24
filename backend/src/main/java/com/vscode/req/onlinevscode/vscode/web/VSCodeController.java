package com.vscode.req.onlinevscode.vscode.web;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.vscode.req.onlinevscode.vscode.model.VSCodeModel;
import com.vscode.req.onlinevscode.vscode.service.VSCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vscode")
public class VSCodeController {
    
    @Autowired
    VSCodeService vscodeService;

    @GetMapping("/")
    public List<VSCodeModel> getList(){
        return vscodeService.getVSCodeList();
    }

    @GetMapping("/{id}")
    public String getLogsVSCodePod(@PathVariable("id") Long id){
        return vscodeService.getLogsVSCodePod(id);
    }

    @PostMapping("/{id}")
    public String createVscode(@PathVariable("id") Long id) throws TimeoutException{
        vscodeService.createVSCode(id);
        return "";
    }

    @DeleteMapping("/{id}")
    public String deleteVScode(@PathVariable("id") Long id){
        vscodeService.deleteVSCode(id);
        return "";
    }
}
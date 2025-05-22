package com.example.laptopstore.controller;

import com.example.laptopstore.model.Mouse;
import com.example.laptopstore.service.MouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebMouseController {

    private final MouseService mouseService;
    
    @Autowired
    public WebMouseController(MouseService mouseService) {
        this.mouseService = mouseService;
    }
    
    @GetMapping("/mouse")
    public String mouseList(
            @RequestParam(required = false) String keyword,
            Model model) {
        
        List<Mouse> mouseList;
        if (keyword != null) {
            mouseList = mouseService.searchMice(keyword);
        } else {
            mouseList = mouseService.getAllMice();
        }
        
        model.addAttribute("mouseList", mouseList);
        model.addAttribute("keyword", keyword);
        return "mouse/list";
    }
    
    @GetMapping("/mouse/{id}")
    public String mouseDetails(@PathVariable Long id, Model model) {
        Mouse mouse = mouseService.getMouseById(id);
        if (mouse == null) {
            return "redirect:/mouse";
        }
        model.addAttribute("mouse", mouse);
        return "mouse/details";
    }
}
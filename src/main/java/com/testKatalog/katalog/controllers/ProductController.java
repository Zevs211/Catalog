package com.testKatalog.katalog.controllers;

import com.testKatalog.katalog.model.ProductsList;
import com.testKatalog.katalog.repo.ProductsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    @Autowired
    private ProductsListRepository productsListRepository;

    @GetMapping("/product")
    public String productMain(Model model) {
        Iterable<ProductsList> productsList = productsListRepository.findAll(); //Iterable-массив данных, posts объект Iterable;findAll() -функция унаследованная от интерфейса CrudRepository
        model.addAttribute("productsList", productsList);
        return "product-main";
    }
    @GetMapping("/product/add")
    public String productAdd(Model model) {
        return "product-add";
    }

    @PostMapping("/product/add")
    public String productPostAdd(@RequestParam String name, @RequestParam String description, Model model){   //Request-получение формы из html файла blog-add
        ProductsList productsList = new ProductsList(name,description);
        productsListRepository.save(productsList);
        return "redirect:/product";
    }

}
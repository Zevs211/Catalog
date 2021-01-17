package com.testKatalog.katalog.controllers;

import com.testKatalog.katalog.model.ProductsList;
import com.testKatalog.katalog.repo.ProductsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public String productPostAdd(@RequestParam String name, @RequestParam String description, Model model) {   //Request-получение формы из html файла blog-add
        ProductsList productsList = new ProductsList(name, description);
        productsListRepository.save(productsList);
        return "redirect:/product";
    }

    @GetMapping("/product/{id}/edit")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        if (!productsListRepository.existsById(id)) {
            return "redirect:/product";
        }

        Optional<ProductsList> productsList = productsListRepository.findById(id);
        ArrayList<ProductsList> res = new ArrayList<>();
        productsList.ifPresent(res::add);
        model.addAttribute("productList", res);
        return "product-edit";
    }

    @PostMapping("/product/{id}/edit")
    public String productPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String description, Model model) {
        ProductsList productsList = productsListRepository.findById(id).orElseThrow();
        productsList.setName(name);
        productsList.setDescription(description);
        productsListRepository.save(productsList);
        return "redirect:/product";
    }

    @PostMapping("/product/{id}/remove")
    public String productPostDelete(@PathVariable(value = "id") long id, Model model) {
        ProductsList productsList = productsListRepository.findById(id).orElseThrow();
        productsListRepository.delete(productsList);
        return "redirect:/product";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<ProductsList> productsLists = productsListRepository.findAll();
        model.put("productsLists", productsLists);
        return "main";
    }


    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<ProductsList> productsLists;

        if (filter != null && !filter.isEmpty()) {
            productsLists = productsListRepository.findByName(filter);
        }else {
            productsLists = productsListRepository.findAll();
        }
        model.put("productsLists", productsLists);
        return "main";
    }
}
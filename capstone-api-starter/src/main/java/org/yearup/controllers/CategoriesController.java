package org.yearup.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;
import java.util.Optional;

// add the annotations to make this a REST controller
@RestController
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
@RequestMapping("/categories")
// add annotation to allow cross site origin requests
@CrossOrigin
public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;

    // create an Autowired constructor to inject the categoryService and productService
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // add the appropriate annotation for a get action
    @GetMapping
    public ResponseEntity<List<Category>> getAll()
    {
        // find and return all categories
        List<Category> categories = this.categoryService.getAllCategories();
        if(categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    // add the appropriate annotation for a get action
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id)
    {
        // get the category by id
        Optional<Category> category = this.categoryService.getById(id);

        if(category.isPresent()){
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        List<Product> products = this.productService.listByCategoryId(categoryId);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // add annotation to call this method for a POST action
    @PostMapping
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category)
    {
        // insert the category and return it with status 201 Created
        Category newCategory = this.categoryService.create(category);

        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PutMapping("/{id}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid Category category, @PathVariable int id)
    {
        // update the category by id and return the updated category (200 OK)
        Category updatedCategory = this.categoryService.update(id, category);

        if(updatedCategory == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        }
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    @DeleteMapping("/{id}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        // delete the category by id and return status 204 No Content
        boolean deleteSuccessful = this.categoryService.delete(id);

        if(!deleteSuccessful){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}

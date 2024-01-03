package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
// http://localhost:8080/categories
// add annotation to allow cross site origin requests
@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController {
    private CategoryDao categoryDao;
    private ProductDao productDao;


    // create an Autowired controller to inject the categoryDao and ProductDao

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // add the appropriate annotation for a get action
    // "/" is what you norm put in a URL to go more specific and {} is how you set the syntax for calling it
    // can prolly leave "" blank, and if not, just put "/{getAll}"
    @PreAuthorize("permitAll()")
    @GetMapping("")
    public List<Category> getAll() {
        // find and return all categories
        // just check interfaces for the methods
        return categoryDao.getAllCategories();
    }

    // add the appropriate annotation for a get action
    @PreAuthorize("permitAll()")
    @GetMapping("/{categoryId}")
    // use @PathVariable instead of @RequestParam bc I'm reading the path by an int ID instead of a String;
    // basically just diff syntax bc it's a diff type
    // name = "" is required here bc it needs to know where it's getting the info from (the URL)
    public Category getById(@PathVariable(name = "categoryId") int id) {
        // get the category by id
        Category category = null;
        try {

            category = categoryDao.getById(id);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return category;
    }

    // the url to return all products in category 1 would look like this
    // inside {} put in the value corresponding to the column
    // https://localhost:8080/categories/1/products
    @PreAuthorize("permitAll()")
    @GetMapping("/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        return productDao.listByCategoryId(categoryId);

    }

    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    // @PostMapping for adding
    // altho order for annotation does not matter too much, it's best practice to have authorization at top
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        // insert the category
        try {

            return categoryDao.create(category);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    // @PutMapping for updating
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{categoryId}")
    public void updateCategory(@PathVariable(name = "categoryId") int id, @RequestBody Category category) {
        // update the category by id
        try {

            categoryDao.update(id, category);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    //this no work, might need to change the product category id and then delete categoryId from categories
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(name = "categoryId") int id) {
        // delete the category by id
         /*var category = categoryDao.getById(id);
        if (category == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);*/

        try {
            categoryDao.delete(id);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }


    }
}


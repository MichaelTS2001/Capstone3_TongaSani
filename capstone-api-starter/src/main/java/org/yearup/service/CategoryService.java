package org.yearup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList;
    }

    public Optional<Category> getById(int categoryId)
    {
        // get category by id
        var category = this.categoryRepository.findById(categoryId);

        return category;
    }

    public Category create(Category category)
    {
        // create a new category
        Category newCategory = categoryRepository.save(category);

        return newCategory;
    }

    public Category update(int categoryId, Category category)
    {
        Optional <Category> updateCategory = categoryRepository.findById(categoryId);
        // update category and return the updated category
        if(updateCategory.isEmpty()) {
            return null;
        }

        Category categoryToUpdate = updateCategory.get();

        categoryToUpdate.setCategoryId(category.getCategoryId());
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());

        categoryRepository.save(categoryToUpdate);

        return categoryToUpdate;
    }

    public boolean delete(int categoryId)
    {
        // delete category
        Optional <Category> categoryToDelete = categoryRepository.findById(categoryId);

        if(categoryToDelete.isEmpty()){
            return false;
        }

        categoryRepository.delete(categoryToDelete.get());
        return true;

    }
}

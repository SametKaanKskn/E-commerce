package com.sametkagankeskin.ecommerce.service;

import java.util.List;

import com.sametkagankeskin.ecommerce.model.dto.CategoryDto;
import com.sametkagankeskin.ecommerce.model.vm.AddCategoryVm;
import com.sametkagankeskin.ecommerce.model.vm.UpdateCategoryVm;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto getByCategoryId(int categoryId);

    int addNewCategory(AddCategoryVm categoryVm);

    String deleteByCategoryId(int categoryId);

    boolean existsById(int categoryId);

    String updateCategory(UpdateCategoryVm categoryVm);
}

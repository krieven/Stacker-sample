package sample.flow.catalog.services;

import sample.flow.catalog.states.category.contract.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryService {

    public List<Category> getProductCategoryesList(){
        List<Category> result = new ArrayList<>();

        result.add(new Category("auto", "Automobiles"));
        result.add(new Category("bike","Motorcycles"));

        return result;
    }
}

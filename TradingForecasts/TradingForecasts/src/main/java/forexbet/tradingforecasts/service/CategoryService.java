package forexbet.tradingforecasts.service;

import forexbet.tradingforecasts.model.entity.Category;
import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;

import java.util.Optional;

public interface CategoryService {

    void seedCategories();

    Category findByCategory(CategoryNameEnum name);
}

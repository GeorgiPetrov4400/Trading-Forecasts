package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.repository.CategoryRepository;
import forexbet.tradingforecasts.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}

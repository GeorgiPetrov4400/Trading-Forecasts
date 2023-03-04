package forexbet.tradingforecasts.repository;

import forexbet.tradingforecasts.model.entity.Category;
import forexbet.tradingforecasts.model.entity.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(CategoryNameEnum categoryNameEnum);
}

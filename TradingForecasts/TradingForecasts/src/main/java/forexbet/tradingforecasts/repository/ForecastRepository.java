package forexbet.tradingforecasts.repository;

import forexbet.tradingforecasts.model.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    List<Forecast> findAllByBuyer_IdAndPriceNotNullOrderByIdDesc(Long id);

    List<Forecast> findByAdmin_IdAndClosedIsNull(long ownerId);

//    List<Forecast> findAllByClosedIsNullOrderByCreatedDesc();

    List<Forecast> findAllByClosedIsNullAndPriceIsNotNullOrderByCreatedDesc();

    List<Forecast> findAllByClosedIsNotNullOrderByClosedDesc();

    List<Forecast> findAllByClosedIsNullAndPriceIsNull();
}

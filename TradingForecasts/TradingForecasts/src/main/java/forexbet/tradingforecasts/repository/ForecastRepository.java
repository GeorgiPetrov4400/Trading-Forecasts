package forexbet.tradingforecasts.repository;

import forexbet.tradingforecasts.model.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    List<Forecast> findAllByBuyer_IdIsNullAndAdmin_IdNot(Long id);

    List<Forecast> findByAdmin_Id(long ownerId);

//    List<Forecast> findAllByClosedIsNotNull();

}

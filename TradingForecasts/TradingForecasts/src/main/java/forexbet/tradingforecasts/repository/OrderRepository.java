package forexbet.tradingforecasts.repository;

import forexbet.tradingforecasts.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Picture, Long> {

//    List<Order> findByBuyer_IdAndAndForecast_Active(Long id);
}

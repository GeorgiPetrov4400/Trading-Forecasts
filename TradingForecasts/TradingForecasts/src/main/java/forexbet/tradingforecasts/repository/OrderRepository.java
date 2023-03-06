package forexbet.tradingforecasts.repository;

import forexbet.tradingforecasts.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findByBuyer_IdAndAndForecast_Active(Long id);
}

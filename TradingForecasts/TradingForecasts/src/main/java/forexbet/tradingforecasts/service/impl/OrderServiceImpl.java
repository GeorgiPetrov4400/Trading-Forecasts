package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.model.entity.Order;
import forexbet.tradingforecasts.repository.OrderRepository;
import forexbet.tradingforecasts.service.ForecastService;
import forexbet.tradingforecasts.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ForecastService forecastService;

    public OrderServiceImpl(OrderRepository orderRepository, ForecastService forecastService) {
        this.orderRepository = orderRepository;
        this.forecastService = forecastService;
    }

//    @Override
//    public List<Order> getActiveOrders(Long id) {
//        return orderRepository.findByBuyer_IdAndAndForecast_Active(id);
//    }
}

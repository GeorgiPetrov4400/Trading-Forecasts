package forexbet.tradingforecasts.service.impl;

import forexbet.tradingforecasts.repository.OrderRepository;
import forexbet.tradingforecasts.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}

package forexbet.tradingforecasts.web;

import forexbet.tradingforecasts.model.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
//@RequestMapping("/orders")
public class OrderController {

//    @GetMapping("/orders/order")
//    public String order() {
//        return "order";
//    }

    @GetMapping("/orders/{id}")
    public String getOrderById(@PathVariable("id") Long id) {
        throw new OrderNotFoundException(id);
    }

    @GetMapping("/orders")
    public String getOrders() {
        throw new NullPointerException("Server Error");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView onOrderNotFound(OrderNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("order-not-found");

        modelAndView.addObject("orderId", onfe.getOrderId());

        return modelAndView;
    }
}

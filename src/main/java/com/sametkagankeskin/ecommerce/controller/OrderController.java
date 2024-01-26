package com.sametkagankeskin.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sametkagankeskin.ecommerce.model.vm.AddOrderVm;
import com.sametkagankeskin.ecommerce.model.vm.GetOrderDetailsVm;
import com.sametkagankeskin.ecommerce.model.vm.GetOrdersVm;
import com.sametkagankeskin.ecommerce.model.vm.SearchOrdersByDateVm;
import com.sametkagankeskin.ecommerce.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders/")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<GetOrdersVm> getAllOrderVm() {
        return orderService.getAllOrderVm();
    }

    @PostMapping(value = "add")
    public int addOrderVm(@Valid @RequestBody AddOrderVm addOrderVm) {
        return orderService.addOrderVm(addOrderVm);
    }

    @GetMapping(value = "/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<GetOrdersVm> getOrderByCustomerId(@PathVariable("customerId") int customerId) {
        return orderService.getOrderByCustomerId(customerId);
    }

    @GetMapping(value = "/searchByDateBefore")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<GetOrdersVm> getOrdersBeforeDate(@Valid @RequestBody SearchOrdersByDateVm orderVm) {
        return orderService.getOrdersBeforeDate(orderVm);
    }

    @GetMapping(value = "/searchByDateAfter")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<GetOrdersVm> getOrdersAfterDate(@Valid @RequestBody SearchOrdersByDateVm orderVm) {
        return orderService.getOrdersAfterDate(orderVm);
    }

    @GetMapping(value = "/details/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<GetOrderDetailsVm> getOrderDetailsById(@PathVariable("orderId") int orderId) {
        return orderService.getOrderDetailsById(orderId);
    }
}

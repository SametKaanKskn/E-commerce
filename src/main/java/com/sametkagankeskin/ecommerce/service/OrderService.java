package com.sametkagankeskin.ecommerce.service;

import java.util.List;

import com.sametkagankeskin.ecommerce.model.vm.AddOrderVm;
import com.sametkagankeskin.ecommerce.model.vm.GetOrderDetailsVm;
import com.sametkagankeskin.ecommerce.model.vm.GetOrdersVm;
import com.sametkagankeskin.ecommerce.model.vm.SearchOrdersByDateVm;

public interface OrderService {
    List<GetOrdersVm> getAllOrderVm();

    List<GetOrdersVm> getOrderByCustomerId(int customerId);

    int addOrderVm(AddOrderVm addOrderVm);

    List<GetOrdersVm> getOrdersBeforeDate(SearchOrdersByDateVm orderVm);

    List<GetOrdersVm> getOrdersAfterDate(SearchOrdersByDateVm orderVm);

    List<GetOrderDetailsVm> getOrderDetailsById(int orderId);

}

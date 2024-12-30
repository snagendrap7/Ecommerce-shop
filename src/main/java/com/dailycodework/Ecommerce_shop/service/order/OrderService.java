package com.dailycodework.Ecommerce_shop.service.order;

import com.dailycodework.Ecommerce_shop.enums.OrderStatus;
import com.dailycodework.Ecommerce_shop.exception.ResourceNotFoundException;
import com.dailycodework.Ecommerce_shop.model.Cart;
import com.dailycodework.Ecommerce_shop.model.Order;
import com.dailycodework.Ecommerce_shop.model.OrderItem;
import com.dailycodework.Ecommerce_shop.model.Product;
import com.dailycodework.Ecommerce_shop.repositery.OrderRepository;
import com.dailycodework.Ecommerce_shop.repositery.ProductRepositery;
import com.dailycodework.Ecommerce_shop.service.cart.CartService;
import com.dailycodework.Ecommerce_shop.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    public final OrderRepository orderRepository;

    public final ProductRepositery productRepositery;

    private final ICartService iCartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart= iCartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItem(order,cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order saveOrder =orderRepository.save(order);
        iCartService.clearCart(cart.getId());

        return saveOrder;
    }

    private Order createOrder(Cart cart){
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }


    private List<OrderItem> createOrderItem(Order order, Cart cart){
        return cart.getItems().
                stream()
                .map(cartItem ->{
                    Product product=cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepositery.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice()
                    );
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemsList){
        return orderItemsList
                .stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<Order> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);
    }


}

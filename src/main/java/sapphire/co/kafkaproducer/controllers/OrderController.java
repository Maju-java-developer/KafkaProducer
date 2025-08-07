package sapphire.co.kafkaproducer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sapphire.co.kafkaproducer.dto.OrderDto;
import sapphire.co.kafkaproducer.entity.OrderEntity;
import sapphire.co.kafkaproducer.services.OrderService;
import sapphire.co.kafkaproducer.utils.ResponseDto;

@AllArgsConstructor
@RequestMapping("Order")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("placed")
    public ResponseEntity<?> placedOrder(@RequestBody OrderDto order) {
        // produce to kafka
        OrderEntity orderEntity = orderService.placeOrder(order.getUserId(), order.getProductId(), order.getQuantity());
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Order placed successfully");
        responseDto.setCode(0);
        responseDto.setData(orderEntity);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("test")
    public ResponseEntity<?> test() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Test Service successfully");
        responseDto.setCode(0);
        return ResponseEntity.ok(responseDto);
    }

}

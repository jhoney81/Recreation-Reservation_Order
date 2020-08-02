package Recreation.Reservation;

import Recreation.Reservation.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    OrderRepository orderRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRoomCompleted_RoomCompletionNotify(@Payload RoomCompleted roomCompleted){

        if(roomCompleted.isMe() && roomCompleted.getStatus()!=null){

            Optional<Order> orders = orderRepository.findById(roomCompleted.getOrderId());
            orders.get().setId(roomCompleted.getId());
            orders.get().setStatus("ROOMORDERCOMPLETE");
            orderRepository.save(orders.get());

            System.out.println("##### listener RoomCompletionNotify : " + roomCompleted.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelAccepted_OrderCancelAccept(@Payload OrderCancelAccepted orderCancelAccepted){

        if(orderCancelAccepted.isMe() && orderCancelAccepted.getStatus()!=null){

            Optional<Order> orders = orderRepository.findById(orderCancelAccepted.getOrderId());
            orders.get().setId(orderCancelAccepted.getId());
            orders.get().setStatus("ORDERCANCELED");
            orderRepository.save(orders.get());

            System.out.println("##### listener OrderCancelAccept : " + orderCancelAccepted.toJson());
        }
    }

}

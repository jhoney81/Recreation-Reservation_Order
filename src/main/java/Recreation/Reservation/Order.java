package Recreation.Reservation;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private Long roomId;
    private String roomName;
    private Long customerId;
    private String orderDate;

    @PostPersist
    public void onPostPersist(){

        if(this.getStatus().equals("ROOMORDED")){
            //System.out.println("=========ROOMORDED==================");
            RoomOrdered roomOrdered = new RoomOrdered();
            BeanUtils.copyProperties(this, roomOrdered);
            roomOrdered.publishAfterCommit();
        }

    }

    @PostUpdate
    public void onPostUpdate(){

        if(this.getStatus().equals("CANCELORDERED")){
            //System.out.println("00000000000JOINORDED000000000000");
            CancelOrdered cancelOrdered = new CancelOrdered();
            BeanUtils.copyProperties(this, cancelOrdered);
            cancelOrdered.publishAfterCommit();
        }else if(this.getStatus().equals("ORDERCANCELED")){
            // System.out.println("00000000000ORDERCANCELED000000000000");
            OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.setStatus("ORDERCANCELED");
            orderCanceled.publishAfterCommit();
        }else if(this.getStatus().equals("ROOMORDERCOMPLETE"))
        {
            // System.out.println("00000000000000ROOMORDERCOMPLETE000000000");
            RoomOrderCompleted roomOrderCompleted = new RoomOrderCompleted();
            BeanUtils.copyProperties(this, roomOrderCompleted);
            roomOrderCompleted.publishAfterCommit();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }




}

package com.paytracker.notification_service.Listener;

import com.paytracker.notification_service.Config.RabbitMQConfig;
import com.paytracker.notification_service.Dto.RenewalEvent;
import com.paytracker.notification_service.Entity.Notification;
import com.paytracker.notification_service.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RenewalEventListener {

    private final NotificationRepository notificationRepository;

    // @RabbitListener means: "watch this queue, and run this method automatically
    // whenever a new message shows up." We don't call this method ourselves —
    // Spring calls it for us in the background.
    @RabbitListener(queues = RabbitMQConfig.RENEWAL_QUEUE)
    public void handleRenewalEvent(RenewalEvent event) {

        // Build a human-readable message
        String message = event.getSubscriptionName() + " renews on " + event.getRenewalDate()
                + " for ₹" + event.getPrice();

        // Save it as a notification record
        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .message(message)
                .subscriptionName(event.getSubscriptionName())
                .amount(event.getPrice())
                .relevantDate(event.getRenewalDate())
                .build();

        notificationRepository.save(notification);

        // Stand-in for "sending an email/push notification" — for a college
        // project, printing it clearly to the console is enough to demonstrate
        // the alert was actually triggered.
        System.out.println("=============================================");
        System.out.println("NOTIFICATION SENT to user " + event.getUserId() + ":");
        System.out.println(message);
        System.out.println("=============================================");
    }
}
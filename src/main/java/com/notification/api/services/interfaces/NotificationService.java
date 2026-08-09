package com.notification.api.services.interfaces;

import com.notification.api.models.request.SendNotifRequest;

public interface NotificationService {

    void sendNotification(SendNotifRequest request);

}

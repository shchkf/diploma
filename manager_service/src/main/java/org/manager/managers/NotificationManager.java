package org.manager.managers;

import org.manager.dao.NotificationDao;
import org.manager.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationManager {

    @Autowired
    private NotificationDao notificationDao;

    public Notification createNotification(Notification notification) {
        // Check if the notification already exists
        Notification existingNotification = notificationDao.findById(notification.getTitle(), notification.getDescription());
        if (existingNotification != null) {
            // If the notification already exists, return the existing one
            return existingNotification;
        }
        // If the notification doesn't exist, create a new one and save it
        return notificationDao.create(notification);
    }

    public List<Notification> getAllNotifications(String typeFilter, String priorityFilter) {
        // Create the query builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);
        Root<Notification> root = cq.from(Notification.class);

        // Add filters to the query
        List<Predicate> predicates = new ArrayList<>();
        if (typeFilter != null && !typeFilter.isEmpty()) {
            predicates.add(cb.equal(root.get("type"), typeFilter));
        }
        if (priorityFilter != null && !priorityFilter.isEmpty()) {
            predicates.add(cb.equal(root.get("priority"), priorityFilter));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Execute the query and return the result
        return entityManager.createQuery(cq).getResultList();
    }

    public Notification getNotificationById(Long id) {
        return notificationDao.findById(id);
    }

    public Notification updateNotification(Long id, Notification updatedNotification) {
        Notification existingNotification = notificationDao.findById(id);
        if (existingNotification == null) {
            return null;
        }

        // Check if the notification has been changed
        boolean hasChanged = false;
        if (!Objects.equals(existingNotification.getTitle(), updatedNotification.getTitle())) {
            existingNotification.setTitle(updatedNotification.getTitle());
            hasChanged = true;
        }
        if (!Objects.equals(existingNotification.getContent(), updatedNotification.getContent())) {
            existingNotification.setContent(updatedNotification.getContent());
            hasChanged = true;
        }
        if (existingNotification.isReadStatus() != updatedNotification.isReadStatus()) {
            existingNotification.setReadStatus(updatedNotification.isReadStatus());
            hasChanged = true;
        }

        // If the notification has been changed, update it
        if (hasChanged) {
            return notificationDao.update(existingNotification);
        } else {
            return existingNotification;
        }
    }

    public void deleteNotification(Long id) {
        notificationDao.delete(id);
    }
}

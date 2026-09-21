package com.fitlog.app.data.sync

/**
 * Real-time push notifications (workout reminders, weekly recap) are a
 * POE-phase requirement per the assignment brief. This class is scaffolded
 * now so the architecture matches the Part 1 design document (Section 2.6),
 * ready to be completed once a Firebase project + google-services.json are
 * added.
 *
 * To activate: add the firebase-messaging dependency in app/build.gradle.kts
 * (already listed there, commented out), extend
 * com.google.firebase.messaging.FirebaseMessagingService, override
 * onMessageReceived to post a local notification via NotificationCompat,
 * and override onNewToken to call
 * ApiService.registerDevice(RegisterDeviceRequest(token)) so the backend
 * knows where to deliver pushes for this device. Then uncomment the
 * <service> entry in AndroidManifest.xml.
 */
class FitLogMessagingService

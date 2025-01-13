package com.charitan.management.project.internal

internal enum class ProjectProducerTopic(
    val topic: String,
) {
    PROJECT_GET_ID("donation.get.projectId"),
    PROJECT_HALT("project.halt"),
    PROJECT_APPROVE("project.approve"),
    EMAIL_PROJECT_HALT_CHARITY("email.project.halt.charity"),
    EMAIL_PROJECT_HALT_DONOR("email.project.halt.donor"),
    EMAIL_PROJECT_APPROVE("email.project.approve"),
    DONATION_GET_BY_PROJECT("donation.get.projectId"),
    NOTIFICATION_HALTED_PROJECT("notification.project.halt.donor"),
    PAYMENT_CANCEL_HALTED_PROJECT_SUBSCRIPTIONS("payment.halt-project-subscriptions"),
}

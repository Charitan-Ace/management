package com.charitan.management.project.internal

internal enum class ProjectProducerTopic(
    val topic: String,
) {
    PROJECT_HALT("project.halt"),
    PROJECT_CONFIRM("project.confirm"),
}

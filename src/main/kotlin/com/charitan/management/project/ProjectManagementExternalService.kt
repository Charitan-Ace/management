package com.charitan.management.project

interface ProjectManagementExternalService {
    suspend fun halt(projectId: String)
}

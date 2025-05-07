package com.mshdabiola.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Settings

val mainIcons =
    arrayOf(
        Icons.Default.Person,
        Icons.Default.Map,
        Icons.Default.Explore,
        Icons.Default.Bookmarks,
        Icons.Default.Reviews,
        Icons.Default.Settings,
        Icons.Default.Info,
        Icons.Default.Feedback,
        Icons.Default.Insights,
        Icons.AutoMirrored.Filled.Logout,
    )
val mainRoute =
    arrayOf(
        "contribute_route",
        "nearby_route",
        "explore_route",
        "bookmarks_route",
        "reviews_route",
    )

val settingIcons =
    arrayOf(
        Icons.Default.Settings,
        Icons.Default.Info,
        Icons.Default.Feedback,
        Icons.Default.Insights,
        Icons.AutoMirrored.Filled.Logout,
    )

val settingRoute =
    arrayOf(
        "settings_route",
        "info_route",
        "feedback_route",
        "about_route",
    )

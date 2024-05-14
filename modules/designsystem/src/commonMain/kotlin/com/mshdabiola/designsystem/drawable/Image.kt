package com.mshdabiola.designsystem.drawable

import androidx.compose.runtime.Composable
import hydraulic.modules.designsystem.generated.resources.Res
import hydraulic.modules.designsystem.generated.resources.compose_multiplatform
import hydraulic.modules.designsystem.generated.resources.logo
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource

val defaultAppIcon
    @Composable
    get() = imageResource(Res.drawable.logo)

val multiplatformIcon
    @Composable
    get() = vectorResource(Res.drawable.compose_multiplatform)
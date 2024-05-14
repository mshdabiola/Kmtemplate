package com.mshdabiola.designsystem.string

import androidx.compose.runtime.Composable
import hydraulic.modules.designsystem.generated.resources.Res
import hydraulic.modules.designsystem.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

val appName
    @Composable
    get() = stringResource(Res.string.app_name)
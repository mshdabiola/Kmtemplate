/*
 *abiola 2024
 */

package com.mshdabiola.data.model

import com.mshdabiola.database.model.ImageEntity
import com.mshdabiola.database.model.NoteEntity
import com.mshdabiola.datastore.model.UserDataSer
import com.mshdabiola.model.Image
import com.mshdabiola.model.Note
import com.mshdabiola.model.UserData

fun Note.asNoteEntity() = NoteEntity(id, title, content)
fun NoteEntity.asNote() = Note(id, title, content)

fun ImageEntity.asImage() = Image(
    descriptionShortUrl = descriptionShortUrl,
    descriptionUrl = descriptionUrl,
    mediaType = mediaType,
    mime = mime,
    timestamp = timestamp,
    url = url,
    user = user,
    userid = userid,
    id = id,
)

fun Image.asExternalImage() = ImageEntity(
    descriptionShortUrl = descriptionShortUrl,
    descriptionUrl = descriptionUrl,
    mediaType = mediaType,
    mime = mime,
    timestamp = timestamp,
    url = url,
    user = user,
    userid = userid,
    id = id,
)

fun UserData.toSer() =
    UserDataSer(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding)

fun UserDataSer.toData() =
    UserData(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding)

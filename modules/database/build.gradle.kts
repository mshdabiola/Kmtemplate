plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.room")

}

android {
    namespace = "com.mshdabiola.database"
}
room {
    schemaDirectory("$projectDir/schemas")
}
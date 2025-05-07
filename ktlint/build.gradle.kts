plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    implementation(libs.slf4j.simple)
    compileOnly(libs.kotlin.stdlib)
    testImplementation(libs.kotlin.test)
    compileOnly("com.pinterest.ktlint:ktlint-rule-engine:1.0.0")
    compileOnly("com.pinterest.ktlint:ktlint-ruleset-standard:1.0.0")
    testImplementation("com.pinterest.ktlint:ktlint-test:1.0.0")
}

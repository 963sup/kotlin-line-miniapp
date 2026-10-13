plugins {
    base
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.spring.boot) apply false
}

tasks.named("check") {
    dependsOn(":apps:server:check", ":apps:miniapp:check")
}

tasks.named("assemble") {
    dependsOn(":apps:server:bootJar", ":apps:miniapp:jsBrowserDistribution")
}

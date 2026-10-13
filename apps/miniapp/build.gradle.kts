import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    js {
        browser {
            // Add a browser test runner with the first frontend behavior.
            testTask {
                enabled = false
            }
            commonWebpackConfig {
                outputFileName = "miniapp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    port = 8081
                    open = false
                }
            }
        }
        binaries.executable()
    }
}

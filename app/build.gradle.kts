plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.labs.battery.alarm"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.labs.battery.alarm"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0") // replace with your version
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") // replace with your version

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material 3
    implementation("androidx.compose.material3:material3")

    // Compose Activity integration
    implementation("androidx.activity:activity-compose:1.9.0")

    // Navigation for Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")

    // Pager for onboarding screens
    implementation("com.google.accompanist:accompanist-pager:0.34.0")

    // Testing
    testImplementation("junit:junit:4.13.2") // replace with your version
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // replace with your version
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // replace with your version
}

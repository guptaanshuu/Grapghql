import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.apollo)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)

}

android {
    namespace = "com.example.myapplication"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
    buildFeatures {
        compose = true
    }

}

apollo {
    service("service") {
        packageName.set("com.example.myapplication")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Apollo GraphQL
    implementation(libs.apollo.runtime)
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.nav.compose)
    implementation(libs.hilt.nav.compose)
    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.litert.support.api)
    implementation(libs.androidx.core.splashscreen)
    ksp(libs.ksp.hilt)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


}
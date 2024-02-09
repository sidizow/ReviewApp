plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.reviewapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.reviewapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            correctErrorTypes = true
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso)

    implementation(libs.bundles.activity.fragment)
    implementation(libs.androidx.recyclerview)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Jetpack's navigation component
    implementation(libs.bundles.navigation)

    //Coroutines
    implementation(libs.coroutines)

    //Glide
    implementation(libs.glide)

    //Room
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(project(":data"))
    implementation(project(":feature:catalog"))
    implementation(project(":feature:film"))
    implementation(project(":feature:signin"))
    implementation(project(":feature:signup"))
    implementation(project(":core:common"))
    implementation(project(":navigation"))

}






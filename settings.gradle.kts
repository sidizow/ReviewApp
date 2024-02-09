pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            version("coroutines","1.7.1")
            version("androidx.activity","1.8.2")
            version("androidx.fragment","1.6.2")
            version("androidx.recyclerview","1.3.2")
            version("androidx.navigation","2.7.6")
            version("glide","4.16.0")
            version("room","2.6.1")
            version("hilt","2.50")

            library("androidx.core", "androidx.core:core-ktx:1.12.0")
            library("androidx.appcompat", "com.google.android.material:material:1.11.0")
            library("android.material", "com.google.android.material:material:1.11.0")
            library("androidx.constraintlayout", "androidx.constraintlayout:constraintlayout:2.1.4")
            library("junit", "junit:junit:4.13.2")
            library("androidx.test.ext", "androidx.test.ext:junit:1.1.5")
            library("androidx.test.espresso", "androidx.test.espresso:espresso-core:3.5.1")

            library("coroutines", "org.jetbrains.kotlinx","kotlinx-coroutines-android").versionRef("coroutines")
            library("androidx.activity", "androidx.activity","activity-ktx").versionRef("androidx.activity")
            library("androidx.fragment", "androidx.fragment","fragment-ktx").versionRef("androidx.fragment")
            library("androidx.recyclerview", "androidx.recyclerview","recyclerview").versionRef("androidx.recyclerview")
            library("androidx.navigation.fragment", "androidx.navigation","navigation-fragment-ktx").versionRef("androidx.navigation")
            library("androidx.navigation.ui", "androidx.navigation","navigation-ui-ktx").versionRef("androidx.navigation")
            library("glide", "com.github.bumptech.glide","glide").versionRef("glide")
            library("room.runtime", "androidx.room","room-runtime").versionRef("room")
            library("room.compiler", "androidx.room","room-compiler").versionRef("room")
            library("room.ktx", "androidx.room","room-ktx").versionRef("room")
            library("hilt.android", "com.google.dagger","hilt-android").versionRef("hilt")
            library("hilt.compiler", "com.google.dagger","hilt-android-compiler").versionRef("hilt")

            bundle("activity.fragment", listOf("androidx.fragment","androidx.activity"))
            bundle("room", listOf("room.runtime","room.ktx"))
            bundle("navigation", listOf("androidx.navigation.fragment","androidx.navigation.ui"))



        }
    }
}

rootProject.name = "ReviewApp"
include(":app")
include(":data")
include(":feature:catalog")
include(":feature:film")
include(":feature:signin")
include(":feature:signup")
include(":core:common")
include(":navigation")

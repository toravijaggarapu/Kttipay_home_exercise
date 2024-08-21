plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.kttipay.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kttipay.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/\"")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "API_KEY", "\"1a0939acd271d12c2807ad63603d452b\"")
            buildConfigField("String", "TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTA5MzlhY2QyNzFkMTJjMjgwN2FkNjM2MDNkNDUyYiIsIm5iZiI6MTcyNDExMzUxOS43ODUwNTQsInN1YiI6IjY2YzNkZmQyNjU3MmJiYzhkZDU2ODdmNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ShpyhIKw9vrZGJUxotnw7N_yb_4lZe5rDQrEMDp94x4\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
//            https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg
            buildConfigField("String", "IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/\"")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "API_KEY", "\"1a0939acd271d12c2807ad63603d452b\"")
            buildConfigField("String", "TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYTA5MzlhY2QyNzFkMTJjMjgwN2FkNjM2MDNkNDUyYiIsIm5iZiI6MTcyNDExMzUxOS43ODUwNTQsInN1YiI6IjY2YzNkZmQyNjU3MmJiYzhkZDU2ODdmNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ShpyhIKw9vrZGJUxotnw7N_yb_4lZe5rDQrEMDp94x4\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    kotlinOptions {
//        jvmTarget = "17"
//    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // ================ ANDROID ================
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ================ Jetpack ================
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.jetbrains.kotlinx.coroutines)

    // ============ OKHTTP & RETROFIT =============
    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.gson.convertor)
    implementation(libs.squareup.logging.interceptor)

    // =============== Glide ===============
    implementation(libs.github.glide.compose)

    // ================ HILT ================
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.compose)
    kapt(libs.hilt.compiler)

    // ================ UNIT TEST ================
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "app.api.json"
    compileSdk = 33

    defaultConfig {
        applicationId = "app.api.json"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Load in the app's configuration:
        apply(from = "config.gradle.kts")
        val appName = extra["appName"] as String
        val apiUrl = extra["apiUrl"] as String
        val titleField = extra["titleField"] as String
        val urlField = extra["urlField"] as String
        val thumbnailUrlField = extra["thumbnailUrlField"] as String
        buildConfigField("String", "API_URL", "\"$apiUrl\"")
        buildConfigField("String", "TITLE_FIELD", "\"$titleField\"")
        buildConfigField("String", "URL_FIELD", "\"$urlField\"")
        buildConfigField("String", "THUMBNAIL_URL_FIELD", "\"$thumbnailUrlField\"")
        manifestPlaceholders["APP_NAME"] = appName
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("com.google.code.gson:gson:2.8.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
<<<<<<< HEAD
    namespace = "com.example.maps"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.maps"
        minSdk = 27
        targetSdk = 34
=======
    namespace = "com.example.scrumapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.scrumapp"
        minSdk = 27
        targetSdk = 33
>>>>>>> 2688e5fe726ccf50e720a8f8d5bc3b1e31ab8532
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
<<<<<<< HEAD
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.room:room-ktx:2.6.1")
=======
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
>>>>>>> 2688e5fe726ccf50e720a8f8d5bc3b1e31ab8532
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
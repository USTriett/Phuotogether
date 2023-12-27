plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.phuotogether"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.phuotogether"
        minSdk = 30
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val lifecycle_version = "2.6.2"

        // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
        // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
        // Lifecycles only (without ViewModel or LiveData)
    implementation ("androidx.lifecycle:lifecycle-runtime:$lifecycle_version")

        // Saved state module for ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")

        // Annotation processor
    annotationProcessor ("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")
        // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation ("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")




    implementation("com.kyleduo.switchbutton:library:2.1.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    implementation("androidx.preference:preference:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Maps SDK for Android
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    // Location SDK for Android
    implementation ("com.google.android.gms:play-services-location:19.0.1")

    // Places SDK for Android
    implementation ("com.google.android.libraries.places:places:3.3.0")

    // volley
    implementation ("com.android.volley:volley:1.2.1")

}
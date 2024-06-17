import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp").version("1.6.0-1.0.1")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}



android {
    namespace = "com.example.newsapp"
    compileSdk = 34

    defaultConfig {
        val secretFile = rootProject.file("secret.properties")
        val secretProperties = Properties().apply {
            val inputStream = FileInputStream(secretFile)
            load(inputStream)
        }
        buildConfigField("String", "API_KEY", secretProperties.getProperty("NEWS_API_KEY")?:"")
        buildConfigField("String", "geminiApiKey", secretProperties.getProperty("GEMINI_API_KEY")?: "")
        applicationId = "com.example.newsapp"
        minSdk = 26
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    //viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    // retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    // moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")
    //palette API
    implementation("androidx.palette:palette:1.0.0")
    //firebase
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))


    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.13.0")
    // room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-paging:2.6.1")
    // paging
    implementation ("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation ("androidx.paging:paging-compose:3.3.0-alpha02")
    // coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    // navigation
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    // preference datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // material
    implementation ("androidx.compose.material:material:1.6.2")
    // glance
    implementation ("androidx.glance:glance-appwidget:1.0.0")
    implementation ("androidx.glance:glance-material:1.0.0")
    implementation ("androidx.glance:glance-material3:1.0.0")
    // gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
//    implementation ("com.github.MahboubehSeyedpour:jetpack-loading:1.1.0")


    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}
kapt {
    correctErrorTypes = true
}
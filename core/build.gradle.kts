plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.xsis.core"
    compileSdk =  ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding =  true
    }
}

dependencies {

    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    testImplementation(Deps.junit)

    implementation (Deps.daggerHilt)
    kapt (Deps.daggerHiltCompiler)
    implementation (Deps.activity)
    implementation (Deps.fragment)
    implementation (Deps.roomRuntime)
    kapt (Deps.roomCompiler)
    implementation (Deps.coroutinesCore)
    implementation (Deps.coroutines)
    implementation (Deps.room)
    api (Deps.lifeData)
    implementation (Deps.timber)
    implementation (Deps.recyclerview)
    implementation (Deps.glide)
    kapt (Deps.glideCompiler)
    implementation (Deps.circleimageview)
    implementation (Deps.retrofit)
    implementation (Deps.retrofitGson)
    implementation (Deps.retrofitMoshi)
    implementation (Deps.retrofitRxJava)
    implementation (Deps.okhttp3Logging)
    implementation (Deps.okhttp3)
    implementation (Deps.sqlCipher)
    implementation (Deps.sqlite)
}
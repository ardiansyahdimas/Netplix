plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.xsis.netplix"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.xsis.netplix"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding =  true
    }
}

dependencies {
    implementation (project(":core"))
    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    implementation(Deps.navFragment)
    implementation(Deps.navUi)
    testImplementation(Deps.junit)

    implementation (Deps.daggerHilt)
    kapt (Deps.daggerHiltCompiler)
    implementation (Deps.activity)
    implementation (Deps.fragment)
    implementation (Deps.coroutinesCore)
    implementation (Deps.coroutines)
    api (Deps.lifeData)
    implementation (Deps.timber)
    implementation (Deps.recyclerview)
    implementation (Deps.glide)
    kapt (Deps.glideCompiler)
    implementation (Deps.circleimageview)
    implementation (Deps.lottie)
    implementation (Deps.indicator)
}
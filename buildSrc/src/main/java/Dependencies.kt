/**
 * To define dependencies
 */
object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val core by lazy { "androidx.core:core-ktx:${Versions.core}"}
    val daggerHilt by lazy { "com.google.dagger:hilt-android:${Versions.daggerHilt}" }
    val daggerHiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltCompiler}" }
    val activity by lazy { "androidx.activity:activity-ktx:${Versions.activity}" }
    val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.fragment}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val coroutinesCore by lazy {"org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"}
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val room by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val lifeData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}" }
    val recyclerview by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerView}" }
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glideCompiler}" }
    val circleimageview by lazy { "de.hdodenhof:circleimageview:${Versions.circleImage}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val retrofitMoshi by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val retrofitRxJava by lazy { "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}" }
    val okhttp3Logging by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Logging}" }
    val okhttp3 by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp3}" }
    val sqlCipher by lazy { "net.zetetic:android-database-sqlcipher:${Versions.sqlCipher}" }
    val sqlite by lazy { "androidx.sqlite:sqlite-ktx:${Versions.sqlite}" }
    val lottie by lazy { "com.airbnb.android:lottie:${Versions.lottie}" }
    val indicator by lazy {"ru.tinkoff.scrollingpagerindicator:scrollingpagerindicator:${Versions.indicator}"}
    val navFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.nav}" }
    val navUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.nav}"}
}
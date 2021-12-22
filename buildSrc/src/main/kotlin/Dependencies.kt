object Versions {
    const val constrain_layout = "2.0.4"
    const val app_compat = "1.2.0"
    const val material = "1.3.0"
    const val lifecycle = "2.3.1"
    const val core = "1.3.2"
    const val kotlin_stdlib = "1.3.72"
    const val hilt = "2.35"
    const val okhttp3 = "4.9.1"
    const val okhttp_logging_interceptor = "4.9.0"
    const val gson = "2.8.6"
    const val retrofit = "2.9.0"
    const val coroutines = "1.4.1"
    const val glide = "4.11.0"
    const val navigation = "2.3.5"
    const val room = "2.3.0"
    const val mockk = "1.10.2"
    const val truth = "1.1"
    const val androidx_test_ext = "1.1.2"
    const val androidx_test_core = "1.3.0"
    const val core_testing = "2.1.0"
    const val coroutines_test = "1.4.1"
    const val espresso_core = "3.3.0"
    const val junit = "4.13.2"
}

object KotlinDependencies {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_stdlib}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_stdlib}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object AndroidXDependencies {
    val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    val constraintLayout ="androidx.constraintlayout:constraintlayout:${Versions.constrain_layout}"
    val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val room = "androidx.room:room-ktx:${Versions.room}"
    val liveData ="androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val viewModel ="androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object MaterialDesignDependencies {
    val materialDesign = "com.google.android.material:material:${Versions.material}"
}

object HiltDependencies {
    val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
}

object OkhttpDependencies {
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"
}

object Gson {
    val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object RetrofitDependencies {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object ImageLoadingDependencies {
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
}

object AndroidXTestDependencies {
    val junitKtx = "androidx.test.ext:junit-ktx:${Versions.androidx_test_ext}"
    val coreKtx = "androidx.test:core-ktx:${Versions.androidx_test_core}"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.core_testing}"
    val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
}

object TestDependencies {
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val truth = "com.google.truth:truth:${Versions.truth}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_test}"
    val junit = "junit:junit:${Versions.junit}"
}

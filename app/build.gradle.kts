//`alias(libs.plugins.android.application)`: Mengaplikasikan Plugin Android Application utama yang
//menyediakan fungsi kompilasi, pembuatan APK/AAB, manajemen resource, dan alur build aplikasi
//Android.

//`alias(libs.plugins.kotlin.android)`: Mengaktifkan dukungan kompilasi bahasa Kotlin untuk
//platform Android, memungkinkan Gradle mengompilasi berkas `.kt` menjadi bytecode Android
//(DEX).
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

//`namespace`: Menentukan package name unik untuk kelas R dan Manifest yang dihasilkan
//otomatis secara internal oleh proses kompilasi.

//`compileSdk`: Versi Android SDK tertinggi yang digunakan untuk mengompilasi kode sumber
//aplikasi. Menentukan ketersediaan API Android modern yang dapat digunakan dalam kode.
android {
    namespace = "com.example.mobileadvapp"
    compileSdk = 36

//Blok `defaultConfig`: Mengatur atribut dasar aplikasi seperti `applicationId` (ID unik aplikasi di
//Google Play Store), `minSdk` (versi Android minimum yang didukung), `targetSdk` (versi Android
//tempat aplikasi diuji optimal), serta versi rilis (`versionCode` & `versionName`).
    defaultConfig {
        applicationId = "com.example.mobileadvapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

//Blok `buildFeatures`: Mengaktifkan atau menonaktifkan fitur khusus platform Android. Mengatur
//`viewBinding = true` akan merintis generasi otomatis kelas binding untuk tiap berkas XML layout
//secara type-safe dan null-safe.
    buildFeatures {
        viewBinding = true
    }
}

//`implementation(...)`: Mendaftarkan pustaka ke dalam classpath kompilasi dan runtime modul.
//Library ini dikemas ke dalam APK akhir tetapi tidak diekspos secara transitif ke modul lain.

//`testImplementation(...)`: Pustaka yang hanya disertakan saat menjalankan Unit Test lokal di
//mesin pengembang (JVM).

//`androidTestImplementation(...)`: Pustaka khusus untuk pengujian instrumentasi UI yang berjalan
//langsung di atas perangkat fisik atau emulator Android.
dependencies {

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
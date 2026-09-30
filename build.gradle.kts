// Top-level build file where you can add configuration options common to all sub-projects/modules.

//`alias(libs.plugins.android.application)`: Mengaplikasikan Plugin Android Application utama yang
//menyediakan fungsi kompilasi, pembuatan APK/AAB, manajemen resource, dan alur build aplikasi
//Android.

//`alias(libs.plugins.kotlin.android)`: Mengaktifkan dukungan kompilasi bahasa Kotlin untuk
//platform Android, memungkinkan Gradle mengompilasi berkas `.kt` menjadi bytecode Android
//(DEX).
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
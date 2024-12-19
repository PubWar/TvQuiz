import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }


    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
//            implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

            implementation("androidx.media3:media3-exoplayer:1.1.0")
            implementation("androidx.media3:media3-exoplayer-dash:1.1.0")
            implementation("androidx.media3:media3-ui:1.1.0")

            implementation(libs.ktor.client.okhttp)
            implementation("dev.whyoleg.cryptography:cryptography-provider-jdk")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.qr)

            api(compose.animation)

            api(libs.datastore.preferences)
            api(libs.datastore)

            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)

            implementation(libs.compose.util)

            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.4.0")
            /// Compose 1.7.0-alpha03
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.5.0-alpha03")
            /// Compose 1.7.0-alpha03 with different tech.annexflow.constraintlayout.core package
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.5.0-alpha03-shaded-core")
            /// Compose 1.7.0-alpha03 with different tech.annexflow.constraintlayout package
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.5.0-alpha03-shaded")

            implementation(libs.bundles.ktor)

            implementation(project.dependencies.platform("dev.whyoleg.cryptography:cryptography-bom:0.3.1"))
            implementation("dev.whyoleg.cryptography:cryptography-core")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("dev.whyoleg.cryptography:cryptography-provider-openssl3-prebuilt")

        }
    }
}


buildkonfig {
    packageName = "com.pubwar.quiz"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "BASE_URL", "default")
        buildConfigField(FieldSpec.Type.STRING, "PACKAGE", packageName)
        buildConfigField(
            FieldSpec.Type.STRING,
            "HEX_KEY",
            "5DC069F784BA37BC8898384799DBFF9FED6B84CBD481A4793DB264FC8EE1D0B4"
        )
        buildConfigField(FieldSpec.Type.STRING, "HEX_IV", "000102030405060708090a0b0c0d0e0f")
        buildConfigField(
            FieldSpec.Type.STRING,
            "BASE_URL",
            "https://pwapi2-d2ajagdefwcncwc5.westeurope-01.azurewebsites.net/"
        )
        buildConfigField(FieldSpec.Type.STRING, "FLAVOR", "default")
    }
}

android {
    namespace = "com.pubwar.quiz"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")


    defaultConfig {
        applicationId = "com.pubwar.quiz"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["app_name"] = "Slagalica"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        implementation(libs.compose.ui.tooling.preview)

    }
}
dependencies {
//    implementation(libs.androidx.constraintlayout)
}


# SleepLog

SleepLog is easy to use application for record the sleep quality with possibility to sort and update the records. Records are stored locally in the device.
## Update for 2024 in sleepLogRefactor2024 

 ### Functionality
 - Same as master version

## Improvements
- Composables now introduces states and events instead of viewModel directly
- Styles and composables improvements for easier re-use and maintenance
- Migration dependencies to newer versions
- Room and Dagger Hilt dependencies are using KSP (Kotlin Symbol Processing) for faster build times instead of previous used kapt - read more: https://developer.android.com/build/migrate-to-ksp
- Support for Material Design 3

## Fixes
- Date picker is now clickable only once

## Support Links
- https://developer.android.com/training/data-storage/room
- https://dagger.dev/hilt/

## Kotlin version
- 1.9.0

## Compose version
- 1.5.1

## Libraries used
- https://github.com/ChargeMap/Compose-NumberPicker - Number Picker Library

## Dependencies
Compose dependencies

```bash
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2"
    implementation "androidx.navigation:navigation-compose:2.7.6"
    implementation "androidx.compose.material:material-icons-extended:$compose_version"
    implementation "androidx.hilt:hilt-navigation-compose:1.1.0"
```

Material components

```bash
    implementation 'com.google.android.material:material:1.11.0'
```

Coroutines

```bash
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'
```

Dagger - Hilt

```bash
    implementation "com.google.dagger:hilt-android:2.50"
    ksp "com.google.dagger:hilt-compiler:2.48"
```
Room

```bash
  implementation "androidx.room:room-runtime:2.6.1"
  ksp "androidx.room:room-compiler:2.6.1"
```

Kotlin Extensions and Coroutines support for Room

```bash
    implementation "androidx.room:room-ktx:2.6.1"
```

Conversion of unix time

```bash
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
```

Plugins

```bash
plugins {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}
```

## Notes
Aplication is under development. If any questions arise reach me out :-)


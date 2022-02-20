# SleepLog

SleepLog is easy to use application for record the sleep quality with possibility to sort and update the records. Records are stored locally in the device.


Sleep List             |  Sorting option             |  Add sleep
:-------------------------:|:-------------------------:|:-------------------------:
![](https://i.ibb.co/44FW7mh/Sleep-list.jpg)  |  ![](https://i.ibb.co/4Pp22h0/Sleep-list-selection.jpg)   |  ![](https://i.ibb.co/0r39XWR/Add-sleep.jpg)



 ### Functionality
 - User can create / edit / delete sleep record.
 - Evaluate sleep with one of the sleep quality options. 
 - Add date of the sleep.
 - Add duration of sleep.
 - Add sleep description (optional).
 - Record list can be sorted based on the date and quality of the sleep.
 - Item color is selected based on the sleep quality.

## Used
- Kotlin
- Jetpack Composable
- Room Database
- Dagger Hilt
- Coroutines
- Clean Architecture, MVVM, UseCases, ViewModels

## Support Links
- https://developer.android.com/training/data-storage/room
- https://dagger.dev/hilt/


## Libraries used
- https://github.com/ChargeMap/Compose-NumberPicker - Number Picker Library

## Dependencies
Compose dependencies

```bash
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-alpha02"
    implementation "androidx.navigation:navigation-compose:2.5.0-alpha02"
    implementation "androidx.compose.material:material-icons-extended:$compose_version"
    implementation "androidx.hilt:hilt-navigation-compose:1.0.0"
```

Material components

```bash
    implementation 'com.google.android.material:material:1.5.0'
```

Coroutines

```bash
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'
```

Dagger - Hilt

```bash
    implementation "com.google.dagger:hilt-android:2.39.1"
    kapt "com.google.dagger:hilt-android-compiler:2.39.1"
    implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    kapt "androidx.hilt:hilt-compiler:1.0.0"
```
Room

```bash
    implementation "androidx.room:room-runtime:2.4.1"
    kapt "androidx.room:room-compiler:2.4.1"
```

Kotlin Extensions and Coroutines support for Room

```bash
    implementation "androidx.room:room-ktx:2.4.1"
```

Conversion of unix time

```bash
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
```

Plugins

```bash
plugins {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
    }
}
```

## Notes
Aplication is in developing process. I am not a proffesional, I try to learn new techniques.


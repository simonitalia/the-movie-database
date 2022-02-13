# The Movie Database (Tmdb)

## About
This app displays a list of Popular and Top Rated Movies.

# App Specifications 

## User Interface and Key Features
This app consists of 3 main screens as follows:

### Movie List Screen
- This screen retrieves and displays two lists in a horizontal layout: i) Popular Movies, ii) Top Rated Movies,
- Movies are fetched from themoviedatabase.org web service using Retrofit and Gson for data serialization,
- Movie poster images are loaded using Glide,
- Binding Apdapters dynamically update layout views with data,
- Tapping on a movie item poster image will navigate the user to the movie details screen,
- User can naviagte from this screen to the Favorites Screen from the Navigation App drawer,

### Movie Detail Screen
- This screen displays more information about a movie,
- The movie can be added to a Favorites list by tapping on the fab (floating action button),
- Favorites are saved to a local (Room) database,
- Motion Layout is used to animate the fab,
- The fab icon indicates if the movie is saved or not to favorites and is dynamically updated when the movie is added or removed from favorites,
- User can navigate from this screen to the Favorites Screen from the Action Bar overflow options menu.

### Favorites Screen
- This screen displays a list of all the movies currently saved to favorites,
- Favorites are fetched from the local (Room) database,
- Tapping on a movie item will navigate the user to the Movie Detail Screen.

## Gesture Capture Detection
- This app supports shake device hardware feature,
- When the user is on the main / Movie List Screen, shaking the device will randomly selecte a Movie and navigate the user to the Movie Detail Screen for that randomly selected movie (Note! Enough force is required to for the device to register the shake).
- This feature is powered by the SensoryManager framework


# Core Technologies, Frameworks and Features

- Room (Local Database)

- Glide

- Retrofit

- Gson

- MVVM (ViewModel, LiveData)

- Motion Layout

- SensorManager (for shake hardware feature)

- Custom Action Bar Toolbar

- Overflow Options Menu

- Navigation App Drawer


# Setup

## Installation
This project's repository can be cloned via git or downloaded as a zip file.

## The Movie Database (Tmdb) API Key Registration
- This app connects to a web service provided by https://themoviedb.org and requires registration of a free API key,
- If required, go to the tmdb website (url above), create an account or login, 
- Go to the Settings page, API section and request a new API Key,
- Once the API keys are generated, copy the v3 key,
- In the project repo (once downloaded or cloned) open the TmdbApi file and replace the API_KEY string value "YOUR_V3_API_KEY_HERE" with the copied API key.

## Project Plugins
id 'com.android.application'
id 'kotlin-android'
id 'kotlin-kapt'
id 'androidx.navigation.safeargs.kotlin'
id 'kotlin-parcelize'

## Dependencies
### Project
classpath 'com.android.tools.build:gradle:7.1.1'
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0-alpha01"

### Module
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'androidx.recyclerview:recyclerview:1.2.1'
implementation 'androidx.cardview:cardview:1.0.0'
implementation fileTree(dir: 'libs', include: ['*.jar'])
implementation 'androidx.core:core-ktx:1.7.0'
implementation 'androidx.appcompat:appcompat:1.4.1'
implementation 'com.google.android.material:material:1.5.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.3'

### Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

### Gson
implementation 'com.google.code.gson:gson:2.8.9'

### Glide
implementation 'com.github.bumptech.glide:glide:4.12.0'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
kapt 'com.github.bumptech.glide:compiler:4.12.0'

### Fragment Navigation
implementation 'androidx.navigation:navigation-fragment-ktx:2.4.0'
implementation 'androidx.navigation:navigation-ui-ktx:2.4.0'

### Room
implementation "androidx.room:room-ktx:$roomVersion"
implementation "androidx.room:room-runtime:$roomVersion"
kapt "androidx.room:room-compiler:$roomVersion"
roomVersion = '2.4.1'


## Built Using
* [Android Studio](https://developer.android.com/studio) - Default IDE used to build android apps
* [Kotlin](https://kotlinlang.org/) - Default language used to build this project


## Deployment information
- <strong>Deployment Target (android API / Version):</strong> 31 / Android 12 (S)

## App Versions
- February, 2022 (Major version 1)
- February, 2022 (Minor version 1.1)

## License
Please review the following [license agreement](https://github.com/simonitalia/the-movie-database/blob/main/LICENSE)

# Android technical test using the GitHub API

Android app in Kotlin that utilises the [GitHub API](https://developer.github.com/v3/) to allow a user to search for a repository by name.

## Technologies used:
* Android SDK
* Kotlin
* Data Binding
* View Binding
* [Retrofit](https://github.com/square/retrofit)
* [Moshi](https://github.com/square/moshi)
* Coroutines
* LiveData
* ViewModels
* Repository Pattern
* [Dagger Hilt](https://dagger.dev/hilt/) - Dependency Injection
* [Stetho](http://facebook.github.io/stetho/) - Inspect network calls made by the app using Chrome Developer Tools
* [Timber](https://github.com/JakeWharton/timber) - Improved logging
* [Markwon](https://noties.io/Markwon/) - Parse Markdown from the README.md


## Build instructions:
* Clone the repository
* From Android Studio:
    * Open project from the folder you cloned it into.
    * From the menu select **Run** and then **Run 'App'**.
* From the command line:
    * MacOs/Linux:
        `./gradlew installDebug`
    * Windows:
        `gradlew installDebug`

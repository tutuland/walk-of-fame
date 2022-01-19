# WoF Core

Kotlin Multiplatform library responsible for providing a motor for the business logic of the apps.

Currently, it supports the following platforms:

- Android
- Desktop (through JVM)
- iOS

### Building the Library

To build the library, you may have to [set up your environment for KMM](https://kotlinlang.org/docs/kmm-setup.html), 
in case you haven't done it before.

Also, it's possible that your will have to set you JAVA_HOME to point do JDK11. I did it in my global gradle.properties 
file, setting up `org.gradle.java.home` to point to the embedded JDK of my Android Studio. 

Also, this library uses [The Movie DB api](https://www.themoviedb.org/about), which requires an api key. 
<br>You can learn more about the api and signup for a free api key [here.](https://www.themoviedb.org/documentation/api)

After you aquire your api key, please include it on your local.properties file as: `com.tutuland.wof.core.apikey`
<br>*(it's always good practice not to commit keys and secrets to source control)*

***Note:***
<br>`Until enough test coverage is implemented, a driver application is available to quickly validate the core library on each platform.`

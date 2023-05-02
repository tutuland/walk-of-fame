# Walk of Fame

![Walk of Fame logo](.github/wof-01.jpg)

***You decide who gets to be the star!***

---

### What?

Walk Of Fame is an exercise in code sharing and modularization:

- First, it used [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) to develop a core library
  containing the business logic and external dependencies, deploying to Android, iOS, JVM for Desktop and JS.
- This core lib is deployed as a published artifact to multiple platforms.
- For each platform a client implementation will be provided and the published artifact included as a dependency.
    - For Android, Desktop and iOS [Jetpack Compose](https://developer.android.com/jetpack/compose) and
      [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) were used to build the UI,
      sharing most of the components (see [wof-compose](/wof-compose))
    - JS uses [Compose for Web](https://github.com/JetBrains/compose-jb/tree/master/tutorials/Web)
    - A JVM based CLI application built with [kotlin-inquirer](https://github.com/kotlin-inquirer/kotlin-inquirer)
- The application will be using [The Movie DB api](https://www.themoviedb.org/about) to:
    - Search for personalities listed in the DB
    - See details about them and their work

### Why?

Modern applications are growing in complexity, and requiring several specialized teams. Since each of these teams
usually have their own pipeline and roadmap of activities, it is hard to always keep them in sync.

Code sharing and multiplatform technologies came to aid in that fragmentation issue, by allowing to centralize business
logic and minimize the duplication of work. But truth is it may end up creating some friction, since it's not always
easy to convince all developers from all teams involved to learn the multiplatform technology and its tools.

So why not create yet ***another*** specialized team *(at least in concept)* responsible for a core library that uses
these technologies internally, but export artifacts native to each platform, and that can be used on a regular
application, the way native developers are used to? This way, each one can choose in which parts of the application to
be involved in.

As a bonus, we keep separated builds for different parts of each application, reducing possible dependencies and tooling
issues.

***Disclaimer:***
<br>*This is an exercise to test and exemplify the hypothesis above.*
<br>*I am by no means an expert on the subject, and doing it to challenge myself and learn while at it.*
<br>*Hopefully, by the end of it I can share my findings on what works and what doesn't :)*

### How?

**[wof-core](/wof-core)** is a Kotlin Multiplatform library where the business logic motor lives.

**[wof-compose](/wof-compose)** is where the Android, Desktop and iOS clients are defined. Using Compose library,
they can share UI components.

**[wof-web](/wof-web)** is a web client built with Compose for Web.

**[wof-cli](/wof-cli)** is a simple CLI java application.

### Who?

*Design - [melocs](https://github.com/melocs)*
<br>*[gacordeiro](https://github.com/gacordeiro) - Programming*
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
*by [tutuland](https://github.com/tutuland), with 💕*

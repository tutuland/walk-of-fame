# Walk of Fame

![Walk of Fame image](https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/Hollywood_Walk_of_Fame.jpg/800px-Hollywood_Walk_of_Fame.jpg)

***You decide who gets to be the star!***

---

### What?

Walk Of Fame is an exercise in code sharing and modularization:

- First, it uses [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) to develop a core library
  containing the business logic and external dependencies.
- This core lib is deployed as a published artifact to multiple platforms.
- For each platform a client implementation will be provided and the published artifact included as a dependency.
    - Android will use [Jetpack Compose](https://developer.android.com/jetpack/compose) to build its UI
    - Desktop and Web will use [Compose Multiplatform](https://www.jetbrains.com/pt-br/lp/compose-mpp/), and possibly we
      will be able to reuse some UI elements between Android, Desktop and Web (at least that's the plan 🙏)
    - For iOS, we will use swift UI (to be confirmed)
    - And possibly there will be a JVM based CLI client too (when time allows it)
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

`TODO: explain each module's responsibilities and link to their changelog`

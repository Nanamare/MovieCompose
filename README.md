<h1 align="center">MovieComposeLaboratory(experimental)</h1>

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/Nanamare/)

### About
The application is a simple movie searching app using Jetpack Compose. Purpose is to **acquire enough knowledge** that can be used in the company. Android Gradle plugin requires Java 11 to run. (Compose 100%)

 ### ScreenShot
* DarkTheme
<p align="center">
<img src="https://user-images.githubusercontent.com/17498974/147248400-df98cefe-a3b9-4c73-92f1-ca31e7af1064.jpeg" width="31%"  />
<img src="https://user-images.githubusercontent.com/17498974/147248379-a5b410bd-822d-418d-8b79-2d1e35ed6762.jpeg"  width="31%" />
<img src="https://user-images.githubusercontent.com/17498974/147248396-aa4220d0-30d1-4351-8788-39b0e661c08f.jpeg" width="31%" />
</p>

* LightTheme
<p align="center">
<img src="https://user-images.githubusercontent.com/17498974/156873101-06b49797-86bf-417a-868b-15e06f4a2018.jpg" width="31%"  />
<img src="https://user-images.githubusercontent.com/17498974/156873110-ea50b7a4-c71e-4a07-b09e-753df5a47bdc.jpg"  width="31%" />
<img src="https://user-images.githubusercontent.com/17498974/156873105-8cb4b47e-03e2-49d8-a39b-0c0d0e12055d.jpg" width="31%" />
</p>

### Architecture
 - Base on [Google Recommended Architecture](https://developer.android.com/jetpack/guide#recommended-app-arch) (UI Layer - Domain Layer - Data Layer) similar to Clean Architecture
 - We tried to removed the Android dependency(LiveData, AAC viewModel etc...) from the ViewModel. To expand to kotlin-multi-platform(KMM) in the future.
<img width="684" alt="interface ViewModel" src="https://user-images.githubusercontent.com/17498974/169699424-36599d07-0613-429d-bb5e-c43d74c0f732.png">
 
 - Using Mvvm with repository pattern (Model-View-ViewModel)
<img width="302" alt="https://github.com/rmoustafa/Kotlin-Coroutines-Clean-Architecture" src="https://user-images.githubusercontent.com/17498974/147250233-2f022a65-3cc2-4546-89d9-0d9037b2db59.png">

### 🛠 Built With 🛠
- Kotlin (Coroutine, Flow)
- Jetpack
  - Compose
  - Navigation
  - ViewModel(AAC)
  - Paging3
  - Material
- Dagger-Hilt
- Retrofit
- OkHttp
- Kotlinx-serialization
- Accompanist
  - SwipeRefresh
  - System-ui-controller
- Coil
- Room (RemoteMediator - Room paging)
- Timber
- UnitTest
  - JUnit
  - Kotlinx-coroutines-test
  - Truth(Google)
  - Mockito
  - OkHttp(MockWebServer)
  - Core-testing
- AndroidTest(Instrumentation Test)
  - Expresso
  - Compose-ui-junit
  - Hilt-android-testing
 
 ### Modularization
  - App (UI Layer) android dependencies
  - Data (Data Layer) network, datasource dependencies
  - Domain (Domain Layer) Pure kotlin, java
  - Base (Collection of utility functions used in UI Layer)
  - build-logic (Managing dependencies using toml)
  - Test-shared (Unit, Android test dependencies)
<img width="310" alt="Modularization" src="https://user-images.githubusercontent.com/17498974/169700258-997b3dc5-570d-4d02-87c7-7833b8db40d3.png">

### Testing(Instrumentation, Unit) 
Beginner in testing(Studying...) 🙏

<img width="300" alt="Instrumentation Test" src="https://user-images.githubusercontent.com/17498974/169700325-49f10c7e-547f-40ee-b80a-233461e2bf67.png">
<img width="300" alt="Unit Test" src="https://user-images.githubusercontent.com/17498974/169700328-3aac0145-5e49-44c4-bbb5-b89fc293b8ba.png">

 ### Open API
  - [TheMovieDB API](https://api.themoviedb.org)

 ### TODO
- [X] [Local Database (Room-Paging)](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db)
- [ ] MovieDetailScreen
- [X] Support System Theme (Dark, Light)
- [ ] Animation
- [ ] MVI migration

<h1 align="center">MovieComposeLaboratory(experimental)</h1>

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/Nanamare/)

### About
The application is a simple movie searching app using Jetpack Compose. Purpose is to **acquire enough knowledge** that can be used in the company. Android Gradle plugin requires Java 11 to run.
<br><br>

 ### ScreenShot
* Dark
<p align="center">
<img src="https://user-images.githubusercontent.com/17498974/147248400-df98cefe-a3b9-4c73-92f1-ca31e7af1064.jpeg" width="31%"  />
<img src="https://user-images.githubusercontent.com/17498974/147248379-a5b410bd-822d-418d-8b79-2d1e35ed6762.jpeg"  width="31%" />
<img src="https://user-images.githubusercontent.com/17498974/147248396-aa4220d0-30d1-4351-8788-39b0e661c08f.jpeg" width="31%" />
</p>

* Light
<p align="center">
<img src="https://user-images.githubusercontent.com/17498974/156873101-06b49797-86bf-417a-868b-15e06f4a2018.jpg" width="31%"  />
<img src="https://user-images.githubusercontent.com/17498974/156873110-ea50b7a4-c71e-4a07-b09e-753df5a47bdc.jpg"  width="31%" />
<img src="https://user-images.githubusercontent.com/17498974/156873105-8cb4b47e-03e2-49d8-a39b-0c0d0e12055d.jpg" width="31%" />
</p>
<br>

### Architecture
 - Base on [Google Recommended Architecture](https://developer.android.com/jetpack/guide#recommended-app-arch) (UI Layer - Domain Layer - Data Layer) similar to Clean Architecture
 - Using Mvvm with repository pattern (Model-View-ViewModel)

<img width="302" alt="스크린샷 2021-12-23 오후 10 55 10" src="https://user-images.githubusercontent.com/17498974/147250233-2f022a65-3cc2-4546-89d9-0d9037b2db59.png"> [ImageLink](https://github.com/rmoustafa/Kotlin-Coroutines-Clean-Architecture)
<br><br>

### 🛠 Built With 🛠
- Kotlin (Coroutine, Flow)
- Compose
  - Navigation
  - ViewModel(AAC)
  - Paging3
- Dagger-Hilt
- Retrofit
- OkHttp
- Kotlinx-serialization
- Accompanist
 - SwipeRefresh
 - System-ui-controller
- Coil
 <br><br>
 
 ### Modularization
  - App (UI Layer) android dependencies
  - Data (Data Layer) network, datasource dependencies
  - Domain (Domain Layer) Pure kotlin, java
  - base (Collection of utility functions used in UI Layer)
  - buildSrc (Managing dependencies)
<img width="310" alt="스크린샷 2021-12-23 오후 8 19 59" src="https://user-images.githubusercontent.com/17498974/147242594-74726b66-8471-486c-b3ce-caef7f60bbf1.png">
<br><br>

 ### Open API
  - [TheMovieDB API](https://api.themoviedb.org)
<br>

 ### TODO
- [X] [Local Database (Room-Paging)](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db)
- [ ] MovieDetailScreen
- [X] Support System Theme (Dark, Light)
- [ ] Animation

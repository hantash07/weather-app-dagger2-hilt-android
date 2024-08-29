## Weather App - MVVM - Dagger2 - Hilt

### Overview
A weather forecasting Android application that provides current weather information based on the user's location. This project demonstrates how to 
implement Dependency Injection in MVVM Architecture design pattern. Dagger2 and hilt are used as a Dependency Injection.


### Repo's Branches
- **`master`**: Default branch and contains the implementation of MVVM with dagger2
- **`dirty-code`**: Implemented the basic features of the weather app without MVVM and dependency injection
- **`dagger2`**: This branch converted the `dirty-code` branch into MVVM with Dagger2
- **`hilt`**: This branch converted the `dirty-code` branch into MVVM with Hilt


### Technologies Used
- Kotlin
- MVVM Architectural Design Pattern
- Dagger2 Dependency Injection
- Hilt Dependency Injection
- Gradle Version Catalogs for managing dependencies in a scalable way
- FusedLocationProviderClient for getting user's current location
- Coroutines Asynchronous Programming
- Retrofit 


### Installation
- Clone the repository
  ```
  git clone https://github.com/YOUR_USER_NAME/weather-app-dagger2-hilt-android.git cd WeatherApp
  ```
- Create your own API key from WeatherAPI (https://www.weatherapi.com/)
- Open the project and add your API Key in `weatherapp/model/utils/Constant.kt` file

### Contact
If you have any questions, please contact [hantashnadeem@gmail.com].

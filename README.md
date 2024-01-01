# Plugi - Bidding and Online Store App

Plugi is an Android application that provides a platform for bidding and online shopping for various items including clothes, boots, and more.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Running the Application](#running-the-application)
  - [Debugging](#debugging)
  - [Release Build](#release-build)
- [Built With](#built-with)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Bidding System:** Participate in auctions and place bids on your favorite items.
- **Online Store:** Browse and shop for a variety of products including clothes and boots.
- **Swipe to Refresh:** Pull-to-refresh functionality for an enhanced user experience.
- **Rating System:** Rate and review products using the integrated rating bar.
- **Real-time Communication:** Utilizes socket.io for real-time communication.
- **Maps Integration:** Integrates Google Maps and Places API for location-based features.
- **Firebase Analytics and Messaging:** Utilizes Firebase for analytics and push notifications.

## Getting Started

### Prerequisites

- Android Studio
- Android device or emulator

### Installation

1. Clone the repository: `git clone https://github.com/mohamedelareeg/Plugi.git`
2. Open the project in Android Studio.
3. Build the project to resolve dependencies and compile the application.
4. Connect an Android device or use an emulator.
5. Run the application from Android Studio.

### Configuration

Ensure you have the required API keys and configurations for Firebase services. Update the necessary files with your configurations.

## Running the Application

### Debugging

To run the application in debug mode:

1. Connect an Android device or use an emulator.
2. In Android Studio, select "Run" from the toolbar.
3. Choose the connected device/emulator and click "OK" to build and run the app.

### Release Build

To build a release version of the application:

1. Open the terminal in Android Studio.
2. Navigate to the project root directory.
3. Run the following command:

   ```bash
   ./gradlew assembleRelease
   
This command generates the release APK in the app/build/outputs/apk/release/ directory.

### Publishing
To publish the application to the Google Play Store:

1. gnerate a signing configuration for the release build in the app/build.gradle file.
2. Build the release version using the steps mentioned above.
3. In Android Studio, select "Build" > "Build Bundle(s) / APK(s)" > "Build APK(s)".
4. The generated APK will be available in the app/build/outputs/apk/release/ directory.

Follow the Play Console guidelines to create a new release, upload the APK, and publish the app.

## Built With

- [Java](https://www.java.com/) - The primary programming language.
- [Firebase](https://firebase.google.com/) - Comprehensive mobile and web app development platform.
- [Socket.IO](https://socket.io/) - Real-time bidirectional event-based communication.
- [Retrofit](https://square.github.io/retrofit/) - Type-safe HTTP client for Android and Java.
- [Glide](https://github.com/bumptech/glide) - Fast and efficient image loading library.
- [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView) - Page indicator for ViewPager.
- [SimpleRatingBar](https://github.com/ome450901/SimpleRatingBar) - Rating bar library.
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Asynchronous programming in Kotlin.
- [Material Calendar View](https://github.com/Applandeo/Material-Calendar-View) - Material Design calendar view.
- [CountdownView](https://github.com/iwgang/CountdownView) - Countdown timer library.
- [Flexbox Layout](https://github.com/google/flexbox-layout) - Flexible box layout.
- [CircularCountdown](https://github.com/douglasspgyn/CircularCountdown) - Circular countdown timer.
- [AutoImageSlider](https://github.com/smarteist/Android-Image-Slider) - Auto image slider.
- [MaterialSearchBar](https://github.com/mancj/MaterialSearchBar) - Material Design search bar.
- [EventBus](https://greenrobot.org/eventbus/) - Event bus library.
- [SwitchButton](https://github.com/kyleduo/SwitchButton) - Switch button library.

## Dependencies

Detailed information about the libraries and dependencies used in the project can be found in the [build.gradle](build.gradle) file.

## Contributing

Feel free to contribute to the development of Plugi by creating issues, submitting pull requests, or suggesting new features.

## License

This project is licensed under the MIT License.

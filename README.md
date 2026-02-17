# Jikan Anime Explorer
An Android application that displays popular anime series using the Jikan API.
The app supports offline caching, paging, (tries to support :/) background sync, and modern Android architecture components.

## UI Views
### Anime List Screen
- Fetches Top Anime from server
- Displays: Title, Episode count, Rating, Poster image
- Infinite scrolling using Paging 3
- Cached data available offline

### Anime Detail Screen
- Fetches info of the specific anime selected by the user
- Displays: Poster image, Title, Synopsis, Genres, Episodes, Rating

## APK Download
You can download and test the application using the debug APK
[here.](app/build/outputs/apk/debug/app-debug.apk)

## Offline Support
- Data stored locally using Room
- Top 100 anime synced automatically
- Users can browse cached content without internet

## UI/UX
- Material 3 Design
- Scrollable detail page
- Image loading using Coil (tried using Glide but had to abandon due to some version conflicts)

## Basic Architecture
The project follows MVVM + Clean Architecture principles.
```
UI Layer
 ├── Compose UI
 ├── ViewModels
 └── UiState

Domain Layer
 ├── Repository Interface
 └── Model

Data Layer
 ├── Repository Implementation
 ├── Remote (Retrofit)
 ├── Local (Room) for 
 ├── Paging RemoteMediator
 └── Background Sync (WorkManager)

```

## Tech Stack
- Core: Kotlin, Jetpack Compose, MVVM Architecture
- Networking: Retrofit
- Image Loading: Coil
- Local Storage: Room Database
- Pagination: Paging 3
- Dependency Injection: Hilt
- Background Tasks: WorkManager

## API
Anime data is fetched from the Jikan API which provides unofficial access to the MyAnimeList database.

#### Top Anime Endpoint
```
https://api.jikan.moe/v4/top/anime
```
#### Anime Detail Endpoint
```
https://api.jikan.moe/v4/anime/{anime_id}
```

## Assumptions Made
- Only first 100 anime are cached locally.
- Trailer playback is optional (URL stored).
- Paging continues beyond cached data when internet is available.
- First launch requires internet connection.

## Limitations / TODOs
- Background sync for syncing data periodically not working as of now
- Trailer playback not implemented
- No user authentication
- Sync currently refreshes fixed top 100 anime

## Possible Future Improvements
- Trailer video player
- Search functionality
- Favorites support
- Pull-to-refresh
- Dark mode optimization
- Unit & UI testing
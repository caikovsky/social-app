# android-code-challenge
This repo is for the Android coding interview for new developers :)

## Source
In `kotlin_app` module.

## Notes

#### Clean architecture

#### MVVM + Unidirectional Interface

#### Swipe to refresh layout

#### Dagger/Hilt as Dependency Injector


#### StateFlow over LiveData
```
Why? In very simples terms, because I can provide an initial state and it's based on Coroutines so therefore its execution is suspending and not blocking.
```

#### Kotlin Serialization over Gson
```
Why? Gson library uses reflection and breaks Kotlin's null-safety which can lead to app crashes. Kotlin Serialization is made in Kotlin, so it takes its advantages.
```
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Preserve line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve all public and protected classes and their public and protected members
-keep public class * {
    public protected *;
}

# Preserve all .class method names
-keepclassmembers class * {
    *** *(...);
}

# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Kotlin
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }

# Jetpack Compose
-keep class androidx.compose.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }

# Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }

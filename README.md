# Message Alarm Pro - Native Android App

A comprehensive alert management system for Android that never lets you miss important messages. Features real-time notification interception, AI-powered smart replies, advanced rule builder, and intelligent escalation system.

## Features

### 🔔 Alert Engine
- Real-time notification interception via Notification Listener Service
- Color-coded priority system (Low, Medium, High, Critical)
- 4-level escalation: Notification → Vibration → Loud Alarm → Full Screen
- Snooze and dismiss controls
- Repeat-until-dismissed logic for critical alerts

### 📋 Rule Builder
- Multi-condition rules (App, Contact, Keyword)
- Include/Exclude logic
- Time-based rules with specific days/hours
- DND override capability
- Enable/disable toggle per rule
- Custom priority assignment

### 🤖 Intelligence Engine
- Retry logic with increasing intensity
- Delay trigger support
- Frequency detection
- Advanced escalation system
- Smart alert grouping

### 🎯 6 Preset Modes
1. **Freelancer** - For freelance professionals
2. **Trader** - For active traders
3. **Family** - For family communications
4. **Sleep** - For night-time alerts
5. **Business** - For business hours
6. **Custom** - Create your own

### 💬 AI Smart Reply Assistant
- 5 tone options: Friendly, Professional, Sales, Support, Formal
- 6 message types: Client reply, Order delivery, Revision, Follow-up, Upsell, Feedback
- 6 template categories: Freelancer, Trader, Business, Ecommerce, Family, Student
- Quick action buttons: Make shorter, Make longer, More professional, Add emoji, Simplify
- Support for OpenAI, Gemini, Claude, and Generic providers

### 📊 Analytics Dashboard
- Real-time statistics
- Alert timeline visualization
- Priority distribution charts
- App-wise and contact-wise statistics
- Alert history with filtering
- Export functionality

### ⚙️ Settings & Privacy
- Profile management
- API key management (OpenAI, Gemini, Claude, Generic)
- Privacy controls (Data masking, No-storage mode)
- Notification preferences
- Device management
- Plan and upgrade information

### 💰 Monetization
- Free Plan: Limited apps (2), rules (5), devices (1), no AI
- Pro Plan: Unlimited everything + AI features
- WhatsApp upgrade flow (wa.me/8801608533529)
- Manual plan verification via Firestore
- Owner notifications on signup/upgrade

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Backend**: Firebase (Auth + Firestore)
- **Push Notifications**: Firebase Cloud Messaging
- **Dependency Injection**: Hilt
- **Database**: Room + Firestore
- **Networking**: Retrofit + OkHttp
- **Build System**: Gradle 8.7

## Project Structure

```
MessageAlarmPro-Android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/trackiq/messagealarm/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── FirebaseConfig.kt
│   │   │   │   │   ├── models/
│   │   │   │   │   │   └── DataModels.kt
│   │   │   │   │   └── repository/
│   │   │   │   │       └── FirebaseRepository.kt
│   │   │   │   ├── services/
│   │   │   │   │   ├── NotificationListenerService.kt
│   │   │   │   │   ├── AlertService.kt
│   │   │   │   │   └── FirebaseMessagingService.kt
│   │   │   │   ├── receivers/
│   │   │   │   │   └── BootReceiver.kt
│   │   │   │   └── ui/
│   │   │   │       ├── screens/
│   │   │   │       │   ├── AuthScreens.kt
│   │   │   │       │   └── MainScreens.kt
│   │   │   │       └── theme/
│   │   │   │           ├── Theme.kt
│   │   │   │           └── Type.kt
│   │   │   ├── AndroidManifest.xml
│   │   │   └── res/
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── .github/
│   └── workflows/
│       └── build-apk.yml
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Setup Instructions

### Prerequisites
- Android Studio (latest version)
- JDK 17 or higher
- Firebase Project (message-alarm-pro)
- Git

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/MessageAlarmPro-Android.git
   cd MessageAlarmPro-Android
   ```

2. **Set up Firebase**
   - Go to Firebase Console (https://console.firebase.google.com)
   - Select your "message-alarm-pro" project
   - Download `google-services.json`
   - Place it in `app/google-services.json`

3. **Open in Android Studio**
   - File → Open → Select the project directory
   - Android Studio will automatically sync Gradle

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```

5. **Install on device/emulator**
   ```bash
   ./gradlew installDebug
   ```

### GitHub Actions Setup

1. **Add Firebase Secret to GitHub**
   - Go to your GitHub repository
   - Settings → Secrets and variables → Actions
   - Create new secret: `GOOGLE_SERVICES_JSON`
   - Value: Base64 encoded content of `google-services.json`
   
   ```bash
   # To encode your google-services.json:
   base64 -i app/google-services.json
   ```

2. **Push to main branch**
   ```bash
   git push origin main
   ```

3. **GitHub Actions will automatically build APK**
   - Go to Actions tab in GitHub
   - View the build progress
   - Download APK from artifacts

## Firebase Configuration

### Firestore Collections Structure

```
users/
├── {userId}/
│   ├── profile (UserProfile)
│   ├── rules/ (AlertRule)
│   ├── alerts/ (Alert)
│   ├── modes/ (Mode)
│   ├── templates/ (Template)
│   └── analytics/ (Analytics)
```

### Required Firebase Services
- Authentication (Email/Password)
- Cloud Firestore
- Cloud Messaging
- Analytics

## API Integration

### LLM Providers
The app supports multiple LLM providers for Smart Reply:
- **OpenAI** (GPT-4, GPT-3.5)
- **Google Gemini**
- **Anthropic Claude**
- **Generic API** (Custom endpoints)

### Configuration
Add your API keys in Settings:
1. Open Settings
2. Scroll to "API Configuration"
3. Select provider
4. Enter API key
5. Test connection

## Permissions Required

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY" />
<uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

## Building Release APK

### Generate Signed APK
1. Build → Generate Signed Bundle/APK
2. Select APK
3. Create or select keystore
4. Fill in key details
5. Select Release build type
6. Click Finish

### Via Command Line
```bash
./gradlew assembleRelease
```

## Troubleshooting

### Notification Listener Not Working
- Go to Settings → Apps → Permissions → Notification
- Find Message Alarm Pro
- Enable "Allow notification access"

### Firebase Connection Issues
- Verify `google-services.json` is in `app/` directory
- Check Firebase project ID matches
- Ensure Firestore is enabled in Firebase Console

### Build Errors
```bash
# Clean build
./gradlew clean build

# Update dependencies
./gradlew dependencies --refresh-dependencies
```

## Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## Performance Optimization

- Notification Listener Service runs efficiently with minimal battery drain
- Firestore queries are optimized with indexes
- Background services use WorkManager for reliability
- ProGuard enabled for release builds

## Security

- All API keys stored securely in Firebase
- Data masking option for privacy
- No-storage mode for sensitive users
- Encrypted Firestore security rules
- Device limit enforcement per plan

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support

For issues, feature requests, or questions:
- Open an issue on GitHub
- Contact: support@messagealarm.pro
- WhatsApp: wa.me/8801608533529

## Roadmap

- [ ] Widget support
- [ ] Wear OS integration
- [ ] Advanced ML-based alert filtering
- [ ] Custom notification sounds
- [ ] Multi-language support
- [ ] Dark mode optimization
- [ ] Backup and restore functionality
- [ ] Team collaboration features

## Credits

Built with ❤️ by the Message Alarm Pro team

---

**Version**: 1.0.0  
**Last Updated**: May 2026  
**Minimum SDK**: 24 (Android 7.0)  
**Target SDK**: 34 (Android 14)

# Message Alarm Pro - Complete Setup Guide

## Step 1: Firebase Project Configuration

### 1.1 Download google-services.json

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Select your project: **message-alarm-pro**
3. Click on **Project Settings** (gear icon)
4. Go to **Your apps** section
5. Find your Android app
6. Click **Download google-services.json**
7. Save the file to: `app/google-services.json`

### 1.2 Enable Required Firebase Services

1. Go to Firebase Console
2. Enable these services:
   - **Authentication** → Email/Password provider
   - **Cloud Firestore** → Create database in production mode
   - **Cloud Messaging** → Enable FCM
   - **Analytics** → Enable Google Analytics

### 1.3 Set Up Firestore Security Rules

Go to Firestore → Rules and replace with:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // User profile
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
      
      // User's rules
      match /rules/{ruleId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      // User's alerts
      match /alerts/{alertId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      // User's modes
      match /modes/{modeId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      // User's templates
      match /templates/{templateId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      // User's analytics
      match /analytics/{analyticsId} {
        allow read, write: if request.auth.uid == userId;
      }
    }
  }
}
```

## Step 2: Local Development Setup

### 2.1 Clone Repository

```bash
git clone https://github.com/yourusername/MessageAlarmPro-Android.git
cd MessageAlarmPro-Android
```

### 2.2 Place google-services.json

```bash
cp /path/to/google-services.json app/google-services.json
```

### 2.3 Open in Android Studio

1. Launch Android Studio
2. File → Open
3. Select the `MessageAlarmPro-Android` directory
4. Wait for Gradle sync to complete

### 2.4 Build and Run

```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device/emulator
./gradlew installDebug

# Run tests
./gradlew test
```

## Step 3: GitHub Actions Setup for Automated Builds

### 3.1 Prepare google-services.json for GitHub Secrets

```bash
# Encode your google-services.json to base64
base64 -i app/google-services.json | tr -d '\n' > encoded.txt

# Copy the output
cat encoded.txt
```

### 3.2 Add Secret to GitHub

1. Go to your GitHub repository
2. Settings → Secrets and variables → Actions
3. Click "New repository secret"
4. Name: `GOOGLE_SERVICES_JSON`
5. Value: Paste the base64 encoded content from step 3.1
6. Click "Add secret"

### 3.3 Verify Workflow File

The workflow file is already at: `.github/workflows/build-apk.yml`

It will:
- Trigger on push to main branch
- Set up JDK 17
- Configure Gradle 8.7
- Decode google-services.json from secret
- Build debug APK
- Upload as artifact

### 3.4 Test the Workflow

```bash
# Push to main branch
git add .
git commit -m "Initial commit"
git push origin main
```

Go to GitHub → Actions tab to see the build progress.

## Step 4: Configure LLM Providers

### 4.1 OpenAI Setup

1. Go to [OpenAI Platform](https://platform.openai.com)
2. Create API key
3. In app Settings → API Configuration
4. Select "OpenAI"
5. Paste API key
6. Click "Test Connection"

### 4.2 Google Gemini Setup

1. Go to [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Create API key
3. In app Settings → API Configuration
4. Select "Google Gemini"
5. Paste API key
6. Click "Test Connection"

### 4.3 Claude Setup

1. Go to [Anthropic Console](https://console.anthropic.com)
2. Create API key
3. In app Settings → API Configuration
4. Select "Claude"
5. Paste API key
6. Click "Test Connection"

## Step 5: Enable Notification Listener Service

After installing the app:

1. Open Settings
2. Go to Apps → Permissions → Notification
3. Find "Message Alarm Pro"
4. Toggle "Allow notification access" ON
5. Confirm the permission

## Step 6: First Time Setup

### 6.1 Register Account

1. Open Message Alarm Pro
2. Click "Register"
3. Enter email and password
4. Confirm registration

### 6.2 Create First Rule

1. Go to Dashboard
2. Click "Create Rule"
3. Select apps to monitor (e.g., WhatsApp)
4. Add keywords (optional)
5. Set priority
6. Save rule

### 6.3 Activate a Mode

1. Go to Modes
2. Select a preset (e.g., "Freelancer")
3. Toggle to activate
4. Configure sound/vibration preferences

### 6.4 Test Smart Reply

1. Go to Smart Reply
2. Enter a sample message
3. Select tone and message type
4. Click "Generate Reply"
5. Review and copy the generated response

## Step 7: Building Release APK

### 7.1 Create Keystore

```bash
keytool -genkey -v -keystore message-alarm-pro.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias message-alarm-pro
```

### 7.2 Build Signed APK

```bash
./gradlew assembleRelease \
  -Pandroid.injected.signing.store.file=message-alarm-pro.jks \
  -Pandroid.injected.signing.store.password=YOUR_PASSWORD \
  -Pandroid.injected.signing.key.alias=message-alarm-pro \
  -Pandroid.injected.signing.key.password=YOUR_PASSWORD
```

Or use Android Studio:
1. Build → Generate Signed Bundle/APK
2. Select APK
3. Choose or create keystore
4. Fill in key details
5. Select Release build type
6. Click Finish

## Step 8: Troubleshooting

### Issue: Gradle Sync Fails

**Solution:**
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

### Issue: Firebase Connection Error

**Solution:**
1. Verify `google-services.json` is in `app/` directory
2. Check Firebase project ID matches
3. Ensure Firestore is enabled in Firebase Console
4. Check internet connection

### Issue: Notification Listener Not Working

**Solution:**
1. Go to Settings → Apps → Permissions → Notification
2. Enable "Allow notification access" for Message Alarm Pro
3. Restart the app

### Issue: APK Build Fails

**Solution:**
```bash
# Check Java version
java -version  # Should be 17+

# Update Gradle
./gradlew wrapper --gradle-version 8.7

# Clean and rebuild
./gradlew clean assembleDebug
```

### Issue: GitHub Actions Build Fails

**Solution:**
1. Verify `GOOGLE_SERVICES_JSON` secret is set correctly
2. Check the base64 encoding is correct
3. Ensure the secret value doesn't have extra newlines
4. View build logs in GitHub Actions tab

## Step 9: Deployment

### 9.1 Google Play Store

1. Create Google Play Developer account ($25 one-time fee)
2. Create app listing
3. Upload signed APK
4. Fill in app details, screenshots, description
5. Set pricing (Free)
6. Submit for review

### 9.2 Direct Distribution

1. Build signed APK (see Step 7)
2. Host on your server or GitHub Releases
3. Share download link with users
4. Users can install via: Settings → Security → Unknown Sources

## Step 10: Monitoring and Analytics

### 10.1 Firebase Analytics

1. Go to Firebase Console
2. Analytics → Dashboard
3. Monitor user engagement and crashes

### 10.2 Firestore Usage

1. Go to Firebase Console
2. Firestore → Usage
3. Monitor read/write operations
4. Set up budget alerts

### 10.3 Crash Reporting

1. Go to Firebase Console
2. Crashlytics
3. View crash reports and stack traces
4. Fix issues and deploy updates

## Step 11: Maintenance

### Regular Updates

```bash
# Update dependencies
./gradlew dependencies --refresh-dependencies

# Check for outdated packages
./gradlew dependencyUpdates

# Run tests before pushing
./gradlew test
```

### Security

- Rotate API keys regularly
- Monitor Firebase security rules
- Keep dependencies updated
- Review Firebase access logs

## Support

For issues or questions:
- Check the README.md
- Review Firebase documentation
- Check Android Studio logs: View → Tool Windows → Logcat
- Open GitHub issue

---

**Last Updated**: May 2026  
**Version**: 1.0.0

# React Native Build Options Reference

## EAS Build (Cloud-based)
- ### APK Build
  - `eas build --platform android --profile preview`
  - ✅ Direct installation on devices
  - ✅ Easy sharing via file transfer
  - ✅ No local Android SDK required
  - ❌ Size limits (~150MB)
- ### AAB Build
  - `eas build --platform android --profile production`
  - ✅ Optimized for Google Play Store
  - ✅ Supports large assets (900MB+ models)
  - ✅ Smaller download size on Play Store
  - ❌ Cannot install directly on devices
- ### Development Build
  - `eas build --platform android --profile development`
  - ✅ Includes dev tools and debugging
  - ✅ Hot reload capabilities
  - ❌ Larger file size
  - ❌ Not for production distribution

## Local Build (SDK Required)
- ### Expo Eject + Local Build
  - `npx expo eject`
  - `cd android && ./gradlew assembleRelease`
  - ✅ Full control over build process
  - ✅ Can include large assets (900MB model)
  - ✅ No cloud dependencies
  - ❌ Requires Android SDK setup
  - ❌ Complex configuration
- ### React Native CLI
  - `npx react-native init MyApp`
  - `npx react-native run-android --variant=release`
  - ✅ Pure React Native workflow
  - ✅ Maximum flexibility
  - ❌ No Expo benefits
  - ❌ More manual setup required

## Model Integration Strategies
- ### Bundle in APK
  - Include .pte model in assets folder
  - ✅ Offline-first approach
  - ❌ Large APK size (900MB+)
  - ❌ Slow distribution
- ### Dynamic Download
  - Download model on first app launch
  - ✅ Small APK size
  - ✅ Fast distribution
  - ✅ Model updates without app updates
  - ❌ Requires internet for first use
- ### Chunked Download
  - Split model into smaller chunks
  - Download and merge chunks
  - ✅ Resumable downloads
  - ✅ Better progress tracking
  - ❌ More complex implementation
- ### Over-the-Air (OTA) Updates
  - Use Expo Updates for model delivery
  - ✅ Seamless updates
  - ✅ No app store approval needed
  - ❌ Still requires base app installation

## Distribution Methods
- ### Direct APK Sharing
  - Email, file sharing, USB transfer
  - ✅ No app store required
  - ✅ Immediate distribution
  - ❌ Manual installation process
- ### Google Play Store
  - Upload AAB via Play Console
  - ✅ Automatic updates
  - ✅ Wide reach
  - ❌ Review process required
- ### Firebase App Distribution
  - Beta testing and internal distribution
  - ✅ Easy team sharing
  - ✅ Analytics and crash reporting
  - ❌ Requires Firebase setup
- ### TestFlight (iOS)
  - Apple's beta testing platform
  - ✅ Easy iOS distribution
  - ✅ Automatic provisioning
  - ❌ iOS only

## Recommended Approach for ODMI LLM App
- ### Phase 1: Development
  - Use EAS Build with APK output
  - Implement dynamic model download
  - Test with small model first
- ### Phase 2: Testing
  - Share APK directly with testers
  - Use Firebase App Distribution for organized testing
  - Implement progress tracking for model downloads
- ### Phase 3: Production
  - Build AAB for Google Play Store
  - Implement OTA updates for model versions
  - Consider chunked downloads for better UX
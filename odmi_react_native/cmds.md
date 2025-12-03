npx create-expo-app@latest

npx expo start

npm install -g eas-cli
eas build:configure
eas build --platform android

```
eas build --platform android
Resolved "production" environment for the build. Learn more: https://docs.expo.dev/eas/environment-variables/#setting-the-environment-for-your-builds
No environment variables with visibility "Plain text" and "Sensitive" found for the "production" environment on EAS.

📝  Android application id Learn more: https://expo.fyi/android-package
√ What would you like your Android application id to be? ... com.kumar_dev.odmi_native 
√ What would you like your Android application id to be? ... com.kumar_dev.odmi_native
No remote versions are configured for this project, versio√ What would you like your Android application id to be? ... com.kumar_dev.odmi_native
No remote versions are configured for this project, versio√ What would you like your Android application id to be? ... com.kumar_dev.odmi_native
No remote versions are configured for this project, versio√ What would you like your Android application id to be? ... com.kumar_dev.odmi_native
No remote versions are configured for this project, versioumar_dev.odmi_native
No remote versions are configured for this project, versionCode will be initialized based on the value from the local project.      
✔ Incremented versionCode from 1 to 2.

nCode will be initialized based on the value from the local project.
✔ Incremented versionCode from 1 to 2.

✔ Incremented versionCode from 1 to 2.

✔ Using remote Android credentials (Expo server)        
√ Generate a new Android Keystore? ... yes
✔ Created keystore

Compressing project files and uploading to EAS Build. Learn more: https://expo.fyi/eas-build-archive            
✔ Compressed project files 1s (1.3 MB)
✔ Uploaded to EAS 3s
✔ Computed project fingerprint

See logs: https://expo.dev/accounts/kumar_dev/projects/odmi_native/builds/f28f57df-d683-4be4-88f7-68271c54d452  

Waiting for build to complete. You can press Ctrl+C to exit.
✔ Build finished

🤖 Android app:
https://expo.dev/artifacts/eas/vXeDX7o8g5G7wMd2xJr6X1.aab
```

### BUILD APK THROUGH ESA (expo web)

````
PS C:\Users\barad\Desktop\flutter\ARM\odmi_react_native\odmi_native>
Resolved "preview" environment for the build. Learn more: https://docs.expo.dev/eas/environment-variables/#setting-the-environment-for-your-builds
No environment variables with visibility "Plain text" and "Sensitive" found for the "preview" environment on EAS.



✔ Using remote Android credentials (Expo server)
✔ Using Keystore from configuration: Build Credentials ttzvAiasf1 (default)

Compressing project files and uploading to EAS Build. Learn more: https://expo.fyi/eas-build-archive
✔ Compressed project files 1s (1.3 MB)
✔ Uploaded to EAS 2s
✔ Computed project fingerprint

See logs: https://expo.dev/accounts/kumar_dev/projects/odmi_native/builds/9a52f240-5c4c-4208-9cb0-64dbd02b67fe

Waiting for build to complete. You can press Ctrl+C to exit.
✔ Build finished

  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
  █ ▄▄▄▄▄ █▄▀▀▄▄ ▀█▄ ▄▀▀▀ ██ ▄█▀█ ▄▄▄▄▄ █
  █ █▄▄▄█ ██▄▀▄▀▄ █▄▄▀▄ ▄▀ ██ ▄ █ █▄▄▄█ █
  █▄▄▄▄▄▄▄█ █ ▀▄▀▄▀▄█ ▀▄█ █ ▀▄▀▄█▄▄▄▄▄▄▄█
  █▄▄█▄ █▄▀▀▄▄▄▀██ ▄▄▄  ██ ▄█▄ ██▄▀▀██▀▄█
  █▀▄█▄▄▄▄▀█ █ ▀▄█▄  ▀██▄  █▄█▄ ▄▄▄▄▄ ▄▄█
  █▀▀▀▄▄ ▄▀▄▀ ▄▄▀▄▀██▄▄▄▀█▀▄█   █▄▄▀ ▀ ▄█
  █▀▀ ▄▀█▄▄█▄▀ ▄▄ ▄██ ▄ ▄▄█▄█▄▄█▀▄▀▄█▀▄ █
  ██▀██▀ ▄█▄▀▀▀ ▄█▀▀█  ██▄▄▄▄▀▀█▀▄██ ▄▄▄█
  █ ██  ▄▄▄▄█ ▄█ ▀▄▄▄█▄███ █  ▄▀▀█▄▀▀██ █
  ██▀█▀██▄▄ ▄▄▀▄█ ▀▀▄▄▀▀█▀ ▄█▄ █ ▄▀  ▀ ▄█
  █▀▀▄  ▀▄█▀█▀▄▄▄▄▄▀ █▀█▄▄▄█▄▄  █  ▄▀▀▀ █
  █ ██▄ █▄▄█▄▄ ▄▀▀ ██▀▄█▀▄▀ █▀▀ █▄▄▀█▀▀▄█
  █ ▄ ▀█▀▄ ▄▄█▀█▄   ▄▄▄███▀▀█▄ █▄█ ▀  ▄ █
  █▄███▄█▄▄ ▄▀▄▀▄   ▀▄▀ █ ▄█ ██ ▄▄▄ █  ██
  █ ▄▄▄▄▄ ██▀█ ▄  ▄ ▄▀ ▄█▄▄▄▀ █ █▄█  ▄█ █
  █ █   █ █ ▀▀▀▀██ ▀▄▀▀███▀██  ▄  ▄▄ █▄▄█
  █ █▄▄▄█ █▀█▄▄ ▄▄▄▄▀▄██▄▄▄▀█▀ ██▀▄ █ ▀ █
  █▄▄▄▄▄▄▄█▄▄█▄▄███▄█▄█▄██▄▄███▄▄▄██▄██▄█


🤖 Open this link on your Android devices (or scan the QR code) to install the app:
https://expo.dev/accounts/kumar_dev/projects/odmi_native/builds/9a52f240-5c4c-4208-9cb0-64dbd02b67fe     

√ Install and run the Android build on an emulator? ... no 
````





# Build locally 

npx expo prebuild --platform android

$env:ANDROID_HOME = "C:\Android\sdk"; .\gradlew.bat assembleRelease
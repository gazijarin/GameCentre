## Getting Started

### Clone the Repository
1) Install Android Studio
2) Check out from Version Control > Git
3) Under URL, paste the following project link:

```
https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0678
```
Click yes to proceed onto Version Control checkout.

4) You should see this in your Gradle Project path:

Note: This is assuming that you have saved it inside Android Studio Projects folder.
If you have saved it under some other custom folder, then it should say so correspondingly.
```
~/AndroidStudioProjects/group_0678
```
Add "Phase2/GameCentre" in order to direct the file to default gradle wrapper:

Recommended way is to click the '...' icon beside the project path, navigating to your Phase 2 folder, clicking on it, and then just choosing GameCentre. 
```
~/AndroidStudioProjects/group_0678/Phase2/GameCentre
```
Click Finish to proceed onto the next screen.

### Running the Repository
5) You might get the message "Unregistered VS root detected", and you should choose to "Add root".
This will allow you to use VCS and its functionalities for the project.
The project should now build.
6) Create an Android Virtual Device within Android Studio.
Select a Pixel2 smartphone as the device to emulate, specifying the device OS as Android 8.1 API 27.
7) Launch the virtual device and ensure it loads correctly.
8) After the test checks out, run the application. You should see the login screen.
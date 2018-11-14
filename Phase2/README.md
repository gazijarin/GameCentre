## Getting Started

### Clone the Repository
1) Install Android Studio
2) Check out from Version Control > Git
3) Under URL, paste the following project link:

```
https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0459
```
Click yes to proceed onto Version Control checkout.

4) You should see this in your Gradle Project path:

Note: This is assuming that you have saved it inside Android Studio Projects folder.
If you have saved it under some other custom folder, then it should say so correspondingly.
```
~/AndroidStudioProjects/group_0459
```
Add "phase1/GameCentre" in order to direct the file to default gradle wrapper:

Recommended way is to click the '...' icon beside the project path, navigating to your phase 1 folder, clicking on it, and then just choosing GameCentre. 
```
~/AndroidStudioProjects/group_0459/phase1/GameCentre
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

### Git Renaming Note
Near the end of development, we renamed A2/SlidingTiles into phase1/GameCentre. This is supposed to have
wiped the git file histories obtained when running the command "git log [FILENAME]". However, it is still
all visible in Android Studio if you navigate to VCS->Git->Show History.\


## What's New?

### New Implementations

#### 1) AuthenticationActivity (Class)

An activity that ensures the user logs in correctly with the proper credentials to
proceed onto the next screen. Utilized using Firebase.

#### 2) AuthenticationManager (Class)

A manager that creates user account, ensures the user signs in properly and stores
user data so that the user can sign in next time without creating another account.
Utilized using Firebase.

#### 3) LoginActivity (Class)

A class that allows the user to log in successfully. Checks whether the user has
entered a correct email address and password to log in.
For testing purposes, you can use:
email: admin@admin.com
password: adminpw

#### 4) SignupActivity (Class)

A class that allows the user to sign up successfully. Checks whether the user has
entered a valid email address and password to sign up.

#### 5) ScoreboardManager (Class)

A class that sets up the scoreboard for the game. It gets user and his/her scores
and obtains the top scores by comparison. Utilized using Firebase.

#### 6) FinishedActivity (Class)

An activity that updates and displays the scoreboard after the end of the game. Features include
displaying the top score list and the previous score list.

#### 7) FixedStack (Class)

A stack that has a fixed max size. Created in order to keep track of user undos.

#### 8) Game (Interface)

An interface that allows other classes to implement main game methods, such as
getting game difficulty, getting game's unique ID, setting the game's preferences
(# of undos), and getting boolean notice of whether an undo or a redo was successful.

#### 9) GameComplexityActivity (Class)

A class that allows the user to pick their own preferences of the game, such as
the number of undos and the board size.

#### 10) GameSelectActivity (Class)

A class that allows the user to pick their preferred game to play. Currently, the
only choice is SlidingTiles.


### Modifications to Existing Classes

#### 1) Board

The Board now takes in instance variable dimension, instead of constants NUM_ROWS and NUM_COLUMNS.
The change was made so that the user has a preference in tiles complexity.
Thus, the get methods were updated correspondingly.

#### 2) BoardManager

Instance variables added: score, numUndos, undoMoves, redoMoves

Methods added: getScore, getDifficulty, getGameId, highTopScore, setNumUndos, undo, redo, storeMove

In this phase, we had the board keep track of score and undos and redos. This is precisely why these
instance variables were added. getDifficulty, getGameId, highTopScore, undo, redo, setNumUndos are all
implemented as per the Game interface, as these are common aspects of many games. storeMove is a method
that stores the move for undoing purposes.


#### 3) GameActivity

Instance variables added: SAVE_FILENAME, handler

Methods added: autoSaveTimer(), makeToastAutoSavedText()

Overridden methods: onStart(), onStop()

The SAVE_FILENAME gets assigned to each user (by corresponding local variable username),
and the new Handler is initiated in the onStart() method, calling autoSaveTimer() to autosave
the game every 30 seconds. Upon onStop(), Handler makes sure it does not keep auto-saving.

#### 4) GameScreenActivity (previously known as StartingActivity)

Deleted instance variable TEMP_SAVE_FILENAME since it meddled with the main save file.
The main save file now includes username so that the save file is unique for each user.
Other than that, GameScreenActivity is the same.
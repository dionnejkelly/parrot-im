## Vision Statement ##
To create a new, easy-to-use, fun, and feature-rich instant messaging program that is compatible with multiple protocols, including XMPP, to target any age and audience. We aim to provide a simple, yet powerful, communication program that will allow many users to enjoy their conversations as naturally as if they were talking face-to-face.

We expect to implement all of our proposed features, including the low-priority features, by Parrot IM's final release. We expect that users will run Parrot IM whenever they wish to be connected to their friends. Also, we expect that they will prefer to run Parrot IM over existing messaging solutions, such as ICQ and Google Talk.

## Architectural Diagram ##
![http://parrot-im.googlecode.com/files/Diagram2.png](http://parrot-im.googlecode.com/files/Diagram2.png)<br>

<h2>List of Features</h2>
<img src='http://parrot-im.googlecode.com/files/Diagram.png' />

Please see a full feature list below:<br>
<br>
<b>Total Features: 42</b><br>

<h3>High Priority (7)</h3>
<ul><li><b>GUI</b> - friendly graphical user interface that allows the users to interact with the program with their keyboard and mouse (e.g. type box, text output box).<br>
</li><li><b>Friend List</b> - tracks the list of users' friends, family, co-workers, and associates in an organized manner.<br>
</li><li><b>XMPP Protocol</b> - the same protocol as Google Talk has used. Several servers and clients have support for this. Anyone can setup their own IM network using a XMPP server.<br>
</li><li><b>Live Instant Messaging</b> - ability to hold a one-on-one conversation with a buddy on given protocol.<br>
</li><li><b>Multiple Users</b> - maintain a database to allow multiple users to log in, each with their own unique friend lists.<br>
</li><li><b>Modular Design or Plug-in System</b> - protocol, or network can be easily added without changing the core program.<br>
</li><li><b>Chatbot</b> - allows users to enable an auto-reply message that is designed to simulate an intelligent conversation with other human users (i.e. away messages).</li></ul>

<h3>Medium Priority (22)</h3>
<ul><li><b>Storing User Information</b> - allows users to save their username and password.<br>
</li><li><b>Friend Groups</b> - allows users to organize their buddies into different groups on the friends list.<br>
</li><li><b>File Transfer</b> - allows users to send their files to another user.<br>
</li><li><b>Status</b> - allows users to set their status (i.e. available, busy, away, etc.).<br>
</li><li><b>Blocking another user</b> - allows users to block other user(s) from contacting them.<br>
</li><li><b>Add/Remove another user</b> - allows users to add or remove new contacts.<br>
</li><li><b>Chatroom/Conference</b> - allows users to create their own chatroom for private conference with more than one user.<br>
</li><li><b>Chat Log</b> - allows users to keep the history of each conversation in the chatroom. Users are not forced to log all chats, and can turn them off or on when desired.<br>
</li><li><b>Font Customization</b> - allows users to customize font size, font, family, and colour of their typing font.<br>
</li><li><b>Sound Notification</b> - plays a short sound clip upon certain events occurring, such as receiving a message.<br>
</li><li><b>Search Engine</b> - allows users to efficiently and intuitively search previous conversations or the friends list for anything.<br>
</li><li><b>Twitter and ICQ protocol</b> - allows users to communicate to people using twitter and ICQ.<br>
</li><li><b>Tabbed Windows</b> - collapse multiple conversations into a single window separated into tabs.<br>
</li><li><b>Profile System</b> - allow users to create/select their own profiles. Each profile is secured by password-protection. It stores the configuration, the account ID and the password of different IM network users' profiles. Therefore, the program allows users to login to several IM networks once a profile is selected.<br>
</li><li><b>Right-click options</b> - right-click on the name of another user to access options such as remove/block contact, send file, start chatting, etc.<br>
</li><li><b>Is Typing</b> - tells users whether the person they are chatting in the prompt window. This information will be displayed at the bottom of the chat window.<br>
</li><li><b>Auto Sign-in</b> - automatic sign-in upon start up.<br>
</li><li><b>Avatar picture</b> - allow user to set avatar picture.<br>
</li><li><b>Emoticon</b> - can insert a graphical emoticon into chat<br>
</li><li><b>Bug Report</b> - allow user to report when they found a bug or when the program crashes<br>
</li><li><b>Spellchecker</b> - adds a red underline for text that is not in the dictionary. Assists user in selecting the correct word. Aids the search function in identifying "close" matches to words.<br>
</li><li><b>Profanity Filter</b> - replaces incoming profane words with symbols.</li></ul>

<h3>Low Priority (13)</h3>
<ul><li><b>UI Customization</b> - allows users to customize the GUI settings depending on their preferences. In the early development, the user would be allowed to choose their favourite menu to be shown on the main window or choose the colour or theme of the window, etc. In the later development, we might try to develop other GUI features, so the user would have some options which GUI to use.<br>
</li><li><b>Additional Protocol Modules</b> - optional software modules that allows users to add accounts from different instant messaging protocols and login simultaneously.<br>
</li><li><b>Calculator</b> - a simple button calculator that allows users to compute up to 8 digits of accuracy. Also allows for in-chat calculation if desired.<br>
</li><li><b>Offline Messaging</b> - allows users to leave messages to another user.<br>
</li><li><b>Keyboard control</b> - use keyboard to control some parts of the program. e.g. press enter while the pointer is in message text box to send out a message or shift+enter to enter a new line without sending it.<br>
</li><li><b>/command</b> - allow user to type /command (eg. /me blah = <code>*</code>user name blah)<br>
</li><li><b>Timed Access Mode</b> - restricts use during predefined times. Generally, an administrator will set the program to function only during certain times of the day or days of the week. Access to modify these settings will be password protected.<br>
</li><li><b>Advanced/Simplified Modes</b> - To use all the program's features, advanced mode must be turned on. This helps to prevent the user from being overwhelmed with all the options upon first using the program.<br>
</li><li><b>Spam Filter</b> - Temporarily blocks user after suspected spamming. This is mainly to help prevent the spread of viruses.<br>
</li><li><b>Email Notifier</b> - if the user is connected to any networks in which he or she also has an email account, an email notification will display upon new emails being received.<br>
</li><li><b>Program Updates</b> - provides automatic updates that do not disrupt the user's chatting experience, while also not secretly hogging system resources.<br>
</li><li><b>Multiple Language Selection</b> - Localized language selection will be included for Parrot IM to display in the user's native language.<br>
</li><li><b>Multiple Computer Platforms Compatibility</b> - Parrot IM will run on Windows, Linux, and Mac OS X.</li></ul>

<h2>Scope of Phased Release</h2>
<ul><li><b>Alpha</b> - Basic Protocol Program capable of person-to-person chatting, presence information, and simple GUI interfaces. All high-priority features, with as many medium-priority features included as possible.<br>
</li><li><b>Beta</b> - More features will be added, and the database will be made as efficient as possible. The GUI may still lack polish at this stage, but will be completely functional. All high and medium-priority features will be implemented, with as many low-priority features included as possible. Adjustments to features may be done due to user feedback from the alpha release.<br>
</li><li><b>Final</b> - All features will be added, including GUI features in which the user interface customization of colors, fonts and window formats will be allowed. Help pages will be polished and usable, and the program will be as bug-free as possible.</li></ul>


<h2>Features That Will Not Be Implemented</h2>

<ul><li><b>Webcam Chat</b> - to eliminate the low video quality and minimize the hardware requirements for using our software.<br>
</li><li><b>Voice Chat</b> - to cut down on development time to instead focus on the important aspects of the chat program.<br>
</li><li><b>Multiplayer Games</b> - would only be compatible with other users that run Parrot IM. This goes against our philosophy: you should be able to connect with any user, regardless of chat client.<br>
</li><li><b>Music Player</b> - would require additional work to design and implement the player, but there are already good solutions out there.
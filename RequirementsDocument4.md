# Introduction #

We did not make any changes to this section. Please view the [previous document](RequirementsDocument.md).

# Features #

Below is the list of features and indications of progress.

[Graphical Graphical User Interface](Feature_GUI.md): No change. Implemented.

[Friend List](Feature_FriendList.md): The Friend List is currently set to show the online status of the user, and indicates whether the friends are away, busy, offline, or blocked. The list is sorted alphabetically, but users that are more online than others receive higher priority at the top of the list. We do not have search function yet. GUI is provided.

[Live Instant Messaging](Feature_LiveInstantMessaging.md): Implemented. Currently can bring up a chat window and use tabbed chatting to select between open conversations. Users can send and receive messages.

[XMPP Protocol](Feature_XMPP.md): Google Talk and Twitter functionality implemented. Jabber functionality is enabled, but not fully supported yet.

[Chatbot](Feature_Chatbot.md): To enable or disable the Chatbot is in the Contact menu, not in the Chat Window. Implemented. The Chatbot currently is able to use either reponse with a customized chatbot questions and answers or repsonse with a list of common phrases.

[Profile System](Feature_ProfileSystem.md): Implemented. Can add multiple accounts to the profile. Changes are saved in the database.

[Font Customization](Feature_FontCustomization.md): GUI elements have been implemented, but it is not functional yet. This is due to XMPP not supporting fonts being sent to buddies.

[Auto Sign In](Feature_AutoSignIn.md): Users are able to flexibly auto sign in upon launching the program.

[Avatar picture](Feature_AvatarPicture.md): Implemented for XMPP Protocol. Users can select the image file on their computer by clicking on the current avatar.

[Tabbed Windows](Feature_TabbedWindows.md): No change. Implemented. Users can click on tabs on the left-hand side of the chat window to toggle between open conversations.

[Blocking Another User](Feature_BlockingAnotherUser.md): Implemented via a work-around. While the proper method of blocking users via XMPP is not adhered to, we have devised a way to achieve the same effect of blocking a user on one computer. The user is technically removed from the friend list, but is stored in the local computer's database. This allows the GUI to show that the user has been blocked, and allow for the user to be unblocked easily. The blocked user cannot see the online status of the person who blocked him or her.

[Status](Feature_Status.md): Implemented. Users can set their status and state, and see the status of others on the buddy list. The states are colour coded on both the buddy list and the chat window so the user can quickly know how available their friend is.

[Chat Log](Feature_ChatLog.md): Implemented and searchable.

[Search Engine](Feature_SearchEngine.md): GUI is able to provide both a buddylist and Google Searching Capability upon users input in the text field.

[Twitter and ICQ Protocols](Feature_TwitterAndICQProtocols.md): Twitter implemented. Users are able to set their status, extract avatar pictures, and add/remove friends.

[Is Typing](Feature_IsTyping.md): Users are able to send signals to other users to indicate "Is Typing" and also receive the signals.

[Emoticons](Feature_Emoticons.md): Users are free to choose any emoticons from the Parrot Emoticion Button.

[Spellchecker](Feature_Spellchecker.md): Spell Checker currently only supports English language.

[Conference Chatting](Feature_ConferenceChatting.md): Not yet implemented however users are  able to create a conference to room and invite users to join and start a conference chatting.

[File Transfer](Feature_FileTransfer.md): Users are able to send a file to another users that is less than 60 KB.

[Sound Notification](Feature_SoundNotification.md): Sounds are notified whenever the user logs in, exit, receive a message, and adds a friend.

[UICustomization](Feature_UICustomization.md): Users are allowed to choose their own theme for the Graphical User Interface.

[Additional Protocol Modules](Feature_AdditionalProtocolModules.md): Not implement yet.

[Offline Messaging](Feature_OfflineMessaging.md): Implemented by means of the XMPP protocol. No specific GUI messages show to indicate which messages are offline messages, though.

[Advanced And Simplified Modes](Feature_AdvancedAndSimplifiedModes.md): The switch is not implemented yet.

[Spam Filter](Feature_SpamFilter.md): Not implemented yet.

[Multiple OS Compatibility](Feature_MultipleOSCompatibility.md): Packaging not designed to install on all OSs, but will run via a .jar file on Mac, Windows, and Linux.

[Language Localization](Feature_LanguageLocalization.md): Not implemented yet.

[Email Notification](Feature_EmailNotification.md): Not implemented yet. However, users are able to send a bug report to the Parrot IM software engineers.

[Slash Commands](Feature_SlashCommands.md): Not implemented yet.

# Intended Audience #

We did not make any changes to this section. Please view the [previous document](RequirementsDocument.md).

# Non-Functional Requirements #

Noted are only the changed non-functional requirements:

### Product Requirements ###
  * **Performance Requirements:** The program will occupy between **30 MB** and **50 MB** of RAM while running.

(Rest unchanged.)

### Organizational Requirements ###
  * **Process Standards:** Conventions detailed on the Parrot IM website will be followed with regards to naming and style conventions.

(Rest unchanged.)

### External requirements ###

(All unchanged)

Metrics for specifying non-functional requirements:

### The Non-functional Requirements Table ###
| Property | Measure |
|:---------|:--------|
| Size     | Hard disk Requirements = The program will take up to **5 MB** on the computer's hard drive to function all the features of Parrot IM that includes storing users' profile and their preference, sound notification, and images. <br> Memory Requirements = The program will occupy between <b>30 MB</b> and <b>50 MB</b> of RAM while running. <br>
<tr><td> Security </td><td> All stored passwords are encrypted inside the database. (Encryption not implemented yet) </td></tr></tbody></table>

<h1>Example Tutorials #
We have added new user tutorials here:

> [Parrot IM Tutorial Guide](http://sites.google.com/site/parrotimhelp/Home)


# Glossary #

We did not make any changes to this section. Please view the [previous document](RequirementsDocument.md).
Note of changes since original document:
  * Introduction
    * New description of Parrot IM.
  * Features
    * Linked to a list of implemented features.
  * Intended Audience
    * Added a list of the protocols we decided on implementing.
    * Revised the example users section.
  * Non-Functional Requirements
    * Edited some values indicating the resources Parrot IM requires while running.
  * Tutorials
    * Linked to our official Help Page.
  * Technical Terms
    * No changes.


<a />
# Introduction #

Parrot Instant Messenger is a desktop application that connects you to friends on multiple networks, and offers useful, fun features such as tabbed conversations, programmable chatbot, chatlog and buddy search, and custom UI.

The program is expected to provide services to users who will run Parrot IM whenever they wish to be connected to their friends and that they will prefer to run Parrot IM over existing messaging solutions, such as ICQ, MSN, and Google Talk.


---

<a />
# Features #

Parrot IM has implemented the following [features](a5_featuresImplemented.md).


---


<a />
# Intended Audience #

The users of our feature-rich instant messaging program that is compatible with multiple protocols, including XMPP (for Jabber and Google Talk), Twitter, MSN, and Oscar (for ICQ and AIM), are available to everyone who would like to use our software. Our software is meant to provide an easy-to-use and friendly instant messaging program that allows the users to share their conversation with other registered users.

## List of the Kinds of Users ##

For privacy reasons we put their initials only

### J.C (Elementary Student) ###
  * **Age:** 10
  * **Gender:** Female
  * **Education:** Grade 4 Elementary School.
  * **Experience:** Familiar with a simple chat program.
  * **Expertise:**
    * Operate computer equipment.
  * **Goal:**
    * ants to communicate with her friends online.
    * ants to learn new words or proper spelling to words through the spellcheck function.
    * ants to use the cool fonts and emoticons that she can use in her chats.
  * **Assumptions:**
    * Ability to operate basic applications on her personal computer in Windows OS.
    * Ability to operate a keyboard and a mouse.
    * Knowledgeable about how to create an account with a password.
    * Has a home network connection available.

### L.L (University Student) ###
  * **Age:** 19
  * **Gender:** Female
  * **Education:** First Year Computing Science student
  * **Experience:** Familiar with Windows Live Messenger.
  * **Expertise:**
    * Operate and maintain computer equipment.
    * Ability to customize systems according to her specific needs.
  * **Goal:**
    * Wants to communicate with her friends effortlessly and effectively.
    * Does not want to spend the time to open multiple programs to track down her friends.
    * Wants to use the search function to find her contacts or important details from previous conversations.
    * Wants to use a simple and clean user interface to boost her productivity.
  * **Assumptions:**
    * Ability to operate basic applications on her personal computer in Windows OS.
    * Ability to operate a keyboard and a mouse.
    * Knowledgeable about how to create an account with a password.
    * Knowledgeable about how to search for information using a keyword.
    * Has a home network connection available.

### A.B (Teaching Assistant) ###
  * **Age:** 27
  * **Gender:** Male
  * **Education:** Master's Degree in Computer Science.
  * **Experience:** Familiar with many different chat programs.
  * **Expertise:**
    * Operate and repair computer equipment.
    * Ability to write and test computer programs in major programming languages.
    * Four years troubleshooting experience and extensive knowledge of web server and database.
  * **Goal:**
    * Wants to maintain and update programs.
    * Wants to rely on multiple-network functionality.
    * Does not want to spend the time to open multiple programs to track down his friends.
    * Wants to employ chatbot to send different messages to his different groups of contacts while he is away.
    * Wants to use slash-commands to boost his messaging experience.
  * **Assumptions:**
    * Ability to operate most of applications on his personal computer in Windows, Linux, and Mac OS.
    * Ability to operate a keyboard and a mouse.
    * Knowledgeable about how to create an account with a secure password.
    * Knowledgeable about slash-commands in different Operating Systems.
    * Occasionally busy having TA's office hours during exam periods.
    * Has a home network connection available.

### J.O (Third Party Developer) ###
  * **Age:** 39
  * **Gender:** Male
  * **Education:** PhD in Computer Science.
  * **Experience:** Recently created a new chat client protocol.
  * **Expertise:**
    * Operate and repair computer equipment.
    * Ability to write and test computer programs in major programming languages.
    * Many experience with software engineering process.
    * Three years of experience in web server and database development.
  * **Goal:**
    * Wants to adapt his own existing protocol to plugin to Parrot IM system with minimal modification.
    * Wants to expand Parrot IM on multiple-network functionality.
  * **Assumptions:**
    * Ability to operate most of applications on his personal computer in Windows, Linux, and Mac OS.
    * Ability to operate a keyboard and a mouse.
    * Knowledgeable about how to create an account with a secure password.
    * Knowledgeable about a plug-in to interact with a host application.
    * Has a home network connection available.

### L.Q (Grandmother) ###
  * **Age:** 71
  * **Gender:** Female
  * **Education:** Bachelor's Degree in English.
  * **Experience:** Familiar with a simple chat program.
  * **Expertise:**
    * Operate computer equipment.
  * **Goal:**
    * Wants to communicate with her grandchildren online.
    * Wants to see pictures of her grandchildren.
    * Wants to use a simple and easy to navigate chat program's interface.
  * **Assumptions:**
    * Ability to operate basic applications on her personal computer in Mac OS X.
    * Ability to operate a keyboard and a mouse.
    * Knowledgeable about how to create an account with a password.
    * Has a home network connection available.


---

<a />
# Non-Functional Requirements #

### Product Requirements ###
  * **Usability:** The user interface for Parrot IM shall be implemented as simple features, yet powerful, that will allow many users to enjoy their conversations as naturally as if they were talking face-to-face.
  * **Usability:** The Parrot IM is compatible with multiple protocols, including XMPP, to target any age and audience.
  * **Performance Requirements:** The program will execute, and the log-in window will show within 8 seconds.
  * **Performance Requirements:** The program will occupy between 10 MB and 60 MB of RAM while running.
  * **Reliability Requirements:** The number of faults found during testing (i.e., before delivery) will be no more than 50 based upon programmer information regarding failures found before release of the software.
  * **Reliability Requirements:** The failures (or other problems) reported by users after delivery will be no more than 20 based upon customer information regarding failures found after release of the software.
  * **Portability Requirements:** Parrot IM is able to run on Windows, Macintosh, and Linux operating environments.

### Organizational Requirements ###
  * **Process Standards:** The system development process and deliverable documents shall conform to the process and deliverables defined in Design Document.
  * **Process Standards:** Parrot IM will follow the conventional programming style of Java [coding](http://java.sun.com/docs/codeconv/).
  * **Implementation Requirements:** Java programming language will be used in Eclipse IDE. (We also need which design method is going to be used)
  * **Delivery Requirements:**
    * Alpha Release: June 26th, 2009
    * Beta Release: July 17th, 2009
    * Final Release: July 31st, 2009

### External requirements ###
  * **Interoperability Requirements:** The program shall not disclose any personal information about program users apart from their name and email address to the Parrot IM users who use the program.
  * **Interoperability Requirements:** Parrot IM will only be releasing our source code under the GPL V2 and interact with systems in other organizations using SQLiteJDBC v054 driver and Smack 3.1.0 interface.
  * **Legislative Requirements:** The program should not use our technical skills and abilities to behave in a dishonest way that misuses other people's computers.
  * **Ethical Requirements:** The Parrot IM software engineers shall be bounded by the Code of Academic Integrity and Good Conduct to participate in lifelong learning regarding the practice of their profession and shall promote an ethical approach to the practice of the profession.

A program goal: The program should be easy to use by anyone (experienced or inexperienced) and should be organized in such a way that errors are minimized.

A verifiable non-functional requirement: Any (experienced or inexperienced) controllers shall be able to use all the system functions after a total of 1 hour’s (temporary) training. After this training, the average number of errors made by any users shall not exceed one (temporary) per day.

Metrics for specifying non-functional requirements:

### The Non-functional Requirements Table ###
| Property | Measure |
|:---------|:--------|
| Speed    | User/Event response time. Messages should be sent within 1 second, and then it will be received by the user as long as their connection is currently stable. The program will execute, and the log-in window will show within 8 seconds on any computer of a Pentium 4 class or higher, or around 1 GHz of CPU capability. Parrot IM will log-in on all selected networks in maximum 30 seconds before the connection times out. Ideally, the client will log-in under 10 seconds. |
| Size     | Hard disk Requirements = The program will take up to 10 MB on the computer's hard drive to function all the features of Parrot IM that includes storing users' profile and their preference, sound notification, and images. <br> Memory Requirements = The program will occupy between 30 MB and 70 MB of RAM while running. <br>
<tr><td> Ease of Use </td><td> Training time = A user who has had no exposure to other instant messaging programs will learn to create a user profile, log-in, add friends, and chat with their friends within 1 hour of training from the Parrot instructor. </td></tr>
<tr><td> Reliability </td><td> Mean time to failure = The number of faults found during testing (i.e., before delivery) will be no more than 50 based upon programmer information regarding failures found before release of the software. The failures (or other problems) reported by users after delivery will be no more than 20 based upon customer information regarding failures found after release of the software. </td></tr>
<tr><td> Robustness </td><td> Time to restart after failure = The program will take up to 15 seconds to restart after a software failure. <br> Percentage of events causing failure  = 2% of internal (i.e., incorrect input data)/external (i.e., power failure)/events causing software failure. </td></tr>
<tr><td> Portability </td><td> Can be used on Windows, Macintosh, and Linux operating environments. </td></tr></tbody></table>


---

<a>
<h1>Tutorials</h1>

For all tutorials, please visit <a href='http://sites.google.com/site/parrotimhelp/Home'>Parrot IM Tutorial</a>.<br>
<br>
<hr />
<a>
<h1>Glossary</h1>

List of all technical terms and their precise definition.<br>
<br>
<h3>Technical terms</h3>

<b>Bold:</b> Makes words in a text darker to emphasize users text.<br>
<br>
<br>
<b>Italic:</b> Makes words in a text slightly slanted to emphasize users text.<br>
<br>
<br>
<b>Underline</b> Makes horizontal lines immediately below users text.<br>
<br>
<br>
<b>Friend/Buddy List:</b> Tracks the users' contact list of friends, family, co-workers, and associates in an organized manner.<br>
<br>
<br>
<b>Send:</b> Allows users to transfer their message to other users via online.<br>
<br>
<br>
<b>Add:</b> Allows users to include other user(s) contact.<br>
<br>
<br>
<b>Remove:</b> Allows users to delete other user(s) contact.<br>
<br>
<br>
<b>Block:</b> Allows users to block other user(s) from contacting them.<br>
<br>
<br>
<b>Is Typing:</b> Informs users whether the person they are chatting is typing in the prompt window.<br>
<br>
<br>
<b>Profile System:</b> Allows multiple users to log-in to their messaging accounts, and store/select their profile in the program. Each profile stores each messaging account (e.g. Twitter, Google Talk, ICQ) so that users can quickly log-in and chat upon starting the program.<br>
<br>
<br>
<b>Chat Log:</b> Allows users to keep the history of each conversation in the chatroom so that they can track their previous conversation.<br>
<br>
<br>
<b>Chatbot:</b> Allows users to enable an auto-reply message that is designed to simulate an intelligent conversation with other human users.<br>
<br>
<br>
<b>Auto Sign-in:</b> Allows users to automatically sign-in upon Parrot IM start up.<br>
<br>
<br>
<b>Offline Messaging:</b> Allows users to leave messages to another users when they are offline.<br>
<br>
<br>
<b>Advanced/Simplified Modes:</b> Allows users to choose their own minimum or maximum program's features.<br>
<br>
<br>
<b>Spam Filter:</b> Temporarily blocks users (either in their contact list or not in their contact list) after suspected spamming.<br>
<br>
<br>
<b>Email Notifier:</b> Sends an email notification to users who are connected to any networks in which he or she also has an email account (This one needs to be more clear).<br>
<br>
<br>
<b>Program Updates:</b> Provides automatic updates (i.e., bug fixes/new feautures) that do not disrupt the user's chatting experience.<br>
<br>
<br>
<b>Conference Chatting:</b> Allows users to have a conversation with multiple friends so that they can all communicate together at the same time.<br>
<br>
<br>
<b>Sound Notification:</b> Allows users to turn on/off of their different sound notifications that will play upon the particular events.<br>
<br>
<br>
<b>UI Customization:</b> The user interface comes included with a default background image, and preset colours for the windows.<br>
<br>
<br>
<b>Spellchecker:</b> As user(s) type to their friends in the Chat Window, a red underline will appear under words that are not in Parrot IM's dictionary.<br>
<br>
<br>
<b>Emoticons:</b> Allow users to add colourful icons to represent them and their friends' emotions as they are chatting,<br>
<br>
<br>
<b>Search Engine:</b> The Search Engine can be accessed via a search box on the Friend List, and it will search both users contacts and chat logs for key words.<br>
<br>
<br>
<b>Status:</b> Allows users to set their current situation (e.g. available, busy, away, custom message) to be shown to all friends.<br>
<br>
<br>
<b>GUI:</b> A graphical user interface that allows users to interact with the program with their keyboard and mouse. The GUI has graphical icons and visual indicators to fully represent the information and actions available to users.<br>
<br>
<br>
<b>XMPP Protocol:</b> An open standard system that allows users to communicate with the Extensible Messaging and Presence Protocol to chat with their friends that use Jabber, Google Talk, or any other program that implements this protocol.<br>
<br>
<br>
<b>Twitter and ICQ Protocol:</b> A service that allows users to connect them to friends from a variety of different instant messaging networks or social networking websites, including ICQ and Twitter.<br>
<br>
<br>
<b>Avatar Picture:</b> Allows users to associate their profile picture to their user account that is diplayed to everybody they chat with.<br>
<br>
<br>
<b>Java 5:</b> Version 5 of the Java programming language, also known as J2SE 1.5.<br>
<br>
<br>
<b>Java Swing:</b> Built-in graphics library in the Java standard library.<br>
<br>
<br>
<b>Eclipse IDE 3.4.2:</b> A software application or tools for Java developers that allow them to facilitate in creating JEE and Web applications, including a Java IDE.<br>
<br>
<br>
<b>Metrics 1.3.6:</b> A plug-in for Eclipse that provides software metrics of a project.<br>
<br>
<br>
<b>JUnit 4.6:</b> An industry-standard automatic testing framework primarily used to aid unit testing.<br>
<br>
<br>
<b>SQLite 3.6.14.2:</b> A relational database management system used to store data as a single cross-platform file on a host machine.<br>
<br>
<br>
<b>SQLiteJDBC v054:</b> A driver used to interface SQLite with Java.<br>
<br>
<br>
<b>Smack 3.1.0:</b> A Java library that provides XMPP interfacing methods.<br>
<br>
<br>
<b>Google Code Hosting:</b> A free software project hosting website provided by Google.<br>
<br>
<br>
<b>Subversion:</b> Google Code provides subversion to serve as a version control system for open source projects.<br>
<br>
<br>
<b>GPLV2:</b> A widely used free software license that guarantees that the program is and always will be free to run, and that its source code is free to view and modify. The license serves in both (a) copyrighting the software, and (b) allowing anybody to copy, distribute, and modify the program's code.<br>
<br>
<br>
<b>.db:</b> The relational database SQLite file format.
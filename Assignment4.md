# Source #
All source can be found on this Google Code website inside trunk->ParrotIM. All other folders are for testing purposes.

SVN link to source (deadline of July 17 1:30pm for updates to its files)
[Source code](http://code.google.com/p/parrot-im/source/browse/#svn/tags/beta)

# Executable File #

Direct link:
[Parrot IM Beta Version](http://parrot-im.googlecode.com/files/ParrotIM_beta.jar)

# Installation Instructions/Tutorial #

[Parrot IM Tutorial Guide](http://sites.google.com/site/parrotimhelp/Home)


---


# Testing Report #

Testing for the Beta released consisted of three parts:

## Unit Testing ##

We have used Eclipse 3.4.2 and JUnit 4.0 to write our unit and integration test cases. The low-level data types were checked for consistency, and each method was verified to return the expected results and make state changes to the objects as expected. The test cases are as follows:

| **Filename** | **Components Tested** |
|:-------------|:----------------------|
| AccountDataTest.java | data consistency, adding friends, removing friends, searching for friends |
| AccountTempDataTest.java | data consistency      |
| ChatbotTest  | response generation, response triggering, enabler |
| ChatlogMessageTempDataTest | data consistency      |
| ConversationDataTest | data consistency, message retrieval, message formatting, message storage |
| CurrentProfileDataTest | data consistency, adding accounts, removing friends, getting friend list |
| GoogleTalkUserData | data consistency      |
| JabberUserData | data consistency      |
| TwitterUserData | data consistency      |
| MessageDataTest | data consistency, message manipulation, message retrieval |
| ServerTypeTest | utility methods for enumerated type |
| UserDataTest | inheritance test, data consistency |
| BuddyListTest | data consistency      |
| BuddyTest    | data consistency      |
| FriendTempDataTest | data consistency      |
| ProfileListTest | data consistency      |
| ProfileTempDataTest | data consistency      |
| ProfileTest  | data consistency      |
| ProtocolSettingsTest | data consistency      |
| ModelToDatabaseTest | data consistency      |

**Results:**

Development of unit test cases allowed us to remove redundant code and add more robust methods to our low-level data. For instance, the Quality Assurance team identified methods that effectively did the same task, and merged them into one.

Another important find was that the search functions were comparing based on an equals method inherited by the Object class. Essentially, two UserData or AccountData objects were treated as equal only if they had the same reference; not if their data was equal. This posed subtle problems for removing or checking for the existence of these objects. As The equals() method was overridden as a result of the test cases.

Furthermore, these tests will be conducted and updated as our low-level data types under go upgrades in future revisions of the program.

All the test cases have successfully passed the test. We have tested most of the code using JUnit but some of the code wasn't tested or the test isn't completed and I include the reason for each one:

**DatabaseFunctions:** We had problems testing it by JUnit, and we couldn't figure out the reason. So, we included database testing in the integration test phase.

**GUI, sounds, Spell checker and Dictionary:** It's more difficult to test those classes using unit testing. So, we tested this by integration and manual testing.

In ChatbotQADataTypeTest, the test case returns failures and errors with processing question or answer alone. We are keeping those tests and functions hoping to fix them or else delete them from the next version.

Also, the test cases for integration package are not implemented because it have some errors, but we are aware of that and we will try to make it work for next version.

## Reports from JUnit test cases ##

Here are three examples for result from JUnit testing:

![http://parrot-im.googlecode.com/files/Untitled.jpg](http://parrot-im.googlecode.com/files/Untitled.jpg)

![http://parrot-im.googlecode.com/files/Untitled2.jpg](http://parrot-im.googlecode.com/files/Untitled2.jpg)

![http://parrot-im.googlecode.com/files/Untitled3.jpg](http://parrot-im.googlecode.com/files/Untitled3.jpg)


The green line and green labels on the methods mean that methods,(or when also the test case are labeled green) are successful and no errors have occured according to test case alogrithms. If the test case are labeled blue this means there were an error according to test case (mostly logical) for example in our DatabasefunctionsTest class, it gives failure to some functions eventhough it works in practice (that's why we didn't fully implemented DatabaseFunctionsTest). When methods are labeled red, this means that there were an error in processing the function itself (syntax error, initialization error) and complier stops the testing for the method in this case.

## Integration Testing ##

We employed two main integration steps to test Parrot IM. First, we integrated the Database and the Model, and checked both for consistencies while accessing and mutating data. Next, we integrated the Controller class, and performed a functional test which in turn integrated the lower-level components of the system, namely the Model and the Database.

  * ModelToDatabaseTest.java
    * Checks for consistency between the model and the database. The only location in which the database can be called is the model. We mainly checked for consistency in dealing with duplicate input.
  * MainController.java
    * Executes functions to mimic user interaction with Parrot IM. All calls come from the this main controller class, which currently only supports XMPP and Twitter functions. The Model and Database are, again, involved in processing the requests. The integrity of both are checked also.


## User Interface Testing ##

We included this test with user accptance test. It tests our product in how:

1. It uses terms that is easy for the user to comprehend

2. Interface is consistent, that's it do similar things for similar operations

3. It's minimal, it doesn't do surprise the user. It do what the user expect it to do.

4. User can recover from errors such as blocking a friend, or removing a friend by mistake

5. It provide help and warnings to the user such as Help tab

6. The user can interact with the interface the way he/she prefer


**Results:**
Integration testing was useful in organizing and cleaning up the code. In designing, it was not completely clear which components of the program handled certain things. For instance, is it the controller, model, or database's job to deal with duplicate account additions? Is it all of them? Integration testing showed errors when we didn't handle these situations properly. Ultimately, the testing made us set a focus to handle data integrity, the Model.

In this phase, we tried to make sure that everything works well in practice. This includes testing the following:

1. All the features are working properly without any errors or crashing.

2. Database retuns correct data.

3. Consistent GUI for easy-to-use interface.

We acheived the testing by following a simple tasks that users normally do. We put a table that include the procedures, observations on testing the task, and the expected output based on our features document:

| **Task** | **Observation** | **Expected result** |
|:---------|:----------------|:--------------------|
| Signing in | It takes around 10 seconds to login. | Users are able to sign in within 8 seconds on any computer of a Pentium 4 class or higher, or around 1 GHz of CPU capability.|
| Signing out | Program signs out all users within 1 second. | Users are able to sign out within 1 second.|
| Signing out, Signing in again | Users are able to flexibly sign-in and sign-out at any time. | Users are able to sign in and out multiple times. |
| Adding a buddy| Users are able to add a buddy within 3 seconds. | When adding a buddy, it takes 3 seconds. |
| Removing a buddy| Users are able to removea user within 2 seconds. | Users have no problem removing a buddy, and the buddy will be removed from the database. |
| Blocking a buddy| Users can organize the list of blocked buddies by blocking a buddy within 2 seconds.| Users are able to see the blocked buddies both in the buddy list and the blocked manager.  |
| Unblocking a buddy| Users are able to unblock a buddy within 2 seconds.|  Users are able to unblock a buddythrough the buddy list and the blocked manager. |
| Viewing Chat log | Shows the previous chat history organized by users' account. | You can view chatlog, and search conversations. |
| Connected buddies| Inconsistent number of connected buddies. | Number of connected buddiesshould be displayed consistently. |
| Changing a personal status| Users are able to change their states effectively. | Users are able to change their states both in the buddylist and the option.|
| Changing a personal avator picture | Users are able to change their avatar pictures effectively|  Users are able to change their avatar pictures both in the buddylist and the option.|
| Chating with a friend | Users are able to chat flexibly with a buddy. |  Users are able to chat flexibly with a buddy in a chat or conference room. |
| Pressing Enter to send | Sending a message by pressing Enter takes no more than 1 second to process. |  Users are able to send a message instantly. |


## User Acceptance Testing ##

For the beta release, each member asked friends and family members to try out Parrot IM. In some cases, we did an ethnographic test, in which we directly observed the user's interaction with our system, and noted which areas of the program seemed to be difficult for them to access. Also, we observed the tendencies of users to perform certain tasks while using our program.

Additionally, we asked some testers to try out Parrot IM alone, and they reported back on any challenges they had or features they'd like changed or added. We also instructed these users to try to do certain tasks, such as adding a friend, or using the chat log, and report back on the experience.

GUI Testing and acceptance steps:

We have asked the following 20 users to test our program and do the following tasks. By carefully observing them while they interacted with our program, we have noticed the users' tendency of their natural reaction to certain interfaces. The tasks included were:


1. We asked 20 users to try and run our software.

## List of Some Users ##

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


2. We asked them to do the following tasks while one of the team is logged in with cmpt275testing@gmail.com

  * a) Create a personal profile

  * b) Register with your GoogleTalk account

  * c) Sign in with the profile

  * d) Add cmpt275testing@gmail.com

  * e) Enable Chat Log

  * f) Send cmpt275testing a message

  * g) Wait for a message coming for cmpt275testing

  * h) Create a conference room

  * i) Invite cmpt275testing to that conference room

  * j) Send a message to the conference room

  * k) Wait for cmpt275testing's reponse

  * l) See if your friend is typing and wait for the message

  * m) Type a mispelled word.

  * n) Send a small file to cmpt275testing.

  * o) Wait for the file acceptance.

  * p) Enalbe Chatbot and "cmpt275testing" will talk to your chatbot

  * q) (cmpt275 will sign out) send a message to chatbot and ask him "What is your name?" and "What is up!" and wait for his respond
  * r) Disable chatbot and talks to "cmpt275testing"'s chatbot

  * s) Change your personal avatar picture, state and statues

  * t) Search for a user "cmpt275testing" in your search bar

  * u) Check Chat Log

  * v) Search Chat Log by typing cmpt275testing

  * w) Sign out


3. We asked them to do the following tasks while one of the team is logged in with cmpt275testing on Twitter

  * a) Create a personal profile

  * b) Register with your !Twitter account

  * c) Sign in with the profile

  * d) Add a twitter friend

  * e) Remove a friend

  * f) Send a message to your twitter friend

  * g) Set your status

  * h) Send a bug report

  * i) Enable Sound Notifcation

  * j) Disable Sound Notifcation

  * j) Go to help menu and click

  * k) Do google search by typing in the text field


**Observations:**
  * User felt good about the sign in window and they thought the buddy list was well organized.
  * User did not like the design of the bug report window.
  * User did not like the fact that he can't find out which account he's logged into while logged on, only the profile.
  * User did not like the About Parrot.IM link opening right on top of the main window, causing a confusing effect.
  * Didn't like text formatting buttons not changing the text in the input box
  * Add a buddy asked for an "Input", not user friendly.
  * Didn't like message box having a delay after pressing enter to clear the box.
  * When opening a conversation, the keyboard input should be focused on the text input field.
  * Wanted an indicator for signing it. Currently it looks like it's frozen when logging in.
  * Users felt the scroll bar is slow. We'll work on that
  * Some users felt the ok button in parrot preferences are missing, but macintosh, msn and adium are not implementing the ok button. Thus, we are following the majority of users mental model and not implementing ok button. Moreover, we need parrot-im to be more effeicent and ok button are not that helpful.



**Future Plans:**
  * Need to improve the GUI for managing multiple accounts withint one profile so the user can easily distinguish multiple accounts.
  * I think we need to impove the view of the buddy list in the Final version.
  * Move Help/About Parrot.IM link a bit down and to the right.

---


# Known Bugs #

## Critical Priority ##

  1. [Issue 61](http://code.google.com/p/parrot-im/issues/detail?id=61)
    * Changing fonts, size, font size, bold, italic, underlnie, and font color used to work fine. However, after a period, the object type of the text field were changed and became non functional as detailed in the issue above, making text editiing impossible. It appears to be an error with the changing in the text object.
    * Was uncovered with user acceptance testing with the helpful comments from the users.

## Medium Priority ##
  1. [Issue 56](http://code.google.com/p/parrot-im/issues/detail?id=56)
    * Exception thrown from an unknown source 50% of the time when logging and being neutral. No other effects seen.


---


# Documents #

Our previous documents can be found here:
  * [Old Requirements Document](RequirementsDocument.md)
  * [Old Design Document](DesignDocument.md)
  * [Old Quality Assurance Plan](QualityAssurancePlan.md)

All changes to the documents are given here:
  * [Requirements Document](RequirementsDocument4.md)
  * [Design Document](DesignDocuments4.md)
  * [Quality Assurance Plan](QualityAssurancePlans4.md)


---


# Testing Schedule #

  * Alpha (On Schedule)
> > Unit Testing Deadline: July 11, 2009 <br>
<blockquote>Integration Testing Deadline: July 12, 2009 <br>
User Acceptance Testing Deadline: July 14, 2009<br></blockquote></li></ul>

<hr />
<h1>Software Metrics</h1>

As of 2009-July-16 at 18:51 PM (<a href='https://code.google.com/p/parrot-im/source/detail?r=1754'>r1754</a>)<br>
<br>
(Gathered via loc at <a href='http://freshmeat.net/projects/loc/'>http://freshmeat.net/projects/loc/</a>)<br>
<table><thead><th> Pure Source </th><th> Comment </th><th> Source followed by Comment </th><th> Blank </th></thead><tbody>
<tr><td> 15435       </td><td> 7099    </td><td> 80                         </td><td> 5764  </td></tr></tbody></table>

Total lines of code: 28378<br>
<br>
(Gathered via Eclipse Metrics plugin at <a href='http://metrics.sourceforge.net/'>http://metrics.sourceforge.net/</a>)<br>
<table><thead><th> Classes </th><th> Methods </th><th> Attributes </th></thead><tbody>
<tr><td> 227     </td><td> 1433    </td><td> 630        </td></tr></tbody></table>

Test cases (methods in test classes): 317<br>
<br>
<hr />

<h1>Coding Conventions</h1>
The following are the conventions used in coding Parrot IM:<br>
<br>
<h2>Code Style</h2>
Listed are the conventions for all visual aspects of the code.<br>
<br>
<h3>Capitalization</h3>
<table><thead><th> <b>Type</b> </th><th> <b>Convention</b> </th></thead><tbody>
<tr><td> Packages    </td><td> Start with lower case, follow in CamelCase </td></tr>
<tr><td> Classes     </td><td> Start with upper case, follow in CamelCase </td></tr>
<tr><td> Methods     </td><td> Start with lower case, follow in CamelCase </td></tr>
<tr><td> Constants   </td><td> All upper case, words separated by underscores </td></tr>
<tr><td> Data Members </td><td> Start with lower case, follow in CamelCase </td></tr></tbody></table>

<h3>Spacing</h3>
<table><thead><th> <b>Type</b> </th><th> <b>Convention</b> </th></thead><tbody>
<tr><td> Block Indentations </td><td> 4 spaces wide     </td></tr>
<tr><td> Tabs        </td><td> 8 spaces wide     </td></tr>
<tr><td> Maximum line size </td><td> 80 characters long </td></tr></tbody></table>

<h3>Comments</h3>
<table><thead><th> <b>Type</b> </th><th> <b>Convention</b> </th></thead><tbody>
<tr><td> Header comment </td><td> C-Style, include filename, programmers, change log, known issues, and license pointer </td></tr>
<tr><td> Classes     </td><td> Include JavaDoc comment block </td></tr>
<tr><td> Data Members </td><td> Include JavaDoc comment block </td></tr>
<tr><td> Methods     </td><td> Include JavaDoc comment block </td></tr>
<tr><td> Single-line comments </td><td> C++ style comments </td></tr>
<tr><td> Multi-line comments </td><td> C++ or C-style comments </td></tr>
<tr><td> Commenting-out code </td><td> C++ or C-style comments </td></tr></tbody></table>

<h2>Naming Conventions</h2>

<h3>Files and terms</h3>
Terminology:<br>
<ul><li><b>userID</b> - The username, with the "@server.com" for a friend or an account.<br>
</li><li><b>account</b> - Represents one userID for the local user. Used to log-in to the server.<br>
</li><li><b>profile</b> - An identity that contains a list of accounts for the local user.<br>
</li><li><b>friends</b> - A list of userIDs in an account, shown in the buddy list.</li></ul>

Filename conventions:<br>
<ul><li>Data types, such as an Account or Profile, are suffixed with "DataType" in the filename.<br>
</li><li>Enumerated types are suffixed with "Type" in the filename.<br>
</li><li>Test cases are suffixed with "Test" in the filename.</li></ul>

<h3>Data Hierarchy</h3>
ParrotIM holds Model, Controller, and Main Window:<br>
<ul><li>Model has:<br>
<ul><li>AccountData<br>
</li><li>ChatCollectionData<br>
</li><li>Conversation<br>
</li><li>ConversationData has:<br>
<ul><li>MessageData<br>
</li></ul></li><li>GoogleTalkAccountData<br>
</li><li>GoogleTalkUserData<br>
</li><li>ICQAccountData<br>
</li><li>ICQUserData<br>
</li><li>JabberAccountData<br>
</li><li>JabberUserData<br>
</li><li>MessageData<br>
</li><li>MultiConversationData<br>
</li><li>ProfileData<br>
<ul><li>AccountData, which has:<br>
<ul><li>UserData<br>
</li><li>FriendData<br>
</li></ul></li></ul></li><li>TwitterAccountData<br>
</li><li>TwitterUserData<br>
</li><li>UserData<br>
</li><li>FriendTempData<br>
<ul><li>UserStateType<br>
</li></ul></li><li>ServerType<br>
</li><li>TypingStateType<br>
</li><li>UpdatedType<br>
</li></ul></li><li>MainController:<br>
</li><li>MainWindow has:<br>
<ul><li>Chat window</li></ul></li></ul>

The CustomizedChatbotModel holds Question/Answer Lists:<br>
<ul><li>CustomizedChatbotModel's Chat Bot Data:<br>
<ul><li>ChatbotQADataType has:<br>
<ul><li>questions<br>
</li><li>answers</li></ul></li></ul></li></ul>

The ChatLogPanel holds chatlog data:<br>
<ul><li>ChatLogMessageTempData has:<br>
<ul><li>time<br>
</li><li>from<br>
</li><li>to<br>
</li><li>text</li></ul></li></ul>







<h2>Installation of Subclipse</h2>
<b>1. Import from SVN</b>

a) Download Eclipse 3.4.2<br>
<br>
b) Open Eclipse<br>
<br>
c) Select Help tab<br>
<br>
d) Go to Software update<br>
<br>
e) Import from following site: http//subclipse.tigirs.org/update1_4x (if you don't have SVN downloaded yet)<br>
<br>
f) Download required and recommended features (you can have a problem with downloading optional features) then just go on until it's downloaded<br>
<br>
g) Select File tab<br>
<br>
h) Select import<br>
<br>
i) Select SVN and import repesitory<br>
<br>
j) Import from the site: https//parrot-im.googlecode.com/svn/trunk<br>
<br>
k) Download parrot-im<br>
<br>
l) Run test cases in unit test, integration test, and beta test.<br>
<br>
<b>2. Copy paste</b>

a) Download eclipse 3.4.2<br>
<br>
b) Download JUnit 4.0 (include http)<br>
<br>
c) Copy test cases into eclipse and run tests.<br>
<br>
<br>
<br>
<h2>Features Not implemented</h2>
# Source #
All source can be found on this Google Code website inside trunk->ParrotIM. All other folders are for testing purposes.

SVN link to source (deadline of June 26 1:30pm for updates to its files) http://code.google.com/p/parrot-im/source/browse/#svn/branches/assignment3_alphaVersion <br>
Direct link (last update, before June 26 1:30pm): <a href='http://parrot-im.googlecode.com/files/ParrotIM_alpha.zip'>http://parrot-im.googlecode.com/files/ParrotIM_alpha.zip</a>

<hr />

<h1>Executable File</h1>

Direct link: <a href='http://parrot-im.googlecode.com/files/ParrotIM_alpha_submit.jar'>http://parrot-im.googlecode.com/files/ParrotIM_alpha_submit.jar</a>

<hr />

<h1>Testing Report</h1>

Testing for the Alpha released consisted of three parts:<br>
<br>
<h2>Unit Testing</h2>

The low-level data types were checked for consistency, and each method was verified to return the expected results and make state changes to the objects as expected. The test cases are as follows:<br>
<br>
<table><thead><th> <b>Filename</b> </th><th> <b>Components Tested</b> </th></thead><tbody>
<tr><td> AccountDataTest.java </td><td> data consistency, adding friends, removing friends, searching for friends </td></tr>
<tr><td> AccountTempDataTest.java </td><td> data consistency         </td></tr>
<tr><td> ChatbotTest     </td><td> response generation, response triggering, enabler </td></tr>
<tr><td> ChatlogMessageTempDataTest </td><td> data consistency         </td></tr>
<tr><td> ConversationDataTest </td><td> data consistency, message retrieval, message formatting, message storage </td></tr>
<tr><td> CurrentProfileDataTest </td><td> data consistency, adding accounts, removing friends, getting friend list </td></tr>
<tr><td> GoogleTalkUserData </td><td> data consistency         </td></tr>
<tr><td> JabberUserData  </td><td> data consistency         </td></tr>
<tr><td> MessageDataTest </td><td> data consistency, message manipulation, message retrieval </td></tr>
<tr><td> ServerTypeTest  </td><td> utility methods for enumerated type </td></tr>
<tr><td> UserDataTest    </td><td> inheritance test, data consistency </td></tr></tbody></table>

<b>Results:</b>

Development of unit test cases allowed us to remove redundant code and add more robust methods to our low-level data. For instance, the Quality Assurance team identified methods that effectively did the same task, and merged them into one.<br>
<br>
Another important find was that the search functions were comparing based on an equals method inherited by the Object class. Essentially, two UserData or AccountData objects were treated as equal only if they had the same reference; not if their data was equal. This posed subtle problems for removing or checking for the existence of these objects. As The equals() method was overridden as a result of the test cases.<br>
<br>
Furthermore, these tests will be conducted and updated as our low-level data types under go upgrades in future revisions of the program.<br>
<br>
<h2>Integration Testing</h2>

We employed two main integration steps to test Parrot IM. First, we integrated the Database and the Model, and checked both for consistencies while accessing and mutating data. Next, we integrated the Controller class, and performed a functional test which in turn integrated the lower-level components of the system, namely the Model and the Database.<br>
<br>
<ul><li>ModelToDatabaseTest.java<br>
<ul><li>Checks for consistency between the model and the database. The only location in which the database can be called is the model. We mainly checked for consistency in dealing with duplicate input.<br>
</li></ul></li><li>XMPPTest.java<br>
<ul><li>Executes functions to mimic user interaction with Parrot IM. All calls come from the MainController class, which currently only supports XMPP functions. The Model and Database are, again, involved in processing the requests. The integrity of both are checked also.</li></ul></li></ul>

<b>Results:</b>
Integration testing was useful in organizing and cleaning up the code. In designing, it was not completely clear which components of the program handled certain things. For instance, is it the controller, model, or database's job to deal with duplicate account additions? Is it all of them? Integration testing showed errors when we didn't handle these situations properly. Ultimately, the testing made us set a focus to handle data integrity, the Model.<br>
<br>
Also, integration testing of the controller was first to show <a href='http://code.google.com/p/parrot-im/issues/detail?id=22'>Issue 22</a>. The XMPPTest.java class attempted to add and remove dummy accounts from our userIDs, and this bug manifested itself. If it weren't for this testing, we may have found this bug out at a later, more crucial time in development. Also, the test confirmed that it was the server at fault, not our controller, GUI, model, or database.<br>
<br>
<h2>User Acceptance Testing</h2>

For the alpha release, each member asked friends and family members to try out Parrot IM. In some cases, we did an ethnographic test, in which we directly observed the user's interaction with our system, and noted which areas of the program seemed to be difficult for them to access. Also, we observed the tendencies of users to perform certain tasks while using our program.<br>
<br>
Additionally, we asked some testers to try out Parrot IM alone, and they reported back on any challenges they had or features they'd like changed or added. We also instructed these users to try to do certain tasks, such as adding a friend, or using the chat log, and report back on the experience.<br>
<br>
GUI Testing and acceptance steps:<br>
<br>
1. We asked 10 users to try and run our software.<br>
<br>
2. We asked them to do the following tasks while one of the team is loggin with cmpt275testing@gmail.com<br>
<br>
a) Register with your GoogleTalk account<br>
<br>
b) Add cmpt275testing@gmail.com<br>
<br>
c) Send cmpt275testing a message<br>
<br>
d) Wait for a message coming for cmpt275testing<br>
<br>
e) (cmpt275 will sign out) send a message to chatbot and ask him "What is your name?" and "What is up!" and wait for his respond<br>
<br>
f) block cmpt275testing (cmpt275testing will sign again and he should not find user)<br>
<br>
g) delete cmpt275 testing<br>
<br>
h) log out<br>
<br>
<b>Observations:</b>
<ul><li>User got confused with the idea of profile and account. Probably the GUI is confusing.<br>
</li><li>User felt good about the sign in window, but they thought the buddy list is a  little bit simple.<br>
</li><li>User got confused with the block user icon, because they thought that icon is not clear.<br>
</li><li>User got could not figure out how to unblock an account.<br>
</li><li>User did not like the design of the Help window.<br>
</li><li>User did not like the fact that he can't find out which account he's logged into while logged on, only the profile.<br>
</li><li>User did not like the Help/About Parrot.IM link opening right on top of the main window, causing a confusing effect.<br>
</li><li>Didn't like buttons with icons not having tooltips.<br>
</li><li>Didn't like text formatting buttons not changing the text in the input box<br>
</li><li>Add a buddy asked for an "Input", not user friendly.<br>
</li><li>Didn't like message box having a delay after pressing enter to clear the box.<br>
</li><li>When opening a conversation, the keyboard input should be focused on the text input field.<br>
</li><li>Wanted an indicator for signing it. Currently it looks like it's frozen when logging in.<br>
</li><li>Exiting Buddy list with File->Exit doesn't also close the chat window.<br>
</li><li>Wanted a Account Setup wizard to open on first run of the program.</li></ul>


<b>Future Plans:</b>
<ul><li>Need to improve the GUI so the user can easily distinguish profile and account.<br>
</li><li>I think we need to impove the view of the buddy list in the Beta version.<br>
</li><li>We should make the block friend icon as clear as the add friend and remove friend icon.<br>
</li><li>Need to make a sleeker Help window.<br>
</li><li>Show which account is talking to each friend in Conversation Window.<br>
</li><li>Move Help/About Parrot.IM link a bit down and to the right.<br>
</li><li>The display picture should have a tooltip saying it's actully clickable to pop the file browser.<br>
<hr /></li></ul>

<h1>Known Bugs</h1>

<h2>Critical Priority</h2>

<ol><li><a href='http://code.google.com/p/parrot-im/issues/detail?id=22'>Issue 22</a>
<ul><li>Adding and remove friends used to work fine. However, after a period, certain accounts would give the errors detailed in the issue above, making adding and removing friends impossible. It appears to be an error with the smack API, and it is impossible to recover from the exception it throws.<br>
</li><li>Was uncovered with integration testing using the XMPPTest test case.</li></ul></li></ol>

<h2>Medium Priority</h2>
<ol><li><a href='http://code.google.com/p/parrot-im/issues/detail?id=9'>Issue 9</a>
<ul><li>Exception thrown from an unknown source 50% of the time when receiving a message. No other effects seen.<br>
</li></ul></li><li><a href='http://code.google.com/p/parrot-im/issues/detail?id=10'>Issue 10</a>
<ul><li>Exception thrown from an unknown source 25% of the time when logging in. No other effects seen.</li></ul></li></ol>

<hr />

<h1>Documents</h1>

Our previous documents can be found here:<br>
<ul><li><a href='RequirementsDocument.md'>Old Requirements Document</a>
</li><li><a href='DesignDocument.md'>Old Design Document</a>
</li><li><a href='QualityAssurancePlan.md'>Old Quality Assurance Plan</a></li></ul>

All changes to the documents are given here:<br>
<ul><li><a href='RequirementsDocumenta3.md'>Requirements Document</a>
</li><li><a href='DesignDocumenta3.md'>Design Document</a>
</li><li><a href='QualityAssurancePlana3.md'>Quality Assurance Plan</a></li></ul>

<hr />

<h1>Testing Schedule</h1>

<ul><li>Alpha (On Schedule)<br>
<blockquote>Unit Testing Deadline: June 18, 2009 <br>
Integration Testing Deadline: June 22, 2009 <br>
User Acceptance Testing Deadline: June 25, 2009<br></blockquote></li></ul>


<h1>Software Metrics</h1>

Notes of change:<br>
Lines of code is around 8,000 in Alpha version. This is 2,000 lines less than our prediction of 10,000 lines of code.<br>
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
The Model holds Conversations and Profiles.<br>
<br>
Model's Chat Data:<br>
<ul><li>ConversationData has:<br>
<ul><li>MessageData</li></ul></li></ul>

Model's Profile Data<br>
<ul><li>CurrentProfileData has:<br>
<ul><li>AccountData, which has:<br>
<ul><li>UserData
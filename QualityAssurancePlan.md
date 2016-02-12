There are three phases of testing that Parrot IM will be subjected to:

# Unit Testing #

We start from the lowest level, testing the individual methods in each of our classes. Unit testing, also known as component testing, tests each component of the system for defects. Immediately after the implementation phase for each version has been completed, the system will undergo unit testing. Our goals for unit testing are to ensure:
  * Each individual method operates as intended
  * Every state of an object is functional, and does not cause errors in methods that interact with that object
  * Objects should be able to interact with each other properly.

As an example, consider our UserProfile class, which interacts with Users inside its FriendList. Our first goal is to ensure that each method in these classes operates normally, and so we design test cases for each method to assert its return value or its change in state. Our second goal indicates that the objects should be tested in a variety of states. One possible test is to verify the operation of all the User class's methods when the User is classified as online, away, busy, and offline. Our final goal is to test the interaction of objects. We should test each method in UserProfile that interacts with Users, and try to test these while each are in as many different states as possible.

## JUnit ##

Just testing these two objects alone amounts for a significant amount of test cases. We require a tool to help us automate our test cases.

JUnit version 4.6 will be used to conduct automatic unit testing. JUnit simplifies unit testing by providing a framework in which test classes and test methods can be invoked to verify the correctness of our program's classes. For instance, most classes will have a corresponding test class. This test class will contain methods that assert the correct function of the program class's methods. These test cases can be run upon command, with no additional user input required.

JUnit is fully [documented](http://junit.org/junit/javadoc/4.5/) via JavaDoc. There is also a very useful [FAQ](http://junit.sourceforge.net/doc/faq/faq.htm) to get started with using JUnit.

To use JUnit, first we must make a test class. Each test class should contain the following import statements:
```
  import org.junit.*;
  import static org.junit.Assert.*;
```
Each method of the test class needs to be annotated with one of the following:
  * @Test
    * This is a test case. All assertions are defined in the associated method.
  * @Before
    * Before each @Test method, the @Before method will execute. It is useful to initiate the objects required for all test cases here. Having this method guarantees that each test case has a new copy of the object to test.
  * @After
    * Code is executed here after every @Test method. If the object needs to be deleted or cleaned up, this method should hold the code to do so.

The test methods will contain an assert method, such as assertTrue or assertEquals. When executing JUnit with this test class, these assertions will be verified, and the results will be shown.

Here is an example method that could appear in a test class for Parrot IM's UserProfile class, it confirms that clearStatusMessage() correctly sets the status String to the empty string:

```
@Test
public void testUserStatusChange
{
   UserProfile userProfile = new UserProfile();
   userProfile.setStatusMessage("This is a test!");
   assertTrue(!userProfile.getStatusMessage().isEmpty());
   userProfile.clearStatusMessage();
   assertTrue(userProfile.getStatusMessage().isEmpty());
}
```
If this method were a part of a test class named TestUserProfile, it could be executed as follows from a terminal window:
```
  java org.junit.runner.JUnitCore TestUserProfile
```
If successful, JUnit will output that all tests passed. Otherwise, it will print a stacktrace showing exactly which test failed.


## Test Case Development ##

Although JUnit's automation makes testing easier, it cannot automatically generate test cases. Therefore, the test cases must be carefully created so that the automated tests can guarantee that the code is error-free. There are three types of test cases that will be implemented:
  * Requirements-based test cases
    * These are high-level test cases, meaning that the data inputted will be data that we expect users to input, not data that we expect will cause errors. These test cases will contain input that trigger the features of the program to be executed. That is, the goal of these tests are to ensure that each requirement of the program operates normally.
  * Partition test cases
    * While the requirements-based test cases simulated user input, the partition test cases simulates both erroneous and valid user input. For example, if the program gives an option to choose only three items, these test cases will attempt to select an option not on the list. The test classes will force our methods to accept a wide range of inputs, and will ensure that no inputs cause the system to crash or give unintended errors. These test cases will be divided into partitions holding types of data. For example, some methods will receive input from partitions holding negative numbers and alphabetical characters.
  * Structural test cases
    * Both of the previous testing methods were similar to black-box testing, in which the actual code or structure of the program was not considered to any great extent; instead, data was generated based on what the user could potentially enter into the program. With structural testing, the code is examined for any potential weaknesses. These weaknesses are then exploited through test cases. For example, a private method that searches for a contact may be given input that will cause the method to fail, and the effects on the program will be observed.

# Integration Testing #

Before a release, and before the corresponding user acceptance testing, our team will conduct integration testing. Essentially, integration testing involves incrementally bringing parts of the program together to test. Up until this stage, the core, GUI, and database will be developed separately by different teams. During integration testing, we will verify that all the parts work together by using the following steps.

## Steps of integration ##

Parrot IM is being designed so that the core of the program can function alone; as long as it can interface with the multiple protocols, the GUI and the database is not required to communicate. Therefore, the first step is to integrate the core program with the external networks and conduct tests to confirm that the program can successfully communicate with other clients. To make this automated and easily repeatable, we will set up test accounts with Jabber, Google, Twitter, ICQ, and other implemented protocols, and test the functionality of the program to add the accounts as friends, remove them, and communicate. The data sent and received will be verified to be correct.

The next component to integrate is the database, which interacts with the core by storing all important local information, such as chat logs, font, and user profiles. Tests will be done that verify the correct interaction between the core and database, while also providing the same communication functionality between the core and external networks. That is, the previous testing of the core does not guarantee that the core will have no errors once it interfaces with the database, so those tests must again be conducted.

The final major component to be added is the graphical user interface. Since the GUI can interact with both the core and the database, testing will be done to verify data passed between both. Again, the previous tests on the core and database will again be done, to verify that the addition of the GUI component does not cause other components to break.

With all three major components accounted for, testing of additional plug-in features will take place in later versions of the software. For instance, after the chatbot is implemented, it will be tested at this stage by integrating with the GUI, database, and the core. For each feature added in this manner, all previous tests must be again conducted. With such a large feature list, automated testing through JUnit will be required to ensure that all aspects of the program are accurately tested.

## Differences between integration testing and unit testing ##

Integration testing comes at a later stage than unit testing. While unit testing is primarily used to test each class and function's validity of data, integration testing involves the test of a system as a whole in incremental steps. In unit testing, automated test cases will be run for each method, which will require additional test methods to be created to assert accurate function. In integration testing, the individual methods will be assumed to be working, but the methods from multiple classes will now interact with each other, which may lead to more flaws or bugs in the program. Additional test methods will be created to force the components to interact with each other.

It is common practice to have a separate quality assurance team conduct the later integration testing, with the software development team conducting the unit testing. Although we will perform a similar practice on our team, it will be slightly different due to the small size of our team. For example, some people on the software development team for the GUI will be moved to the quality assurance team when integration testing commences. Although each group has a different focus, the people behind the processes may be the same.

# User Acceptance Testing #

User acceptance testing involves users, who are not on our team, testing Parrot IM. They will be presented a fully-working copy of the program that has already been unit and integration tested. They will use the program as a normal user would, but some will be instructed to try to use certain parts of the program more intensively. We will encourage user feedback, and either fix found bugs or keep a list of design or program issues for consideration in the next iteration of the program.

## When? ##

  * Three days before Alpha release, (Tuesday, June 23, 2009)
  * Three days before Beta release, (Tuesday, July 14, 2009)
  * One week before Final release, (Friday, July 24, 2009)

## How many users? ##

There will be 20 users involved in testing during each phase. Each team member will ask an average of two users to test certain aspects of the program.

## Where? ##

For each test, a release candidate of the program will be distributed online for these users. That is, the users need not be at a specific location; they can use the program and provide feedback from anywhere.

## Goals of each phase of testing ##

The first two phases allow for only two days to work with the user-provided feedback before the release of that version. In these two days, only major bugs will be resolved before release. However, the user feedback will be fully utilized for the next version of the program. We anticipate that the users will offer feedback that will require design changes to the program, e.g., major feature additions, feature modification, graphical user interface redesign. All design-related feedback will be considered for future iterations of the program.

The final testing phase allows for six days to consider feedback and make necessary changes to the program. By this phase, all major design modifications will be implemented as required by previous user feedback, so any changes to features or user interface will be minor.

## What will be tested ##

Since there will be 20 users testing the program, they can each test different aspects of the program. For instance, a group of users could focus on trying to add and remove friends, while another group could focus on chatting with friends using different protocols. Additionally each phase of testing can have a different focus. The final user acceptance test will have more of an emphasis on regular use of the program, whereas the test for the beta version will see users trying out many features and evaluating their usefulness and usability.

The users will be divided in groups to test each of the versions. Listed below are the number of users assigned to a specific test area, and some of the tasks they will perform:

### Alpha ###
  * 10 users
    * Use program to talk to as many friends as possible, with multiple conversations happening at the same time. Report back with features they liked and disliked, and also evaluate the user interface.
      * Task: Create a profile and log-in Parrot IM with a Google Talk or Jabber account. Expected Result: Log-in screen shows the new user profile. User successfully is logged into their account. Friend List shows all friends connected with the account. The log-in screen disappears as the main Friend List appears. Log-in should take 10 seconds maximum to successfully complete.
      * Task: Double-click a friend's name. Expected Result: A chat window should appear with the friend inside and an empty chat log. The Friend List should not disappear.
      * Task: Type messages to the friend, and then press enter or click the "Send" box. Expected Result: The message should show in the chat history within 1 second. Assuming he or she is online, the friend should reply, and the chat history will show the response.
      * Task: Enter conversations with new friends. Switch between the tabs and talk to different ones. Expected Result: Entering a conversation with a new friend should add the friend to the Chat Window, and show an empty chat history with that friend. The user should be able to click on the previous friend and resume the conversation, with that chat history shown. Messages typed to one friend should not show on the other friend's chat history, and should not be sent to multiple friends.
  * 5 users
    * Use the chatbot to set as many away messages as possible. Report on the ease of use of the chatbot, and suggest improvements.
      * Task: Click Options --> Chatbot. Expected Result: The screen should show an option to enable/disable chatbot, and show a list of keywords and responses ordered by priority.
      * Task: Add a keyword and a response. Enable chatbot. Chat with a friend, and ask him or her to mention one of the keywords in the message. Expected Result: The user should be able to talk to the friend as normal, but when the friend sends a message with the specified keyword, the user's Parrot IM will automatically send the response message.
      * Task: Disable and enable the chatbot from the chat window, and ask the friend to send keywords in both states. Expected Result: The chatbot should be shown to turn off and on. While off, the user's Parrot IM should not automatically respond to keywords.
  * 2 users
    * Experiment with the avatar picture option. Try to add different types of images. Make sure that all other users can see the set avatar.
      * Task: Click the user's picture on the Friend List. Click on change picture. Find a picture on the hard drive to change to. Expected Result: The new picture should automatically be displayed in the Friend List.
      * Task: Chat with a friend. Ask what picture he or she sees. Expected Result: The friend should see the new picture.
      * Task: Exit Parrot IM and then start it again. Expected Result: The log-in window should show the new picture.
  * 3 users
    * Create a new account through Jabber and Google Talk and try adding and removing friends on Parrot IM. Judge the ease-of-use of going from an empty friend list to filling the friend list and talking to people as normal.
      * Task: Navigate to www.jabber.org (or www.gmail.com). Create a new account. Create a new user profile in Parrot IM and add the account. Expected Result: Parrot IM should accept the username, and be ready for log-in.
      * Task: Log-in. Add friends to the account. Expected Result: The first log-in should show an empty friend list. Adding a friend should add him or her to the friend list. When the request is accepted by the friend, they should appear to come online.

### Beta ###
  * 10 users
    * Add friends using XMPP, Twitter, and ICQ. Judge how seamless Parrot IM integrates conversations from each protocol. List any functions that were difficult to perform on any protocol.
      * Task: Add Twitter and ICQ accounts to the user profile. Log-in. Expected Result: Users from all accounts should show in the Friend List. The status messages of the Twitter friends should be the latest status messages seen on the Twitter website. Chatting with an ICQ friend should function identically to chatting with a Jabber friend. Chatting with a Twitter friend should prompt a message saying that the message would be sent to their inbox.
      * Task: Change the status message. Expected Result: If enabled in the options, a confirmation should appear asking if the user wants to publish the status to Twitter. If accepted, the changes should show on the Twitter website.
  * 3 users
    * Change as many menu options as possible and ensure that no errors or inconsistencies resulted from doing so.
      * Task: Open the option menu from the Friend List. Navigate through each option, and change as many options as possible. Expected Result: Only the changed options should take effect.
  * 7 users
    * Try searching as much as possible. Judge how useful the search feature is.
      * Task: Search for a friend by their display name by typing the username or part of the username in the search box. Expected Result: The person should jump to the top of the friend list as long as there is text in the search box. Clicking the user should initiate a conversation.
      * Task: Search for text that appeared in a chat on a previous session. Expected Result: A Search window will appear with all results of the search. There will be links to chat logs in which the given keyword was found, and the relevant text will be shown beside the link. The links are rated based on both relevance and date; more recent chats are more likely to show up at the time. Relevance  is largely calculated by how many times the set of keywords appears on a certain chat session--those chats are more likely to display at the top of the search results page.

### Final ###
  * 5 users
    * Chat as normal. Note anything that is annoying or inconvenient.
  * 5 users
    * Use MSN and AIM accounts and note any inconsistencies.
  * 3 users
    * Try Parrot IM on Windows, Mac OS X, and Linux (one user for each).
  * 7 users
    * Test file transfer and note the speed and validity of each file sent and received.

# Testing Schedule #

## Deadlines ##

All internal testing deadlines are shown in the [project calendar](http://www.google.com/calendar/embed?src=nq1c2mh2402meqim7qa2gt8ro4%40group.calendar.google.com&ctz=America/Los_Angeles). Each version of the program has a slightly different allocation of types of testing, as outlined below:

  * Alpha
    * Unit Testing Deadline: June 18, 2009
    * Integration Testing Deadline: June 22, 2009
    * User Acceptance Testing Deadline: June 25, 2009
  * Beta
    * Unit Testing Deadline: July 11, 2009
    * Integration Testing Deadline: July 13, 2009
    * User Acceptance Testing Deadline: July 16, 2009
  * Final
    * Unit Testing Deadline: July 22, 2009
    * Integration Testing Deadline: July 23, 2009
    * User Acceptance Testing Deadline: July 30, 2009

## Number of days for each phase of development ##

| | Alpha | Beta | Final |
|:|:------|:-----|:------|
| Implementation | 18    | 14   | 4     |
| Unit Testing | 2     | 1    | 1     |
| Integration Testing | 4     | 2    | 1     |
| User Acceptance Testing | 3     | 3    | 7     |
| Total Testing | 9     | 6    | 9     |
| Total Days (Implementation + Total Testing) | 27    | 20   | 13    |

The Alpha version requires the most Unit and Integration Testing, because the components of the program have been coded for the first time. While the Beta and Final versions also require this testing, it is of a lower priority because most of the components will be error free. We will have user acceptance testing in each phase to gain valuable user feedback. The final stage has a full seven days of user acceptance testing to ensure that the program meets the requirements of our users. The extra time ensures that we won't have to compromise user satisfaction due to not having enough time to implement the required changes.

# Software Metrics #

To measure our software's complexity, we will use the [Metrics](http://metrics.sourceforge.net/) plug-in for Eclipse. This plug-in allows for the calculation of lines of code, classes, methods, and many more statistics for all of our code. Before testing each version, these software metrics will be graphed. This will give the quality assurance team a quick view of the areas of the program that are the most complex, and as such will require more testing time to be allocated.

Below are graphs of the projected software metrics for all versions of the program:

Classes:
![http://parrot-im.googlecode.com/files/Classes.jpg](http://parrot-im.googlecode.com/files/Classes.jpg)

Lines of code:
![http://parrot-im.googlecode.com/files/Lines%20of%20code.jpg](http://parrot-im.googlecode.com/files/Lines%20of%20code.jpg)

Methods:
![http://parrot-im.googlecode.com/files/Methods.jpg](http://parrot-im.googlecode.com/files/Methods.jpg)


# References #

The phases of our testing were largely modelled by information found in Sommerville's book:
  * Sommerville, Ian. Software Engineering 8. Chapter 23: Software Testing.
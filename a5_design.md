Note of changes since original document:
  * Guidelines
    * No changes.
  * System Diagrams
    * New UML diagram.
  * Data Requirements
    * No changes.
  * 

# Guidelines #

## Technical Guidelines ##

Parrot IM will be constructed using the following technical tools:
  * Java 5
    * Version 5 of the Java programming language, also known as J2SE 1.5.
  * Java Swing
    * Built-in graphics library in the Java standard library.
  * Eclipse IDE 3.4.2.
    * By using the same IDE, we minimize the chance that we upload incompatible code.
  * Metrics 1.3.6
    * A plug-in for Eclipse that provides software metrics of a project.
  * Java [coding conventions](http://java.sun.com/docs/codeconv/) as advised by Sun
    * With ten members editing the code, it is important to write using the same coding conventions so that we will be more productive in reading and adding to other members' code.
  * JUnit 4.6
    * An industry-standard automatic testing framework primarily used to aid unit testing.
  * SQLite 3.6.14.2
    * A relational database used to store user profiles in Parrot IM.
  * SQLiteJDBC v054
    * A driver used to interface SQLite with Java.
  * Smack 3.1.0
    * A Java library that provides XMPP interfacing methods.
  * Google Code Hosting
    * We will use Google Code Hosting to manage our source code.
  * Subversion
    * Google Code provides subversion to serve as version control for our code.

## Ethical or Legal Issues ##

  * The program should not use our technical skills and abilities to behave in a dishonest way that misuses other people's computers.
  * The program should not contain any external code without citing properly.
  * The Parrot IM software engineers shall act consistently in a manner that is in the interests with the public users.
  * The Parrot IM software engineers shall ensure that their products and related modifications meet the highest professional standards possible.
  * The Parrot IM software engineers shall be fair to and supportive of their competitive colleagues.
  * The Parrot IM software engineers shall participate in lifelong learning regarding the pratice of their profession and shall promote an ethical approach to the pratice of the profession.


---


# System Diagrams #

A Sequence Diagram:
  * To model the behavior of Parrot IM objects, the sequence diagram has been used to show the sequence of messages exchanged by objects.
  * Objects and actors are aligned along the top of the diagram.
  * Labelled arrows indicate operations (the sequeunce of operations is from top to bottom).
  * In this scenario, the Parrot IM user accesses the software to create ID, login, add/delete, chat, and exit under the user requests.

![http://parrot-im.googlecode.com/files/CMPT_275_Assignment_2_Sequence_Diagram.png](http://parrot-im.googlecode.com/files/CMPT_275_Assignment_2_Sequence_Diagram.png)

A UML diagram that shows how the major modules, classes, and functions of our system relate and share data:
  * Object-oriented modelling is used to identify the classes of object that are important in the Parrot IM domain.
  * The classes of object are organized into a taxonomy to show how an object class is related to other classes through common attributes and services.
  * To display this taxonomy, the classes are organized into an inheritance hierarchy with the most general object classes at the top of the hierarchy.
  * Specialized objects inherit the attributes and services from their top hiearchy class and may have their own attributes and services as well.

![http://parrot-im.googlecode.com/files/alphaUML.png](http://parrot-im.googlecode.com/files/alphaUML.png)


---


# Data Requirements #

This is a summary of all the I/O for our system, including exact definitions of file and database formats, what other systems (if any) ours interacts with, and how the user interacts with the system via the mouse/keyboard/wii-mote/etc.

## File Formats ##

The following data will be saved locally:

  * User Profiles
    * Accounts
      * Protocol
      * Username
      * Password
    * Font
    * Chatbot settings
    * UI customization
    * Chatlog
    * Avatar Picture
    * Local Friend Information

Each account saves its friend list and other profile information on their own servers, so there is no need to store it in the local database.

To store the above data locally, we will use the relational database SQLite, which will use the following file format:
  * File Name: parrot.db
Only one file is required to store all the user profiles.


The chat log will initially be stored in the profile, but an option will be given to export the log to a text file:
  * File Name: chatlog_`<`profileName>.txt
This file will contain all chats that were saved under the corresponding user profile._

### Save Users Profile File Format ###
User profile will be organized in the following manner.

Descriptions: Saves users information such as their name, gender, age, occupation, contact information, email address, and profile pictures.

Format:
```
   <Last Name>
   <First Name>
   <Gender>
   <Age>
   <Occupation>
   <Phone Number>
   <Email Address>
   <Picture Directory>
```
Example:
```
   <Bits>
   <Jenny>
   <Female>
   <7>
   <Student>
   <6045671234>
   <jennybits@gmail.com>
   <C:\Documents and Settings\Desktop\Jenny.png>
```

### Save Users Chat Log File Format ###

Chat log information is saved in the following manner in the database.

Descriptions: Saves users chatting log history.

Format:
```
   <Time>
   <Day>
   <Month>
   <Year>
   <Sender ID>
   <Sender Email Address>
   <Messages>
```
Example:
```
   <13:23>
   <23>
   <May>
   <2009>
   <JamesPower>
   <jpower@gmail.com>
   <I was wondering what will you be doing tomorrow.>
```

### Save Users Block List File Format ###
Block lists will be saved as follows in the database.

Descriptions: Saves users block list.
Format:
```
   <userID>
```

Example:
```
   <jrParrot>
   <srParrot>
   <dk10>
```

### Save Users Customization File Format ###
Customization options will be saved in the following manner.

Descriptions: Saves users font type, font size, theme, avatar picture, and their Advanced/Simplified Modes.

Format:
```
   <Simplified = 0;Advanced = 1>
   <Spam Filter = 0(off)/1(on)>
   <Chat Bot = 0(off)/1(on)>
   <Auto Sign-In = 0(off)/1(on)>
   <Spell Checker = 0(off)/1(on)>
   <Sound Notification = 0(off)/1(on)>
   <Email Notification = 0(off)/1(on)>
   <Slash Commands = 0(off)/1(on)>
   <Font Type>
   <Font Size>
```

Example:
```
   <Simplified = 0>
   <Spam Filter = 1>
   <Chat Bot = 0>
   <Auto Sign-In = 1>
   <Spell Checker = 0>
   <Sound Notification = 1>
   <Email Notification = 1>
   <Slash Commands = 0>
   <Arial>
   <12>
```

### Users Input/Output Interaction ###
  * The users of Parrot IM will interact with our program by using their compatible keyboard and mouse on their Operating System.
  * Users interaction with Parrot IM GUI is displayed on their monitor (CLT/LCD/TV) screen.
  * The users' information will be stored on their own server.
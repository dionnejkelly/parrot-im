This is a document about changes we made from Beta version testing

# Introduction #

As our previous versions we had three phases for testing:

1. **Unit Testing**

2. **Integration Testing**

3. **User Acceptance Testing**

In this version, we focused more on testing that new features:

Is all working, and doesn't cause problems when integrated with our system.

Also, we tested our user interface, by cognitive walkthrough by our team, and asking our users to use our system and give us what they thought about the interface.

---


## Unit Testing ##

As our previous versions, we used JUnit. We focused more on blackbox testing here more than whitebox because it's faster, but still for some test cases we needed to read the actual implementation. We added to our test cases package:

SlashCommandTest.java

To Check our test (steps to import our test cases or installing JUnit check our previous document).

Also, we revise our work on previous unit tests, and we made most of them succesfully working. But we left test cases related to database, model, GUI and controller because testing them using unit testing is more complicated and time consuming than just walkthrough the system which is simpler.



---


## Integration Testing ##

We walkthrough our system to see if the new features are integrating perfectly with our system.

Also, we asked some users to use our system and perform new tasks (plus the tasks from previous documents) :

**Observations and Changes:**

1. MSN is working and you can chat with a friend with no problem.

2. SQL exceptions from ' in some text fields are now solved and don't cause exceptions.



---


## User Acceptance Testing ##

After we made changes to our user interface, and we made sure that our system is working well and the user interface are fully polished, we asked () users to use our system and give us there thoughts about the system.

**Observations on Product:**


---

## Known Bugs and Errors ##
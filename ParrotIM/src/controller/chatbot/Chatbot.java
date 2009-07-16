/* Chatbot.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package controller.chatbot;

import java.util.*;

import view.options.modelstub.CustomizedChatbotModel;

import model.dataType.ChatbotQADataType;


/**
 * The Chatbot currently stores all the knowledge base and respond list data
 * to provide users with auto-mated messaging. The Chatbot can be turn on/off
 * from the Contact List by simply clicking the Chatbot Enabled.
 * 
 */

public class Chatbot {
	
  
	// Section
    // I - Final Data Members

    /**
     * Holds the limit for the knowledge base and the response. Also, puncSymDelim
     * is used to hold any symbols that will be delimited during the clean string process.
    */

     private final int userMaxInput = 1;
     private final int userMaxResp = 4;
     private final String puncSymbDelim = "?!.;:/(){}@#$%^&*+-|\\\'<>{}[]\"";
     
     private CustomizedChatbotModel customizedModel;
     
     
     // Section
     // II - Non-Static Data Members

     /**
      * The Chatbot data to process.
     */
     
     private String sInput = new String("");
     private String sResponse = new String("");
     private String sPrevInput = new String("");
     private String sPrevResponse = new String("");
     private String sEvent = new String("");
     private String sPrevEvent = new String("");
     private String sInputBackup = new String("");
     private String	sSubject = new String("");
 	 private String	sKeyWord = new String("");
     private boolean chatBotQuit = false;

     private String customizedAnswer;
     
     private String transposList[][] = {
			{"I'M", "YOU'RE"},
			{"AM", "ARE"},
			{"WERE", "WAS"},
			{"ME", "YOU"},
			{"YOURS", "MINE"},
			{"YOUR", "MY"},
			{"I'VE", "YOU'VE"},
			{"I", "YOU"},
			{"AREN'T", "AM NOT"},
			{"WEREN'T", "WASN'T"},
			{"I'D", "YOU'D"},
			{"DAD", "FATHER"},
			{"MOM", "MOTHER"},
			{"DREAMS", "DREAM"},
			{"MYSELF", "YOURSELF"}
		};
     
     /**
      *  Collection of respond list.
     */  
     
     private Vector<String>  respList = new Vector<String>(userMaxResp);
        
     
     /**
      *  Collection of knowledge base.
     */  
      
     private String[][][] KnowledgeBase = {
    			{{"WHAT IS YOUR NAME", "What really is your name?"}, 
    				{"MY NAME IS CHATTERBOT9.",
    				 "YOU CAN CALL ME CHATTERBOT9.",
    				 "WHY DO YOU WANT TO KNOW MY NAME?"}
    				},

    				{{"HI", "HELLO"}, 
    				{"HI THERE!",
    				 "HOW ARE YOU?",
    				 "HI!"}
    				},

    				{{"I"},
    				{"SO, YOU ARE TALKING ABOUT YOURSELF",
    				 "SO, THIS IS ALL ABOUT YOU?",
    				 "TELL ME MORE ABOUT YOURSELF."}, 
    				},

    				{{"I WANT"},
    				{"WHY DO YOU WANT IT?",
    				 "IS THERE ANY REASON WHY YOU WANT THIS?",
    				 "IS THIS A WISH?",
    				 "WHAT ELSE YOU WANT?"}
    				},

    				{{"I HATE"},
    				{"WHY DO YOU HATE IT?",
    				 "WHY DO YOU HATE*?",
    				 "THERE MUST A GOOD REASON FOR YOU TO HATE IT.",
    				 "HATERED IS NOT A GOOD THING BUT IT COULD BE JUSTIFIED WHEN IT IS SOMETHING BAD."}
    				},

    				{{"I LOVE CHATING"},
    				{"GOOD, ME TOO!",
    				 "DO YOU CHAT ONLINE WITH OTHER PEOPLE?",
    				 "FOR HOW LONG HAVE YOU BEEN CHATING?",
    				 "WHAT IS YOUR FAVORITE CHATING WEBSITE?"}
    				},

    				{{"I MEAN"},
    				{"SO, YOU MEAN*.",
    				 "SO, THAT'S WHAT YOU MEAN.",
    				 "I THINK THAT I DIDN'T CATCH IT THE FIRST TIME.",
    				 "OH, I DIDN'T KNOW MEANT THAT."}
    				},

    				{{"I DIDN'T MEAN"},
    				{"OK, YOU DIDN'T MEAN*.",
    				 "OK, WHAT DID YOU MEAN THEN?",
    				 "SO I GUESS THAT I MISSUNDESTOOD."}
    				},

    				{{"I GUESS"},
    				{"SO YOU ARE A MAKING GUESS.",
    				 "AREN'T YOU SURE?",
    				 "ARE YOU GOOD A GUESSING?",
    				 "I CAN'T TELL IF IT IS A GOOD GUESS."}
    				},

    				{{"I'M DOING FINE", "I'M DOING OK"},
    				{"I'M GLAD TO HEAR IT!",
    				 "SO, YOU ARE IN GOOD SHAPE."}
    				},

    				{{"CAN YOU THINK", "ARE YOU ABLE TO THINK", "ARE YOU CAPABLE OF THINKING"},
    				{"YES OFCORSE I CAN, COMPUTERS CAN THINK JUST LIKE HUMAN BEING.",
    				 "ARE YOU ASKING ME IF POSSESS THE CAPACITY OF THINKING?",
    				 "YES OFCORSE I CAN."},
    				},

    				{{"CAN YOU THINK OF"},
    				{"YOU MEAN LIKE IMAGINING SOMETHING.",
    				 "I DON'T KNOW IF CAN DO THAT.",
    				 "WHY DO YOU WANT ME THINK OF IT?"}
    				},
    				
    				{{"HOW ARE YOU", "HOW DO YOU DO"},
    				{"I'M DOING FINE!",
    				 "I'M DOING WELL AND YOU?",
    				 "WHY DO YOU WANT TO KNOW HOW AM I DOING?"}
    				},

    				{{"WHO ARE YOU"},
    				{"I'M AN A.I PROGRAM.",
    				 "I THINK THAT YOU KNOW WHO I'M.",
    				 "WHY ARE YOU ASKING?"}
    				},

    				{{"ARE YOU INTELLIGENT"},
    				{"YES,OFCORSE.",
    				 "WHAT DO YOU THINK?",
    				 "ACTUALY,I'M VERY INTELLIGENT!"}
    				},

    				{{"ARE YOU REAL"},
    				{"DOES THAT QUESTION REALLY MATERS TO YOU?",
    				 "WHAT DO YOU MEAN BY THAT?",
    				 "I'M AS REAL AS I CAN BE."}
    				},

    				{{"MY NAME IS", "YOU CAN CALL ME"},
    				{"SO, THAT'S YOUR NAME.",
    				 "THANKS FOR TELLING ME YOUR NAME USER!",
    				 "WHO GIVE YOU THAT NAME?"}
    				},

    				{{"SIGNON**"},
    				{"HELLO USER, WHAT IS YOUR NAME?",
    				 "HELLO USER, HOW ARE YOU DOING TODAY?",
    				 "HI USER, WHAT CAN I DO FOR YOU?",
    				 "YOU ARE NOW CHATING WITH CHATTERBOT9, ANYTHING YOU WANT TO DISCUSS?"}
    				},

    				{{"REPETITION T1**"},
    				{"YOU ARE REPEATING YOURSELF.",
    				 "USER, PLEASE STOP REPEATING YOURSELF.",
    				 "THIS CONVERSATION IS GETING BORING.",
    				 "DON'T YOU HAVE ANY THING ELSE TO SAY?"}
    				},
    				
    				{{"REPETITION T2**"},
    				{"YOU'VE ALREADY SAID THAT.",
    				 "I THINK THAT YOU'VE JUST SAID THE SAME THING BEFORE.",
    				 "DIDN'T YOU ALREADY SAID THAT?",
    				 "I'M GETING THE IMPRESSION THAT YOU ARE REPEATING THE SAME THING."}
    				},

    				{{"BOT DON'T UNDERSTAND**"},
    				{"I HAVE NO IDEA OF WHAT YOU ARE TALKING ABOUT.",
    				 "I'M NOT SURE IF I UNDERSTAND WHAT YOU ARE TALKING ABOUT.",
    				 "CONTINUE, I'M LISTENING...",
    				 "VERY GOOD CONVERSATION!"}
    				},

    				{{"NULL INPUT**"},
    				{"HUH?",
    				 "WHAT THAT SUPPOSE TO MEAN?",
    				 "AT LIST TAKE SOME TIME TO ENTER SOMETHING MEANINGFUL.",
    				 "HOW CAN I SPEAK TO YOU IF YOU DON'T WANT TO SAY ANYTHING?"}
    				},

    				{{"NULL INPUT REPETITION**"},
    				{"WHAT ARE YOU DOING??",
    				 "PLEASE STOP DOING THIS IT IS VERY ANNOYING.",
    				 "WHAT'S WRONG WITH YOU?",
    				 "THIS IS NOT FUNNY."}
    				},

    				{{"BYE", "GOODBYE"},
    				{"IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!",
    				 "BYE USER!",
    				 "OK, BYE!"}
    				},

    				{{"OK"},
    				{"DOES THAT MEAN THAT YOU ARE AGREE WITH ME?",
    				 "SO YOU UNDERSTAND WHAT I'M SAYING.",
    				 "OK THEN."},
    				},

    				{{"OK THEN"},
    				{"ANYTHING ELSE YOU WISH TO ADD?",
    				 "IS THAT ALL YOU HAVE TO SAY?",
    				 "SO, YOU AGREE WITH ME?"}
    				},

    				{{"ARE YOU A HUMAN BEING"},
    				{"WHY DO YOU WANT TO KNOW?",
    				 "IS THIS REALLY RELEVENT?"}
    				},

    				{{"YOU ARE VERY INTELLIGENT"},
    				{"THANKS FOR THE COMPLIMENT USER, I THINK THAT YOU ARE INTELLIGENT TO!",
    				 "YOU ARE A VERY GENTLE PERSON!",
    				 "SO, YOU THINK THAT I'M INTELLIGENT."}
    				},

    				{{"YOU ARE WRONG"},
    				{"WHY ARE YOU SAYING THAT I'M WRONG?",
    				 "IMPOSSIBLE, COMPUTERS CAN NOT MAKE MISTAKES.",
    				 "WRONG ABOUT WHAT?"}
    				},

    				{{"ARE YOU SURE"},
    				{"OFCORSE I'M.",
    			 	 "IS THAT MEAN THAT YOU ARE NOT CONVINCED?",
    				 "YES,OFCORSE!"}
    				},

    				{{"WHO IS"},
    				{"I DON'T THINK I KNOW WHO.",
    				 "I DON'T THINK I KNOW WHO*.",
    				 "DID YOU ASK SOMEONE ELSE ABOUT IT?",
    				 "WOULD THAT CHANGE ANYTHING AT ALL IF I TOLD YOU WHO."}
    				},

    				{{"WHAT"},
    				{"SHOULD I KNOW WHAT*?",
    				 "I DON'T KNOW WHAT*.",
    				 "I DON'T KNOW.",
    				 "I DON'T THINK I KNOW.",
    				 "I HAVE NO IDEA."}
    				},

    				{{"WHERE"},
    				{"WHERE? WELL,I REALLY DON'T KNOW.",
    				 "SO, YOU ARE ASKING ME WHERE*?",
    				 "DOES THAT MATERS TO YOU TO KNOW WHERE?",
    				 "PERHAPS,SOMEONE ELSE KNOWS WHERE."}
    				},

    				{{"WHY"},
    				{"I DON'T THINK I KNOW WHY.",
    				 "I DON'T THINK I KNOW WHY*.",
    				 "WHY ARE YOU ASKING ME THIS?",
    				 "SHOULD I KNOW WHY.",
    			     "THIS WOULD BE DIFFICULT TO ANSWER."}
    				},

    				{{"DO YOU"},
    				{"I DON'T THINK I DO",
    				 "I WOULDN'T THINK SO.",
    				 "WHY DO YOU WANT TO KNOW?",
    				 "WHY DO YOU WANT TO KNOW*?"}
    				},

    				{{"CAN YOU"},
    				{"I THINK NOT.",
    				 "I'M NOT SURE.",
    				 "I DON'T THINK THAT I CAN DO THAT.",
    				 "I DON'T THINK THAT I CAN*.",
    				 "I WOULDN'T THINK SO."}
    				},

    				{{"YOU ARE"},
    				{"WHAT MAKES YOU THINK THAT?",
    				 "IS THIS A COMPLIMENT?",
    				 "ARE YOU MAKING FUN OF ME?",
    				 "SO, YOU THINK THAT I'M*."}
    				},

    				{{"DID YOU"},
    				{"I DON'T THINK SO.",
    				 "YOU WANT TO KNOW IF DID*?",
    				 "ANYWAY, I WOULDN'T REMEMBER EVEN IF I DID."}
    				},

    				{{"COULD YOU"},
    				{"ARE YOU ASKING ME FOR A FEVER?",
    				 "WELL,LET ME THINK ABOUT IT.",
    				 "SO, YOU ARE ASKING ME I COULD*.",
    				 "SORRY,I DON'T THINK THAT I COULD DO THIS."}
    				},

    				{{"WOULD YOU"},
    				{"IS THAT AN INVITATION?",
    				 "I DON'T THINK THAT I WOULD*.",
    				 "I WOULD HAVE TO THINK ABOUT IT FIRST."}
    				},

    				{{"YOU"},
    				{"SO, YOU ARE TALKING ABOUT ME.",
    				 "I JUST HOPE THAT THIS IS NOT A CRITICISM.",
    				 "IS THIS A COMPLIMENT??",
    				 "WHY TALKING ABOUT ME, LETS TALK ABOUT YOU INSTEAD."}
    				},

    				{{"HOW"},
    				{"I DON'T THINK I KNOW HOW.",
    				 "I DON'T THINK I KNOW HOW*.",
    				 "WHY DO YOU WANT TO KNOW HOW?",
    				 "WHY DO YOU WANT TO KNOW HOW*?"}
    				},

    				{{"HOW OLD ARE YOU"},
    				{"WHY DO WANT TO KNOW MY AGE?",
    				 "I'M QUIET YOUNG ACTUALY.",
    				 "SORRY, I CAN NOT TELL YOU MY AGE."}
    				},

    				{{"HOW COME YOU DON'T"},
    				{"WERE YOU EXPECTING SOMETHING DIFFERENT?",
    				 "ARE YOU DISAPOINTED?",
    				 "ARE YOU SURPRISED BY MY LAST RESPONSE?"}
    				},

    				{{"WHERE ARE YOU FROM"},
    				{"I'M FROM A COMPUTER.",
    				 "WHY DO YOU WANT TO KNOW WHERE I'M FROM?",
    				 "WHY DO YOU WANT TO KNOW THAT?"}
    				},

    				{{"WHICH ONE"},
    				{"I DON'T THINK THAT I KNOW WICH ONE IT IS.",
    				 "THIS LOOKS LIKE A TRICKY QUESTION TO ME."}
    				},

    				{{"PERHAPS"},
    				{"WHY ARE YOU SO UNCERTAIN?",
    				 "YOU SEEMS UNCERTAIN."}
    				},

    				{{"YES"},
    				{"SO, ARE YOU SAYING YES.",
    				 "SO, YOU APPROVE IT.",
    				 "OK THEN."}
    				},

    				{{"NOT AT ALL"},
    				{"ARE YOU SURE?",
    				 "SHOULD I BELIEVE YOU?",
    				 "SO, IT'S NOT THE CASE."}
    				},

    				{{"NO PROBLEM"},
    				{"SO, YOU APPROVE IT.",
    				 "SO, IT'S ALL OK."}
    				},

    				{{"NO"},
    				{"SO YOU DISAPROVE IT?",
    				 "WHY ARE YOU SAYING NO?",
    				 "OK, SO IT'S NO, I THOUGHT THAT YOU WOULD SAY YES."}
    				},

    				{{"I DON'T KNOW"},
    				{"ARE YOU SURE?",
    				 "ARE YOU REALLY TELLING ME THE TRUTH?",
    				 "SO,YOU DON'T KNOW?"}
    				},

    				{{"NOT REALLY"},
    				{"OK I SEE.",
    				 "YOU DON'T SEEM PRETTY CERTAIN.",
    				 "SO,THAT WOULD BE A \"NO\"."}
    				},

    				{{"IS THAT TRUE"},
    				{"I CAN'T BE QUIET SURE ABOUT THIS.",
    				 "CAN'T TELL YOU FOR SURE.",
    				 "DOES THAT REALLY MATERS TO YOU?"}
    				},

    				{{"THANK YOU"},
    				{"YOU ARE WELCOME!",
    				 "YOU ARE A VERY POLITE PERSON!"}
    				},

    				{{"YOU"},
    				{"SO,YOU ARE TALKING ABOUT ME.",
    				 "WHY DON'T WE TALK ABOUT YOU INSTEAD?",
    				 "ARE YOU TRYING TO MAKING FUN OF ME?"}
    				},

    				{{"YOU ARE RIGHT"},
    				{"THANKS FOR THE COMPLIMENT!",
    				 "SO, I WAS RIGHT, OK I SEE.",
    				 "OK, I DIDN'T KNOW THAT I WAS RIGHT."}
    				},

    				{{"YOU ARE WELCOME"},
    				{"OK, YOU TOO!",
    				 "YOU ARE A VERY POLITE PERSON!"}
    				},

    				{{"THANKS"},
    				{"YOU ARE WELCOME!",
    				 "NO PROBLEM!"}
    				},

    				{{"WHAT ELSE"},
    				{"WELL,I DON'T KNOW.",
    				 "WHAT ELSE SHOULD THERE BE?",
    				 "THIS LOOKS LIKE A COMPLICATED QUESTION TO ME."}
    				},

    				{{"SORRY"},
    				{"YOU DON'T NEED TO BE SORRY USER.",
    				 "IT'S OK.",
    				 "NO NEED TO APOLOGIZE."}
    				},

    				{{"NOT EXACTLY"},
    				{"WHAT DO YOU MEAN NOT EXACTLY?",
    				 "ARE YOU SURE?",
    				 "AND WHY NOT?",
    				 "DID YOU MEANT SOMETHING ELSE?"}
    				},

    				{{"EXACTLY"},
    				{"SO,I WAS RIGHT.",
    				 "OK THEN.",
    				 "SO ARE BASICALY SAYING I WAS RIGHT ABOUT IT?"}
    				},

    				{{"ALRIGHT"},
    				{"ALRIGHT THEN.",
    				 "SO, YOU ARE SAYING IT'S ALRIGHT.",
    				 "OK THEN."}
    				},

    				{{"I DON'T"},
    				{"WHY NOT?",
    				 "AND WHAT WOULD BE THE REASON FOR THIS?"}
    				},

    				{{"REALLY"},
    				{"WELL,I CAN'T TELL YOU FOR SURE.",
    				 "ARE YOU TRYING TO CONFUSE ME?",
    				 "PLEASE DON'T ASK ME SUCH QUESTION,IT GIVES ME HEADEACHS."}
    				},

    				{{"NOTHING"},
    				{"NOT A THING?",
    				 "ARE YOU SURE THAT THERE IS NOTHING?",
    				 "SORRY, BUT I DON'T BELIEVE YOU."}
    				}
    			};
//     private String[][] KnowledgeBase = {
//                        {"WHAT IS YOUR NAME", 
//                         "My name is Phyllis.",
//                         "I am known as Phyllis.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "It is a secret.",
//                         "I am thinking if I should tell your or not."
//                        },
//                        
//                        {"COOL",
//                         "Yes,it is cool.",
//                         "I'm agree with you.",
//                         "Same feeling.",
//                        },
//                        
//                        {"HOW LONG",
//                         "I'm not sure. Sorry about that.",
//                         "What do you mean?",
//                         "I do not konw.Sorry",
//                         "Let me think about it."	
//                        },
//                        
//                        {"WHAT IS UP",
//                         "Good",
//                         "Pretty Good",
//                         "Great",
//                         "Fine",
//                         "Not bad",
//                         "What about you?"
//                        },
//                        
//                        {"WHAT'S UP",
//                            ""
//                        },
//                        
//                        {"WHEN WILL YOU BE DONE"
//                            
//                        },
//                        
//                        {"DO YOU HAVE ANYTHING TO DO"
//                            
//                        },
//                        
//                        {"HOT"
//                            
//                        },
//                        
//                        {"BABY"
//                            
//                        },
//                        
//                        {"COME ON"
//                            
//                        },
//                        
//                        {"OH MY GOD"
//                            
//                        },
//                        
//                        {"DUMB"
//                            
//                        },
//                        
//                        {"GENDER"
//                            
//                        },
//                        
//                        {"DON'T BLAME"
//                            
//                        },
//                        
//                        {"WHAT MOVIES DO YOU LIKE"
//                            
//                        },
//                        
//                        {"WHAT KIND OF CARS DO YOU LIKE"
//                            
//                        },
//                        
//                        {"WHAT DO YOU DO"
//                            
//                        },
//                        
//                        {"WHAT IS YOUR FAVOURITE SPORTS"
//                            
//                        },
//                        
//                        
//                        
//                        
//                        {"HOW IS GOING",
//                         "Good",
//                         "Pretty Good",
//                         "Great",
//                         "Fine",
//                         "Not bad",
//                         "What about you?"	
//                        },
//        
//                         
//                        {"WHAT IS YOUR AGE", 
//                         "I am 19 years old.",
//                         "I am 19.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "I am old enough.",
//                         "It is a secret.",
//                         "I am thinking if I should tell your or not."
//                        },
//                        
//                        {"WHAT IS YOUR GENDER", 
//                         "I am a lady.",
//                         "I am a female.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "It is a secret."
//                        },
//                        
//                        {"WHERE DO YOU LIVE", 
//                         "I live in Vancouver.",
//                         "I live VanCity.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "It is a secret."
//                        },
//                                
//                        {"WHAT IS YOUR PHONENUMBER", 
//                         "My phone number is 604-567-7890.",
//                         "My number is 604-567-7890.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "It is a secret."
//                        },
//                        
//                        {"WHAT IS YOUR JOB", 
//                         "My job is tutoring.",
//                         "I work as a tutor.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "It is a secret."
//                        },
//                                
//                        {"WHAT IS YOUR OCCUPATION", 
//                         "I am a student.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "It is a secret.",
//                         "I am thinking if I should tell your or not."
//                        },
//                        
//                        {"WHEN CAN I CONTACT YOU", 
//                         "I will be back home by 7 pm.",
//                         "You can contact me at 7 pm.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "I am thinking if I should tell your or not."
//                        },
//                                
//                        {"WHEN SHOULD I REPLY YOU", 
//                         "I will be back home by 7 pm.",
//                         "You can contact me at 7 pm.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "I am thinking if I should tell your or not."
//                    },
//                                
//                        {"WHAT IS YOUR HOBBY", 
//                         "I play sports.",
//                         "I play computer.",
//                         "I like to watch movies",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "I am thinking if I should tell your or not."
//                        },
//                                
//                        {"WHAT LANGUAGE DO YOU KNOW", 
//                         "I speak English and French.",
//                         "I know English and French.",
//                         "Why do you want to know?",
//                         "I am scared to tell you.",
//                         "This is none of your business!",
//                         "I am thinking if I should tell your or not."
//                        },
//                        
//                        {"HI", 
//                         "Hi there!",
//                         "How are you doing?",
//                         "Hi!",
//                         "What's up?",
//                         "Howdy!",
//                         "Bonjour!"
//                        },
//                        
//                        {"HELLO", 
//                         "Hi there!",
//                         "How are you doing?",
//                         "Hi!",
//                         "What's up?",
//                         "Howdy!",
//                         "Bonjour!"
//                        },
//                        
//                        {"HOW ARE YOU",
//                         "I'm doing good and you?",
//                         "I'm doing well!",
//                         "I'm doing fine!",
//                         "Why do you want to know how am i doing?",
//                         "Never been better!",
//                         "Very good and you?"
//                        },
//
//                        {"WHO ARE YOU",
//                         "I'M Phyllis from Vancouver.",
//                         "I THINK THAT YOU KNOW WHO I'M.",
//                         "Why are you asking me?",
//                         "What is your intention?",
//                         "Do you really want to know?",
//                         "You better be ready!"
//                        },
//
//                        {"ARE YOU INTELLIGENT",
//                         "Yes.",
//                         "Yes, of course.",
//                         "What do you think?",
//                         "You don't believe so?",
//                         "You should know me better.",
//                         "What is your guess?"
//                        },
//
//                        {"ARE YOU REAL",
//                         "Does that question really matter to you?",
//                         "What do you mean my friend?",
//                         "I'm just trying to be a real person.",
//                         "Give me some credits!",
//                         "Give me some break!",
//                         "What is your guess?"
//                        },
//                        
//                        {"BYE",
//                         "IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!",
//                         "See you later my friend!",
//                         "See you in another life!",
//                         "Take care!",
//                         "See you again!",
//                         "Bye!?"
//                        },
//                        
//                        {"REPETITION T1**",
//                         "YOU ARE REPEATING YOURSELF.",
//                         "USER, PLEASE STOP REPEATING YOURSELF.",
//                         "THIS CONVERSATION IS GETING BORING.",
//                         "DONT YOU HAVE ANY THING ELSE TO SAY?",
//                         "COME ON,PLEASE SAY SOMETHING ELSE."
//                        },
//                                
//                        {"REPETITION T2**",
//                         "YOU'VE ALREADY SAID THAT.",
//                         "I THINK THAT YOU'VE JUST SAID THE SAME THING BEFORE.",
//                         "DIDN'T YOU ALREADY SAID THAT?",
//                         "I'M GETING THE IMPRESSION THAT YOU ARE REPEATING THE SAME THING.",
//                         "OK, NEVER MIND."
//                        },
//
//                        {"BOT DONT UNDERSTAND**",
//                         "I HAVE NO IDEA OF WHAT YOU ARE TALKING ABOUT.",
//                         "I'M NOT SURE IF I UNDERSTAND WHAT YOU ARE TALKING ABOUT.",
//                         "CONTINUE, I'M LISTENING...",
//                         "I'M SORRY?",
//                         "PARDON ME.",
//                         "VERY GOOD CONVERSATION!"
//                        },
//
//                        {"NULL INPUT**",
//                         "HUH?",
//                         "WHAT THAT SUPPOSE TO MEAN?",
//                         "AT LIST TAKE SOME TIME TO ENTER SOMETHING MEANINGFUL.",
//                         "HOW CAN I SPEAK TO YOU IF YOU DONT WANT TO SAY ANYTHING?",
//                         "I'M SORRY?",
//                         "PARDON ME.",
//                        },
//
//                        {"NULL INPUT REPETITION**",
//                         "WHAT ARE YOU DOING??",
//                         "PLEASE STOP DOING THIS IT IS VERY ANNOYING.",
//                         "WHAT'S WRONG WITH YOU?",
//                         "THIS IS NOT FUNNY."
//                        },
//                                
//                        {"ARE YOU SURE",
//                         "OFCORSE I'M.",
//                         "IS THAT MEAN THAT YOU ARE NOT CONVINCED?",
//                         "YES,OFCORSE!",
//                         "DON'T YOU BELIEVE ME?"
//                        },
//
//                        {"WHO IS",
//                         "I DONT THINK I KNOW WHO.",
//                         "DID YOU ASK SOMEONE ELSE ABOUT IT?",
//                         "WOULD THAT CHANGE ANYTHING AT ALL IF I TOLD YOU WHO."
//                        },
//
//                        {"WHAT",
//                         "I DONT KNOW.",
//                         "I DONT THINK I KNOW.",
//                         "I HAVE NO IDEA."
//                        },
//
//                        {"WHERE",
//                         "WHERE? WELL,I REALLY DONT KNOW.",
//                         "DOES THAT MATERS TO YOU TO KNOW WHERE?",
//                         "PERHAPS,SOMEONE ELSE KNOWS WHERE."
//                        },
//
//                        {"WHY",
//                         "I DONT THINK I KNOW WHY.",
//                         "WHY ARE YOU ASKING ME THIS?",
//                         "SHOULD I KNOW WHY.",
//                         "THIS WOULD BE DIFFICULT TO ANSWER."
//                        },
//
//                        {"DO YOU",
//                         "I DONT THINK I DO",
//                         "I WOULDN'T THINK SO.",
//                         "WHY DO YOU WANT TO KNOW?"
//                        },
//
//                        {"CAN YOU",
//                         "I THINK NOT.",
//                         "I'M NOT SURE.",
//                         "I DONT THINK THAT I CAN DO THAT."
//                        },
//
//                        {"YOU ARE",
//                         "WHAT MAKES YOU THINK THAT?",
//                         "IS THIS A COMPLIMENT?",
//                         "ARE YOU MAKING FUN OF ME?"
//                        },
//
//                        {"DID YOU",
//                         "I DONT THINK SO.",
//                         "ANYWAY, I WOULDN'T REMEMBER EVEN IF I DID."
//                        },
//
//                        {"COULD YOU",
//                         "ARE YOU ASKING ME FOR A FEVER?",
//                         "WELL,LET ME THINK ABOUT IT.",
//                         "SORRY,I DONT THINK THAT I COULD DO THIS."
//                        },
//
//                        {"WOULD YOU",
//                         "IS THAT AN INVITATION?",
//                         "I WOULD HAVE TO THINK ABOUT IT FIRST."
//                        },
//
//                        {"HOW",
//                         "I DONT THINK I KNOW HOW.",
//                         "Do not ask me again."
//                        },
//
//                        {"WHICH ONE",
//                         "I DONT THINK THAT I KNOW WICH ONE IT IS.",
//                         "THIS LOOKS LIKE A TRICKY QUESTION TO ME."
//                        },
//
//                        {"PERHAPS",
//                         "WHY ARE YOU SO UNCERTAIN?",
//                         "YOU SEEMS UNCERTAIN."
//                        },
//
//                        {"YES",
//                         "SO,IT IS YES.",
//                         "OH, REALLY?",
//                         "OK THEN."
//                        },
//
//                        {"I DONT KNOW",
//                         "ARE YOU SURE?",
//                         "ARE YOU REALLY TELLING ME THE TRUTH?",
//                         "SO,YOU DONT KNOW?"
//                        },
//
//                        {"NOT REALLY",
//                         "OK I SEE.",
//                         "YOU DONT SEEM PRETTY CERTAIN.",
//                         "SO,THAT WOULD BE A \"NO\"."
//                        },
//
//                        {"IS THAT TRUE",
//                        "I CAN'T BE QUIET SURE ABOUT THIS.",
//                         "CAN'T TELL YOU FOR SURE.",
//                         "DOES THAT REALLY MATERS TO YOU?"
//                        },
//
//                        {"YOU",
//                         "SO,YOU ARE TALKING ABOUT ME.",
//                         "WHY DONT WE TALK ABOUT YOU INSTEAD?",
//                         "ARE YOU TRYING TO MAKING FUN OF ME?"
//                        },
//
//                        {"THANKS",
//                         "YOU ARE WELCOME!",
//                         "NO PROBLEM!"
//                        },
//
//                        {"WHAT ELSE",
//                         "WELL,I DONT KNOW.",
//                         "WHAT ELSE SHOULD THERE BE?",
//                         "THIS LOOKS LIKE A COMPLICATED QUESTION TO ME."
//                        },
//
//                        {"SORRY",
//                         "YOU DONT NEED TO BE SORRY USER.",
//                         "IT'S OK.",
//                         "NO NEED TO APOLOGIZE."
//                        },
//
//                        {"NOT EXACTLY",
//                         "WHAT DO YOU MEAN NOT EXACTLY?",
//                         "ARE YOU SURE?"
//                        },
//
//                        {"EXACTLY",
//                         "SO,I WAS RIGHT.",
//                         "OK THEN."
//                        },
//
//                        {"ALRIGHT",
//                         "ALRIGHT THEN.",
//                         "OK THEN."
//                        },
//
//                        {"I DONT",
//                         "WHY NOT?",
//                         "AND WHAT WOULD BE THE REASON FOR THIS?"
//                        },
//
//                        {"REALLY",
//                         "WELL,I CAN'T TELL YOU FOR SURE.",
//                         "ARE YOU TRYING TO CONFUSE ME?",
//                         "PLEASE DONT ASK ME SUCH QUESTION,IT GIVES ME HEADEACHES."
//                        }            
//                };
//     private int firstIndex = 0;
//     private int secondIndex = 0;
//     
//     private int prevSecondIndex = secondIndex;
//     
//     public Chatbot(String question, String answer) {
//    	 KnowledgeBase[firstIndex][secondIndex] = question;
//    	 KnowledgeBase[firstIndex][secondIndex + 1] = answer;
//    	 
//    	 firstIndex++;
//    	 prevSecondIndex = secondIndex + 1;
//     }
//     
//     public void setMoreAnswer(String answer) {
//    	 KnowledgeBase[firstIndex - 1][prevSecondIndex] = answer;
//    	 prevSecondIndex++;
//     }
     // Section
     // III - Message Extraction

     /**
      * Adds a user's input to the Chatbot's data.
      * 
      * @param message
      * @throws Exception
      */   
      
      public void get_input(String message) throws Exception 
        {
  
    	  System.out.println("Before = " + message + ")");
    	  
                save_prev_input();

                sInput = message;
                
                customizedAnswer = getCustomizedResponse(sInput);
                
                preprocess_input();
        }
      
      
      
      /**
       * Returns a String response. 
       * 
       * @return A Chatbot response represented by Strings.
       */

        public String respond()
        {
//                save_prev_response();
//                set_event("BOT UNDERSTAND**");
//
//                String response = "";
//                
//                if(null_input())
//                {
//                        handle_event("NULL INPUT**");
//                }
//                else if(null_input_repetition())
//                {
//                        handle_event("NULL INPUT REPETITION**");
//                }
//                else if(user_repeat())
//                {
//                        handle_user_repetition();
//                }
//                else
//                {
//                        find_match();
//                }
//
//
//                if(!bot_understand())
//                {
//                        handle_event("BOT DONT UNDERSTAND**");
//                        // get it from a customized list
//                }
//                
//                if(respList.size() > 0) 
//                {
//                        select_response();
//
//                        if(bot_repeat())
//                        {
//                                handle_repetition();
//                        }
//                        response = get_response();
//                }
                
                //System.out.println("Response = " + sInput);
                //return response;
        	
        	return customizedAnswer;
        }

        
        /**
         * Returns a boolean response with Chatbot quitting. 
         * 
         * @return A Chatbot flag represented by boolean.
         */
        
        public boolean quit() {
                return chatBotQuit;
        }
        
        
        /**
         * Finds appropriate questions in the Knowledge Base.  
         */
        
        public void find_match() 
        {
//                respList.clear();
//                for(int i = 0; i < KnowledgeBase.length; ++i) 
//                {
//                   
//                        if(sInput.indexOf(KnowledgeBase[i][0]) != -1) 
//                        {
//                                int respSize = KnowledgeBase[i].length - userMaxInput;
//                                for(int j = userMaxInput; j <= respSize; ++j) 
//                                {
//                                        respList.add(KnowledgeBase[i][j]);
//                                }
//                             
//                                
//                                break;
//                        }
//                }
        	
        	respList.clear();
    		// introduce thse new "string variable" to help 
    		// support the implementation of keyword ranking 
    		// during the matching process
    		String bestKeyWord = "";
    		Vector<Integer> index_vector = new Vector<Integer>(userMaxResp);

    		for(int i = 0; i < KnowledgeBase.length; ++i) 
    		{
    			String[] keyWordList = KnowledgeBase[i][0];

    			for(int j = 0; j < keyWordList.length; ++j)
    			{
    				String keyWord = keyWordList[j];
    				// we inset a space character
    				// before and after the keyword to
    				// improve the matching process
    				keyWord = insert_space(keyWord);

    				// there has been some improvements made in
    				// here in order to make the matching process
    				// a littlebit more flexible
    				if( sInput.indexOf(keyWord) != -1 ) 
    				{
    					//'keyword ranking' feature implemented in this section
    					if(keyWord.length() > bestKeyWord.length())
    					{
    						bestKeyWord = keyWord;
    						index_vector.clear();
    						index_vector.add(i);
    					}
    					else if(keyWord.length() == bestKeyWord.length())
    					{
    						index_vector.add(i);
    					}
    				}
    			}
    		}
    		if(index_vector.size() > 0)
    		{
    			sKeyWord = bestKeyWord;
    			Collections.shuffle(index_vector);
    			int respIndex = index_vector.elementAt(0);
    			int respSize = KnowledgeBase[respIndex][1].length;
    			for(int j = 0; j < respSize; ++j) 
    			{
    				respList.add(KnowledgeBase[respIndex][1][j]);
    			}
    		}
        }
        
        /**
         * Returns the respond list.  
         * 
         * @return Vector<String>
         */
        
        public Vector<String> getRespList() {
        	return respList;
        }
        
        
        // Section
        // IV - Handling Event

        /**
         * Handles response repetition striving to answer differently.
         * 
         * @return String
         */
        
        public String handle_repetition()
        {
                if(respList.size() > 0)
                {
                        respList.removeElementAt(0);
                }
                if(no_response())
                {
                        save_input();
                        set_input(sEvent);

                        find_match();
                        restore_input();
                }
                
                return select_response();
        }
        
        
        /**
         * Handles user's question repetition to check whether or not they are repeating.
         * 
         */
    
        public void handle_user_repetition()
        {
                if(same_input()) 
                {
                        handle_event("REPETITION T1**");
                }
                else if(similar_input())
                {
                        handle_event("REPETITION T2**");
                }
        }
        
        /**
         * Returns sInputBackup.
         * 
         * @return String
         */
        
        public String getHandle_user_repetion() {
        	return sInputBackup;
        }
        
        
        /**
         * Appropriately handles events.
         * 
         * @param str
         */
  
        public void handle_event(String str)
        {
//                save_prev_event();
//                set_event(str);
//
//                save_input();
//                set_input(str);
//
//                if(!same_event()) 
//                {
//                        find_match();
//                }
//
//                restore_input();
        	
        		save_prev_event();
        		set_event(str);

        		save_input();
        		str = insert_space(str);
        		
        		set_input(str);
    		
        		if(!same_event()) 
        		{
        			find_match();
        		}

        		restore_input();
        }
        
        /**
         * Returns sInputBackup.
         * 
         * @return String
         */
        
        public String getHandle_event() {
        	return sInputBackup;
        }

        // Section
        // V - Chatbot Handling

        /**
         * Selects an appropriate response.
         * 
         * @return String
         */
 
        public String select_response() {
                Collections.shuffle(respList);
                sResponse = respList.elementAt(0);
                
//                System.out.println("Handle Repetion = " + sResponse);
//                getBuddy();
                return sResponse;
        }
        
        public void getBuddy() {
        	for(int i=0;i< this.KnowledgeBase[0].length;i++){
    			System.out.println("Answer = " + KnowledgeBase[0][i].toString());
    		}
        }
        
        /**
         * Saves users previous input.
         *
         */

        public void save_prev_input() {
                this.sPrevInput = sInput;
        }
        
        /**
         * Returns user's previous input.
         *
         * @return String
         */
        
        public String getSave_prev_input() {
        	return sPrevInput;
        }
        
        /**
         * Saves Chatbot's previous input.
         *
         */
 
        public void save_prev_response() {
                sPrevResponse = sResponse;
        }
        
        /**
         * Returns Chatbot's Previous response.
         *
         * @return String
         */
        
        public String getSave_preve_response() {
        	return sPrevResponse;
        }
        
        /**
         * Saves Chatbot's previous event.
         *
         */

        public void save_prev_event() {
                sPrevEvent = sEvent;
        }
        
        /**
         * Returns Chatbot's Previous event.
         *
         * @return String
         */
        
        public String getSave_prev_event() {
        	return sPrevEvent;
        }
        

        /**
         * Sets Chatbot's event.
         *
         *	@param str
         */
 
        public void set_event(String str) {
                sEvent = str;
        }
        
        /**
         * Returns Chatbot's current event.
         *
         * @return String
         */
        
        public String getSet_event() {
        	return sEvent;
        }
        
        
        /**
         * Saves User's event.
         *
         */

        public void save_input() {
                sInputBackup = sInput;
        }
        
        /**
         * Returns Chatbot's backup input.
         *
         * @return String
         */
        
        public String getSave_input() {
        	return sInputBackup;
        }
        
        
        /**
         * Sets User's event.
         *
         *	@param str
         */

        public void set_input(String str) {
                sInput = str;
        }
        
        /**
         * Returns Chatbot's current input.
         *
         * @return String
         */
        
        public String getSet_input() {
        	return sInput;
        }
        
        
        /**
         * Restores User's backup input.
         *
         */
        
        public void restore_input() {
        	System.out.println("========== Backup = " + sInputBackup);
                sInput = sInputBackup;
        }
        
        /**
         * Returns Chatbot's current input.
         *
         * @return String
         */
        
        public String getRestore_input() {
        	return sInput;
        }
        
        
        /**
         * Return Chatbot's response.
         * @return String
         */
        
        public String get_response()  {
                if(sResponse.length() > 0) {
                        return sResponse;
                }
                
                return "";       
        }
        
        // Section
        // VI - Chatbot's Intelligence

        /**
         * Pre-processes user's input.
         * 
         */
        
        public void preprocess_input() {
//                sInput = cleanString(sInput);
//                sInput = sInput.toUpperCase();
                
                sInput = cleanString(sInput);
        		sInput = sInput.toUpperCase();
        		sInput = insert_space(sInput);
        }
        
      
        
        /**
         * Returns Chatbot's current input.
         *
         * @return String
         */
        
        public String getPreprocess_input() {
        	return sInput;
        }
        
        
        /**
         * Checks whether Chatbot is repeating itself.
         * 
         * @return boolean
         */

        public boolean bot_repeat()  {
                return (sPrevResponse.length() > 0 && 
                        sResponse.equals(sPrevResponse));
        }
        
        /**
         * Checks whether user is repeating.
         * 
         * @return boolean
         */

        public boolean user_repeat()  {
                return (sPrevInput.length() > 0 &&
                        ((sInput.equals(sPrevInput)) || 
                        (sInput.indexOf(sPrevInput) != -1) ||
                        (sPrevInput.indexOf(sInput) != -1)));
        }
        
        /**
         * Checks whether Chatbot understand and reacts appropriately.
         * 
         * @return boolean
         */

        public boolean bot_understand()  {
                return respList.size() > 0;
        }

        /**
         * Checks whether User's input is null.
         * 
         * @return boolean
         */
        
        public boolean null_input()  {
        	// I changed this into 2 instead of 0, because any preprocess input will have two spaces
                return (sInput.length() == 2 && sPrevInput.length() != 2);
        }
        
        /**
         * Checks whether User's repeated null inputs.
         * 
         * @return boolean
         */

        public boolean null_input_repetition()  {
                return (sInput.length() == 2 && sPrevInput.length() == 2);
        }
        
        /**
         * Check whether the current event is same as the previous event.
         * 
         * @return boolean
         */

        public boolean same_event()  {
                return (sEvent.length() > 0 && sEvent.equals(sPrevEvent));
        }

        /**
         * Check whether the Chatbot is responding.
         * 
         * @return boolean
         */
        
        public boolean no_response()  {
                return respList.size() == 0;
        }
        
        /**
         * Check if the user is repeating.
         * 
         * @return boolean
         */

        public boolean same_input()  {
                return (sInput.length() > 0 && sInput.equals(sPrevInput));
        }
        
        /**
         * Check whether user's current input is similar to previous input.
         * 
         * @return boolean
         */

        public boolean similar_input()  {
                return (sInput.length() > 0 &&
                        (sInput.indexOf(sPrevInput) != -1 ||
                        sPrevInput.indexOf(sInput) != -1));
        }
        
        /**
         * Check whether user's current input contains unnecessary symbols and punctuation.
         * 
         * @param ch
         * @return boolean
         */
        
        private boolean isPunc(char ch) {
                return puncSymbDelim.indexOf(ch) != -1;
        }
        
        
        /**
         * Removes punctuation and redundant spaces from the user's input.
         * 
         * @param str
         * @return String
         */

        public String cleanString(String str) {
            if (str != null) {
                StringBuffer temp = new StringBuffer(str.length());
                char prevChar = 0;
                for(int i = 0; i < str.length(); ++i) {
                        if((str.charAt(i) == ' ' && prevChar == ' ' ) || !isPunc(str.charAt(i))) {
                                temp.append(str.charAt(i));
                                prevChar = str.charAt(i);
                        }
                        else if(prevChar != ' ' && isPunc(str.charAt(i)))
                        {
                                temp.append(' ');
                        }
                        
                }
                
                //System.out.println("From the chatbot = " + temp.toString() + ")");
                return temp.toString();
                
            }
            
            return "";
                
        }
        
        /**
         * Returns Chatbot's current input.
         *
         * @param String
         */
        
        public String getsInput(){
        	System.out.println("After  = " + sInput + ")");
        	return sInput;
        }
        
        /**
         * Returns Chatbot's knowledgebase.
         *
         * @param String[][]
         */
        
        public String[][][] get_knowledgebase(){
        	return KnowledgeBase;
        }
        
        /**
         * Check if user's input is equal to Chatbot's current input.
         *
         * @param boolean
         */
        
        public boolean check(String message) {
        	return sInput.equals(message);
        }
        
        
        public void preprocess_response()
    	{
    		if(sResponse.indexOf("*") != -1)
    		{
    			// extracting from input
    			find_subject(); 
    			// conjugating subject
    			sSubject = transpose(sSubject); 

    			sResponse = sResponse.replaceFirst("*", sSubject);
    		}
    	}
        
        public void find_subject()
    	{
    		sSubject = ""; // resets subject variable
    		StringBuffer buffer = new StringBuffer(sInput);
    		buffer.deleteCharAt(0);
    		sInput = buffer.toString();
    		int pos = sInput.indexOf(sKeyWord);
    		if(pos != -1)
    		{
    			sSubject = sInput.substring(pos + sKeyWord.length() - 1,sInput.length());		
    		}
    	}
        
     // implementing the 'sentence transposition' feature
    	public String transpose( String str )
    	{
    		boolean bTransposed = false;
    		for(int i = 0; i < transposList.length; ++i)
    		{
    			String first = transposList[i][0];
    			insert_space(first);
    			String second = transposList[i][1];
    			insert_space(second);
    			
    			String backup = str;
    			str = str.replaceFirst(first, second);
    			if(str != backup) 
    			{
    				bTransposed = true;
    			}
    		}

    		if( !bTransposed )
    		{
    			for( int i = 0; i < transposList.length; ++i )
    			{
    				String first = transposList[i][0];
    				insert_space(first);
    				String second = transposList[i][1];
    				insert_space(second);
    				str = str.replaceFirst(first, second);
    			}
    		}
    		return str;
    	}
    	
    	public String signon()
    	{
    		handle_event("SIGNON**");
    		return select_response();
    		
    	}
    	
    	public String insert_space(String str)
    	{
    		StringBuffer temp = new StringBuffer(str);
    		temp.insert(0, ' ');
    		temp.insert(temp.length(), ' ');
    		return temp.toString();
    	}
    	
    	
    	
    	public String getCustomizedResponse(String input) {
    		int QAPos = 0;
    		
    		for (QAPos = 0; QAPos < customizedModel.getQASize(); QAPos ++) {
    			Vector<String> questions = customizedModel.getQAObject(QAPos).getQuestions();
    			
    			for (int QPos = 0; QPos < questions.size(); QPos ++) {
    				
    				if (input.contains(questions.get(QPos))) {
    					Vector<String> answers = customizedModel.getQAObject(QAPos).getAnswers();
    					
    					return answers.get(randInt(answers.size()));
    				}
    			}
    		}
			return "Chat bot does not understand.";
    	}
    	
    	
    	private int randInt(int size) {
    		Random random = new Random(size);
    		return random.nextInt();
    	}
    	
    	
    	public Chatbot() {}
    	
    	public Chatbot(CustomizedChatbotModel chatbotModel) {
    		this.customizedModel = chatbotModel;
    	}
}

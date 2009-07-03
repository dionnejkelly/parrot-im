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
     private final int userMaxResp = 6;
     private final String puncSymbDelim = "?!.;:/(){}@#$%^&*+-|\\\'<>{}[]\"";
        
     
     
     // Section
     // II - Non-Static Data Members

     /**
      * The Chatbot data to process.
     */
     
     private String  sInput = new String("");
     private String  sResponse = new String("");
     private String  sPrevInput = new String("");
     private String  sPrevResponse = new String("");
     private String  sEvent = new String("");
     private String  sPrevEvent = new String("");
     private String  sInputBackup = new String("");
     private boolean chatBotQuit = false;

     /**
      *  Collection of respond list.
     */  
     
     private Vector<String>  respList = new Vector<String>(userMaxResp);
        
     
     /**
      *  Collection of knowledge base.
     */  
      
     private String[][] KnowledgeBase = {
                        {"WHAT IS YOUR NAME", 
                         "My name is Phyllis.",
                         "I am known as Phyllis.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "It is a secret.",
                         "I am thinking if I should tell your or not."
                        },
                        
                        {"COOL",
                         "Yes,it is cool.",
                         "I'm agree with you.",
                         "Same feeling.",
                        },
                        
                        {"HOW LONG",
                         "I'm not sure. Sorry about that.",
                         "What do you mean?",
                         "I do not konw.Sorry",
                         "Let me think about it."	
                        },
                        
                        {"WHAT IS UP",
                         "Good",
                         "Pretty Good",
                         "Great",
                         "Fine",
                         "Not bad",
                         "What about you?"
                        },
                        
                        {"WHAT'S UP",
                            ""
                        },
                        
                        {"WHEN WILL YOU BE DONE"
                            
                        },
                        
                        {"DO YOU HAVE ANYTHING TO DO"
                            
                        },
                        
                        {"HOT"
                            
                        },
                        
                        {"BABY"
                            
                        },
                        
                        {"COME ON"
                            
                        },
                        
                        {"OH MY GOD"
                            
                        },
                        
                        {"DUMB"
                            
                        },
                        
                        {"GENDER"
                            
                        },
                        
                        {"DON'T BLAME"
                            
                        },
                        
                        {"WHAT MOVIES DO YOU LIKE"
                            
                        },
                        
                        {"WHAT KIND OF CARS DO YOU LIKE"
                            
                        },
                        
                        {"WHAT DO YOU DO"
                            
                        },
                        
                        {"WHAT IS YOUR FAVOURITE SPORTS"
                            
                        },
                        
                        
                        
                        
                        {"HOW IS GOING",
                         "Good",
                         "Pretty Good",
                         "Great",
                         "Fine",
                         "Not bad",
                         "What about you?"	
                        },
        
                         
                        {"WHAT IS YOUR AGE", 
                         "I am 19 years old.",
                         "I am 19.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "I am old enough.",
                         "It is a secret.",
                         "I am thinking if I should tell your or not."
                        },
                        
                        {"WHAT IS YOUR GENDER", 
                         "I am a lady.",
                         "I am a female.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "It is a secret."
                        },
                        
                        {"WHERE DO YOU LIVE", 
                         "I live in Vancouver.",
                         "I live VanCity.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "It is a secret."
                        },
                                
                        {"WHAT IS YOUR PHONENUMBER", 
                         "My phone number is 604-567-7890.",
                         "My number is 604-567-7890.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "It is a secret."
                        },
                        
                        {"WHAT IS YOUR JOB", 
                         "My job is tutoring.",
                         "I work as a tutor.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "It is a secret."
                        },
                                
                        {"WHAT IS YOUR OCCUPATION", 
                         "I am a student.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "It is a secret.",
                         "I am thinking if I should tell your or not."
                        },
                        
                        {"WHEN CAN I CONTACT YOU", 
                         "I will be back home by 7 pm.",
                         "You can contact me at 7 pm.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "I am thinking if I should tell your or not."
                        },
                                
                        {"WHEN SHOULD I REPLY YOU", 
                         "I will be back home by 7 pm.",
                         "You can contact me at 7 pm.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "I am thinking if I should tell your or not."
                    },
                                
                        {"WHAT IS YOUR HOBBY", 
                         "I play sports.",
                         "I play computer.",
                         "I like to watch movies",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "I am thinking if I should tell your or not."
                        },
                                
                        {"WHAT LANGUAGE DO YOU KNOW", 
                         "I speak English and French.",
                         "I know English and French.",
                         "Why do you want to know?",
                         "I am scared to tell you.",
                         "This is none of your business!",
                         "I am thinking if I should tell your or not."
                        },
                        
                        {"HI", 
                         "Hi there!",
                         "How are you doing?",
                         "Hi!",
                         "What's up?",
                         "Howdy!",
                         "Bonjour!"
                        },
                        
                        {"HELLO", 
                         "Hi there!",
                         "How are you doing?",
                         "Hi!",
                         "What's up?",
                         "Howdy!",
                         "Bonjour!"
                        },
                        
                        {"HOW ARE YOU",
                         "I'm doing good and you?",
                         "I'm doing well!",
                         "I'm doing fine!",
                         "Why do you want to know how am i doing?",
                         "Never been better!",
                         "Very good and you?"
                        },

                        {"WHO ARE YOU",
                         "I'M Phyllis from Vancouver.",
                         "I THINK THAT YOU KNOW WHO I'M.",
                         "Why are you asking me?",
                         "What is your intention?",
                         "Do you really want to know?",
                         "You better be ready!"
                        },

                        {"ARE YOU INTELLIGENT",
                         "Yes.",
                         "Yes, of course.",
                         "What do you think?",
                         "You don't believe so?",
                         "You should know me better.",
                         "What is your guess?"
                        },

                        {"ARE YOU REAL",
                         "Does that question really matter to you?",
                         "What do you mean my friend?",
                         "I'm just trying to be a real person.",
                         "Give me some credits!",
                         "Give me some break!",
                         "What is your guess?"
                        },
                        
                        {"BYE",
                         "IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!",
                         "See you later my friend!",
                         "See you in another life!",
                         "Take care!",
                         "See you again!",
                         "Bye!?"
                        },
                        
                        {"REPETITION T1**",
                         "YOU ARE REPEATING YOURSELF.",
                         "USER, PLEASE STOP REPEATING YOURSELF.",
                         "THIS CONVERSATION IS GETING BORING.",
                         "DONT YOU HAVE ANY THING ELSE TO SAY?",
                         "COME ON,PLEASE SAY SOMETHING ELSE."
                        },
                                
                        {"REPETITION T2**",
                         "YOU'VE ALREADY SAID THAT.",
                         "I THINK THAT YOU'VE JUST SAID THE SAME THING BEFORE.",
                         "DIDN'T YOU ALREADY SAID THAT?",
                         "I'M GETING THE IMPRESSION THAT YOU ARE REPEATING THE SAME THING.",
                         "OK, NEVER MIND."
                        },

                        {"BOT DONT UNDERSTAND**",
                         "I HAVE NO IDEA OF WHAT YOU ARE TALKING ABOUT.",
                         "I'M NOT SURE IF I UNDERSTAND WHAT YOU ARE TALKING ABOUT.",
                         "CONTINUE, I'M LISTENING...",
                         "I'M SORRY?",
                         "PARDON ME.",
                         "VERY GOOD CONVERSATION!"
                        },

                        {"NULL INPUT**",
                         "HUH?",
                         "WHAT THAT SUPPOSE TO MEAN?",
                         "AT LIST TAKE SOME TIME TO ENTER SOMETHING MEANINGFUL.",
                         "HOW CAN I SPEAK TO YOU IF YOU DONT WANT TO SAY ANYTHING?",
                         "I'M SORRY?",
                         "PARDON ME.",
                        },

                        {"NULL INPUT REPETITION**",
                         "WHAT ARE YOU DOING??",
                         "PLEASE STOP DOING THIS IT IS VERY ANNOYING.",
                         "WHAT'S WRONG WITH YOU?",
                         "THIS IS NOT FUNNY."
                        },
                                
                        {"ARE YOU SURE",
                         "OFCORSE I'M.",
                         "IS THAT MEAN THAT YOU ARE NOT CONVINCED?",
                         "YES,OFCORSE!",
                         "DON'T YOU BELIEVE ME?"
                        },

                        {"WHO IS",
                         "I DONT THINK I KNOW WHO.",
                         "DID YOU ASK SOMEONE ELSE ABOUT IT?",
                         "WOULD THAT CHANGE ANYTHING AT ALL IF I TOLD YOU WHO."
                        },

                        {"WHAT",
                         "I DONT KNOW.",
                         "I DONT THINK I KNOW.",
                         "I HAVE NO IDEA."
                        },

                        {"WHERE",
                         "WHERE? WELL,I REALLY DONT KNOW.",
                         "DOES THAT MATERS TO YOU TO KNOW WHERE?",
                         "PERHAPS,SOMEONE ELSE KNOWS WHERE."
                        },

                        {"WHY",
                         "I DONT THINK I KNOW WHY.",
                         "WHY ARE YOU ASKING ME THIS?",
                         "SHOULD I KNOW WHY.",
                         "THIS WOULD BE DIFFICULT TO ANSWER."
                        },

                        {"DO YOU",
                         "I DONT THINK I DO",
                         "I WOULDN'T THINK SO.",
                         "WHY DO YOU WANT TO KNOW?"
                        },

                        {"CAN YOU",
                         "I THINK NOT.",
                         "I'M NOT SURE.",
                         "I DONT THINK THAT I CAN DO THAT."
                        },

                        {"YOU ARE",
                         "WHAT MAKES YOU THINK THAT?",
                         "IS THIS A COMPLIMENT?",
                         "ARE YOU MAKING FUN OF ME?"
                        },

                        {"DID YOU",
                         "I DONT THINK SO.",
                         "ANYWAY, I WOULDN'T REMEMBER EVEN IF I DID."
                        },

                        {"COULD YOU",
                         "ARE YOU ASKING ME FOR A FEVER?",
                         "WELL,LET ME THINK ABOUT IT.",
                         "SORRY,I DONT THINK THAT I COULD DO THIS."
                        },

                        {"WOULD YOU",
                         "IS THAT AN INVITATION?",
                         "I WOULD HAVE TO THINK ABOUT IT FIRST."
                        },

                        {"HOW",
                         "I DONT THINK I KNOW HOW.",
                         "Do not ask me again."
                        },

                        {"WHICH ONE",
                         "I DONT THINK THAT I KNOW WICH ONE IT IS.",
                         "THIS LOOKS LIKE A TRICKY QUESTION TO ME."
                        },

                        {"PERHAPS",
                         "WHY ARE YOU SO UNCERTAIN?",
                         "YOU SEEMS UNCERTAIN."
                        },

                        {"YES",
                         "SO,IT IS YES.",
                         "OH, REALLY?",
                         "OK THEN."
                        },

                        {"I DONT KNOW",
                         "ARE YOU SURE?",
                         "ARE YOU REALLY TELLING ME THE TRUTH?",
                         "SO,YOU DONT KNOW?"
                        },

                        {"NOT REALLY",
                         "OK I SEE.",
                         "YOU DONT SEEM PRETTY CERTAIN.",
                         "SO,THAT WOULD BE A \"NO\"."
                        },

                        {"IS THAT TRUE",
                        "I CAN'T BE QUIET SURE ABOUT THIS.",
                         "CAN'T TELL YOU FOR SURE.",
                         "DOES THAT REALLY MATERS TO YOU?"
                        },

                        {"YOU",
                         "SO,YOU ARE TALKING ABOUT ME.",
                         "WHY DONT WE TALK ABOUT YOU INSTEAD?",
                         "ARE YOU TRYING TO MAKING FUN OF ME?"
                        },

                        {"THANKS",
                         "YOU ARE WELCOME!",
                         "NO PROBLEM!"
                        },

                        {"WHAT ELSE",
                         "WELL,I DONT KNOW.",
                         "WHAT ELSE SHOULD THERE BE?",
                         "THIS LOOKS LIKE A COMPLICATED QUESTION TO ME."
                        },

                        {"SORRY",
                         "YOU DONT NEED TO BE SORRY USER.",
                         "IT'S OK.",
                         "NO NEED TO APOLOGIZE."
                        },

                        {"NOT EXACTLY",
                         "WHAT DO YOU MEAN NOT EXACTLY?",
                         "ARE YOU SURE?"
                        },

                        {"EXACTLY",
                         "SO,I WAS RIGHT.",
                         "OK THEN."
                        },

                        {"ALRIGHT",
                         "ALRIGHT THEN.",
                         "OK THEN."
                        },

                        {"I DONT",
                         "WHY NOT?",
                         "AND WHAT WOULD BE THE REASON FOR THIS?"
                        },

                        {"REALLY",
                         "WELL,I CAN'T TELL YOU FOR SURE.",
                         "ARE YOU TRYING TO CONFUSE ME?",
                         "PLEASE DONT ASK ME SUCH QUESTION,IT GIVES ME HEADEACHES."
                        }            
                };
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
                
                preprocess_input();
        }
      
      
      
      /**
       * Returns a String response. 
       * 
       * @return A Chatbot response represented by Strings.
       */

        public String respond()
        {
                save_prev_response();
                set_event("BOT UNDERSTAND**");

                String response = "";
                
                if(null_input())
                {
                        handle_event("NULL INPUT**");
                }
                else if(null_input_repetition())
                {
                        handle_event("NULL INPUT REPETITION**");
                }
                else if(user_repeat())
                {
                        handle_user_repetition();
                }
                else
                {
                        find_match();
                }


                if(!bot_understand())
                {
                        handle_event("BOT DONT UNDERSTAND**");
                }
                
                if(respList.size() > 0) 
                {
                        select_response();

                        if(bot_repeat())
                        {
                                handle_repetition();
                        }
                        response = get_response();
                }
                
                //System.out.println("Response = " + sInput);
                return response;
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
                respList.clear();
                for(int i = 0; i < KnowledgeBase.length; ++i) 
                {
                   
                        if(sInput.indexOf(KnowledgeBase[i][0]) != -1) 
                        {
                                int respSize = KnowledgeBase[i].length - userMaxInput;
                                for(int j = userMaxInput; j <= respSize; ++j) 
                                {
                                        respList.add(KnowledgeBase[i][j]);
                                }
                             
                                
                                break;
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
                save_prev_event();
                set_event(str);

                save_input();
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
                
                System.out.println("Handle Repetion = " + sResponse);
                getBuddy();
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
                sInput = cleanString(sInput);
                sInput = sInput.toUpperCase();
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
                return (sInput.length() == 0 && sPrevInput.length() != 0);
        }
        
        /**
         * Checks whether User's repeated null inputs.
         * 
         * @return boolean
         */

        public boolean null_input_repetition()  {
                return (sInput.length() == 0 && sPrevInput.length() == 0);
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
        
        public String[][] get_knowledgebase(){
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


}

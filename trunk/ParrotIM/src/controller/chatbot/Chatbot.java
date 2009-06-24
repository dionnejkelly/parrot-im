package controller.chatbot;

import java.io.*;
import java.util.*;

public class Chatbot {
        
        final int userMaxInput = 1;
        final int userMaxResp = 6;
        final String puncSymbDelim = "?!.;:/(){}@#$%^&*+-|\\\'<>{}[]\"";
        
        private String  sInput = new String("");
        private String  sResponse = new String("");
        private String  sPrevInput = new String("");
        private String  sPrevResponse = new String("");
        private String  sEvent = new String("");
        private String  sPrevEvent = new String("");
        private String  sInputBackup = new String("");
        private boolean chatBotQuit = false;

        // Collection of respond list
        private Vector<String>  respList = new Vector<String>(userMaxResp);
        
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
        
        

        
        
        public void get_input(String message) throws Exception 
        {
                //System.out.print(">");

                // saves the previous input
                save_prev_input();
//              BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//              sInput = in.readLine();
                sInput = message;
                
                preprocess_input();
        }

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

            if(user_want_to_quit())
                {
                        chatBotQuit = true;
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
                        response = print_response();
                }
                
                return response;
        }

        
        public boolean quit() {
                return chatBotQuit;
        }
        
        public void find_match() 
        {
                respList.clear();
                for(int i = 0; i < KnowledgeBase.length; ++i) 
                {
                        // there has been some improvements made in
                        // here in order to make the matching process
                        // a littlebit more flexible
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

        public String select_response() {
                Collections.shuffle(respList);
                sResponse = respList.elementAt(0);
                return sResponse;
        }

        public void save_prev_input() {
                sPrevInput = sInput;
        }

        public void save_prev_response() {
                sPrevResponse = sResponse;
        }

        public void save_prev_event() {
                sPrevEvent = sEvent;
        }

        public void set_event(String str) {
                sEvent = str;
        }

        public void save_input() {
                sInputBackup = sInput;
        }

        public void set_input(String str) {
                sInput = str;
        }
        
        public void restore_input() {
                sInput = sInputBackup;
        }
        
        public String print_response()  {
                if(sResponse.length() > 0) {
                        //System.out.println(sResponse);
                        return sResponse;
                }
                return "";
                
        }
        
        public void preprocess_input() {
                sInput = cleanString(sInput);
                sInput = sInput.toUpperCase();
        }

        public boolean bot_repeat()  {
                return (sPrevResponse.length() > 0 && 
                        sResponse == sPrevResponse);
        }

        public boolean user_repeat()  {
                return (sPrevInput.length() > 0 &&
                        ((sInput == sPrevInput) || 
                        (sInput.indexOf(sPrevInput) != -1) ||
                        (sPrevInput.indexOf(sInput) != -1)));
        }

        public boolean bot_understand()  {
                return respList.size() > 0;
        }

        public boolean null_input()  {
                return (sInput.length() == 0 && sPrevInput.length() != 0);
        }

        public boolean null_input_repetition()  {
                return (sInput.length() == 0 && sPrevInput.length() == 0);
        }

        public boolean user_want_to_quit()  {
                return sInput.indexOf("BYE") != -1;
        }

        public boolean same_event()  {
                return (sEvent.length() > 0 && sEvent == sPrevEvent);
        }

        public boolean no_response()  {
                return respList.size() == 0;
        }

        public boolean same_input()  {
                return (sInput.length() > 0 && sInput == sPrevInput);
        }

        public boolean similar_input()  {
                return (sInput.length() > 0 &&
                        (sInput.indexOf(sPrevInput) != -1 ||
                        sPrevInput.indexOf(sInput) != -1));
        }
        
        private boolean isPunc(char ch) {
                return puncSymbDelim.indexOf(ch) != -1;
        }
        
        // removes punctuation and redundant
        // spaces from the user's input
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
                
                System.out.println("From the chatbot = " + temp.toString());
                return temp.toString();
                
            }
            
            return "";
                
        }


}

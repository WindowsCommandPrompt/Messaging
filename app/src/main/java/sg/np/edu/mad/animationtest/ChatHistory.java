package sg.np.edu.mad.animationtest;

import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import java.util.function.*;

import java.lang.*;

import java.io.*;

public class ChatHistory {
     private ArrayList<HashMap<ChatHistory.MessageStats.MessageEntity, String>> messageList;
     private String receiver;
     private ChatHistory.MessageStats messageStats;

     //messageList must also contain information such as who sent that message in the chat and stuff....
     //End-to-end chats only.
     public ChatHistory(String receiver, ArrayList<HashMap<ChatHistory.MessageStats.MessageEntity, String>> messageList, ChatHistory.MessageStats messageStats){
         this.messageList = messageList;
         this.receiver = receiver;
         this.messageStats = messageStats;
     }

     //Create another ChatHistory constructor that is needed for

     public static class MessageStats{
         private String timeSentBySender;
         private String timeReadBySender;
         private String timeSentByReceiver;
         private String timeReadByReceiver;

         public MessageStats(String timeSentBySender, String timeReadBySender, String timeSentByReceiver, String timeReadByReceiver){
             this.timeReadByReceiver = timeReadByReceiver;
             this.timeSentBySender = timeSentBySender;
             this.timeReadBySender = timeReadBySender;
             this.timeSentByReceiver = timeSentByReceiver;
         }

         public static class MessageEntity{
             private String entity;
             private String entityTokenId;

             public MessageEntity(String entity, String entityTokenId){
                 this.entity = entity;
                 this.entityTokenId = entityTokenId ;
             }

             //[Access modifier] (static | abstract) [class | interface] [identifier] *(extends) [superclass] *(implements) [...superInterface]
             public static String GenerateTokenId() throws InvalidBinaryNumberFormat, ContentsNotPrimitiveException, NotAnArrayException{
                 final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
                 final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                 final String DIGITS = "0123456789";
                 final String SYMBOLS = "~!@#$%^&*()+`-|:<>?;'./";
                 final String STRING_POOL = LOWER_CASE_LETTERS + UPPER_CASE_LETTERS + DIGITS + SYMBOLS;
                 //string format outcome length -> 12
                 int decimalNumber = new Random().nextInt(4096);



                 return new String(String.format("ID:%s#", STRING_POOL.charAt(new Random().nextInt(STRING_POOL.length() + 1))));
             }
         }

         public static class MessageContent{
             private HashMap<String, String> messageBody;
             private String messageTokenId;

             public MessageContent(HashMap<String, String> messageBody, String messageTokenId){
                 this.messageBody = messageBody;
                 this.messageTokenId = messageTokenId;
             }
         }

         public static class MessageStatsDateFormat{
             private int daySegment;
             private int monthSegment;
             private int yearSegment;

             public MessageStatsDateFormat(@IntRange(from = 01 , to = 31 ) byte daySegment, @IntRange(from = 01, to = 12) byte monthSegment, @IntRange(from = 0000, to = 5000)int yearSegment){
                 this.daySegment = daySegment;
                 this.monthSegment = monthSegment;
                 this.yearSegment = yearSegment;
             }

             public String returnDateAsFullString(){
                 return this.daySegment + "/" + this.monthSegment + "/" + this.daySegment;
             }
         }

         public static class MessageStatsTimeFormat{
             private int hourSegment;
             private int minuteSegment;
             private int secondsSegment;

             public MessageStatsTimeFormat(int hourSegment, int minuteSegment, int secondsSegment){
                 this.hourSegment = hourSegment;
                 this.minuteSegment = minuteSegment;
                 this.secondsSegment = secondsSegment;
             }

             public String returnTimeAsFullString(){
                 return this.hourSegment + ":" + this.minuteSegment;
             }
         }
     }
}

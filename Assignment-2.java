import java.util.*; 

//This simulates the HTTP Request 
class Payload { 
      String msg; 
      String key; 
      public Payload(String msg, String key) { 
            this.msg=msg; 
            this.key=key; 
      } 
      @Override 
      public String toString() { 
            return "########Request(MSG, KEY)#########"; 
      }
} 

public class MonoalphabeticCiperAlgorithm { 
      //Substitution Key 
      private static String key="QWERTYUIOPASDFGHJKLZXCVBNM"; 

      //Encryption Logic using Monoalphabetic-Cipher Algorithm 
      private static Payload encrypt(String keyword) { 
            String encodedMsg=""; 
            keyword=keyword.toUpperCase(); 
            //Encrypting by replacing the ASCII letter with key letter 
            for (char letter:keyword.toCharArray()) { 
                  if (letter>=65 && letter<=90) encodedMsg+=key.charAt(letter-65); 
                  else encodedMsg+=letter; 
            } 
            System.out.println(">Sender side encrypted codeword : "+encodedMsg); 
            Payload req=new Payload(encodedMsg, key); 
            System.out.println("------------REQUEST SENT   SUCCESSFULLY----------"); 
            System.out.println(req); 
            return req; 
      } 

      //Decrypting Logic using Monoalphabetic-Ciper Algorithm 
      private static void decrypt(Payload req) { 
            System.out.println("---------REQUEST RECEIVED SUCCESSFULLY---------"); 
            String decodedMsg="";
            //Decrypting by replacing the key letter by finding the index of the curr letter in key and converting to ASCII format 
            for (char letter:req.msg.toCharArray()) { 
                  if (letter>=65 && letter<=90) decodedMsg+=((char)(key.indexOf(letter)+65)); 
                  else decodedMsg+=letter; 
            } 
            System.out.println(">Receiver side decrypted codeword : "+decodedMsg); 
      }  

      public static void main(String[] args) { 
            System.out.println(">Implementing Monoalphabetic-Cipher Algorithm"); 
            System.out.print("\n\n>Enter the Message : "); 
            Scanner sc=new Scanner(System.in); 
            String keyword=sc.nextLine(); 
            System.out.println(">The keyword is : "+keyword); 
            decrypt(encrypt(keyword)); 
      } 
}

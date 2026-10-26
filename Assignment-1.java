import java.util.Scanner; 
class Payload { 
    String msg; 
    int key; 
    public Payload(String msg, int key) { 
        this.msg=msg; 
        this.key=key; 
    } 
     @Override 
     public String toString() { 
          return "########Request(MSG, KEY)#########"; 
     } 
} 

public class CaesarCipherAlgorithm { 
     private static Payload enciper(String msg) { 
          int key=3; 
          String code="";  
          for (char ele:msg.toCharArray()) { 
              if (ele>=65 && ele<=90) code+=(char)(((ele-65+key)%26)+65); 
              else if (ele>=97 && ele<=122) code+=(char)(((ele-97+key)%26)+97); 
              else code+=ele; 
          } 
          Payload request=new Payload(code, key); 
          return request; 
     } 
     private static String dcipher(Payload request) { 
          String decode=""; 
          for (char ele:request.msg.toCharArray()) { 
               if (ele>=65 && ele<=90) decode+=(char)(((ele-65-request.key+26)%26)+65); 
               else if (ele>=97 && ele<=122) decode+=(char)(((ele-97-request.key+26)%26)+97); 
               else decode+=ele; 
          } 
         return decode; 
     } 
     public static void main(String[] args) { 
          System.out.print(">Enter the message : "); 
          Scanner scanner=new Scanner(System.in); 
          String input=scanner.nextLine(); 
          System.out.println(">Input message is : "+input); 
          Payload msgToTransmit=enciper(input); 
          System.out.println(">Sender side encrypted codeword : "+msgToTransmit.msg);
          System.out.println("------------REQUEST SENT SUCCESSFULLY----------"); 
          String msgReceived=dcipher(msgToTransmit); 
          System.out.println(msgToTransmit); 
          System.out.println("----------REQUEST RECEIVED SUCCESSFULLY---------"); 
          System.out.println(">Receiver side decrypted codeword : "+msgReceived); 
     } 
}

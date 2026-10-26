import java.util.*;

class Payload { 
      String msg; 
      int rails; 
      public Payload(String msg, int rails) { 
            this.msg=msg; 
            this.rails=rails; 
      } 
      @Override 
      public String toString() { 
            return "########Request(MSG, KEY)#########"; 
      }
} 

public class RailFenceCipherAlgorithm { 
      /// Encryption Logic using Rail-Fence Cipher Algorithm
      public static Payload encrypt(String message, int rails) {
            int size = (int) Math.ceil((double) message.length() / rails);
            int k=0;
            char[][] matrix=new char[rails][size];
            for (int j=0;j<matrix[0].length;j++) {
                  for (int i=0;i<matrix.length;i++) {
                        if (k<message.length()) matrix[i][j]=message.charAt(k++);
                  }
            }
            StringBuilder encodedMsg=new StringBuilder();
            for (int i=0;i<matrix.length;i++) {
                  for (int j=0;j<matrix[0].length;j++) {
                        encodedMsg.append(matrix[i][j]);
                  }
            }
            System.out.println(">Sender side encrypted codeword : "+encodedMsg); 
            Payload req=new Payload(encodedMsg.toString(), rails); 
            System.out.println("------------REQUEST SENT SUCCESSFULLY----------"); 
            System.out.println(req); 
            return req; 
      }
      
      ///Decryption Logic using Rail-Fence Cipher Algorithm
      public static void decrypt(Payload req) {
            System.out.println("---------REQUEST RECEIVED SUCCESSFULLY---------"); 
            int size = (int) Math.ceil((double) req.msg.length() / req.rails);
            System.out.println(">Size : "+size);
            int k=0;
            char[][] matrix=new char[req.rails][size];
            for (int i=0;i<matrix.length;i++) {
                  for (int j=0;j<matrix[0].length;j++) {
                        if (k<req.msg.length()) matrix[i][j]=req.msg.charAt(k++);
                  }
            }
            for (int i=0;i<matrix.length;i++) {
                  for (int j=0;j<matrix[0].length;j++) System.out.print(matrix[i][j]+" ");
                  System.out.println();
            }
            StringBuilder decodedMsg=new StringBuilder();
            for (int j=0;j<matrix[0].length;j++) {
                  for (int i=0;i<matrix.length;i++) {
                        decodedMsg.append(matrix[i][j]);
                  }
            }
            System.out.println(">Receiver side decrypted codeword : "+decodedMsg); 
      }

      ///Rail-Fence Cipher Alogrithm
      public static void main(String[] args) { 
            System.out.println(">Implementing Rail-Fence Cipher Algorithm"); 
            System.out.print("\n\n>Enter the Message : "); 
            Scanner sc=new Scanner(System.in); 
            String msg=sc.nextLine(); 
            System.out.println(">Using 2 rails : ");
            decrypt(encrypt(msg, 2));
            System.out.println("\n>Using 3 rails : ");
            decrypt(encrypt(msg, 3));
      } 
} 

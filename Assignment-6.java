import java.util.*;

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

public class VernamCipherAlgorithm {
///Encrypting the message using Vernam Algorithm
public static Payload encrypt(String msg, String key) throws Exception {
String encodedMsg="";
int i=0, j=0;
while (i<msg.length() && j<key.length()) {
char ele=msg.charAt(i);
encodedMsg+=(char)(ele^key.charAt(j));
i++; j++;
}
System.out.println(">Sender side encrypted codeword : "+encodedMsg);
Payload req=new Payload(encodedMsg, key);
System.out.println("-----------REQUEST SENT SUCCESSFULLY-----------");
System.out.println(req); 
return req;
}
///Decrypting the message using Vernam Algorithm
public static void decrypt(Payload req) throws Exception {
System.out.println("---------REQUEST RECEIVED SUCCESSFULLY---------");
String decodedMsg="";
int i=0, j=0;
while (i<req.msg.length() && j<req.key.length()) {
char ele=req.msg.charAt(i);
decodedMsg+=(char)(ele^req.key.charAt(j));
i++; j++;
}
System.out.println(">Receiver side decrypted codeword : "+decodedMsg);
} 
public static void main(String[] args) {
try {
System.out.println(">Implementing Affine-Cipher Algorithm\n");
Scanner sc=new Scanner(System.in);
System.out.print(">Enter the message: ");
String msg=sc.nextLine();
System.out.print("\n>Enter the secret key: ");
String key=sc.nextLine();
decrypt(encrypt(msg, key));
sc.close();
} catch (Exception e) {
System.out.println(e.getMessage());
}
} 
}

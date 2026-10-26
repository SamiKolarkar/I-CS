import java.util.*;

class Payload {
String msg;
int factor1;
int factor2;
public Payload(String msg, int factor1, int factor2) {
this.msg=msg;
this.factor1=factor1;
this.factor2=factor2;
}
@Override
public String toString() {
return "########Request(MSG, FACTOR1, FACTOR2)#########";
}
}

public class AffineCipherAlgorithm {
///Computing Inverse of a number
public static int findInverse(int num) {
int i=1;
while ((num*i)%26!=1) i++;
return i;
}
///Encrypting the message using Affine Algorithm
public static Payload encrypt(String msg, int factor1, int factor2) throws Exception {
String encodedMsg="";
for (char ele:msg.toCharArray()) {
if (65<=ele && ele<=90) encodedMsg+=(char)((((ele-65)*factor1+factor2)%26)+65);
else encodedMsg+=ele;
}
System.out.println(">Sender side encrypted codeword : "+encodedMsg);
Payload req=new Payload(encodedMsg, factor1, factor2);
System.out.println("------------REQUEST SENT SUCCESSFULLY----------");
System.out.println(req); 
return req;
}
///Decrypting the message using Affine Algorithm
public static void decrypt(Payload req) throws Exception {
System.out.println("---------REQUEST RECEIVED SUCCESSFULLY---------");
int inverse=findInverse(req.factor1);
String decodedMsg="";
for (char ele:req.msg.toCharArray()) {
if (65<=ele && ele<=90) decodedMsg+=(char)((((ele-65+26-req.factor2)*inverse)%26)+65);
else decodedMsg+=ele;
}
System.out.println(">Receiver side decrypted codeword : "+decodedMsg);
} 
public static void main(String[] args) {
try {
System.out.println(">Implementing Affine-Cipher Algorithm\n");
Scanner sc=new Scanner(System.in);
System.out.print(">Enter the message: ");
String msg=sc.nextLine();
System.out.print("\n>Enter the 1st multiplicative factor: ");
int num1=sc.nextInt();
System.out.print("\n>Enter the 2nd multiplicative factor: ");
int num2=sc.nextInt();
decrypt(encrypt(msg, num1, num2));
sc.close();
} catch (Exception e) {
System.out.println(e.getMessage());
}
} 
}

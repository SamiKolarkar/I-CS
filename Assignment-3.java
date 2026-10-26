import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class PlayfairCipherAlgorithm{
/// Finding co-ordinates of the given alphabets
private static int[] findCoordinates(char[][] keyMatrix, char alphabet) {
for (int i=0;i<5;i++) {
for (int j=0;j<5;j++) {
if (keyMatrix[i][j]==alphabet) return new int[]{i, j};
}
}
return null;
}

/// Key Matrix generation by excluding 'J' & using isPresent Boolean Array
private static char[][] generateKeyMatrix(String keyword) {
char[][] keyMatrix=new char[5][5];
int k=0;
boolean[] isPresent=new boolean[26];
for (char ele:keyword.toCharArray()) if (ele!='J') isPresent[ele-65]=true;
for (int i=0;i<=25;i++) if (i!=9 && !isPresent[i]) keyword+=(char)(65+i);
for (int i=0;i<5;i++) {
for (int j=0;j<5;j++) {
keyMatrix[i][j]=keyword.charAt(k++);
}
}
return keyMatrix;
}
/// Eg:- "Inform at mid-noon!!!"
private static List<String> fetchDigrams(String digram) {
List<String> pairs=new ArrayList<>();
int i=0, j=0;
while (j<digram.length()) {
if (j-i+1==2) {
pairs.add(digram.substring(i, j+1));
i=j+1;
}
j++;
}
return pairs;
}
/// Encrypting the message using keyMatrix
private static Payload encrypt(String msg, String keyword) throws Exception{
char[][] keyMatrix=generateKeyMatrix(keyword.toUpperCase());
StringBuilder encodedMsg=new StringBuilder();
/// Form digram String containing only UpperCase Alphabets
StringBuilder digram=new StringBuilder();
for (char ele:msg.toUpperCase().toCharArray()) {
if (65<=ele && ele<=90) {
if (ele!='J') digram.append(ele);	
else digram.append("I");
}
}
if (digram.length()%2!=0) digram.append("Z");
List<String> pairs=fetchDigrams(digram.toString());
for (String pair:pairs) {
String temp="";
int[] ele1=findCoordinates(keyMatrix, pair.charAt(0));
int[] ele2=findCoordinates(keyMatrix, pair.charAt(1));
if (ele1[1]==ele2[1]) {
temp+=keyMatrix[ele1[0]][(ele1[1]+1)%5];
temp+=keyMatrix[ele2[0]][(ele2[1]+1)%5];
} else if (ele1[0]==ele2[0]) {
temp+=keyMatrix[(ele1[0]+1)%5][ele1[1]];
temp+=keyMatrix[(ele2[0]+1)%5][ele2[1]];
} else {
temp+=keyMatrix[ele1[0]][ele2[1]];
temp+=keyMatrix[ele2[0]][ele1[1]];
}
encodedMsg.append(temp);
}
/// Restoring the original msg punctuations & spaces
for (int i=0;i<msg.length();i++) {
char ele=msg.charAt(i);
if ((ele<65 || ele>90) && !(97<=ele && ele<=122)) {
encodedMsg.insert(i, ele);
}
}
System.out.println(">Sender side encrypted codeword : "+encodedMsg);
Payload req=new Payload(encodedMsg.toString(), keyword);
System.out.println("------------REQUEST SENT SUCCESSFULLY----------");
System.out.println(req);
return req; 
}

/// Decrypting the message using keyMatrix
private static void decrypt(Payload req) throws Exception {
System.out.println("---------REQUEST RECEIVED SUCCESSFULLY---------");
StringBuilder decodedMsg=new StringBuilder();
char[][] keyMatrix=generateKeyMatrix(req.key.toUpperCase());
/// Form digram String containing only UpperCase Alphabets
StringBuilder digram=new StringBuilder();
for (char ele:req.msg.toCharArray()) {
if (65<=ele && ele<=90) {
digram.append(ele);
}
}
List<String> pairs=fetchDigrams(digram.toString());	
for (String pair:pairs) {
String temp="";
int[] ele1=findCoordinates(keyMatrix, pair.charAt(0));
int[] ele2=findCoordinates(keyMatrix, pair.charAt(1));
if (ele1[1]==ele2[1]) {
temp+=keyMatrix[ele1[0]][(ele1[1]-1+5)%5];
temp+=keyMatrix[ele2[0]][(ele2[1]-1+5)%5];
} else if (ele1[0]==ele2[0]) {
temp+=keyMatrix[(ele1[0]-1+5)%5][ele1[1]];
temp+=keyMatrix[(ele2[0]-1+5)%5][ele2[1]];	
} else {
temp+=keyMatrix[ele1[0]][ele2[1]];
temp+=keyMatrix[ele2[0]][ele1[1]];
}
decodedMsg.append(temp);
}
decodedMsg.deleteCharAt(decodedMsg.length()-1);
/// Restoring the original msg punctuations & spaces
for (int i=0;i<req.msg.length();i++) {	
char ele=req.msg.charAt(i);
if ((ele<65 || ele>90) && !(97<=ele && ele<=122)) {
decodedMsg.insert(i, ele);
}
}
System.out.println(">Receiver side decrypted codeword : "+decodedMsg);
}

public static void main(String[] args) { 
try {
System.out.println(">Implementing Playfair-Cipher Algorithm\n");
Scanner sc=new Scanner(System.in);
System.out.print(">Enter the Message : ");
String msg=sc.nextLine();
System.out.print("\nEnter the Keyword : ");
String keyword=sc.nextLine();
decrypt(encrypt(msg, keyword));
} catch(Exception e) {
System.out.println(e.getMessage());
}
}
}

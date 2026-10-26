import java.util.*;

class Payload {
String msg;
int[][] keyMatrix;
int padding;
public Payload(String msg, int[][] keyMatrix, int padding) {
this.msg = msg;
this.keyMatrix = keyMatrix;
this.padding = padding;
}
@Override
public String toString() {
return "########Request(MSG, KEY MATRIX)#########";
}
}

public class HillCipherAlgorithm {
/// Given 3x3 key matrix for Hill Cipher
private static int[][] generateKeyMatrix() {
return new int[][]{
{6, 24, 1},
{13, 16, 10},
{20, 17, 15}
};
}
/// Finding determinant of the 3x3 key matrix
private static int determinant(int[][] matrix) {
return matrix[0][0] * (matrix[1][1] * matrix[2][2]
- matrix[1][2] * matrix[2][1])
- matrix[0][1] * (matrix[1][0] * matrix[2][2]
- matrix[1][2] * matrix[2][0])
+ matrix[0][2] * (matrix[1][0] * matrix[2][1]
- matrix[1][1] * matrix[2][0]);
}
/// Finding modular inverse of a number
private static int modularInverse(int number, int mod) {
number = ((number % mod) + mod) % mod;
for (int i = 1; i < mod; i++) {
if ((number * i) % mod == 1) {
return i;
}
}
return -1;
}
/// Finding inverse of the 3x3 key matrix modulo 26
private static int[][] findInverseMatrix(int[][] matrix) throws Exception {
int det = determinant(matrix);
int detMod = ((det % 26) + 26) % 26;
int detInverse = modularInverse(detMod, 26);
if (detInverse == -1) {
throw new Exception("Key matrix is not invertible modulo 26.");
}
int[][] adjoint = new int[3][3];
adjoint[0][0] = matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1];
adjoint[0][1] = -(matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
adjoint[0][2] = matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0];
adjoint[1][0] = -(matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1]);
adjoint[1][1] = matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0];
adjoint[1][2] = -(matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0]);
adjoint[2][0] = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
adjoint[2][1] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);
adjoint[2][2] = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

// Transpose cofactor matrix to obtain adjoint
int[][] inverseMatrix = new int[3][3];
for (int i = 0; i < 3; i++) {
for (int j = 0; j < 3; j++) {
inverseMatrix[i][j]=(adjoint[j][i] * detInverse) % 26;
if (inverseMatrix[i][j] < 0) {
inverseMatrix[i][j]+=26;
}
}
}
return inverseMatrix;
}

/// Displaying a matrix
private static void displayMatrix(int[][] matrix) {
for (int i = 0; i < matrix.length; i++) {
for (int j = 0; j < matrix[i].length; j++) {
System.out.print(matrix[i][j] + "\t");
}
System.out.println();
}
}

/// Converting alphabet into numerical value
private static int alphabetToNumber(char alphabet) {
return alphabet - 'A';
}

/// Converting numerical value into alphabet
private static char numberToAlphabet(int number) {
return (char) ('A' + ((number % 26 + 26) % 26));
}

/// Extracting only alphabets from the given message
private static String extractAlphabets(String msg) {
StringBuilder digram = new StringBuilder();
for (char ele : msg.toUpperCase().toCharArray()) {
if (ele >= 'A' && ele <= 'Z') {
digram.append(ele);
}
}
return digram.toString();
}

/// Encrypting the message using key matrix
private static Payload encrypt(String msg, int[][] keyMatrix) throws Exception {
System.out.println(">Sender side encryption started...");
StringBuilder cleanMsg = new StringBuilder(extractAlphabets(msg));
/// Padding message so its length becomes a multiple of 3
int padding = (3 - (cleanMsg.length() % 3)) % 3;
for (int i = 0; i < padding; i++) {
cleanMsg.append('X');
}
StringBuilder encodedMsg = new StringBuilder();
/// Process plaintext in groups of three characters
for (int i = 0; i < cleanMsg.length(); i += 3) {
int p1 = alphabetToNumber(cleanMsg.charAt(i));
int p2 = alphabetToNumber(cleanMsg.charAt(i + 1));
int p3 = alphabetToNumber(cleanMsg.charAt(i + 2));
/// Matrix multiplication C = K * P mod 26
int c1 = (keyMatrix[0][0] * p1 + keyMatrix[0][1] * p2 + keyMatrix[0][2] * p3) % 26;
int c2 = (keyMatrix[1][0] * p1 + keyMatrix[1][1] * p2 + keyMatrix[1][2] * p3) % 26;
int c3 = (keyMatrix[2][0] * p1 + keyMatrix[2][1] * p2 + keyMatrix[2][2] * p3) % 26;
encodedMsg.append(numberToAlphabet(c1));
encodedMsg.append(numberToAlphabet(c2));
encodedMsg.append(numberToAlphabet(c3));
}

/// Restoring original message spaces and punctuation
int encodedIndex = 0;
for (int i = 0; i < msg.length(); i++) {
char ele = msg.charAt(i);
if ((ele < 'A' || ele > 'Z')) {
encodedMsg.insert(encodedIndex, ele);
encodedIndex++;
} else {
encodedIndex++;
}
}
System.out.println(">Sender side encrypted codeword : " + encodedMsg);
Payload req = new Payload(encodedMsg.toString(), keyMatrix, padding);
System.out.println("------------REQUEST SENT SUCCESSFULLY----------");
System.out.println(req);
return req;
}

/// Decrypting the message using inverse key matrix
private static void decrypt(Payload req) throws Exception {
System.out.println("---------REQUEST RECEIVED SUCCESSFULLY---------");
int[][] inverseMatrix =findInverseMatrix(req.keyMatrix);
System.out.println("\n>Inverse Key Matrix (mod 26):");
displayMatrix(inverseMatrix);
/// Form string containing only uppercase alphabets
String digram = extractAlphabets(req.msg);
StringBuilder decodedMsg = new StringBuilder();
/// Process ciphertext in groups of three characters
for (int i = 0; i < digram.length(); i += 3) {
int c1 = alphabetToNumber(digram.charAt(i));
int c2 = alphabetToNumber(digram.charAt(i + 1));
int c3 = alphabetToNumber(digram.charAt(i + 2));

/// Matrix multiplication P = K^-1 * C mod 26
int p1 = (inverseMatrix[0][0] * c1 + inverseMatrix[0][1] * c2 + inverseMatrix[0][2] * c3) % 26;
int p2 = (inverseMatrix[1][0] * c1 + inverseMatrix[1][1] * c2 + inverseMatrix[1][2] * c3) % 26;
int p3 = (inverseMatrix[2][0] * c1 + inverseMatrix[2][1] * c2 + inverseMatrix[2][2] * c3) % 26;
decodedMsg.append(numberToAlphabet(p1));
decodedMsg.append(numberToAlphabet(p2));
decodedMsg.append(numberToAlphabet(p3));
}

/// Removing padding characters added during encryption
if (req.padding > 0) {
decodedMsg.delete(decodedMsg.length() - req.padding, 	decodedMsg.length());
}

/// Restoring original message spaces and punctuation
int decodedIndex = 0;
for (int i = 0; i < req.msg.length(); i++) {
char ele = req.msg.charAt(i);
if ((ele < 'A' || ele > 'Z')) {
decodedMsg.insert(decodedIndex, ele);
decodedIndex++;
} else {
decodedIndex++;
}
}
System.out.println(">Receiver side decrypted codeword : "+decodedMsg);
}

public static void main(String[] args) {
try {
System.out.println(">Implementing Hill-Cipher Algorithm\n");
Scanner sc = new Scanner(System.in);
System.out.print(">Enter the Message : ");
String msg = sc.nextLine();
Int[][] keyMatrix = generateKeyMatrix();
System.out.println("\n>Given Key Matrix :");
displayMatrix(keyMatrix);
/// Checking whether key matrix is invertible
int det = determinant(keyMatrix);
int detMod = ((det % 26) + 26) % 26;
System.out.println("\n>Determinant : " + det);
System.out.println(">Determinant mod 26 : " + detMod);
if (modularInverse(detMod, 26) == -1) {
throw new Exception("Invalid key matrix. Matrix is not invertible modulo 26.");
}
System.out.println(">Key matrix is invertible modulo 26.");
decrypt(encrypt(msg, keyMatrix));
} catch (Exception e) {
System.out.println(e.getMessage());
}
}
}

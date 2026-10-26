import java.util.Scanner;

public class RSAAlgorithm {
    static class Payload {
        String msg;
        int[] encryptedValues;

        Payload(String msg, int[] encryptedValues) {
            this.msg = msg;
            this.encryptedValues = encryptedValues;
        }

        @Override
        public String toString() {
            return "########Request(MSG, ENCRYPTED VALUES)#########\n"
                    + "Message: " + msg + "\n"
                    + "Encrypted Values: " + arrayToString(encryptedValues);
        }

        private String arrayToString(int[] values) {
            StringBuilder sb = new StringBuilder();
            for (int value : values) {
                sb.append(value).append(" ");
            }
            return sb.toString().trim();
        }
    }

    // Calculate GCD
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Calculate modular inverse of e modulo phi
    static int modularInverse(int e, int phi) {
        for (int d = 1; d < phi; d++) {
            if ((e * d) % phi == 1) {
                return d;
            }
        }
        return -1;
    }

    // Fast modular exponentiation: base^exponent mod modulus
    static int modularExponentiation(int base, int exponent, int modulus) {
        long result = 1;
        long b = base % modulus;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * b) % modulus;
            }
            b = (b * b) % modulus;
            exponent /= 2;
        }
        return (int) result;
    }

    // Convert alphabet to number: A=0, B=1, ..., Z=25
    static int alphabetToNumber(char ch) {
        return Character.toUpperCase(ch) - 'A';
    }

    // Convert number to alphabet
    static char numberToAlphabet(int num) {
        return (char) ('A' + num);
    }

    // Extract only alphabetic characters
    static String extractAlphabets(String msg) {
        StringBuilder result = new StringBuilder();
        for (char ch : msg.toCharArray()) {
            if (Character.isLetter(ch)) {
                result.append(Character.toUpperCase(ch));
            }
        }
        return result.toString();
    }

    // RSA Encryption
    static Payload encrypt(String msg, int e, int n) {
        String letters = extractAlphabets(msg);
        int[] encryptedValues = new int[letters.length()];
        for (int i = 0; i < letters.length(); i++) {
            int plainValue = alphabetToNumber(letters.charAt(i));
            encryptedValues[i] = modularExponentiation(plainValue, e, n);
        }
        return new Payload(msg, encryptedValues);
    }

    // RSA Decryption
    static String decrypt(Payload req, int d, int n) {
        StringBuilder decrypted = new StringBuilder();
        for (int value : req.encryptedValues) {
            int plainValue = modularExponentiation(value, d, n);
            decrypted.append(numberToAlphabet(plainValue));
        }
        return decrypted.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("######## RSA ALGORITHM #########");
        System.out.print("Enter message: ");
        String message = scanner.nextLine();

        // Given prime numbers
        int p = 5;
        int q = 11;

        // Calculate n and Euler's Totient
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        // Select public exponent
        int e = 3;

        // Verify e and phi are relatively prime
        if (gcd(e, phi) != 1) {
            System.out.println("Invalid value of e.");
            scanner.close();
            return;
        }

        // Calculate private exponent
        int d = modularInverse(e, phi);

        System.out.println("\nPrime p: " + p);
        System.out.println("Prime q: " + q);
        System.out.println("n = p * q: " + n);
        System.out.println("phi(n): " + phi);
        System.out.println("Public Key  (e, n): (" + e + ", " + n + ")");
        System.out.println("Private Key (d, n): (" + d + ", " + n + ")");

        // Encryption
        Payload request = encrypt(message, e, n);
        System.out.println("\n" + request);

        // Decryption
        String decryptedMessage = decrypt(request, d, n);
        System.out.println("\n######## DECRYPTION #########");
        System.out.println("Decrypted Message: " + decryptedMessage);
        scanner.close();
    }
}

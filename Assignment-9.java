import java.util.*;

public class DiffieHellmanAlgorithm {
    static class Payload {
        int p;
        int g;
        int alicePublicKey;
        int bobPublicKey;
        int sharedSecret;

        public Payload(int p, int g, int alicePublicKey, int bobPublicKey, int sharedSecret) {
            this.p = p;
            this.g = g;
            this.alicePublicKey = alicePublicKey;
            this.bobPublicKey = bobPublicKey;
            this.sharedSecret = sharedSecret;
        }

        @Override
        public String toString() {
            return "########Request(P, G, PUBLIC KEYS, SHARED SECRET)#########\n" + "Prime p: " + p + "\n" + "Primitive Root g: " + g + "\n" + "Alice Public Key: " + alicePublicKey + "\n" + "Bob Public Key: " + bobPublicKey + "\n" + "Shared Secret: " + sharedSecret;
        }
    }

    // Calculate base^exponent mod modulus
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

    // Generate Alice's public key
    static int generateAlicePublicKey(int g, int a, int p) {
        return modularExponentiation(g, a, p);
    }

    // Generate Bob's public key
    static int generateBobPublicKey(int g, int b, int p) {
        return modularExponentiation(g, b, p);
    }

    // Calculate Alice's shared secret
    static int calculateAliceSecret(int bobPublicKey, int a, int p) {
        return modularExponentiation(bobPublicKey, a, p);
    }

    // Calculate Bob's shared secret
    static int calculateBobSecret(int alicePublicKey, int b, int p) {
        return modularExponentiation(alicePublicKey, b, p);
    }

    // Perform Diffie-Hellman key exchange
    static Payload exchangeKeys(int p, int g, int a, int b) {
        int alicePublicKey = generateAlicePublicKey(g, a, p);
        int bobPublicKey = generateBobPublicKey(g, b, p);
        int aliceSecret = calculateAliceSecret(bobPublicKey, a, p);
        int bobSecret = calculateBobSecret(alicePublicKey, b, p);

        // Both secrets should be equal
        if (aliceSecret != bobSecret) {
            throw new RuntimeException("Key exchange failed: shared secrets are different.");
        }
        return new Payload(p, g, alicePublicKey, bobPublicKey, aliceSecret);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("######## DIFFIE-HELLMAN KEY EXCHANGE #########");

        // Public parameters
        int p = 23;
        int g = 5;

        // Private keys
        int a = 6;
        int b = 15;
        System.out.println("\nPublic Parameters:");
        System.out.println("Prime p: " + p);
        System.out.println("Primitive Root g: " + g);

        System.out.println("\nPrivate Keys:");
        System.out.println("Alice Private Key: " + a);
        System.out.println("Bob Private Key: " + b);

        // Perform key exchange
        Payload request = exchangeKeys(p, g, a, b);
        System.out.println("\n" + request);

        // Independently calculate both shared secrets
        int aliceSecret = calculateAliceSecret(request.bobPublicKey, a, p);
        int bobSecret = calculateBobSecret(request.alicePublicKey, b, p);
        System.out.println("\n######## VERIFICATION #########");
        System.out.println("Alice Shared Secret: " + aliceSecret);
        System.out.println("Bob Shared Secret: " + bobSecret);

        if (aliceSecret == bobSecret) {
            System.out.println("Verification: SUCCESS");
            System.out.println("Both parties have the same shared secret.");
        } else {
            System.out.println("Verification: FAILED");
        }
        scanner.close();
    }
}

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class AlgorithmAnalysisAidanRSATest2 {

	public static void main(String[] args) {
		BigInteger[] keys = { BigInteger.valueOf(0), BigInteger.valueOf(0), BigInteger.valueOf(0),
				BigInteger.valueOf(0), BigInteger.valueOf(0) };
		int decision = 4;
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("Enter 0 to generate a random RSA key and encrypted message.\nEnter 1 to encrypt "
					+ "a message with a known public key.\nEnter 2 to decrypt a message with a known "
					+ "secret key.\nEnter 3 to quit.");
			decision = scan.nextInt();

			if (decision == 0) {
				GenerateRandom(keys, scan);
			} else if (decision == 1) {
				Encrypt(keys, scan);
			} else if (decision == 2) {
				Decrypt(keys, scan);
			} else if (decision == 3) {
				scan.close();
				break;
			}
		}
	} // end main

	// returns (d, x, y) such that d is the greatest common divisor, and ax + by = d
	// if a and b are coprime, x is the multiplicative inverse of a %
	static BigInteger[] ExtendedEuclid(BigInteger a, BigInteger b) {
		if (b.compareTo(BigInteger.ZERO) == 0) {
			return new BigInteger[] { a, BigInteger.valueOf(1), BigInteger.valueOf(0) };
		} else {
			BigInteger[] array = ExtendedEuclid(b, a.mod(b));
			return new BigInteger[] { array[0], array[2], (array[1].subtract((a.divide(b).multiply(array[2])))) };
		}
	} // end ExtendedEuclid

	// returns a^b % n
	static BigInteger ModularExponentiation(BigInteger a, BigInteger b, BigInteger n) {
		BigInteger c = BigInteger.ZERO;
		BigInteger d = BigInteger.ONE;
		String bBinary = b.toString(2);
		for (int i = 0; i < bBinary.length(); i++) {
			c = c.multiply(new BigInteger("2"));
			d = (d.multiply(d)).mod(n);
			if (bBinary.charAt(i) == '1') {
				c = c.add(BigInteger.ONE);
				d = (d.multiply(a)).mod(n);
			}
		}
		return d;
	} // end ModularExponentiation

	static boolean Pseudoprime(BigInteger n) {
		if (ModularExponentiation(new BigInteger("2"), n.subtract(BigInteger.ONE), n).compareTo(BigInteger.ONE) != 0) {
			return false;
		} else {
			return true;
		}
	} // end pseudoprime

	static BigInteger[] RSA(BigInteger secretMessage) {
		BigInteger p = BigInteger.ZERO;
		BigInteger q = BigInteger.ZERO;
		BigInteger n;
		BigInteger e = new BigInteger("3");
		BigInteger d;
		BigInteger phiN;
		while (true) {
			BigInteger[] pq = randomPrimes();
			p = pq[0];
			q = pq[1];
			n = p.multiply(q);
			p = p.subtract(BigInteger.ONE);
			q = q.subtract(BigInteger.ONE);
			phiN = p.multiply(q);
			while (true) {
				if (ExtendedEuclid(phiN, e)[0].compareTo(BigInteger.ONE) == 0) {
					break;
				} else {
					e = e.add(new BigInteger("2"));
				}
			}
			d = ExtendedEuclid(e, phiN)[1];
			if (e.compareTo(d) == 0) {
				continue;
			}
			break;
		}
		BigInteger decrypt = RSAEncrypt(secretMessage, e, n);
		BigInteger decrypted = RSADecrypt(decrypt, d, n);
		return new BigInteger[] { e, n, d, decrypted, decrypt };
	}

	// generates two random prime numbers between 3 and integer max value
	static BigInteger[] randomPrimes() {
		BigInteger p = BigInteger.ZERO;
		BigInteger q = BigInteger.ZERO;
		BigInteger random = new BigInteger("9999999999999999999").subtract(new BigInteger("2"));
		random = BigDecimal.valueOf(random.doubleValue() * Math.random()).toBigInteger();
		random.add(new BigInteger ("3"));
		while (q.compareTo(BigInteger.ZERO) == 0) {
			if (Pseudoprime(random) == true) {
				if (p.compareTo(BigInteger.ZERO) == 0) {
					p = random;
				} else if (random.compareTo(p) == -1) {
					q = random;
				}
			}
			random = new BigInteger("9999999999999999999").subtract(new BigInteger("2"));
			random = BigDecimal.valueOf(random.doubleValue() * Math.random()).toBigInteger();
			random.add(new BigInteger ("3"));
		}
		return new BigInteger[] { p, q };
	}

	static BigInteger RSAEncrypt(BigInteger encrypt, BigInteger e, BigInteger n) {
		encrypt = encrypt.modPow(e, n);
		return encrypt;
	}

	static BigInteger RSADecrypt(BigInteger decrypt, BigInteger d, BigInteger n) {
		decrypt = decrypt.modPow(d, n);
		return decrypt;
	}

	static void GenerateRandom(BigInteger[] keys, Scanner scan) {
		System.out.println("Enter number to encrypt:");
		BigInteger secretMessage = scan.nextBigInteger();

		while (keys[3].compareTo(secretMessage) == -1) {
			keys = RSA(secretMessage);
		}

		System.out.println("Your public key is: " + "(" + keys[0] + ", " + keys[1] + ")");
		System.out.println("Your secret key is: " + "(" + keys[2] + ", " + keys[1] + ")");

		System.out.println(secretMessage + " encrypted with the above keys is " + keys[4]);
	}

	static void Encrypt(BigInteger[] keys, Scanner scan) {
		System.out.println("Enter your number to be encrypted: ");
		keys[3] = scan.nextBigInteger();
		System.out.println("Enter the first number of your public key (e): ");
		keys[0] = scan.nextBigInteger();
		System.out.println("Enter the second number of your public key (n): ");
		keys[1] = scan.nextBigInteger();
		System.out.println("Your encrypted message is: " + RSAEncrypt(keys[3], keys[0], keys[1]));
	}

	static void Decrypt(BigInteger[] keys, Scanner scan) {
		System.out.println("Enter your encrypted message: ");
		keys[4] = scan.nextBigInteger();
		System.out.println("Enter the first number of your secret key (d): ");
		keys[2] = scan.nextBigInteger();
		System.out.println("Enter the second number of your secret key (n): ");
		keys[1] = scan.nextBigInteger();
		System.out.println("Your decrypted message is: " + RSADecrypt(keys[4], keys[2], keys[1]));
	}

} // end class
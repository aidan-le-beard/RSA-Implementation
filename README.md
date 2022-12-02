# RSA-Implementation
This implementation of RSA encryption of integers utilizes three algorithms: Pseudoprime, ExtendedEuclid, and ModularExponentiation. Pseudoprime has the ability to occasionally return false primes, but it is sufficient for this implementation. 

## You do NOT have permission to use this code OR EBNF for any schoolwork purposes under any circumstances. 

## You do NOT have permission to use this code OR EBNF for any commercial purposes without speaking to me to work out a deal.

This program chooses two large prime numbers, p and q, and multiplies them together, giving another number n that is nearly impossible to factor. Similarly (p – 1) and (q – 1) are multiplied together, giving ϕ(n) and a number coprime to ϕ(n) is found, giving e. This pair, (e, n) make up the public key. The secret key is then generated, by calling ExtendedEuclid algorithm to find the multiplicative inverse of (e, ϕ(n)), which is the second number returned, giving d. The secret key then is (d, n).

### To execute on Windows:
Run the file in your Java IDE of choice.

## Sample Output

![image](https://user-images.githubusercontent.com/33675444/205196756-c2851892-f865-4711-b6c1-c15b84265f84.png)

![image](https://user-images.githubusercontent.com/33675444/205196778-14dcb2e7-df2f-45d0-adb7-2c0a35486a1f.png)

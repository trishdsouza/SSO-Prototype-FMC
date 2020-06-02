package com.freedommortgage.services.signing.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/***
 * <code>TEA</code>represents a Tiny Encryption Algorithm.**Adopted from
 * http://www.ftp.cl.cam.ac.uk/ftp/papers/djw-rmn/djw-rmn-tea.html
 ** 
 * @author JSanchez created Feb 9, 2005 at 5:02:52 PM
 *
 *         Revision History:
 */
public class TEA {

    // CHECKSTYLE:OFF

    private int[] key;

    private byte[] keyBytes;

    /**
     * Generates key for enciphering/deciphering.
     *
     * @throws ArrayIndexOutOfBoundsException
     *             if the key isn't the correct length.
     */
    protected TEA() {

        final byte[] keyB = new BigInteger("39e858f86df9b909a8c87cb8d9ad599", 16).toByteArray();

        final int klen = keyB.length;

        key = new int[4];

        int i;
        int j;

        for (i = 0, j = 0; j < klen; j += 4, i++) {
            key[i] = (keyB[j] << 24) | (((keyB[j + 1]) & 0xff) << 16) | (((keyB[j + 2]) & 0xff) << 8) | ((keyB[j + 3]) & 0xff);
        }

        keyBytes = keyB;
    }

    /**
     * String Representation of TEA class's key
     */
    public String toString() {

        final String tea = this.getClass().getName();
        return tea + ": Tiny Encryption Algorithm (TEA)  key: " + getHex(keyBytes);
    }

    /**
     * Display bytes in HEX.
     * 
     * @param b
     *            bytes to display.
     * @return string representation of the bytes.
     */
    private String getHex(final byte[] b) {

        final StringBuilder r = new StringBuilder();
        final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        for (int i = 0; i < b.length; i++) {
            int c = ((b[i]) >>> 4) & 0xf;
            r.append(hex[c]);
            c = (int) b[i] & 0xf;
            r.append(hex[c]);
        }

        return r.toString();
    }

    /**
     * Accepts a cipher text string and decrypts
     */
    protected String decrypt(final String cipherText) {

        final byte[] cipher = fromHexString(cipherText);
        final byte[] clear = decode(cipher, cipher.length);
        return new String(clear, StandardCharsets.UTF_8).trim();
    }

    /**
     * Convert a byte array to ints and then decode. There may be some padding at the end of the byte array
     * from the previous encode operation.
     *
     * @param b
     *            bytes to decode
     * @param count
     *            number of bytes in the array to decode
     *
     * @return <code>byte</code> array of decoded bytes.
     */
    private byte[] decode(final byte[] b, final int count) {

        int i;
        int j;

        final int intCount = count / 4;
        final int[] ini = new int[intCount];

        for (i = 0, j = 0; i < intCount; i += 2, j += 8) {
            ini[i] = (b[j] << 24) | (((b[j + 1]) & 0xff) << 16) | (((b[j + 2]) & 0xff) << 8) | ((b[j + 3]) & 0xff);
            ini[i + 1] = (b[j + 4] << 24) | (((b[j + 5]) & 0xff) << 16) | (((b[j + 6]) & 0xff) << 8) | ((b[j + 7])
                    &
                    0xff);
        }

        return decode(ini);
    }

    /**
     * Decode an integer array. There may be some padding at the end of the byte array from the previous
     * encode operation.
     *
     * @param b
     *            bytes to decode
     * @param count
     *            number of bytes in the array to decode
     *
     * @return <code>byte</code> array of decoded bytes.
     */
    private byte[] decode(final int[] b) {

        // create the large number and start stripping ints out, two at a time.
        final int intCount = b.length;

        final byte[] outb = new byte[intCount * 4];
        final int[] tmp = new int[2];
        int p = 0;

        // decipher all the ints.
        int i;
        int j;

        for (j = 0, i = 0; i < intCount; i += 2, j += 8) {
            tmp[0] = b[i];
            tmp[1] = b[i + 1];
            decipher(tmp);
            outb[j] = (byte) (tmp[p] >>> 24);
            outb[j + 1] = (byte) (tmp[p] >>> 16);
            outb[j + 2] = (byte) (tmp[p] >>> 8);
            outb[j + 3] = (byte) (tmp[p]);
            outb[j + 4] = (byte) (tmp[p + 1] >>> 24);
            outb[j + 5] = (byte) (tmp[p + 1] >>> 16);
            outb[j + 6] = (byte) (tmp[p + 1] >>> 8);
            outb[j + 7] = (byte) (tmp[p + 1]);
        }

        return outb;
    }

    /**
     * Decipher two <code>int</code>s. Replaces the original contents of the parameters with the results. The
     * integers are usually decocted to 8 bytes. The decoction of the <code>int</code>s to bytes can be done
     * this way.
     *
     * <PRE>
     * int x[] = decipher(ins);
     * outb[j] = (byte) (x[0] >>> 24);
     * outb[j + 1] = (byte) (x[0] >>> 16);
     * outb[j + 2] = (byte) (x[0] >>> 8);
     * outb[j + 3] = (byte) (x[0]);
     * outb[j + 4] = (byte) (x[1] >>> 24);
     * outb[j + 5] = (byte) (x[1] >>> 16);
     * outb[j + 6] = (byte) (x[1] >>> 8);
     * outb[j + 7] = (byte) (x[1]);
     * </PRE>
     *
     * @param v
     *            <code>int</code> array of 2
     *
     * @return deciphered <code>int</code> array of 2
     */
    private int[] decipher(final int[] v) {

        int q = 0;
        int y = v[q];
        int z = v[q + 1];
        int sum = 0xC6EF3720;
        final int delta = 0x9E3779B9;
        final int a = key[q];
        final int b = key[q + 1];
        final int c = key[q + 2];
        final int d = key[q + 3];
        int n = 32;

        // sum = delta<<5, in general sum = delta * n

        while (n-- > 0) {
            z -= (y << 4) + c ^ y + sum ^ (y >>> 5) + d;
            y -= (z << 4) + a ^ z + sum ^ (z >>> 5) + b;
            sum -= delta;
        }

        v[q] = y;
        v[q + 1] = z;

        return v;
    }

    private static byte[] fromHexString(final String s) {

        final int stringLength = s.length();

        if ((stringLength & 0x1) != 0) {
            throw new IllegalArgumentException("fromHexString requires an even number of hex characters");
        }

        final byte[] b = new byte[stringLength / 2];

        for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
            final int high = charToNibble(s.charAt(i));
            final int low = charToNibble(s.charAt(i + 1));
            b[j] = (byte) ((high << 4) | low);
        }

        return b;
    }

    /**
     * convert a single char to corresponding nibble.
     *
     * @param c
     *            char to convert. must be 0-9 a-f A-F, no spaces, plus or minus signs.
     *
     * @return corresponding integer
     */
    private static int charToNibble(final char c) {

        if ('0' <= c && c <= '9') {
            return c - '0';
        } else if ('a' <= c && c <= 'f') {
            return c - 'a' + 0xa;
        } else if ('A' <= c && c <= 'F') {
            return c - 'A' + 0xa;
        } else {
            throw new IllegalArgumentException("Invalid hex character: " + c);
        }

    }

    // CHECKSTYLE:ON
}

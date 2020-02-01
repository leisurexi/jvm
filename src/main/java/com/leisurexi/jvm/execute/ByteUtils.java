package com.leisurexi.jvm.execute;

/**
 * @author: leisurexi
 * @date: 2020-02-01 4:48 下午
 * @description: byte 数组处理工具
 * @since JDK 1.8
 */
public class ByteUtils {

    public static int bytes2Int(byte[] bytes, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int) bytes[i]) & 0xff;
            n <<= (--len) * 8;
            sum = n + sum;
        }
        return sum;
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return bytes;
    }

    public static String bytes2String(byte[] bytes, int start, int len) {
        return new String(bytes, start, len);
    }

    public static byte[] string2Bytes(String str) {
        return str.getBytes();
    }

    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
        byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        System.arraycopy(originalBytes, 0, newBytes, 0, offset);
        System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length,
                originalBytes.length - offset - len);
        return newBytes;
    }

}

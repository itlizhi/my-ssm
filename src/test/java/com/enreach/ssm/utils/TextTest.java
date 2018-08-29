package com.enreach.ssm.utils;

import com.enreach.ssm.utils.text.CryptoUtil;
import com.enreach.ssm.utils.text.EncodeUtil;
import com.enreach.ssm.utils.text.HashUtil;
import com.enreach.ssm.utils.text.WildcardMatcher;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextTest {

    @Test
    public void hmacSha1() {

        /**
         * HMACSHA1 是从 SHA1 哈希函数构造的一种键控哈希算法，被用作 HMAC（基于哈希的消息验证代码）。
         * 此 HMAC 进程将密钥与消息数据混合，使用哈希函数对混合结果进行哈希计算，将所得哈希值与该密钥混合，然后再次应用哈希函数。 输出的哈希值长度为 160 位。
         在发送方和接收方共享机密密钥的前提下，HMAC 可用于确定通过不安全信道发送的消息是否已被篡改。
         发送方计算原始数据的哈希值，并将【原始数据】和【哈希值】放在一个消息中同时传送。
         接收方重新计算所接收消息的哈希值，并检查计算所得的 HMAC 是否与传送的 HMAC 匹配。
         */

        //byte[] key = new byte[]{0x16, 0x43, 0x22, 0x54, 0x26, 0x5, 0xf, 0xc}; //CryptoUtil.generateHmacSha1Key();

        byte[] key = CryptoUtil.generateHmacSha1Key();
        System.out.println(EncodeUtil.encodeBase64(key));

        byte[] result = CryptoUtil.hmacSha1("HMACSHA1 是从 SHA1 哈希函数构造的一种键控哈希算法，被用作 HMAC（基于哈希的消息验证代码）".getBytes(), key);
        System.out.println(result);

        System.out.println(EncodeUtil.encodeBase64(result));


    }

    @Test
    public void aes() {

        byte[] key = CryptoUtil.generateAesKey();
        System.out.println(EncodeUtil.encodeHex(key));

        String input = "hello world!!!!!";

        byte[] encryptResult = CryptoUtil.aesEncrypt(input.getBytes(), key);
        String descryptResult = CryptoUtil.aesDecrypt(encryptResult, key);

        System.out.println("aesEncrypt :" + EncodeUtil.encodeHex(encryptResult));
        System.out.println("aesDecrypt :" + descryptResult);

        //IV
        byte[] iv = CryptoUtil.generateIV();
        encryptResult = CryptoUtil.aesEncrypt(input.getBytes(), key, iv);
        descryptResult = CryptoUtil.aesDecrypt(encryptResult, key, iv);

        System.out.println("aesEncrypt iv :" + EncodeUtil.encodeHex(encryptResult));
        System.out.println("aesDecrypt iv :" + descryptResult);

    }

    @Test
    public void encode() {

        String input = "hello world!!!!!";

        String base64 = EncodeUtil.encodeBase64(input.getBytes());

        System.out.println(base64);

        System.out.println(new String(EncodeUtil.decodeBase64(base64)));


        System.out.println(EncodeUtil.encodeHex(input.getBytes()));
        System.out.println(EncodeUtil.urlEncode("https://baidu.com/"));
    }


    @Test
    public void hash() {

        //System.out.println(HashUtil.crc32AsInt("hello world abcdefg"));
        //System.out.println(HashUtil.crc32AsInt("hello world "));

        byte[] sha1 = HashUtil.sha1("lizhi888");
        System.out.println(EncodeUtil.encodeBase64(sha1));

        byte[] salt = HashUtil.generateSalt(8);

        String base64= EncodeUtil.encodeBase64(salt);

        System.out.println(base64);

        sha1 = HashUtil.sha1("lizhi888", salt);

        String str= EncodeUtil.encodeBase64(sha1);

        System.out.println(str);

        /**
         * salt MkI2A67PXLA=
         result: H4irnyE1/4rxRrn+dP+3dKWxfKw=
         */

        byte[] key = EncodeUtil.decodeBase64("MkI2A67PXLA=");
        String result = EncodeUtil.encodeBase64(HashUtil.sha1("lizhi888", key));

        assertThat(result).isEqualTo("H4irnyE1/4rxRrn+dP+3dKWxfKw=");

    }

    @Test
    public void match() {

        assertThat(WildcardMatcher.match("abc", "*")).isTrue();
        assertThat(WildcardMatcher.match("abc", "*c")).isTrue();
        assertThat(WildcardMatcher.match("abc", "a*")).isTrue();
        assertThat(WildcardMatcher.match("abc", "a*c")).isTrue();

        assertThat(WildcardMatcher.match("abc", "a?c")).isTrue();
        assertThat(WildcardMatcher.match("abcd", "a?c?")).isTrue();
        assertThat(WildcardMatcher.match("abcd", "a??d")).isTrue();

        assertThat(WildcardMatcher.match("abcde", "a*d?")).isTrue();

        assertThat(WildcardMatcher.match("abcde", "a*d")).isFalse();
        assertThat(WildcardMatcher.match("abcde", "a*x")).isFalse();
        assertThat(WildcardMatcher.match("abcde", "a*df")).isFalse();

        assertThat(WildcardMatcher.match("abcde", "?abcd")).isFalse();

        assertThat(WildcardMatcher.match("ab\\\\*cde", "ab\\\\*c*")).isTrue();
        assertThat(WildcardMatcher.match("ab\\\\*cde", "ab\\\\*?de")).isTrue();

        // matchOne
        assertThat(WildcardMatcher.matchIndex("abcde", new String[]{"a*d?", "abde?"})).isEqualTo(0);
        assertThat(WildcardMatcher.matchIndex("abcde", new String[]{"?abcd", "a*d?"})).isEqualTo(1);
        assertThat(WildcardMatcher.matchIndex("abcde", new String[]{"?abcd", "xyz*"})).isEqualTo(-1);

    }


}

package com.example.safe.utils

import android.annotation.TargetApi
import android.os.Build
import android.util.Base64
import android.util.Log
import com.xmssx.common.mvp.CIView
import java.security.Key
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

/**
 * Created by hy on 2019/10/30
 */


inline fun <T> T?.println() = kotlin.io.println(this)

fun <T> T.handleError(t: Throwable, view: CIView?, defaultMsg: String) {
    Log.e("error",t.message)
    view!!.error(defaultMsg)
}

fun encodeMd5(str: String): String {
    val digest = MessageDigest.getInstance("MD5")
    val result = digest.digest(str.toByteArray())
    //没转16进制之前是16位
    println("result${result.size}")
    //转成16进制后是32字节
    return toHex(result)
}

fun toHex(byteArray: ByteArray): String {
    val result = with(StringBuilder()) {
        byteArray.forEach {
            val hex = it.toInt() and (0xFF)
            val hexStr = Integer.toHexString(hex)
            if (hexStr.length == 1) {
                this.append("0").append(hexStr)
            } else {
                this.append(hexStr)
            }
        }
        this.toString()
    }
    //转成16进制后是32字节
    return result
}

fun encodeSha1(str:String): String {
    val digest = MessageDigest.getInstance("SHA-1")
    val result = digest.digest(str.toByteArray())
    return toHex(result)
}

/**
 * 私钥加密
 */
fun encryptByPrivateKey(input: String, privateKey: PrivateKey): String {
    /********************非对称加/解密三部曲**********************/
    //1.创建cipher对象
    val cipher = Cipher.getInstance("RSA")
    //2.初始化cipher
    cipher.init(Cipher.ENCRYPT_MODE, privateKey)
    //3.加密/解密
    val encrypt = cipher.doFinal(input.toByteArray())
    return Base64.encodeToString(encrypt,Base64.DEFAULT)
}

/**
 * 公钥解密
 */
fun decryptByPublicKey(input: String, publicKey: PublicKey): String {
    val decode = Base64.decode(input,Base64.DEFAULT)
    /********************非对称加/解密三部曲**********************/
    //1.创建cipher对象
    val cipher = Cipher.getInstance("RSA")
    //2.初始化cipher
    cipher.init(DECRYPT_MODE, publicKey)
    //3.加密/解密
    val encrypt = cipher.doFinal(decode)
    return String(encrypt)
}

/**
 * 公钥加密
 */
fun encryptByPublicKey(input: String, publicKey: PublicKey): String {
    /********************非对称加/解密三部曲**********************/
    //1.创建cipher对象
    val cipher = Cipher.getInstance("RSA")
    //2.初始化cipher
    cipher.init(Cipher.ENCRYPT_MODE, publicKey)
    //3.加密/解密
    val encrypt = cipher.doFinal(input.toByteArray())
    return Base64.encodeToString(encrypt,Base64.DEFAULT)
}

/**
 * 私钥解密
 */
fun decryptByPrivateKey(input: String, privateKey: PrivateKey): String {
    val decode = Base64.decode(input,Base64.DEFAULT)
    /********************非对称加/解密三部曲**********************/
    //1.创建cipher对象
    val cipher = Cipher.getInstance("RSA")
    //2.初始化cipher
    cipher.init(DECRYPT_MODE, privateKey)
    //3.加密/解密
    val encrypt = cipher.doFinal(decode)
    return String(encrypt)
}

/**
 * des加密
 */
fun encrypt(input: String, password: String): String {
    //1.创建cipher对象 学习查看api
    val cipher = Cipher.getInstance("DES")
    //2.初始化cirpher（参数1：加密/解密模式）
    val kf = SecretKeyFactory.getInstance("DES")
    val keySpe = DESKeySpec(password.toByteArray())
    val key: Key = kf.generateSecret(keySpe)
    //加密模式
    cipher.init(Cipher.ENCRYPT_MODE, key)
    //3.加密/解密
    val encrypt = cipher.doFinal(input.toByteArray())
    //通过Base64解决乱码问题
    return Base64.encodeToString(encrypt,Base64.DEFAULT)
}

/**
 * des解密
 */
fun decrypt(input: String, password: String): String {
    //1.创建cipher对象 学习查看api
    val cipher = Cipher.getInstance("DES")
    //2.初始化cirpher（参数1：加密/解密模式）
    val kf = SecretKeyFactory.getInstance("DES")
    val keySpe = DESKeySpec(password.toByteArray())
    val key: Key = kf.generateSecret(keySpe)
    //解密模式
    cipher.init(DECRYPT_MODE, key)
    //3.加密/解密
    //val encrypt = cipher.doFinal(input.toByteArray())
    //base64解码
    val encrypt = cipher.doFinal(Base64.decode(input,Base64.DEFAULT))
    return String(encrypt)
}

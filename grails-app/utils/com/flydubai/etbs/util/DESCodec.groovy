package com.flydubai.etbs.util

import java.security.*
import javax.crypto.*
import javax.crypto.spec.*

class DESCodec {
	
	static encode = { String target ->
		def cipher = getCipher(Cipher.ENCRYPT_MODE)
		return cipher.doFinal(target.bytes).encodeBase64()
	}

	static decode = { String target ->
		def cipher = getCipher(Cipher.DECRYPT_MODE)
		return new String(cipher.doFinal(target.decodeBase64()))
	}

	private static getCipher(mode) {
		def keySpec = new DESKeySpec(getPassword())
		def cipher = Cipher.getInstance("DES")
		def keyFactory = SecretKeyFactory.getInstance("DES")
		cipher.init(mode, keyFactory.generateSecret(keySpec))
		return cipher
	}

	private static getPassword() { "secret12".getBytes("UTF-8") }

	static void main(args) {
		if(args) {
			println encode(args[0])
		}
	}
	
	static public decodePassword(def password) {
		if(password) {
			println decode(password)
		}
	}
	
	static public encodePassword(def password) {
		if(password) {
			println encode(password)
		}
	}
}

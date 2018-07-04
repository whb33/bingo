function encrypt(message) {
    var key = "xhmpcastconspage";
    var iv = "0102030405060708";
	key  = CryptoJS.enc.Utf8.parse(key);
	iv   = CryptoJS.enc.Utf8.parse(iv);
	var encrypted = CryptoJS.AES.encrypt(message, key, {iv: iv, mode:CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7});  
	return encodeURIComponent(CryptoJS.enc.Base64.stringify(encrypted.ciphertext));
}

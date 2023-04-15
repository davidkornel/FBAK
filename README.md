# FBAK

Repository for the Mobile Computing course's group work. 

## Registration example

Note: It's case sensitive

- Nickname: KarelNovak
- Name: Karel
- Surname: Novak
- Email: karel@gmail.com
- Password: @Karel
- Card number: 1234123412341234
- Expiration date: 02/24
- CVV: 123
 

## Class diagram

![UML class(1)](https://user-images.githubusercontent.com/49610117/229872009-70458961-a4e1-473c-a2df-6f3712d35394.png)

## Figma prototype

[Figma prototype](https://www.figma.com/file/4Y4rR6XdTI61CnZNyBUSGq/Acme-electorinc-store?node-id=0%3A1&t=guwb6qmTbyyXrwBA-1)

## Terminal app

For terminal app to work you need to install this on emulator. https://apkpure.com/it/barcode-scanner/com.google.zxing.client.android/download

## QR Codes 

<img src="./docs/imgs/tags/Wine.png" width="20%" height="20%" /> 
<img src="./docs/imgs/tags/Rice.png" width="20%" height="20%" /> 
<img src="./docs/imgs/tags/Salad.png" width="20%" height="20%" /> 
<img src="./docs/imgs/tags/Pasta.png" width="20%" height="20%" /> 
<img src="./docs/imgs/tags/potatoes.png" width="20%" height="20%" /> 
<img src="./docs/imgs/tags/Ice_cream.png" width="20%" height="20%" /> 
<img src="./docs/imgs/tags/Hamburger.png" width="20%" height="20%" /> 

## Registration and Login

Be aware that every build clears storage (uninstalls and installs the app) so login information are lost. 

## Private/public key access

There is a `UserKeyManager.kt` file which consists access to the private/public keys. Also it has some methods for decoding/signing from the teacher. The key pairs are generation during registration so be aware that the keys are not there if you didn't register.


## Code to import public key

```
const val THE_CERT = "-----BEGIN CERTIFICATE-----\n" +
          "    MIIBFTCBwKADAgECAgQAuPR8MA0GCSqGSIb3DQEBCwUAMBIxEDAOBgNVBAMTB0FjbWVLZXkwHhcN\n" +
          "    MjMwMzE5MTIxNjEwWhcNNDMwMzE5MTIxNjEwWjASMRAwDgYDVQQDEwdBY21lS2V5MFwwDQYJKoZI\n" +
          "    hvcNAQEBBQADSwAwSAJBAOPca9EgR9hTSVfgrvpbMTRfHiy473tw7Ok4g+sh6PdJS+nrEZbkqmXB\n" +
          "    HjFMiPzIgo9qOs0SPk5Kp5MKAiEFMjUCAwEAATANBgkqhkiG9w0BAQsFAANBAJ8BcI5d6Ymm5d2R\n" +
          "    KRKczwuLZHH05P0nuE8281q5srM7oUNym21y0tQht9oOwMD1Io4lxaVyhpAV26dCc2Jx/4s=\n" +
          "    -----END CERTIFICATE-----"

private fun importPublicKey() {
    try {
      val cert = CertificateFactory.getInstance("X509").generateCertificate(
        ByteArrayInputStream(THE_CERT.toByteArray(StandardCharsets.UTF_8))
      ) as X509Certificate
      val keyStore = KeyStore.getInstance("AndroidKeyStore")
      val keyProtection =
        KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_VERIFY)
          .setDigests(KeyProperties.DIGEST_SHA1, KeyProperties.DIGEST_SHA256)
          .setRandomizedEncryptionRequired(true)
          .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
          .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS)
          .setUserAuthenticationRequired(false)
          .build()
      keyStore.load(null)
      keyStore.setEntry("AcmeKey", KeyStore.TrustedCertificateEntry(cert), keyProtection)
    } catch (e: java.lang.Exception) {
      throw RuntimeException(e)
    }
  }
```

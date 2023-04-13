package org.feup.apm.readqrtag

internal object Constants {
  const val KEY_SIZE = 512
  const val ANDROID_KEYSTORE = "AndroidKeyStore"
  const val keyAlias = "AcmeKey"
  const val ENC_ALGO = "RSA/NONE/PKCS1Padding"
  const val tagId = 0x41636D65                           // equal to "Acme"
}

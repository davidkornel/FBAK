using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using System.Text;

namespace AcmeBackend
{
    public class User
    {
        public string UserId { get; set; }
        public RSA PublicKey { get; set; }
        public List<Transaction> Transactions { get; set; }
        public List<string> VoucherIds { get; set; }
        public double AccDiscount { get; set; }
        public double SpendingUntilVoucher { get; set; }

        string Name { get; set; }
        string Surname { get; set; }
        CardInfo Card { get; set; }

        public User(string publicKey, string name, string surname, CardInfo card)
        {
            publicKey = publicKey
                .Replace("-----BEGIN CERTIFICATE-----", null)
                .Replace("-----END CERTIFICATE-----", null);
            var cert = new X509Certificate2(Convert.FromBase64String(publicKey));
            var rsaPublicKey = cert.GetRSAPublicKey();
            if (rsaPublicKey is null)
                throw new Exception("No public key provided");

            PublicKey = rsaPublicKey;
            UserId = Guid.NewGuid().ToString();
            Transactions = new List<Transaction>();
            VoucherIds = new List<string>();
            AccDiscount = 0.0;
            SpendingUntilVoucher = 100.0;

            Name = name;
            Surname = surname;
            Card = card;
        }

        public bool IsMessageValid(string? message, string signature)
        {
            if (message is null)
                return true;
            return PublicKey.VerifyData(Encoding.UTF8.GetBytes(message), Convert.FromBase64String(signature), HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);
        }
    }
}
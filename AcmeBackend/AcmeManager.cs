using System.Text.Json;
using Microsoft.AspNetCore.Mvc;

namespace AcmeBackend
{
    public class AcmeManager
    {
        private static string superMarketPublicKey =
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIBFTCBwKADAgECAgQAuPR8MA0GCSqGSIb3DQEBCwUAMBIxEDAOBgNVBAMTB0FjbWVLZXkwHhcN\n" +
            "MjMwMzE5MTIxNjEwWhcNNDMwMzE5MTIxNjEwWjASMRAwDgYDVQQDEwdBY21lS2V5MFwwDQYJKoZI\n" +
            "hvcNAQEBBQADSwAwSAJBAOPca9EgR9hTSVfgrvpbMTRfHiy473tw7Ok4g+sh6PdJS+nrEZbkqmXB\n" +
            "HjFMiPzIgo9qOs0SPk5Kp5MKAiEFMjUCAwEAATANBgkqhkiG9w0BAQsFAANBAJ8BcI5d6Ymm5d2R\n" +
            "KRKczwuLZHH05P0nuE8281q5srM7oUNym21y0tQht9oOwMD1Io4lxaVyhpAV26dCc2Jx/4s=\n" +
            "-----END CERTIFICATE-----";
        private static Dictionary<string, User> users = new Dictionary<string, User>();
        public IResult RegisterUser([FromBody] RegistrationRequestForm registrationRequestForm)
        {
            try
            {
                var newUser = new User(
                    registrationRequestForm.PublicKey,
                    registrationRequestForm.Name,
                    registrationRequestForm.Surname,
                    registrationRequestForm.Card
                );
                users[newUser.UserId] = newUser;

                return Results.Ok(new RegistrationResponseForm(superMarketPublicKey, newUser.UserId));
            }
            catch (Exception ex)
            {
                return Results.UnprocessableEntity(new AcmeError(ex.Message));
            }

        }

        public IResult CheckoutUser([FromBody] CheckoutRequestForm checkoutRequestForm)
        {
            if (!users.ContainsKey(checkoutRequestForm.UserId))
                return Results.UnprocessableEntity(new AcmeError("No such user"));
            var user = users[checkoutRequestForm.UserId];

            // Verify signature.
            var serializeOptions = new JsonSerializerOptions
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase
            };
            var jsonObject = JsonSerializer.SerializeToNode(checkoutRequestForm, serializeOptions)?.AsObject();
            jsonObject?.Remove("signature");
            // if (!user.IsMessageValid(jsonObject?.ToJsonString(), checkoutRequestForm.Signature))
                // return Results.UnprocessableEntity(new AcmeError("Invalid signature"));

            // Summing prices and applying discount.
            double priceToPay = checkoutRequestForm.Products.Sum(p => p.Price);
            string? voucherId = checkoutRequestForm.VoucherId;
            if (voucherId is not null && voucherId != "null")
            {
                if (!user.VoucherIds.Contains(voucherId))
                    return Results.UnprocessableEntity(new AcmeError("No such voucher"));

                user.VoucherIds.Remove(voucherId);
                user.AccDiscount += priceToPay * 0.15;

                if (checkoutRequestForm.DiscountNow == true)
                {
                    double discountApplied = Math.Min(priceToPay, user.AccDiscount);
                    priceToPay -= discountApplied;
                    user.AccDiscount -= discountApplied;
                }
            }

            // Giving the user vouchers if needed.
            user.SpendingUntilVoucher -= priceToPay;
            while (user.SpendingUntilVoucher <= 0.0)
            {
                user.VoucherIds.Add(Guid.NewGuid().ToString());
                user.SpendingUntilVoucher += 100.0;
            }

            // Saving the transaction.
            var transaction = new Transaction(checkoutRequestForm.DiscountNow, checkoutRequestForm.Products, checkoutRequestForm.UserId, checkoutRequestForm.VoucherId);
            user.Transactions.Add(transaction);

            return Results.Ok(new CheckoutResponseForm(priceToPay));
        }

        public IResult FetchUserData([FromBody] UserDataRequestForm userDataRequestForm)
        {
            if (!users.ContainsKey(userDataRequestForm.UserId))
                return Results.UnprocessableEntity(new AcmeError("No such user"));
            var user = users[userDataRequestForm.UserId];

            // Verify signature.
            // if (!user.IsMessageValid(userDataRequestForm.UserId, userDataRequestForm.Signature))
                // return Results.UnprocessableEntity(new AcmeError("Invalid signature"));

            return Results.Ok(new UserDataResponseForm(user.VoucherIds.ToArray(), user.Transactions.ToArray()));
        }
    }
}

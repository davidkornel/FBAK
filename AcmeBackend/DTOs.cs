namespace AcmeBackend
{
    record RegistrationRequestForm(string UserPublicKey);
    record RegistrationResponseForm(string UserId, string SuperMarketId);
    record CheckoutRequestForm(Product[] Products, string UserId, string? VoucherId, bool? DiscountNow, string Signature);
    record CheckoutResponseForm(double AmountPaid);
    record UserDataRequestForm(string UserId, string Signature);
    record UserDataResponseForm(Transaction[] LastTransactions, string[] AvailableVoucherIds);
    record Product(string Id, int Price);
    record Transaction(Product[] Products, string UserId, string? VoucherId, bool? DiscountNow);
}

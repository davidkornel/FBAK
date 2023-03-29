namespace AcmeBackend
{
    public record RegistrationRequestForm(string UserPublicKey);
    public record RegistrationResponseForm(string UserId, string SuperMarketPublicKey);
    public record CheckoutRequestForm(Product[] Products, string UserId, string? VoucherId, bool? DiscountNow, string Signature);
    public record CheckoutResponseForm(double AmountPaid);
    public record UserDataRequestForm(string UserId, string Signature);
    public record UserDataResponseForm(Transaction[] LastTransactions, string[] AvailableVoucherIds);
    public record Product(string Id, int Price);
    public record Transaction(Product[] Products, string UserId, string? VoucherId, bool? DiscountNow);
}

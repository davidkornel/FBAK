namespace AcmeBackend
{
    public record RegistrationRequestForm(string UserPublicKey);
    public record RegistrationResponseForm(string SuperMarketPublicKey, string UserId);
    public record CheckoutRequestForm(bool? DiscountNow, Product[] Products, string Signature, string UserId, string? VoucherId);
    public record CheckoutResponseForm(double AmountPaid);
    public record UserDataRequestForm(string Signature, string UserId);
    public record UserDataResponseForm(string[] AvailableVoucherIds, Transaction[] LastTransactions);
    public record Product(string Id, int Price);
    public record Transaction(bool? DiscountNow, Product[] Products, string UserId, string? VoucherId);
}

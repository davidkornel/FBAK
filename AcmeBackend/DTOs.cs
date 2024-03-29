﻿namespace AcmeBackend
{
    public record RegistrationRequestForm(string Name, string Surname, CardInfo Card, string PublicKey);
    public record RegistrationResponseForm(string SuperMarketPublicKey, string UserId);
    public record CheckoutRequestForm(bool? DiscountNow, Product[] Products, string Signature, string UserId, string? VoucherId);
    public record CheckoutResponseForm(double AmountPaid);
    public record UserDataRequestForm(string Signature, string UserId);
    public record UserDataResponseForm(string[] AvailableVoucherIds, Transaction[] LastTransactions);

    public record CardInfo(string Number, string Cvc, string ExpirationDate);
    public record Product(string Id, double Price);
    public record Transaction(bool? DiscountNow, Product[] Products, string UserId, string? VoucherId, double TotalPaid);

    public record AcmeError(string ErrorMessage);
}

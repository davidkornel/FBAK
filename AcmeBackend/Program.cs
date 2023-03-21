using Microsoft.AspNetCore.Mvc;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options =>
{
    options.SupportNonNullableReferenceTypes();
});

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.MapPost("/register", ([FromBody] RegistrationRequestForm registrationRequestForm) => new RegistrationResponseForm("42", "43"))
.WithName("RegisterUser")
.WithOpenApi();

app.MapPost("/checkout", ([FromBody] CheckoutRequestForm checkoutRequestForm) => new CheckoutResponseForm(42))
.WithName("CheckoutUser")
.WithOpenApi();

app.MapPost("/userdata", ([FromBody] UserDataRequestForm userDataRequestForm) => new UserDataResponseForm(new Transaction[] { new Transaction(new Product[] { new Product("42", 43) }, "42", "43", false) }, new int[] { 42 }))
.WithName("FetchUserData")
.WithOpenApi();

app.Run();

internal record RegistrationRequestForm(string UserPublicKey);
internal record RegistrationResponseForm(string UserId, string SuperMarketId);
internal record CheckoutRequestForm(Product[] Products, string UserId, string? VoucherId, bool? DiscountNow, string Signature);
internal record CheckoutResponseForm(int AmountPaid);
internal record UserDataRequestForm(string UserId, string Signature);
internal record UserDataResponseForm(Transaction[] LastTransactions, int[] AvailableVoucherIds);
internal record Product(string Id, int Price);
internal record Transaction(Product[] Products, string UserId, string? VoucherId, bool? DiscountNow);

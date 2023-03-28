using AcmeBackend;
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

app.MapPost("/register", ([FromBody] RegistrationRequestForm registrationRequestForm) => new RegistrationResponseForm("cef0cbf3-6458-4f13-a418-ee4d7e7505d", "cef0cbf3-6458-4f13-a418-ee4d7e7505d"))
.WithName("RegisterUser")
.WithOpenApi();

app.MapPost("/checkout", ([FromBody] CheckoutRequestForm checkoutRequestForm) => new CheckoutResponseForm(42.5))
.WithName("CheckoutUser")
.WithOpenApi();

app.MapPost("/userdata", ([FromBody] UserDataRequestForm userDataRequestForm) => new UserDataResponseForm(new Transaction[] { new Transaction(new Product[] { new Product("42", 43) }, "42", "43", false) }, new string[] { "cef0cbf3-6458-4f13-a418-ee4d7e7505d" }))
.WithName("FetchUserData")
.WithOpenApi();

app.Run();

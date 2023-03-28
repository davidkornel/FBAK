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

var acmeManager = new AcmeManager();

app.MapPost("/register", acmeManager.RegisterUser)
.WithName("RegisterUser")
.WithOpenApi();

app.MapPost("/checkout", acmeManager.CheckoutUser)
.WithName("CheckoutUser")
.WithOpenApi();

app.MapPost("/userdata", acmeManager.FetchUserData)
.WithName("FetchUserData")
.WithOpenApi();

app.Run();

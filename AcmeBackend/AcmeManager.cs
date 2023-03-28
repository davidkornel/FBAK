using Microsoft.AspNetCore.Mvc;

namespace AcmeBackend
{
    public class AcmeManager
    {
        public RegistrationResponseForm RegisterUser([FromBody] RegistrationRequestForm registrationRequestForm)
        {
            return new RegistrationResponseForm("cef0cbf3-6458-4f13-a418-ee4d7e7505d", "cef0cbf3-6458-4f13-a418-ee4d7e7505d");
        }

        public CheckoutResponseForm CheckoutUser([FromBody] CheckoutRequestForm checkoutRequestForm)
        {
            return new CheckoutResponseForm(42.5);
        }

        public UserDataResponseForm FetchUserData([FromBody] UserDataRequestForm checkoutRequestForm)
        {
            return new UserDataResponseForm(new Transaction[] { new Transaction(new Product[] { new Product("42", 43) }, "42", "43", false) }, new string[] { "cef0cbf3-6458-4f13-a418-ee4d7e7505d" });
        }
    }
}

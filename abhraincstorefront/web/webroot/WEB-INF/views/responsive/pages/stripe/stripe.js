$(document).ready(function() {
    Stripe.setPublishableKey('pk_test_fCFFStJ1Eum0dKY1FlxmOZbv');

    var stripeHandler = function(status, response) {
        if (response.error) {
            // some error has occured
            $(".payment-errors").text(response.error.message);
        } else {
            var form$ = $("#payment-form");
            // token contains id, last4, and card type
            //var token = response['id'];
            //form$.append("<input type='hidden' name='stripeToken' value='" + token + "'/>");
            //form$.get(0).submit();
            alert("id: ", response['id']);
            console.log("last4: ", response['last4']);
            console.log("created: ", response['created']);
            console.log("card type: ", response['card']['type']);
        }
    }

    $("#payment-submit").click(function() {
        console.log("something");
        Stripe.createToken({
            number: $('.card-number').val(),
            cvc: $('.card-cvc').val(),
            exp_month: $('.card-expiry-month').val(),
            exp_year: $('.card-expiry-year').val()
        }, stripeHandler);
    });
});

ACC.cartitem = {

	_autoload: [
		"bindCartItem"
	],

	submitTriggered: false,

	bindCartItem: function ()
	{
		
		$('.updatecartcount').on("click", function (){
			var entryNumber = $(this).attr('id').split("_")
			var form = $('#updateCartForm' + entryNumber[1]);
			
			var ele = form.find('.update-entry-quantity-input');
			var eleval = ele.val();
			if(eleval !== null){
				var updatevalue = parseInt(eleval) + 10;
				ele.attr("value",updatevalue);
				ele.val(updatevalue)
			}
		});
		
		$('.remove-entry-button').on("click", function ()
		{
			var entryNumber = $(this).attr('id').split("_")
			var form = $('#updateCartForm' + entryNumber[1]);

			var productCode = form.find('input[name=productCode]').val();
			var initialCartQuantity = form.find('input[name=initialQuantity]');
			var cartQuantity = form.find('input[name=quantity]');

			ACC.track.trackRemoveFromCart(productCode, initialCartQuantity.val());

			cartQuantity.val(0);
			initialCartQuantity.val(0);
			form.submit();
		});
		
		
		$('.updatecart').on("click", function (e)
		{
			console.log(this);
			//var ele = $('.update-entry-quantity-input');
			ACC.cartitem.handleUpdateQuantity(this, e);
			
//			for(var i= 0; i < ele.length; i++){
//				ACC.cartitem.handleUpdateQuantity(ele[i], e);
//				console.log(ele[i]);
//			}
			//ACC.cartitem.handleUpdateQuantity(ele, e);

		})
//		.on("keyup", function (e)
//		{
//			return ACC.cartitem.handleKeyEvent(this, e);
//			alert("13");
//		}
//		).on("keydown", function (e)
//		{
//			return ACC.cartitem.handleKeyEvent(this, e);
//		}
//		);
	},

	handleKeyEvent: function (elementRef, event)
	{
		//console.log("key event (type|value): " + event.type + "|" + event.which);

		if (event.which == 13 && !ACC.cartitem.submitTriggered)
		{
			ACC.cartitem.submitTriggered = ACC.cartitem.handleUpdateQuantity(elementRef, event);
			return ACC.cartitem.submitTriggered;
			alert("1");
		}
		else 
		{
			// Ignore all key events once submit was triggered
			if (ACC.cartitem.submitTriggered)
			{
				return false;
			}
		}

		return true;
	},

	handleUpdateQuantity: function (elementRef, event)
	{
		
		var entryNumber = $(elementRef).attr('id').split("_")
		var form = $('#updateCartForm' + entryNumber[1]);
	
		var productCode = form.find('input[name=productCode]').val();
		var initialCartQuantity = form.find('input[name=initialQuantity]').val();
		var newCartQuantity = form.find('input[name=quantity]').val();
		//console.log(newCartQuantity);

		if(initialCartQuantity != newCartQuantity)
		{
			
			ACC.track.trackUpdateCart(productCode, initialCartQuantity, newCartQuantity);
			form.submit();
			

			return true;
		}

		return false;
		
	}
};


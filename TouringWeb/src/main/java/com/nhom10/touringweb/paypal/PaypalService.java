package com.nhom10.touringweb.paypal;

import com.nhom10.touringweb.model.user.Booking;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {
	
	@Autowired
	private APIContext apiContext;

		private Booking booking;

		public Payment createPayment(Booking booking,
									 String currency,
									 String cancelUrl,
									 String successUrl) throws PayPalRESTException{
			this.booking =booking;


			Double total = booking.getTotalPrice()/24000;

			Amount amount = new Amount();
			amount.setCurrency(currency);
			total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
			amount.setTotal(String.format("%.2f", total));

			Transaction transaction = new Transaction();
			transaction.setDescription("Payment for items purchased in the store");
			transaction.setAmount(amount);

			List<Transaction> transactions = new ArrayList<>();
			transactions.add(transaction);

			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl(cancelUrl);
			redirectUrls.setReturnUrl(successUrl);
			payment.setRedirectUrls(redirectUrls);

			return payment.create(apiContext);
		}

		public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerId);
			return payment.execute(apiContext, paymentExecute);
		}

	public APIContext getApiContext() {
		return apiContext;
	}

	public void setApiContext(APIContext apiContext) {
		this.apiContext = apiContext;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}

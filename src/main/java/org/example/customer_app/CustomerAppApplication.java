package org.example.customer_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class CustomerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAppApplication.class, args);
	}

	// සර්වර් එක Run වෙලා ඉවර වුණ ගමන් Browser එක ඕපන් කරන කෝඩ් එක
	@EventListener(ApplicationReadyEvent.class)
	public void openBrowser() {
		// Windows/Mac වල වැඩ කරන්න මේක "false" කරන්න ඕනේ
		System.setProperty("java.awt.headless", "false");

		Desktop desktop = Desktop.getDesktop();
		try {
			// අපේ පේජ් එක තියෙන URL එක මෙතන දෙන්න
			desktop.browse(new URI("http://localhost:8080/customers"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
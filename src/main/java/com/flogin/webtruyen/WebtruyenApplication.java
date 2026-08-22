package com.flogin.webtruyen;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class WebtruyenApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebtruyenApplication.class)
		    .headless(false)
            .run(args);
	}

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowserAfterStartup() {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("http://localhost:8080/webtruyen"));
            }
        } catch (Exception e) {
            System.out.println("Lỗi mở trình duyệt tự động: " + e.getMessage());
        }
    }
}

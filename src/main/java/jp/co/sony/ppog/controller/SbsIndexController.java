package jp.co.sony.ppog.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

/**
 * インデクスコントローラ
 *
 * @author ArcaHozota
 * @since 1.87
 */
@Controller
public class SbsIndexController {

	@GetMapping("/index")
	public void index(final HttpServletResponse response) throws IOException {
		response.sendRedirect("/public/sbscities.html");
	}
}

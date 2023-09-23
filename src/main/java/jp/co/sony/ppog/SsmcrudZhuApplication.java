package jp.co.sony.ppog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import jp.co.sony.ppog.utils.Messages;
import lombok.extern.log4j.Log4j2;

/**
 * JpaSsmcrudアプリケーション
 *
 * @author ArcaHozota
 * @since 1.00beta
 */
@Log4j2
@SpringBootApplication
@ServletComponentScan
public class SsmcrudZhuApplication {
	public static void main(final String[] args) {
		SpringApplication.run(SsmcrudZhuApplication.class, args);
		log.info(Messages.MSG003);
	}
}

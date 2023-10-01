package jp.co.sbsssmcrud.ppog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import jp.co.sbsssmcrud.ppog.utils.Messages;
import lombok.extern.log4j.Log4j2;

/**
 * SbsSsmcrudアプリケーション
 *
 * @author shubonnsei
 * @since 1.00
 */
@Log4j2
@SpringBootApplication
@ServletComponentScan
public class ShuBonnseiSsmCrudApplication {
	public static void main(final String[] args) {
		SpringApplication.run(ShuBonnseiSsmCrudApplication.class, args);
		log.info(Messages.MSG003);
	}
}
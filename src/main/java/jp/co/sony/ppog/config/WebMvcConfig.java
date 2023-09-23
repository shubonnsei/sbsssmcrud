package jp.co.sony.ppog.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import jp.co.sony.ppog.utils.Messages;
import lombok.extern.log4j.Log4j2;

/**
 * ウェブコンフィギュレーション
 *
 * @author ArcaHozota
 * @since 1.00beta
 */
@Log4j2
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * SpringMVCフレームワークを拡張するメッセージ・コンバーター
	 *
	 * @param converters コンバーター
	 */
	@Override
	protected void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		log.info(Messages.MSG001);
		// 創建消息轉換器對象；
		final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		// 設置對象轉換器，底層使用Jackson將Java對象轉為JSON；
		messageConverter.setObjectMapper(new JacksonObjectMapper());
		// 將上述消息轉換器追加到SpringMVC框架的轉換器容器中；
		converters.add(0, messageConverter);
	}

	/**
	 * 静的リソースマッピングを設定する
	 *
	 * @param registry 登録
	 */
	@Override
	protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
		log.info(Messages.MSG002);
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/jquery/**").addResourceLocations("classpath:/static/jquery/");
		registry.addResourceHandler("/bootstrap-3.4.1-dist/**")
				.addResourceLocations("classpath:/static/bootstrap-3.4.1-dist/");
	}
}

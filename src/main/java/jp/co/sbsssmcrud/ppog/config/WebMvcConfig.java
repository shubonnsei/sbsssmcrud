package jp.co.sbsssmcrud.ppog.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import jp.co.sbsssmcrud.ppog.utils.Messages;
import lombok.extern.log4j.Log4j2;

/**
 * SpringMVC設定クラス
 *
 * @author shubonnsei
 * @since 1.00
 */
@Log4j2
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 静的リソースマッピングを設定する
	 *
	 * @param registry 注冊説明
	 */
	@Override
	protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
		log.info(Messages.MSG002);
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
	}

	/**
	 * ビューコントローラを定義する
	 *
	 * @param registry
	 */
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addRedirectViewController("/index", "/public/sbscities.html");
	}

	/**
	 * SpringMVCフレームワークを拡張するメッセージ・コンバーター
	 *
	 * @param converters コンバーター
	 */
	@Override
	protected void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		log.info(Messages.MSG001);
		// メッセージコンバータオブジェクトを作成する。
		final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		// オブジェクトコンバータを設定し、Jacksonを使用してJavaオブジェクトをJSONに変換します。
		messageConverter.setObjectMapper(new JacksonObjectMapper());
		// 上記のメッセージコンバータをSpringMVCフレームワークのコンバータコンテナに追加します。
		converters.add(0, messageConverter);
	}
}

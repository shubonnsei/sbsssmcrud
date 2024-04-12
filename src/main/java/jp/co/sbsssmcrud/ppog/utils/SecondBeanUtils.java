package jp.co.sbsssmcrud.ppog.utils;

import java.beans.PropertyDescriptor;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import com.google.common.collect.Sets;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 共通Beanツール
 *
 * @author ArkamaHozota
 * @since 3.04
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecondBeanUtils extends BeanUtils {

	/**
	 * NULLではないプロパティだけコピーする
	 *
	 * @param source コピー元
	 * @param target コピー先
	 * @throws BeansException
	 */
	public static void copyNullableProperties(final Object source, final Object target) throws BeansException {
		BeanUtils.copyProperties(source, target, SecondBeanUtils.getNullProperties(source));
	}

	/**
	 * コピー元のNULLプロパティを取得する
	 *
	 * @param source コピー元
	 * @return NULLプロパティ
	 */
	private static String[] getNullProperties(final Object source) {
		final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
		final PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
		final Set<String> nullFields = Sets.newHashSet();
		if (propertyDescriptors.length > 0) {
			for (final PropertyDescriptor p : propertyDescriptors) {
				final String name = p.getName();
				final Object value = beanWrapper.getPropertyValue(name);
				if (value == null) {
					nullFields.add(name);
				}
			}
		}
		return nullFields.toArray(new String[nullFields.size()]);
	}
}
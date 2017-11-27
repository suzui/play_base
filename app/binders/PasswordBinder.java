package binders;

import play.data.binding.TypeBinder;
import utils.CodeUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class PasswordBinder implements TypeBinder<String> {

	@Override
	public String bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType)
			throws Exception {
		return value != null && value.length() < 32 ? CodeUtils.md5(value) : value;
	}
}
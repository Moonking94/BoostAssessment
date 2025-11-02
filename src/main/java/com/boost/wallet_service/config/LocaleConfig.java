package com.boost.wallet_service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleConfig extends AcceptHeaderLocaleResolver {

    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
            new Locale("en"), new Locale("ms")
    );

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getHeader("Accept-Language");
        return lang == null || lang.isEmpty()
                ? Locale.ENGLISH
                : Locale.lookup(Locale.LanguageRange.parse(lang), SUPPORTED_LOCALES);
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);  // Default language
        resolver.setSupportedLocales(SUPPORTED_LOCALES);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // Allows ?lang=ms in URL
        return interceptor;
    }
}

package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        IncludeBean includeBean = applicationContext.getBean(IncludeBean.class);
        assertThat(includeBean).isInstanceOf(IncludeBean.class);

        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(ExcludeBean.class));
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {
    }
}

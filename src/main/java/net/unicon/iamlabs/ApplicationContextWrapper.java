package net.unicon.iamlabs;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

public class ApplicationContextWrapper implements ApplicationContextAware, ApplicationContext {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public Environment getEnvironment() {
        return this.context.getEnvironment();
    }

    @Override
    public boolean containsBeanDefinition(String arg0) {
        return this.containsBeanDefinition(arg0);
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String arg0, Class<A> arg1) {
        return this.context.findAnnotationOnBean(arg0, arg1);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.context.getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.context.getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> arg0) {
        return this.context.getBeanNamesForType(arg0);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> arg0, boolean arg1, boolean arg2) {
        return this.context.getBeanNamesForType(arg0, arg1, arg2);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> arg0) throws BeansException {
        return this.context.getBeansOfType(arg0);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> arg0, boolean arg1, boolean arg2) throws BeansException {
        return this.context.getBeansOfType(arg0, arg1, arg2);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> arg0) throws BeansException {
        return this.context.getBeansWithAnnotation(arg0);
    }

    @Override
    public boolean containsBean(String arg0) {
        return this.context.containsBean(arg0);
    }

    @Override
    public String[] getAliases(String arg0) {
        return this.context.getAliases(arg0);
    }

    @Override
    public Object getBean(String arg0) throws BeansException {
        return this.context.getBean(arg0);
    }

    @Override
    public <T> T getBean(Class<T> arg0) throws BeansException {
        return this.context.getBean(arg0);
    }

    @Override
    public <T> T getBean(String arg0, Class<T> arg1) throws BeansException {
        return this.context.getBean(arg0, arg1);
    }

    @Override
    public Object getBean(String arg0, Object... arg1) throws BeansException {
        return this.context.getBean(arg0, arg1);
    }

    @Override
    public Class<?> getType(String arg0) throws NoSuchBeanDefinitionException {
        return this.context.getType(arg0);
    }

    @Override
    public boolean isPrototype(String arg0) throws NoSuchBeanDefinitionException {
        return this.context.isPrototype(arg0);
    }

    @Override
    public boolean isSingleton(String arg0) throws NoSuchBeanDefinitionException {
        return this.context.isSingleton(arg0);
    }

    @Override
    public boolean isTypeMatch(String arg0, Class<?> arg1) throws NoSuchBeanDefinitionException {
        return this.context.isTypeMatch(arg0, arg1);
    }

    @Override
    public boolean containsLocalBean(String arg0) {
        return this.context.containsLocalBean(arg0);
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.context.getParentBeanFactory();
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return this.context.getMessage(code, args, defaultMessage, locale);

    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return this.context.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return this.context.getMessage(resolvable, locale);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.context.publishEvent(event);

    }

    @Override
    public Resource[] getResources(String locationPattern) throws IOException {
        return this.context.getResources(locationPattern);
    }

    @Override
    public Resource getResource(String location) {
        return this.context.getResource(location);
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.context.getClassLoader();
    }

    @Override
    public String getId() {
        return this.context.getId();
    }

    @Override
    public String getDisplayName() {
        return this.context.getDisplayName();
    }

    @Override
    public long getStartupDate() {
        return this.context.getStartupDate();
    }

    @Override
    public ApplicationContext getParent() {
        return this.context.getParent();
    }

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return this.context.getAutowireCapableBeanFactory();
    }

}

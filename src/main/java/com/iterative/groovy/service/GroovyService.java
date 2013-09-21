/*
 * Copyright 2007 Bruce Fancher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iterative.groovy.service;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Bruce Fancher
 */
public abstract class GroovyService implements ApplicationContextAware {
    public static final String CONTEXT_KEY = "ctx";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Object> bindings;
    private boolean launchAtStart;
    private Thread serverThread;
    private String customScriptsLocation = "/etc/cas/scripts/";
    protected ApplicationContext context;

    public GroovyService() {
        super();
    }

    public GroovyService(final Map<String, Object> bindings) {
        this();
        this.bindings = bindings;
    }

    public void launchInBackground() {
        serverThread = new Thread() {
            @Override
            public void run() {
                try {
                    logger.debug("Launching groovy service background thread...");
                    launch();
                } catch (final Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };

        serverThread.setDaemon(false);
        serverThread.start();
    }

    public abstract void launch();

    protected Binding createBinding() {
        final Binding binding = new Binding();

        final String[] beanNames = context.getBeanDefinitionNames();
        logger.debug("Found [{}] beans in the application context", context.getBeanDefinitionCount());

        for (final String name : beanNames) {

            try {
                binding.setVariable(name, context.getBean(name));
                logger.debug("Added context bean [{}] to groovy bindings", name);
            } catch (final Exception e) {
                logger.warn("Could not add bean id [{}] to the binding. Reason: [{}]", name, e.getMessage());
            }
        }

        if (bindings != null) {
            for (final Map.Entry<String, Object> nextBinding : bindings.entrySet()) {

                logger.debug("Added variable [{}] to groovy bindings", nextBinding.getKey());
                binding.setVariable(nextBinding.getKey(), nextBinding.getValue());
            }
        }

        logger.debug("Added application context [{}] to groovy bindings", CONTEXT_KEY);
        binding.setVariable(CONTEXT_KEY, context);

        loadCustomGroovyScriptsIntoClasspath(binding);

        return binding;
    }

    public void initialize() {
        if (launchAtStart) {
            launchInBackground();
        }
    }

    public void destroy() {
    }

    public void setBindings(final Map<String, Object> bindings) {
        this.bindings = bindings;
    }

    public boolean isLaunchAtStart() {
        return launchAtStart;
    }

    public void setLaunchAtStart(final boolean launchAtStart) {
        this.launchAtStart = launchAtStart;
    }

    public void setCustomScriptsLocation(final String customScriptsLocation) {
        this.customScriptsLocation = customScriptsLocation;
    }

    @Override
    public void setApplicationContext(final ApplicationContext arg0) throws BeansException {
        this.context = arg0;
    }

    private final void loadCustomGroovyScriptsIntoClasspath(final Binding binding) {
        final FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.endsWith("groovy"));
            }
        };

        final GroovyClassLoader loader = new GroovyClassLoader(this.getClass().getClassLoader());
        final File[] files = new File(this.customScriptsLocation).listFiles(filter);
        for (final File file : files) {
            try {
                final Class c = loader.parseClass(file);
                
                final String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
                
                binding.setVariable(fileNameWithOutExt, c.newInstance());
                logger.debug("Add custom groovy script [{}] to the binding", fileNameWithOutExt);
            } catch (final Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        IOUtils.closeQuietly(loader);
    }
}

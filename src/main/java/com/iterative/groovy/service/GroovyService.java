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

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bruce Fancher
 */
public abstract class GroovyService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Object> bindings;
    private boolean launchAtStart;
    private Thread serverThread;

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

        if (bindings != null) {
            for (final Map.Entry<String, Object> nextBinding : bindings.entrySet()) {
                
                logger.debug("Added variable [{}] to groovy bindings", nextBinding.getKey());
                binding.setVariable(nextBinding.getKey(), nextBinding.getValue());
            }
        }

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
}

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
import groovy.ui.Console;

/**
 * @author Bruce Fancher
 */
public class GroovyConsoleService extends GroovyService {    
    private Thread thread;

    public GroovyConsoleService() {
        super();
    }

    @Override
    public void launch() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    final Binding binding = createBinding();
                    logger.debug("Creating new groovy console instance with binding [{}]", binding);
                    new Console(binding).run();
                } catch (final Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        };

        logger.debug("Launched groovy console service daemon thread");
        thread.setDaemon(true);
        thread.start();
    }
}

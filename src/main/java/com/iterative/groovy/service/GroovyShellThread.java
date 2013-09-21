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

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bruce Fancher
 */
public final class GroovyShellThread extends Thread {
    public static final String OUT_KEY = "out";

    private final Socket socket;
    private final Binding binding;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public GroovyShellThread(final Socket socket, final Binding binding) {
        super();
        this.socket = socket;
        this.binding = binding;
    }

    @Override
    public void run() {
        PrintStream out = null;
        InputStream in = null;

        try {
            out = new PrintStream(socket.getOutputStream());
            in = socket.getInputStream();
            logger.debug("Created socket IO streams...");
            
            binding.setVariable(OUT_KEY, out);
            logger.debug("Added output stream to binding collection as {}", OUT_KEY);
            
            final IO io = new IO(in, out, out);
            final Groovysh gsh = new Groovysh(this.getContextClassLoader(), binding, io);
            
            try {
                logger.debug("Launching groovy interactive shell");
                gsh.run(new String[] {});
            } catch (final Exception e) {
                logger.error(e.getMessage(), e);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                IOUtils.closeQuietly(out);
            }

            if (in != null) {
                IOUtils.closeQuietly(in);
            }

            if (socket != null) {
                IOUtils.closeQuietly(this.socket);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}

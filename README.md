cas-groovy-shell
================

A Groovy shell embedded inside the CAS server overlay

# Intro

This is an instance of the Groovy interactive console that is embedded inside a CAS server overlay. The shell is fully aware of the CAS application context and API and is also able to load custom groovy scripts which may want to peek inside the CAS configuration, invoke its API and perhaps report back various bits of configuration about CAS.

There are no hard dependencies on the CAS server. The shell may be extracted to be used with *any* Java web application.

# Versions

    <cas.version>3.5.2</cas.version>
    <groovy.version>2.1.7</groovy.version>

# Deployment

* Execute `mvn clean package` at the root project directory. Copy the `target\cas.war` over to the server container, and also copy the contents of the `etc` directory over to `/etc/cas`.

or...

* Review the `build.xml` file for environment settings, and then simply execute `ant deploy` which will entirely automate the build/deployment process.

...and start the container.

# Configuration

The shell is simply a wrapper around the `Groovysh` tool that is able to respond to client requests by launching a separate thread for each. In the `groovyShellContext.xml` file By default, the groovy shell service listener component on the server side launches on startup and binds on the port `6789`:

    <bean id="groovyShellService" class="com.iterative.groovy.service.GroovyShellService" 
          parent="groovyService">
        <property name="socket" value="${groovy.shell.socket.port:6789}" />
        <property name="launchAtStart" value="${groovy.shell.launch.startup:true}" />
        <property name="customScriptsLocation" value="${groovy.shell.scripts.path:/etc/cas/scripts/" />
    </bean>

..which means that in order to connect, you could:

* Use **Telnet**: `telnet localhost 6789` (You may have to turn off local echos)
* Use **Putty** with the following configuration:
    * Connect to `localhost` and `6789`
    * Connection Type: `Telnet`
    * Close Window on Exit: `Never`
    * Telnet Negotiation Mode: `Passive`
    * Session Logging: `All Session Output`

By default custom groovy scripts are loaded from `/etc/cas/scripts/`. You can change this of course by the `customScriptsLocation` setting as is shown above.


# Groovy Bindings
The following variables are available to the shell automatically:

* All beans that are registered with the application context. In other words, every bean that is registered with CAS application context inside its XML files is available to the console, except of course those that cannot be instantiated, such as abstract beans.
* The output stream `out` variable.
* The application context `ctx` variable

Extra bindings may also be provided in the `groovyShellContext.xml` file, such as:

    <bean id="groovyService" abstract="true" init-method="initialize" destroy-method="destroy">
        <property name="bindings">
            <map>
                <entry key="variableName" value-ref="refToSomeOtherBeanId" />
                ...
            </map>
        </property>
    </bean>

# Executing Groovy Commands

* Use `help` to learn groovy shell commands. In particular `show classes`, `display`, `history` and `load <groovy-file` are extra helpful.

* Directly interact with the bindings. For instance you may inspect the `ticketRegistry` bean:


    groovy:000> ticketRegistry
    ===> org.jasig.cas.ticket.registry.DefaultTicketRegistry@bc1fe6
    
All beans and their public APIs may be used by the shell to interact with the application context.







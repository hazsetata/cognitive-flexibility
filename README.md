# cognitive-flexibility: taskswitching

Web application for running task switching experiments with participants. For
details on existing studies, see

"Cognitive Flexibility and Programming Performance"
http://www.sussex.ac.uk/Users/bend/ppig2014/1ppig2014_submission_19.pdf

The maven commands are to be written in the same directory where this file is.

## Testing

Assuming that you have Java (version >= 1.7) and Maven on your computer, you can
try this directly on your local machine. If you have Docker installed,
you can use `docker-compose` to start a fully configured MySQL server,
complete with the necessary credentials and database:

```shell
docker-compose up
```

Docker-compose will expose MySQL on the host's 3306 port and PhpMyAdmin
will be available on port 8090.

The `root` user has password `rootchangeme`, and there is a dedicated
database user too: `cognitiveuser` with password `changeme`. Please
don't use these values in production!

Alternatively you can create a local / remote MySQL database and modify
the values in the method dataSource() of TSProfileDevelopment.java to match
your database details.

Once you have that set, write

```shell
mvn spring-boot:run
```

on command line, and the application is running at [http://localhost:8080]().

## Packaging

To package the source code for production, type the command

```shell
mvn clean package
```

After packaging, you'll have a new war-file in the newly created `target`
folder (cognitive-flexibility-tstest-<VERSION>.war).

You can launch it by e.g. saying

```shell
java -jar cognitive-flexibility-tstest-<VERSION>.war
```

## Configuration

To use production mode (incl a real database, MySQL assumed), set the production
server environment variable "SPRING_PROFILES_ACTIVE" to production;

```shell
export SPRING_PROFILES_ACTIVE=production
```

Now when the application starts, it knows to utilize the configuration in 
TsProfileDevelopment.java -- by default, it will assume that a file called
`application.properties` exists, and that it contains details on the database, e.g.

```properties
db.hostname=localhost
db.port=3306
db.path=/mydatabase?zeroDateTimeBehavior=convertToNull
db.username=something
db.password=else
```

### Profile activation (alternative)

The `production` profile can be activated from the `application.properties`
file too, by adding the following line:

```properties
spring.profiles.active=production
```

### Webhook setup (optional)

If this web-application is used as a part of a larger system, it can be
useful to notify a 3rd party server when a user finishes his/ her cognitive
test. This can be achieved by two things:

When the user logs into the system, specify `callbackToken` parameter
that will identify the cognitive-test session towards the 3rd party server.

 * If using the GET based `app/auth/{username}` request path, specify
   `callbackToken` as a query parameter.
 * If the POST based `app/formauth` request path is used, specify
   `callbackToken` as a form field (probably hidden).

You also need to specify two new configuration parameters, added to
the `application.properties` file mentioned previously:

```properties
callback.url=https://your.server.com/callback/path
callback.payloadTemplate={"session": "{{callbackToken}}"}
```

The `{{callbackToken}}` part in the payload-template will be replaced
by the value of `callbackToken` specified at login.

### Example configuration file

An example `application.properties` file can be found in the `deployment`
directory.

## Deployment

Copy the created war-file to the server. For example in Tomcat, you need to copy
the war-file to the `webapps` folder of Tomcat. The application will start under
the path that corresponds to the name of the war-file.

### Java version

The POM file was updated to make it possible to run the application on Java 11.
On older Java versions where XML binding / Activation framework was still
part os the Java runtime, the dependencies added for Java 11 compatibility
must be removed.

This part of the POM file is marked between
`<!-- Java 11 compatibility start -->` and `<!-- Java 11 compatibility end -->`
tags.

## Using this in your research (or just for fun)
Ask for more details! :) Have fun!

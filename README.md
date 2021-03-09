# Cognitive-flexibility Test

Web application for running task switching experiments with participants.
This code is an updated version of [Measureself's Cognitive Flexibility repository.](https://github.com/measureself/cognitive-flexibility)

For details on existing studies, see:

[Cognitive Flexibility and Programming Performance (pdf)](http://www.sussex.ac.uk/Users/bend/ppig2014/1ppig2014_submission_19.pdf)

All the commands are to be run in the same directory where this file is
(repository root).

## Development / testing

The code requires Java (version >= 11.x). It can be built with:

```shell
./gradlew clean build
```

This will create a JAR package in the `build/libs` directory. As the
code uses [Spring Boot](https://spring.io/projects/spring-boot), this
JAR file is executable. However, the application requires a MySQL
compatible database (MySQL, MariaDB, etc.), and we need to supply the
connection details.

### Developing with development-time Docker Compose setup

This setup utilizes a Docker Compose setup that spins up MySQL and
PhpMyAdmin. This setup is ideal when you want to work on the code,
and don't want to spend time on rebuilding the application's Docker
image. We are going to use the `docker-compose-dev.yml` file.

To start it, open a new terminal and run:

```shell
docker-compose -f ./docker-compose-dev.yml up
```

Once ready, you can connect to the MySQL instance with your favourite
client (e.g.: DataGrip) directly:

| Parameter | Value |
| --------- | ----- |
| Host | localhost |
| Port | 3306 |
| Root password| rootchangeme |
| DB user | cognitiveuser |
| DB user's password | changeme |
| Application DB | cognitivedb |

You can also use PhpMyAdmin instead of a local client (available at
[http://localhost:8090/](http://localhost:8090/)).

Now start the application (in a different terminal):

```shell
DATABASE_URL=mysql://cognitiveuser:changeme@localhost:3306/cognitivedb java -jar build/libs/cognitive-test-2.0.0.ace7ece.jar
```

To stop the application, press Ctrl+C. To stop Docker Compose, first
press Ctrl+C in its terminal, then run:

```shell
docker-compose -f ./docker-compose-dev.yml down
```

This keeps the built Docker images, and the MySQL database contents. If
you want to clean those up too, run:

```shell
docker-compose -f ./docker-compose-dev.yml down -v --rmi all
```

### Working with the full Docker Compose setup

This setup utilizes a Docker Compose setup that spins up MySQL,
PhpMyAdmin and the application too. This setup is ideal when you want
to quickly demonstrate the whole application. We are going to use the
`docker-compose.yml` file.

To start it, open a new terminal and run:

```shell
docker-compose up
```

Once ready, the application is available at
[http://localhost:8080/](http://localhost:8080/).
You can also connect to the MySQL instance with your favourite
client (e.g.: DataGrip) directly:

| Parameter | Value |
| --------- | ----- |
| Host | localhost |
| Port | 3306 |
| Root password| rootchangeme |
| DB user | cognitiveuser |
| DB user's password | changeme |
| Application DB | cognitivedb |

Or use PhpMyAdmin instead of a local client (available at
[http://localhost:8090/](http://localhost:8090/)).

To stop Docker Compose, first press Ctrl+C in its terminal, then run:

```shell
docker-compose down
```

This keeps the built Docker images, and the MySQL database contents. If
you want to clean those up too, run:

```shell
docker-compose down -v --rmi all
```

## Deployment configuration

The application was modified from the original code, with the intent to
run it on Heroku. As Heroku only supports configuration through
environment variables, the application now supports this mode too.

The only required configuration value is the URL with the database
access parameters. The application supports MySQL compatible databases
(MySQL, MariaDB, etc.), and the URL handling part of the code supports
all the database add-ons on Heroku that fulfills this:

| Add-on name | Heroku add-on page |
| ----------- | ------------------ |
| JawsDB Maria | https://elements.heroku.com/addons/jawsdb-maria |
| JawsDB MySQL | https://elements.heroku.com/addons/jawsdb |

To make it easy to run the application, the configuration value can
be specified in one of the three environment variables below (checked
in this order): `DATABASE_URL`, `JAWSDB_MARIA_URL` and `JAWSDB_URL`.
The latter two are automatically set by Heroku when the corresponding
add-on is configured.

### Client-id setup (optional)

By default, the application doesn't require any user authentication,
and only asks for the users' id. They can enter their id freely. To
support deployments where the id should be supplied by a 3rd party
application, and the use of this application should only originate
from this 3rd party application a required client-id can be set up.

Define an environment variable (using `heroku config:set` or if running
in a different environment by defining the same environment variable):

| Environment variable | Value |
| -------------------- | ------------ |
| TTL_COGNITIVE_CLIENT_CLIENTID | Random alphanumeric string, **without** special characters (e.g.: pA7dxKc8RmQ6U2UbCe7) |

When the user logs into the system, specify `clientId` parameter
that will identify the cognitive-test session towards the 3rd party server.

 * If using the GET based `app/auth/{username}` request path, specify
   `clientId` as a query parameter.
 * The POST based `app/formauth` request path should not be used if
   client-id will be required.

### Webhook setup (optional)

If this web-application is used as a part of a larger system, it can be
useful to notify a 3rd party server when a user finishes his / her cognitive
test. This can be achieved by two things:

Define these environment variables (using `heroku config:set` or if running
in a different environment by defining the same environment variables):

| Environment variable | Default value | Optional | Description |
| -------------------- | ------------- | -------- | ----------- |
| TTL_COGNITIVE_CALLBACK_CALLBACKURL | Not defined | No | The full URL with path, e.g.: https://localhost:8085/callback |
| TTL_COGNITIVE_CALLBACK_PAYLOADTEMPLATE | {"session": "{{callbackToken}}"} | No | The `{{callbackToken}}` part in the payload-template will be replaced by the value of `callbackToken` specified at login. No format restrictions. |
| TTL_COGNITIVE_CALLBACK_LOGHTTPREQUEST | false | Yes | If true, the debug log will contain the HTTP call's log. Useful for debugging. |

When the user logs into the system, specify `callbackToken` parameter
that will identify the cognitive-test session towards the 3rd party server.

 * If using the GET based `app/auth/{username}` request path, specify
   `callbackToken` as a query parameter.
 * If the POST based `app/formauth` request path is used, specify
   `callbackToken` as a form field (probably hidden).

### Configuration using file

If deploying the application on a different hosting provider than Heroku,
configuration can be defined using a standard Spring Boot
`application.properties` file. The file **must** be placed in the
directory where the application will be started from.

An example configuration file can be found in the `deployment` folder
of this repository.

## Using this in your research (or just for fun)
Ask for more details! :) Have fun!

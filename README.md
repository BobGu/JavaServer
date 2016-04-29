* JavaServer

This JavaServer allows you to load files from a directory and then serves those
files. To start the server type in this from the root of the project.  `java
-jar build/libs/javaserver.jar`

To choose a directory to serve files from use this flag at the end of the
command to start the server '-d location-of-directory`

The directory can be a relative path to where you run the command or an absolute
path like `/Users/username/somedirectory`

Also when you start the server you can specify a port number for the server to
use by using this flag `-p port-number`

This project uses the acceptance criteria that is part of
[cob_spec](https://github.com/8thlight/cob_spec).  This link has insturctions
how to run the acceptance tests.

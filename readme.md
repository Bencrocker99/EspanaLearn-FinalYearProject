**Libraries**

In order to run the client and server for this program, the following from the 'jars' directory need to be added as libraries in the IDE:

* jasypt-1.9.2.jar 
* java-json.jar
* hamcrest-core-1.3.jar (if wanting to run the test classes)
* junit-4.12.jar (if wanting to run the test classes)

Java FX (https://openjfx.io/) must also be installed and added as a library.

**Recommendations**

* Run program on Windows 10
* Run program using IntelliJ

The program may work fine on other IDEs and operating systems, but it hasn't been explicitly tested out on any others.


**Running the Program**

The server must be run before the client in order to be able to get past the login screen on the client.

In order to run the **Server**, com.Server must be run with the following VM Arguments: --module-path C:\Users\User\ _FilePathToJavaFX_\JavaFXML\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml 

(where *_FilePathToJavaFX_* represents the file path from the user area to Java FX)

In order to run the **Client**, src.com.Run must be run with the following VM Arguments: --module-path C:\Users\User\ _FilePathToJavaFX_\JavaFXML\javafx-sdk-11.0.2\lib --add-modules=javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.web --add-reads javafx.graphics=ALL-UNNAMED --add-opens javafx.controls/com.sun.javafx.charts=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.iio.common=ALL-UNNAMED --add-opens javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-opens javafx.base/com.sun.javafx.runtime=ALL-UNNAMED

(where _FilePathToJavaFX_ represents the file path from the user  area to Java FX)

Link to GitLab: https://git-teaching.cs.bham.ac.uk/mod-ug-proj-2020/bjc756

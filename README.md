# MockOMatik

Generate test templates based on existing code.

**Version 0.1.0**

MockO'Matik is a simple open-source Java application that generates tests and test templates based on existing Java source code. The tests are constructed in Junit and the Mockito framework (and hopefully other frameworks in the future) to help ensure successful mocking. While the purpose of testing source code is to check and hopefully ensure an application is functioning as anticipated, many projects contain thousands of lines of hard to read code. In projects like these the potential for human error and countless hours of wasted time is immeasurable. However, completely taking the programmer out of testing is also a terrible idea. So, MockO'Matik does not replace the human element needed for creating tests, but simply speeds up the process and minimizes errors. 

- Annotations will only work in a marked test directory.
- Package statements will not work if output is not directed to the correct test package.
- Unless you have the required jar files, gradle cannot be set to 'Offline work'.

## How Its Used

The process is simple, input the path to the source code you want tested, then input where you want the output to be generated.

- Annotations will only work in a marked test directory.
- Package statements will not work if output is not directed to the correct test package.
- Unless you have the required jar files, gradle cannot be set to 'Offline work'.

---

## Versions Used

- OpenJDK-11.0.2
- Gradle-5.6.4

## Simple Rate Limiter

1. Compile the Java code using `javac`:
    ```bash
    javac Main.java
    ```

2. Execute the compiled Java program with input and output files:
    ```bash
    java Main <input_file> <output_file>
    ```
    Example:
    ```bash
    java Main input.txt output.txt
    ```

3. Read results in the output file

For example input and output
| Input                                                                                            | Output                                |
|--------------------------------------------------------------------------------------------------|---------------------------------------|
| 10 3<br>2022-01-20T00:13:05Z<br>2022-01-20T00:27:31Z<br>2022-01-20T00:45:27Z<br>2022-01-20T01:00:49Z<br>2022-01-20T01:15:45Z<br>2022-01-20T01:20:01Z<br>2022-01-20T01:50:09Z<br>2022-01-20T01:52:15Z<br>2022-01-20T01:54:00Z<br>2022-01-20T02:00:00Z | <br>true<br>true<br>true<br>false<br>true<br>false<br>true<br>true<br>false<br>false |






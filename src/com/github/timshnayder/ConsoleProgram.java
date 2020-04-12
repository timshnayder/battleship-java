package com.github.timshnayder;

import java.util.Scanner;

public class ConsoleProgram {
    public String readLine(String prompt)
    {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

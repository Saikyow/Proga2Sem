package org.example.managers;

import org.example.interfaces.InputOutput;
import org.jline.reader.*;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class ManagerInputOutput implements InputOutput {
    private static ManagerInputOutput managerInputOutput;
    private LineReader lineReader;
    private Terminal terminal;
    private Stack<BufferedReader> readerStack;
    private boolean executeScript = false;

    private ManagerInputOutput() {
        this.readerStack = new Stack<>();

        try {
            // Настройка терминала для автодополнения
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .dumb(false)  // Отключаем dumb режим
                    .jansi(true)   // Включаем JANSI для лучшей совместимости
                    .build();

            // Базовая конфигурация LineReader
            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .option(LineReader.Option.AUTO_FRESH_LINE, true)
                    .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                    .build();

            System.out.println("JLine инициализирован успешно");
        } catch (Exception e) {
            System.err.println("Ошибка инициализации JLine: " + e.getMessage());
            terminal = null;
            lineReader = null;
        }
    }

    public void setCommands(List<String> commandNames) {
        if (lineReader != null && terminal != null) {
            try {
                Completer completer = new StringsCompleter(commandNames);
                lineReader = LineReaderBuilder.builder()
                        .terminal(terminal)
                        .completer(completer)
                        .option(LineReader.Option.AUTO_FRESH_LINE, true)
                        .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                        .build();
                System.out.println("Автодополнение настроено для " + commandNames.size() + " команд");
            } catch (Exception e) {
                System.err.println("Ошибка настройки автодополнения: " + e.getMessage());
            }
        }
    }

    public static ManagerInputOutput getInstance() {
        if (managerInputOutput == null) {
            managerInputOutput = new ManagerInputOutput();
        }
        return managerInputOutput;
    }

    public void pushFileExecute(BufferedReader reader) {
        this.readerStack.push(reader);
        this.executeScript = true;
    }

    public void popFileExecute() {
        if (!readerStack.isEmpty()) {
            try {
                BufferedReader currentReader = readerStack.peek();
                if (currentReader != null) {
                    currentReader.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии ридера: " + e.getMessage());
            } finally {
                readerStack.pop();
                if (readerStack.isEmpty()) {
                    this.executeScript = false;
                }
            }
        }
    }

    public boolean isScriptMode() {
        return this.executeScript || !readerStack.isEmpty();
    }

    public boolean isCurrentReader(BufferedReader reader) {
        return !readerStack.isEmpty() && readerStack.peek() == reader;
    }

    public String readLineIO(String prompt) {
        // Режим выполнения скрипта
        while (!readerStack.isEmpty()) {
            BufferedReader currentReader = readerStack.peek();
            try {
                String line = currentReader.readLine();
                if (line != null) {
                    System.out.println("[Значение из скрипта] " + line);
                    return line;
                } else {
                    popFileExecute();
                    return null;
                }
            } catch (IOException e) {
                popFileExecute();
                return null;
            }
        }

        // Интерактивный режим с JLine
        if (lineReader != null) {
            try {
                return lineReader.readLine(prompt);
            } catch (UserInterruptException e) {
                return "";
            } catch (EndOfFileException e) {
                System.out.println("\nОбнаружен EOF");
                return null;
            } catch (Exception e) {
                System.err.println("Ошибка JLine: " + e.getMessage());
                lineReader = null; // Отключаем JLine при ошибке
            }
        }

        // Fallback на системный console
        try {
            java.io.Console console = System.console();
            if (console != null) {
                return console.readLine(prompt);
            }
        } catch (Exception e) {
            // Игнорируем
        }

        // Последний fallback - Scanner
        System.out.print(prompt);
        return new java.util.Scanner(System.in).nextLine();
    }

    public String readLineIO() {
        return readLineIO("");
    }

    public void writeLineIO(String message) {
        System.out.print(message);
    }

    public boolean hasNextIntIO() {
        return true;
    }

    public int nextIntIO() {
        String line = readLineIO();
        if (line != null) {
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public void closeIO() {
        while (!readerStack.isEmpty()) {
            try {
                BufferedReader reader = readerStack.pop();
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии ридера: " + e.getMessage());
            }
        }

        try {
            if (terminal != null) {
                terminal.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при закрытии терминала: " + e.getMessage());
        }

        writeLineIO("IO закрыт\n");
    }
}
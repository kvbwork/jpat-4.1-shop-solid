package kvbdev;

import kvbdev.menu.MenuPage;
import kvbdev.menu.UserInputReader;

import java.util.Scanner;

public class ConsoleInteractiveMenu implements UserInputReader {
    protected Scanner scanner;
    protected MenuPage page;

    public ConsoleInteractiveMenu() {
        scanner = new Scanner(System.in);
    }

    public void dispose() {
        scanner.close();
    }

    public void setPage(MenuPage page) {
        this.page = page;
        printTitle();
    }

    public void start() {
        while (page != null) {
            execute(page);
        }
    }

    @Override
    public String readString() {
        return scanner.nextLine().trim();
    }

    protected void printTitle(){
        System.out.println();
        System.out.println(page.getTitle());
        System.out.println("=".repeat(20));
    }

    protected void execute(MenuPage page) {
        if (page == null) return;
        System.out.println(page.getView());

        while (!page.exec(readString())) {
            System.err.println("Команда не распознана, повторите ввод.");
        }
    }

}

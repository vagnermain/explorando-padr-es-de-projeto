import java.util.ArrayList;
import java.util.List;

abstract class Handler {
    protected Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(Expense expense);
}

class Manager extends Handler {
    private double approvalLimit = 1000;

    @Override
    public void handleRequest(Expense expense) {
        if (expense.getAmount() <= approvalLimit) {
            System.out.println("Despesa de " + expense.getAmount() + " aprovada pelo Gerente.");
        } else if (successor != null) {
            successor.handleRequest(expense);
        }
    }
}

class Director extends Handler {
    private double approvalLimit = 5000;

    @Override
    public void handleRequest(Expense expense) {
        if (expense.getAmount() <= approvalLimit) {
            System.out.println("Despesa de " + expense.getAmount() + " aprovada pelo Diretor.");
        } else if (successor != null) {
            successor.handleRequest(expense);
        }
    }
}

class CEO extends Handler {
    @Override
    public void handleRequest(Expense expense) {
        System.out.println("Despesa de " + expense.getAmount() + " aprovada pelo CEO.");
    }
}

class Expense {
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

public class Main {
    public static void main(String[] args) {
        Handler manager = new Manager();
        Handler director = new Director();
        Handler ceo = new CEO();

        manager.setSuccessor(director);
        director.setSuccessor(ceo);

        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Laptop", 800));
        expenses.add(new Expense("Conferência", 4500));
        expenses.add(new Expense("Nova sede", 20000));

        for (Expense expense : expenses) {
            manager.handleRequest(expense);
            System.out.println();
        }
    }
}

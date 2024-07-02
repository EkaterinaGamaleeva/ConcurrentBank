public class Account {
    private int id;

    private int deposit;

    public Account(int id, int deposit) {
        this.deposit = deposit;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public synchronized void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getDeposit() {
        return deposit;
    }
}

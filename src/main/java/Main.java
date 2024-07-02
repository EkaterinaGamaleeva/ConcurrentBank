public class Main {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentBank concurrentBank = new ConcurrentBank();
        Account account = concurrentBank.createAccount(1000);
        Account account1 = concurrentBank.createAccount(100);
        Account account2 = concurrentBank.createAccount(200);
        System.out.println(account.getDeposit());
        System.out.println(account1.getDeposit());
        System.out.println(account2.getDeposit());
        Thread transferThread1 = new Thread(() -> {
            try {
                concurrentBank.transfer(account, account1, 400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        Thread transferThread2 = new Thread(() -> {
            try {
                concurrentBank.transfer(account1, account2, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        Thread transferThread3 = new Thread(() -> {
            try {
                concurrentBank.transfer(account2, account, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        transferThread1.start();
        transferThread2.start();
        transferThread1.join();
        transferThread2.join();
        // Вывод общего баланса
        System.out.println("\n");
        System.out.println(account.getDeposit());
        System.out.println(account1.getDeposit());
        System.out.println(account2.getDeposit());
        System.out.println("Total balance: " + concurrentBank.getTotalBalance());
    }
}


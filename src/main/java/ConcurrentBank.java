import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentBank  extends Thread {
    private int id;
    //private Condition condition;
    private Lock bankLock ;
    private List<Account> concurrentBankList = new ArrayList<>();

    public ConcurrentBank() {
    //    condition = bankLock.newCondition();
    }

    public synchronized Account createAccount(int amount) {
        bankLock = new ReentrantLock();
        Account account = new Account(id, amount);
        concurrentBankList.add(account);
        id++;
        return account;
    }

    public synchronized List<Account> getConcurrentBankList() {
        return concurrentBankList;
    }

    public  int getTotalBalance() {
        bankLock.lock();
        try {
            return concurrentBankList.stream().map(e -> e.getDeposit()).reduce((a, v) -> a + v).get();
        }
        finally {
            bankLock.unlock();
        }
        }

    public long getId() {
        return id;
    }

    public synchronized int getDeposit() {
        return concurrentBankList.get(id).getDeposit();
    }

    public void deposit(Account account, int amount) {
        bankLock.lock();
        try {
            account.setDeposit(account.getDeposit() + amount);
        }
    finally {
            bankLock.unlock();
        }
    }

    public void withdraw(Account account, int amount) throws InterruptedException {
        bankLock.lock();
        try {
//            while (account.getDeposit() < amount) {
//                condition.await();
//            }
            if (account.getDeposit()<amount){
                System.out.println("Не достаточно средств");
            }
            else {
                System.out.print(Thread.currentThread());
                account.setDeposit(account.getDeposit() - amount);
//                condition.signalAll();
        }}
        finally {
            bankLock.unlock();
        }
    }

    public void transfer(Account account1, Account account2, int amount) throws InterruptedException {
        bankLock.lock();
        try {
            if (account1.getDeposit()<amount){
                System.out.println("Не достаточно средств");
            }
//            while (account1.getDeposit()<amount) {
//                condition.await();
//            }
//            account2.setDeposit(account2.getDeposit() + amount);
           else {
                deposit(account2, amount);
                withdraw(account1, amount);
            }
    //        condition.signalAll();
//           account1.setDeposit(account1.getDeposit() - amount);
        }
        finally {
            bankLock.unlock();
        }
    }
}

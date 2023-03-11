package Entity;

import Interfaces.IAccount;
import Models.CreateTransaction;
import Models.TransactionStatus;
import Models.TransactionType;
import Services.TransactionService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;

public abstract class Account implements IAccount {
    @DocumentId
    private String Id;

    @PropertyName("userId")
    private String userId;

    @PropertyName("localTransferLimit")
    private double localTransferLimit;

    @PropertyName("internationalTransferLimit")
    private double internationalTransferLimit;

    @PropertyName("atmWithdrawalLimit")
    private double atmWithdrawalLimit;

    @PropertyName("pinNo")
    private String pinNo;

    private final TransactionService transactionService = new TransactionService();

    public String getId() {
        return Id;
    }

    public String getUserId() {
        return userId;
    }

    public double getLocalTransferLimit() {
        return localTransferLimit;
    }

    public double getInternationalTransferLimit() {
        return internationalTransferLimit;
    }

    // TODO: Develop algorithm to determine if accountNo is international
    // TODO: International Transfer should always be pending.
    public void InternationalTransfer(double amount, String accountNo)
    {
    }

    //TODO: Reminder that there will be 2 transactions inserted.
    //TODO: Both transferee and transferor should have transaction logs.
    //TODO: Check for valid accountNo
    public void Transfer(double amount, String accountNo)
    {
        try {
            CreateTransaction transferorTransaction = new CreateTransaction(amount,
                    "SGD",
                    TransactionType.InternalTransfer,
                    TransactionStatus.Completed,
                    this.Id);

            CreateTransaction transfereeTransaction = new CreateTransaction(amount,
                    "SGD",
                    TransactionType.InternalTransfer,
                    TransactionStatus.Completed,
                    accountNo);

            //Set respective to/from
            transferorTransaction.setTo(accountNo);
            transfereeTransaction.setFrom(this.Id);


            transactionService.createTransaction(transferorTransaction);
            transactionService.createTransaction(transfereeTransaction);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getPinNo() {
        return pinNo;
    }

    //TODO : Check
    public void Deposit(double amount)
    {
        try {
            CreateTransaction transaction = new CreateTransaction(amount,
                    "SGD",
                    TransactionType.Deposit,
                    TransactionStatus.Completed,
                    this.Id);

            transactionService.createTransaction(transaction);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //TODO: Check
    public void Withdraw(double amount)
    {
        try {
            CreateTransaction transaction = new CreateTransaction(
                    amount,
                    "SGD",
                    TransactionType.Withdrawal,
                    TransactionStatus.Completed,
                    this.Id);

            transactionService.createTransaction(transaction);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Transaction> getTransactions()
    {
        return transactionService.getTransactionsByAccountId(this.Id);
    }

    @Override
    public double getAvailableBalance() {
        List<Transaction> transactions = getTransactions();
        double pendingBalance = 0;
        double availableBalance = 0;

        for (Transaction transaction: transactions) {
            TransactionType transactionType = transaction.getTransactionType();
            TransactionStatus transactionStatus = transaction.getTransactionStatus();

            if (transaction.getTo() != null) {
                // To means deduction
                if (transactionStatus == TransactionStatus.Pending) {
                    pendingBalance -= transaction.getTransactionAmount();
                } else if (transactionStatus == TransactionStatus.Completed) {
                    availableBalance -= transaction.getTransactionAmount();
                }
            } else if (transaction.getFrom() != null) {
                if (transactionStatus == TransactionStatus.Pending) {
                    pendingBalance += transaction.getTransactionAmount();
                } else if (transactionStatus == TransactionStatus.Completed) {
                    availableBalance += transaction.getTransactionAmount();
                }
            } else {
                // Can only be Withdrawal/Deposit
                if (transactionType == TransactionType.Withdrawal) {
                    availableBalance -= transaction.getTransactionAmount();
                } else if (transactionType == TransactionType.Deposit) {
                    availableBalance += transaction.getTransactionAmount();
                }
            }
        }
        // Available balance is defined balance that you can move around. (Liquidity)
        // Pending funds are considered not available (Those are called balance)
        return availableBalance + pendingBalance;
    }

    @Override
    public double getPendingAmount() {
        List<Transaction> transactions = getTransactions();
        double pendingBalance = 0;
        double availableBalance = 0;

        for (Transaction transaction: transactions) {
            TransactionType transactionType = transaction.getTransactionType();
            TransactionStatus transactionStatus = transaction.getTransactionStatus();

            if (transaction.getTo() != null) {
                // To means deduction
                if (transactionStatus == TransactionStatus.Pending) {
                    pendingBalance -= transaction.getTransactionAmount();
                } else if (transactionStatus == TransactionStatus.Completed) {
                    availableBalance -= transaction.getTransactionAmount();
                }
            } else if (transaction.getFrom() != null) {
                if (transactionStatus == TransactionStatus.Pending) {
                    pendingBalance += transaction.getTransactionAmount();
                } else if (transactionStatus == TransactionStatus.Completed) {
                    availableBalance += transaction.getTransactionAmount();
                }
            } else {
                // Can only be Withdrawal/Deposit
                if (transactionType == TransactionType.Withdrawal) {
                    availableBalance -= transaction.getTransactionAmount();
                } else if (transactionType == TransactionType.Deposit) {
                    availableBalance += transaction.getTransactionAmount();
                }
            }
        }
        return pendingBalance;
    }

    @Override
    public double getATMWithdrawalLimit() {
        return this.atmWithdrawalLimit;
    }

    public abstract double getYearlyProjectedInterestRate();
}
package com.rjdxbanking.rjdxbank.Entity;

import com.rjdxbanking.rjdxbank.Exception.InsufficientFundsException;
import com.rjdxbanking.rjdxbank.Interfaces.IAccount;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.rjdxbanking.rjdxbank.Models.CreateTransaction;
import com.rjdxbanking.rjdxbank.Models.TransactionStatus;
import com.rjdxbanking.rjdxbank.Models.TransactionType;
import com.rjdxbanking.rjdxbank.Services.AccountService;
import com.rjdxbanking.rjdxbank.Services.TransactionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PropertyName("accountType")
    private String accountType;

    @PropertyName("accountNumber")
    private String accountNumber;

    // TODO : Hide the rest of the password field maybe?
    public String getaccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    private final TransactionService transactionService = new TransactionService();
    private final AccountService accountService = new AccountService();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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

    @Override
    public double getATMWithdrawalLimit() {
        return this.atmWithdrawalLimit;
    }

    public String getPinNo() {
        return pinNo;
    }

    // TODO: Develop algorithm to determine if accountNo is international
    // TODO: International Transfer should always be pending.
    public void InternationalTransfer(double amount, String accountNo) {
    }

    // This is actually internal transfer. Which means that we should be able to
    // cross-check
    // with the reference in our database.
    // TODO: Reminder that there will be 2 transactions inserted.
    // TODO: Both transferee and transferor should have transaction logs.
    // TODO: Check for valid accountNo
    public void Transfer(double amount, String accountNo) {
        try {
            // Firstly check available balance to see if user has sufficient amount to
            // transfer else throw exception
            // TODO: Custom exception
            if (getAvailableBalance() < amount)
                // TODO: Use custom exceptions
                throw new Exception("Unable to transfer as user does not have sufficient amount to transfer.");
            else if (!accountService.checkAccountExist(accountNo))
                throw new Exception("The account does not exist");
            LocalDateTime now = LocalDateTime.now();
            CreateTransaction transferorTransaction = new CreateTransaction(dtf.format(now), amount,
                    "SGD",
                    TransactionType.LocalTransfer,
                    TransactionStatus.Completed,
                    this.Id);

            CreateTransaction transfereeTransaction = new CreateTransaction(dtf.format(now), amount,
                    "SGD",
                    TransactionType.LocalTransfer,
                    TransactionStatus.Completed,
                    accountNo);

            // Set respective to/from
            transferorTransaction.setTo(accountNo);
            transfereeTransaction.setFrom(this.Id);

            transactionService.createTransaction(transferorTransaction);
            transactionService.createTransaction(transfereeTransaction);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Deposit(double amount) {
        LocalDateTime now = LocalDateTime.now();
        try {
            CreateTransaction transaction = new CreateTransaction(dtf.format(now), amount,
                    "SGD",
                    TransactionType.Deposit,
                    TransactionStatus.Completed,
                    this.Id);

            transactionService.createTransaction(transaction);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Withdraw(double amount) throws InsufficientFundsException {
        LocalDateTime now = LocalDateTime.now();
        try {
            CreateTransaction transaction = new CreateTransaction(dtf.format(now),
                    amount,
                    "SGD",
                    TransactionType.Withdrawal,
                    TransactionStatus.Completed,
                    this.Id);

            if (this.getAvailableBalance() < amount) {
                throw new InsufficientFundsException("Insufficient funds in account");
            } else {
                transactionService.createTransaction(transaction);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new InsufficientFundsException(ex.getMessage());
        }
    }

    public List<Transaction> getTransactions() {
        return transactionService.getTransactionsByAccountId(this.Id);
    }

    public List<Transaction> getTransactionsToday(TransactionType type) {
        return transactionService.getWithdrawTransactionsToday(this.Id, type);
    }

    @Override
    public double getAvailableBalance() {
        List<Transaction> transactions = getTransactions();
        double pendingBalance = 0;
        double availableBalance = 0;

        for (Transaction transaction : transactions) {
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

        for (Transaction transaction : transactions) {
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

    public double getCurrentLimit(TransactionType type) {
        double limit = 0;
        List<Transaction> transactions = getTransactionsToday(type);
        switch (type) {
            case Withdrawal:
                limit = this.atmWithdrawalLimit;
            case LocalTransfer:
                limit = this.localTransferLimit;
            case OverseasTransfer:
                limit = this.internationalTransferLimit;
        }
        for (Transaction transaction : transactions) {
            limit -= transaction.getTransactionAmount();
        }

        return limit;
    }

    public abstract double getYearlyProjectedInterestRate();

}

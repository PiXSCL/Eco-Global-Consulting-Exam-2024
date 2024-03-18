package ExamNumber3FX_Trade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FX_Trade {

    // Method to compute FX transactions
    public void compute(String currencyFile, String transactionFile, String outputFile, String txnDate) {
        try {
            // Convert txnDate to the format "dd/MM/yyyy"
            String formattedTxnDate = convertDateFormat(txnDate);

            // Read currency data
            Map<String, Currency> currencies = readCurrencyData(currencyFile);

            // Read transaction data for the given txnDate
            Map<String, Double> transactions = readTransactionData(transactionFile, formattedTxnDate, currencies);

            // Compute total FX_SELL and FX_BUY in PHP
            double totalFXSell = computeTotalFXSell(transactions, currencies);
            double totalFXBuy = computeTotalFXBuy(transactions, currencies);

            // Write the computed totals to the output file
            writeOutput(outputFile, totalFXSell, totalFXBuy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to convert date format
    private String convertDateFormat(String txnDate) {
        String[] parts = txnDate.split("[-/]");
        if (parts[0].length() == 4) {
            // Date is in yyyy-MM-dd format, convert it to dd/MM/yyyy
            return parts[2] + "/" + parts[1] + "/" + parts[0];
        } else {
            // Date is in dd-MM-yyyy format, return it as is
            return parts[0] + "/" + parts[1] + "/" + parts[2];
        }
    }

    // Method to read currency data from file
    private Map<String, Currency> readCurrencyData(String currencyFile) throws IOException {
        Map<String, Currency> currencies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(currencyFile))) {
            String line;
            // Skip the header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    // Print out the problematic line for debugging
                    System.err.println("Invalid line: " + line);
                    continue; // Skip this line and continue with the next one
                }
                String code = parts[1];
                int decimalPlaces = Integer.parseInt(parts[3]);
                double rateVsPHP = Double.parseDouble(parts[4]);
                currencies.put(code, new Currency(decimalPlaces, rateVsPHP));
            }
        }
        return currencies;
    }

    // Method to read transaction data from file
    private Map<String, Double> readTransactionData(String transactionFile, String txnDate, Map<String, Currency> currencies) throws IOException {
        Map<String, Double> transactions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            // Skip the header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String date = convertDateFormat(parts[3]);
                    if (date.equals(txnDate)) {
                        String ccy = parts[1];
                        String txnCode = parts[2];
                        double amount = Double.parseDouble(parts[4]);
                        double convertedAmount = convertCurrency(amount, ccy, currencies);
                        transactions.put(txnCode, transactions.getOrDefault(txnCode, 0.0) + convertedAmount);
                    }
                }
            }
        }
        return transactions;
    }

    // Method to compute total FX sell transactions
    private double computeTotalFXSell(Map<String, Double> transactions, Map<String, Currency> currencies) {
        double totalFXSell = 0;
        for (Map.Entry<String, Double> entry : transactions.entrySet()) {
            String txnCode = entry.getKey();
            double amount = entry.getValue();
            if (txnCode.equals("FX_SELL")) {
                totalFXSell += amount;
            }
        }
        return totalFXSell;
    }

    // Method to compute total FX buy transactions
    private double computeTotalFXBuy(Map<String, Double> transactions, Map<String, Currency> currencies) {
        double totalFXBuy = 0;
        for (Map.Entry<String, Double> entry : transactions.entrySet()) {
            String txnCode = entry.getKey();
            double amount = entry.getValue();
            if (txnCode.equals("FX_BUY")) {
                totalFXBuy += amount;
            }
        }
        return totalFXBuy;
    }

    // Method to write output to file
    private void writeOutput(String outputFile, double totalFXSell, double totalFXBuy) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            DecimalFormat df = new DecimalFormat("#.##"); // Format to two decimal places
            writer.write("FX_SELL=" + df.format(totalFXSell) + "\n");
            writer.write("FX_BUY=" + df.format(totalFXBuy) + "\n");
        }
    }

    // Method to convert currency based on exchange rate
    private double convertCurrency(double amount, String currencyCode, Map<String, Currency> currencies) {
        Currency currency = currencies.get(currencyCode);
        if (currency != null) {
            return amount * currency.getRateVsPHP();
        }
        return 0; // Handle currency not found
    }

    // Currency inner class
    private static class Currency {
        private int decimalPlaces;
        private double rateVsPHP;

        public Currency(int decimalPlaces, double rateVsPHP) {
            this.decimalPlaces = decimalPlaces;
            this.rateVsPHP = rateVsPHP;
        }

        public int getDecimalPlaces() {
            return decimalPlaces;
        }

        public double getRateVsPHP() {
            return rateVsPHP;
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the transaction date (YYYY-MM-DD or DD-MM-YYYY): ");
        String txnDate = scanner.nextLine().trim();

        scanner.close();

        FX_Trade calculator = new FX_Trade();
        String currencyFile = "src\\ExamNumber3FX_Trade\\Currency.csv";
        String transactionFile = "src\\ExamNumber3FX_Trade\\Transaction.csv";
        String outputFile = "src\\ExamNumber3FX_Trade\\outputFile.csv";

        calculator.compute(currencyFile, transactionFile, outputFile, txnDate);

        // Print the contents of the output file
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            System.out.println("Contents of the output file:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


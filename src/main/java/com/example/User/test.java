package com.example.User;



import com.example.User.constant.AppConstants;
import com.example.User.utils.Logger;
import org.apache.commons.codec.binary.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;

public class test {

    private String applyFormatPattern(Double value, String input) {

        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        String amount = "";
        Logger.info("value = " + value);
        Logger.info("input = " + input);
        if (StringUtils.equals("###.###,##", input)) {
            customSymbols.setGroupingSeparator('.');
            customSymbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 1");
        } else if (StringUtils.equals("###.###.##", input)) {
            customSymbols.setGroupingSeparator('.');
            customSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 2");
        } else if (StringUtils.equals("##,##,###.##", input)) {
            if (value < 1000) {
                amount = customFormatAmount("###", value, true);
            } else {
                double hundreds = value % 1000;
                int other = (int) (value / 1000);
                amount = customFormatAmount(",##", other, false) + ','
                        + customFormatAmount("000", hundreds, true);
            }
            Logger.info("if 3");
        } else if (StringUtils.equals("###,###.##", input)) {
            customSymbols.setGroupingSeparator(',');
            customSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 4");
        } else if (StringUtils.equals("### ###,##", input)) {
            customSymbols.setGroupingSeparator(' ');
            customSymbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 5");
        } else if (StringUtils.equals("### ###.##", input)) {
            customSymbols.setGroupingSeparator(' ');
            customSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 6");
        } else if (StringUtils.equals("### ### ##", input)) {
            customSymbols.setGroupingSeparator(' ');
            customSymbols.setDecimalSeparator(' ');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 7");
        } else if (StringUtils.equals("### ###", input)) {
            customSymbols.setGroupingSeparator(' ');
            customSymbols.setDecimalSeparator(' ');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
//            df.setMinimumFractionDigits(0);
//            df.setMaximumFractionDigits(0);
//            long truncatedValue = value.longValue();
            amount = df.format(Math.floor(value));
            Logger.info("if 8");
        } else if (StringUtils.equals("###'###,##", input)) {
            customSymbols.setGroupingSeparator('\'');
            customSymbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 9");
        } else if (StringUtils.equals("###'###.##", input)) {
            customSymbols.setGroupingSeparator('\'');
            customSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            amount = df.format(value);
            Logger.info("if 10");
        } else if (StringUtils.equals("###.###", input)) {
            customSymbols.setGroupingSeparator(' ');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(false);
            df.setGroupingSize(3);
            long newVal= value.longValue();
            amount = df.format(newVal);
            Logger.info("if 11");
        } else if (StringUtils.equals("###,###.###", input)) {
            customSymbols.setGroupingSeparator(',');
            customSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(3);
            amount = df.format(value);
            Logger.info("if 12");
        } else if (StringUtils.equals("### ### ###", input)) {
            customSymbols.setGroupingSeparator(' ');
            customSymbols.setDecimalSeparator(' ');
            DecimalFormat df = new DecimalFormat(AppConstants.DEFAULT_DECIMAL_FORMAT_PATTERN, customSymbols);
            df.setDecimalSeparatorAlwaysShown(true);
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(3);
            df.setMaximumFractionDigits(3);
            amount = df.format(value);
            Logger.info("if 13");
        }
        Logger.info("final amount = " + amount);
        return amount;
    }

    private static String customFormatAmount(String pattern, Object value, Boolean showDecimalSeparator) {
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(',');
        customSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat(pattern, customSymbols);
        df.setDecimalSeparatorAlwaysShown(showDecimalSeparator);
        df.setGroupingSize(2);
        if (showDecimalSeparator) {
            df.setMinimumFractionDigits(2);
        }
        df.setMaximumFractionDigits(2);

        return df.format(value);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        test formatter = new test();

        while (true) {
            // Prompt user for input
            System.out.println("Enter the amount (or '*' to exit): ");
            String amountInput = scanner.nextLine();

            // Exit condition
            if ("*".equals(amountInput)) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            }

            System.out.println("Enter the formatting pattern: ");
            String pattern = scanner.nextLine();

            try {
                // Parse the amount to Double
                Double amount = Double.parseDouble(amountInput);

                // Call the formatting method and display the result
                String formattedValue = formatter.applyFormatPattern(amount, pattern);
                System.out.println("input Value: " + amountInput);
                System.out.println("input pattern: " + pattern);
                System.out.println("Formatted Value: " + formattedValue);

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount! Please enter a valid numeric value.");
            }
        }

        // Close the scanner
        scanner.close();
    }
}

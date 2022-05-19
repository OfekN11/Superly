package Globals.util;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Static functions for human interactions
 */
public class HumanInteraction {
    public static class OperationCancelledException extends Exception {
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void operationCancelled() throws OperationCancelledException {
        System.out.println("Operation Canceled");
        throw new OperationCancelledException();
    }

    public static boolean areYouSure() {
        System.out.println("Are you sure?");
        return yesOrNo();
    }

    public static boolean yesOrNo() {
        int ans = 0;
        while (ans != 1 && ans != 2) {
            System.out.println("1 -- yes\n2 -- no");
            try {
                ans = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer value (1 or 2)");
            } catch (Exception ex) {
                System.out.println("An unexpected error happened. Please try again");
            }
        }
        return ans == 1;
    }

    public static LocalDate buildDate() throws OperationCancelledException {
        boolean success = false;
        int day = 0;
        int month = 0;
        int year = 0;
        while (!success) {
            System.out.println("Enter day");
            try {
                day = Integer.parseInt(scanner.nextLine());
                if (day == -1) {
                    operationCancelled();
                } else if (day < 1 || day > 31) {
                    System.out.println("Enter valid day");
                } else {
                    success = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and 31");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        success = false;
        while (!success) {
            System.out.println("Enter month");
            try {
                month = Integer.parseInt(scanner.nextLine());
                if (month == -1) {
                    operationCancelled();
                } else if (month < 1 || month > 12) {
                    System.out.println("Enter valid month");
                } else {
                    success = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and 12");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        success = false;
        while (!success) {
            System.out.println("Enter year");
            try {
                year = Integer.parseInt(scanner.nextLine());
                if (year == -1) {
                    operationCancelled();
                } else if (year < 1900 || year > 2030) {
                    System.out.println("Enter valid year");
                } else {
                    success = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1900 and 2030");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }

        return LocalDate.of(year, month, day);
    }

    public static int getNumber() throws OperationCancelledException {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number == -1) {
                    operationCancelled();
                } else {
                    return number;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }

    public static int getNumber(int min) throws OperationCancelledException {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number == -1) {
                    operationCancelled();
                } else if (number < min) {
                    System.out.println("Please enter an integer bigger or equal to " + min);
                } else {
                    return number;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }

    public static int getNumber(int min, int max) throws OperationCancelledException {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number == -1) {
                    operationCancelled();
                } else if (number < min || number > max) {
                    System.out.println("Please enter an integer between " + min + " and " + max);
                } else {
                    return number;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }

    public static int getNumber(int min, String because) throws OperationCancelledException {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number == -1) {
                    operationCancelled();
                } else if (number < min) {
                    System.out.println("Please enter an integer bigger or equal to " + min + " because: " + because);
                } else {
                    return number;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer");
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }
}
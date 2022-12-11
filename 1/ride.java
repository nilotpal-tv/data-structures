import java.util.Scanner;

class ReadySteadyGo {
  public static boolean checkIfValueEqual(String str1, String str2) {
    int firstTotal = 1, secondTotal = 1;

    for (int i = 0; i < str1.length(); i++) {
      firstTotal = firstTotal * ((int) str1.charAt(i) - 64);
    }

    for (int i = 0; i < str2.length(); i++) {
      secondTotal = secondTotal * ((int) str2.charAt(i) - 64);
    }

    return firstTotal % 47 == secondTotal % 47;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter the first string : ");
    String firstStr = sc.next().toUpperCase();

    System.out.println("Enter the second string : ");
    String secondStr = sc.next().toUpperCase();

    Boolean result = checkIfValueEqual(firstStr, secondStr);
    if (result)
      System.out.println("GO");
    else
      System.out.println("STAY");

    sc.close();
  }
}
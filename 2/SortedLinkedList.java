
import java.util.Scanner;

class LinkedListNode {
  int data;
  int count;
  LinkedListNode next;

  LinkedListNode(int data) {
    this.data = data;
    this.count = 1;
    this.next = null;
  }
}

public class SortedLinkedList {
  public static void printList(LinkedListNode head) {
    if (head == null) {
      System.out.println("\nList is empty.");
      return;
    }

    System.out.print("\nPrinting list -> ");
    LinkedListNode temp = head;
    while (temp != null) {
      System.out.print(temp.data + " (" + temp.count + ") times, ");
      temp = temp.next;
    }
  }

  public static LinkedListNode deleteNode(LinkedListNode head, int data) {
    if (head == null) {
      System.out.println("\nList is empty. You can't delete.");
      return head;
    }

    if (data == head.data) {
      if (head.count > 1) {
        System.out
            .println("\n" + data + " has " + head.count + " occurrences in the list so only one is deleted.");
        head.count--;
      } else {
        System.out.println("\n" + data + " has been removed.");
        head = head.next;
      }
      return head;
    }

    LinkedListNode currentNode = head;
    LinkedListNode prevNode = null;

    while (currentNode != null && data != currentNode.data) {
      prevNode = currentNode;
      currentNode = currentNode.next;
    }

    if (currentNode == null)
      System.out.println("\n" + data + " is not present in the list.");
    else {
      if (currentNode.count > 1) {
        System.out
            .println("\n" + data + " has " + currentNode.count + " occurrences in the list so only one is deleted.");
        currentNode.count--;
      } else {
        prevNode.next = currentNode.next;
        System.out.println("\n" + data + " has been removed.");
      }
    }
    return head;
  }

  public static LinkedListNode insertAscending(LinkedListNode head, int data) {
    LinkedListNode newNode = new LinkedListNode(data);

    if (head == null || data < head.data) {
      newNode.next = head;
      head = newNode;
      return head;
    } else if (data == head.data) {
      head.count++;
      return head;
    } else {
      LinkedListNode currentNode = head;
      LinkedListNode prevNode = null;

      while (currentNode.next != null && data > currentNode.data) {
        prevNode = currentNode;
        currentNode = currentNode.next;
      }

      if (currentNode.next == null) {
        if (data == currentNode.data)
          currentNode.count++;
        else {
          if (currentNode.data > data) {
            prevNode.next = newNode;
            newNode.next = currentNode;
          } else
            currentNode.next = newNode;
        }
      } else {
        System.out.println(prevNode.data);
        if (data == currentNode.data)
          currentNode.count++;
        else {
          prevNode.next = newNode;
          newNode.next = currentNode;
        }
      }
      System.out.println("\n" + data + " has been added to the list.");
      return head;
    }
  }

  public static LinkedListNode insertDescending(LinkedListNode head, int data) {
    LinkedListNode newNode = new LinkedListNode(data);

    if (head == null || data > head.data) {
      newNode.next = head;
      head = newNode;
      return head;
    } else if (data == head.data) {
      head.count++;
      return head;
    } else {
      LinkedListNode currentNode = head;
      LinkedListNode prevNode = null;

      while (currentNode.next != null && data < currentNode.data) {
        prevNode = currentNode;
        currentNode = currentNode.next;
      }

      if (currentNode.next == null) {
        if (data == currentNode.data)
          currentNode.count++;
        else {
          if (currentNode.data > data)
            currentNode.next = newNode;
          else {
            prevNode.next = newNode;
            newNode.next = currentNode;
          }
        }
      } else {
        if (data == currentNode.data)
          currentNode.count++;
        else {
          prevNode.next = newNode;
          newNode.next = currentNode;
        }
      }
      System.out.println("\n" + data + " has been added to the list");
      return head;
    }
  }

  static Boolean checkIfExistsCorrectSortDirection(int direction) {
    int[] directions = { 1, -1 };
    for (int i = 0; i < directions.length; i++) {
      if (directions[i] == direction)
        return true;
    }

    return false;
  }

  public static void main(String[] args) {
    int option;
    int sortDirection = 0;
    LinkedListNode head = null;
    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.println("\n\t******************************");
      System.out.println("\t1. Select a sort direction. -1 for descending and 1 for ascending.");
      System.out.println("\t2. Insert item.");
      System.out.println("\t3. Delete item.");
      System.out.println("\t4. Print list.");
      System.out.println("\n\tTip: You need to choose a sort direction before inserting.");
      System.out.println("\t******************************");

      System.out.print("\nEnter your choice : ");
      option = sc.nextInt();

      if (option == 1) {
        if (checkIfExistsCorrectSortDirection(sortDirection)) {
          System.out.println("Can't set sort direction more than once.");
          continue;
        }

        System.out.print("\nEnter your sort direction : ");
        sortDirection = sc.nextInt();

        if (!checkIfExistsCorrectSortDirection(sortDirection)) {
          System.out
              .println(
                  "\n\tOnly -1 and 1 are allowed where -1 represents descending and 1 represents ascending order.");
        }
      } else if (option == 2) {
        if (!checkIfExistsCorrectSortDirection(sortDirection)) {
          System.out.println("\n\tSelect a sort valid direction first.");
          continue;
        }

        System.out.print("\nEnter value you want to insert : ");
        int val = sc.nextInt();

        head = sortDirection == 1 ? insertAscending(head, val) : insertDescending(head, val);
      } else if (option == 3) {
        System.out.print("\nEnter the value you want to delete : ");
        int val = sc.nextInt();

        head = deleteNode(head, val);
      } else if (option == 4)
        printList(head);
      else
        break;
    }

    sc.close();
  }
}
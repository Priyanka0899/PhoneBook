import java.util.*;

class ContactNode {
    List<String> name;
    List<String> mobile;
    List<String> email;
    ContactNode next, pre;

    ContactNode(List<String> name, List<String> mobile, List<String> email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        next = pre = null;
    }
}

class Contact {
    private ContactNode head = null, tail = null;

    private void sort(ContactNode newContact) {
        if (head != null) {
            ContactNode temp = head;
            String newNodeName = newContact.name.get(0).toLowerCase();

            while (temp != null) {
                String oldNodeName = temp.name.get(0).toLowerCase();
                int res = newNodeName.compareTo(oldNodeName);

                if (res >= 0 && temp.next != null) {
                    temp = temp.next;
                } else if (res <= 0 && temp.pre == null) {
                    newContact.next = temp;
                    newContact.pre = temp.pre;
                    temp.pre = newContact;
                    head = newContact;
                    break;
                } else if (res <= 0) {
                    newContact.next = temp;
                    newContact.pre = temp.pre;
                    temp.pre.next = newContact;
                    temp.pre = newContact;
                    break;
                } else if (temp.next == null) {
                    tail.next = newContact;
                    newContact.pre = tail;
                    tail = tail.next;
                    break;
                }
            }
        } else {
            head = newContact;
            tail = head;
        }
    }

    private void display(List<ContactNode> temp, String s) {
        System.out.println("|-----------------------------------|");
        System.out.println("|\t" + s + "\t\t|");
        System.out.println("|-----------------------------------|");
        System.out.println("Contacts -> \t\t" + temp.size() + " found");

        for (int i = 0; i < temp.size(); ++i) {
            System.out.print(i + " : " + temp.get(i).name.get(0) + ' ' + temp.get(i).name.get(1) + "\t");
            for (String mobile : temp.get(i).mobile) {
                System.out.print("+91 " + mobile + "\n");
            }
            System.out.print("\t");
            for (String email : temp.get(i).email) {
                System.out.print(email + "\n");
            }
            System.out.print('\n');
        }
    }

    protected void createNew() {
        Scanner sc = new Scanner(System.in);
        List<String> name = new ArrayList<>();
        List<String> mobile = new ArrayList<>();
        List<String> email = new ArrayList<>();

        System.out.print("First Name : ");
        String firstName = sc.next();
        System.out.print("Last Name : ");
        String lastName = sc.next();
        name.add(firstName);
        name.add(lastName);

        System.out.print("Phone : ");
        String phone = sc.next();
        while (phone.length() != 10) {
            System.out.print("\nEnter Correct Phone Number: ");
            phone = sc.next();
        }
        mobile.add(phone);

        System.out.print("Email : ");
        String eMail = sc.next();
        email.add(eMail);

        ContactNode newContact = new ContactNode(name, mobile, email);
        sort(newContact);
        System.out.println("\n\n------Contact Saved Successfully------\n\n");
    }

    protected void deleteContact() {
        List<ContactNode> searchResult = search();
        if (searchResult.isEmpty()) return;

        Scanner sc = new Scanner(System.in);
        System.out.print("\n\nEnter Your Index to delete : ");
        int index = sc.nextInt();
        ContactNode temp = searchResult.get(index);

        if (temp == head && temp.next == null) {
            head = null;
            tail = null;
        } else if (temp == head) {
            temp.next.pre = null;
            head = head.next;
        } else if (temp.next == null) {
            temp.pre.next = null;
            tail = temp.pre;
        } else {
            temp.pre.next = temp.next;
            temp.next.pre = temp.pre;
        }

        System.out.println("-------Deletion successful-------\n");
    }

    protected List<ContactNode> search() {
        Scanner sc = new Scanner(System.in);
        ContactNode temp = head;
        String s;
        boolean flag = true;
        List<ContactNode> searchResult = new ArrayList<>();

        System.out.print("Enter detail for search : ");
        s = sc.next();
        String searchName = s.toLowerCase();

        while (temp != null) {
            String firstName = temp.name.get(0).toLowerCase();
            String lastName = temp.name.get(1).toLowerCase();
            String email = temp.email.get(0).toLowerCase();

            if (firstName.equals(searchName) || lastName.equals(searchName) || temp.mobile.get(0).equals(searchName) || email.equals(searchName)) {
                searchResult.add(temp);
            }
            temp = temp.next;
        }

        if (searchResult.isEmpty()) {
            System.out.println("Contact not found!!!\n");
        } else {
            display(searchResult, searchName);
        }

        return searchResult;
    }

    protected void view() {
        ContactNode temp = head;
        if (head == null) {
            System.out.println("\n\nNo Contact Found!!!\n");
            return;
        }

        System.out.println();
        int c = 1;
        while (temp != null) {
            System.out.print(c + ". " + temp.name.get(0) + ' ' + temp.name.get(1) + "\t");
            for (String mobile : temp.mobile) {
                System.out.print("+91 " + mobile + "\t");
            }
            System.out.print("\t");
            for (String email : temp.email) {
                System.out.print(email + "\n");
            }
            temp = temp.next;
            c++;
            System.out.print('\n');
        }
    }

    protected void update() {
        List<ContactNode> searchResult = search();
        if (searchResult.isEmpty()) return;

        Scanner sc = new Scanner(System.in);
        System.out.print("\n\nEnter Your Index : ");
        int index = sc.nextInt();
        ContactNode temp = searchResult.get(index);

        if (temp != null) {
            String n, m, e;
            List<String> mobile = new ArrayList<>();
            List<String> email = new ArrayList<>();
            char c;

            System.out.print("\nDo you want to update First name? (y/n): ");
            c = sc.next().charAt(0);
            if (c == 'y' || c == 'Y') {
                System.out.print("\nEnter the name for updation: ");
                n = sc.next();
                temp.name.set(0, n);
            }

            System.out.print("\nDo you want to update Last name? (y/n): ");
            c = sc.next().charAt(0);
            if (c == 'y' || c == 'Y') {
                System.out.print("\nEnter the name for updation: ");
                n = sc.next();
                temp.name.set(1, n);
            }

            System.out.print("\nDo you want to update contact number? (y/n): ");
            c = sc.next().charAt(0);
            if (c == 'y' || c == 'Y') {
                System.out.print("Enter the contact number: ");
                m = sc.next();
                mobile.add(m);
                temp.mobile.set(0, m);
            }

            System.out.print("\nDo you want to update email? (y/n): ");
            c = sc.next().charAt(0);
            if (c == 'y' || c == 'Y') {
                System.out.print("Enter the e-mail id: ");
                e = sc.next();
                email.add(e);
                temp.email.set(0, e);
            }

            System.out.println("\n------Updation Successful------\n");
        }

        if (head == temp && temp.next != null) {
            head = head.next;
            temp.next.pre = null;
            temp.next = null;
        } else if (head == temp) {
            tail = head;
        } else if (temp.next != null) {
            temp.pre.next = temp.next;
            temp.next.pre = temp.pre;
            temp.pre = null;
            temp.next = null;
        } else {
            temp.pre.next = temp.next;
            tail = temp.pre;
            temp.pre = null;
        }
        sort(temp);
    }

    protected void deleteAllContacts() {
        while (head != null) {
            ContactNode temp = head;
            head = head.next;
        }
        System.out.println("\n\n------------Deleted All Contacts Successfully----------\n\n");
    }
}

class Choice extends Contact {
    private void menu() {
        System.out.println(" ____________________________");
        System.out.println("|1. Create New Contact       |");
        System.out.println("|2. Update Existing Contact  |");
        System.out.println("|3. Delete Contact           |");
        System.out.println("|4. View All Contacts        |");
        System.out.println("|5. Search Contact           |");
        System.out.println("|6. Delete All Contacts      |");
        System.out.println("|7. Exit                     |");
        System.out.println("|____________________________|");
    }

    public void chooseChoice() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            menu();
            System.out.print("\nEnter Your Choice : ");
            char choice = sc.next().charAt(0);
            switch (choice) {
                case '1':
                    createNew();
                    break;
                case '2':
                    update();
                    break;
                case '3':
                    deleteContact();
                    break;
                case '4':
                    view();
                    break;
                case '5':
                    search();
                    break;
                case '6':
                    deleteAllContacts();
                    break;
                case '7':
                    System.out.println("\n\n\tThank You!!!\n\n");
                    System.exit(0);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Choice ob = new Choice();
        ob.chooseChoice();
    }
}

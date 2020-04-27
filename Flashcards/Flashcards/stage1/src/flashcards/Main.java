package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        String term, definition, answer, cardNumber;

        Scanner scanner = new Scanner(System.in);

        // for saved key = term and value = definition
        Map<String, String> linkedList = new LinkedHashMap<>();
        Map<String, String> hashList = new HashMap<>();

        System.out.println("Input the number of card: ");
        cardNumber = scanner.nextLine();

        int range = Integer.parseInt(cardNumber);
        for (int i = 1; i <= range; i++) {


            System.out.printf("The card #%d:%n", i);
            while (true) {
                term = scanner.nextLine();
                if (linkedList.containsKey(term)) {
                    System.out.printf("The card \"%s\" already exists. Try again:%n", term);
                    continue;
                }
                System.out.printf("The definition of the card #%d:%n", i);
                break;
            }
            while (true) {

                definition = scanner.nextLine();
                if (linkedList.containsValue(definition)) {
                    System.out.printf("The definition \"%s\" already exists. Try again:%n", definition);
                    continue;
                }
                break;
            }
            linkedList.put(term, definition);
            hashList.put(definition, term);

        }
        for (String key : linkedList.keySet()) {
            System.out.printf("Print the definition of \"%s\":%n", key);
            answer = scanner.nextLine();
            if (answer.equals(linkedList.get(key))) {
                System.out.println("Correct answer.");
            } else if (linkedList.containsValue(answer)) {
                System.out.printf("Wrong answer. The correct one is \"%s\", " +
                        "you've just written the definition of \"%s\"%n", linkedList.get(key), hashList.get(answer));
            } else {
                System.out.printf("Wrong answer. The correct one is \"%s\".%n", linkedList.get(key));
            }

        }

    }
}




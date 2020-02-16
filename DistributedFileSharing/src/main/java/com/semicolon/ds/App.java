package com.semicolon.ds;

import com.semicolon.ds.core.GNode;

import java.util.*;

/**
 * Main App
 *
 */
public class App 
{
    public static void main(String[] args) {
        try {
            String uniqueID = UUID.randomUUID().toString();
            GNode node = new GNode("node" + uniqueID);
            node.init();

            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.println("\nChoose what do you want to do below : ");
                System.out.println("1) Do a search");
                System.out.println("2) Print the routing table");
                System.out.println("3) Exit the network");

                System.out.println("\nPlease enter the option : ");

                String commandOption = scanner.nextLine();

                if (commandOption.equals("1")){
                    System.out.println("\nEnter your search query below : ");
                    String searchQuery = scanner.nextLine();

                    if (searchQuery != null && !searchQuery.equals("")){
                        int results = node.doSearch(searchQuery);

                        if (results != 0){

                            while(true){

                                try{
                                    System.out.println("\nPlease choose the file you need to download : ");
                                    String fileOption = scanner.nextLine();

                                    int option = Integer.parseInt(fileOption);

                                    if (option > results){
                                        System.out.println("Please give an option within the search results...");
                                        continue;
                                    }

                                    node.getFile(option);
                                    break;

                                } catch (NumberFormatException e){
                                    System.out.println("Enter a valid integer indicating " +
                                            "the file option shown above in the results...");
                                }
                            }
                        }

                    } else {
                        System.out.println("Please give a valid search query!!!");
                    }
                } else if (commandOption.equals("2")){
                    node.printRoutingTable();

                } else if (commandOption.equals("3")){
                    node.unRegister();
                    System.exit(0);

                } else {
                    System.out.println("Please enter a valid option...");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

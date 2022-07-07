package com.chen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    enum Move {ROCK, PAPER, SCISSORS}

    public static Scanner scan = new Scanner(System.in);

    public static void printHeader() {
        System.out.println(ASCIIText.welcome);
        System.out.println(ASCIIText.rockPaperScissors);
    }

    public static boolean start(){
        System.out.println("Enter 'S' to start!");
        String input = scan.next();
        if (!input.equalsIgnoreCase("s")) {
            System.out.println("Wrong key pressed, exiting now");
            return false;
        }
        return true;
    }

    public static int totalWins(){
        System.out.println("\nHow many wins to win the game?");
        if(!scan.hasNextInt()){
            System.out.println("Not an integer, exiting now");
            return 0;
        }
        return scan.nextInt();
    }

    public static String getPlayerMove() {
        System.out.print("Enter your move(rock, paper or scissors): ");
        String input = scan.next();

        List<String> moveList = Arrays.stream(Move.values()).map(Enum::toString).collect(Collectors.toList());

        if(!moveList.contains(input.toUpperCase())) {
            System.out.println("Input not recognized, enter 'Y' to try again, 'Q' to quit the game.");
            String input2 = scan.next();
            if(!input2.equalsIgnoreCase("y")){
                System.out.println("Exiting the game...");
                scan.close();
                System.exit(0);
            }
            return getPlayerMove();
        }
        return input;
    }

    public static String getComputerMove() {
        String computerMove;
        Random random = new Random();
        int input = random.nextInt(3) + 1;
        if (input == 1)
            computerMove = Move.ROCK.toString();
        else if (input == 2)
            computerMove = Move.PAPER.toString();
        else
            computerMove = Move.SCISSORS.toString();

        return computerMove;
    }

    public static int result(String playerMove, String computerMove) {
        // if both playerMove and computerMove produces the same formation, then Game is a tie
        // 0 means tie, 2 means computer wins, 1 means player wins
        if (playerMove.equalsIgnoreCase(computerMove))
            return 0;
            // if playerMove is ROCK
        else if (playerMove.equalsIgnoreCase(Move.ROCK.toString()))
            return (computerMove.equals(Move.PAPER.toString()) ? 2 : 1);
            // if playerMove is PAPER
        else if (playerMove.equalsIgnoreCase(Move.PAPER.toString()))
            return (computerMove.equals(Move.SCISSORS.toString()) ? 2 : 1);
            // if playerMove is SCISSORS
        else
            return (computerMove.equalsIgnoreCase(Move.ROCK.toString()) ? 2 : 1);
    }

    public static void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startNewGame(){
        System.out.println("Do you want to start a new game?('Y' for yes, 'N' for no)");
        String input = scan.next();
        if(!input.equalsIgnoreCase("y")){
            scan.close();
            System.out.println("See you soon!");
            sleep();
            System.out.println("Exiting the game...");
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        while(true){
            printHeader();

            boolean startResult = start();
            if(!startResult) return;

            int wins = totalWins();
            if(wins<=0) System.exit(1);

            for (int i = 1, player = 0, computer = 0; i <= (wins * 2 - 1); i++) {
                System.out.printf("\nTotal Rounds: %d; Current Round: %d; Player Wins: %d; Computer Wins: %d\n",
                        (wins * 2 - 1), i, player, computer);

                String playerMove = getPlayerMove();
                String computerMove = getComputerMove();

                System.out.printf("\nYour move is: %s\n", playerMove);
                sleep();
                System.out.printf("Computer's move is: %s\n", computerMove);
                sleep();

                int currentRoundResult = result(playerMove, computerMove);
                switch (currentRoundResult) {
                    case 0:
                        i--;
                        System.out.println("It's a tie, nobody gets point.\n");
                        break;
                    case 1:
                        player++;
                        System.out.println("You won this round.\n");
                        break;
                    case 2:
                        computer++;
                        System.out.println("Computer won this round.\n");
                        break;
                }
                sleep();

                if (player == wins) {
                    System.out.printf("\n%s\n", ASCIIText.congratulations);
                    break;
                }
                if (computer == wins) {
                    System.out.printf("\n%s\n",ASCIIText.sorry);
                    break;
                }
            }
            startNewGame();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }
}

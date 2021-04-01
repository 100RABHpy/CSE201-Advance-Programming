
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Game newGame = new Game();

        newGame.startGame();
    }
}

class Game {
    private Console console;
    private static final Random rand = new Random();
    private ArrayList<Integer> arr;
    private User<?> user;

    public void startGame() {
        System.out.println("Welcome to Mafia");
        Scanner in = new Scanner(System.in);
        int correctInput = 1;
        int n = 0;

        while (correctInput == 1) {
            System.out.print("Enter Number of players: ");
            try {
                n = Integer.parseInt(in.nextLine());
                if (n >= 6) {
                    correctInput = 0;
                } else {
                    System.out.println("No of Player must be greater than or equal to 6");
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong Input. Give Correct Input");
            }
        }
        arr = new ArrayList<Integer>(n);
        int noOfMafia = n / 5;
        int noOfDetective = n / 5;
        int noOfHealer = Math.max(1, n / 10);
        int noOfCommoner = n - noOfDetective - noOfHealer - noOfMafia;
        ArrayList<Player> allPlayers = new ArrayList<Player>(n);
        ArrayList<Player> mafias = new ArrayList<Player>(noOfMafia);
        ArrayList<Player> detectives = new ArrayList<Player>(noOfDetective);
        ArrayList<Player> healers = new ArrayList<Player>(noOfHealer);
        ArrayList<Player> commoners = new ArrayList<Player>(noOfCommoner);

        for (int i = 0; i < noOfMafia; i++) {

            Player temp = new Mafia(IDGenerator(n));
            mafias.add(temp);
            allPlayers.add(temp);

        }

        for (int i = 0; i < noOfDetective; i++) {
            Player temp = new Detective(IDGenerator(n));
            detectives.add(temp);
            allPlayers.add(temp);

        }

        for (int i = 0; i < noOfHealer; i++) {
            Player temp = new Healer(IDGenerator(n));
            healers.add(temp);
            allPlayers.add(temp);

        }

        for (int i = 0; i < noOfCommoner; i++) {
            Player temp = new Commoner(IDGenerator(n));
            allPlayers.add(temp);
            commoners.add(temp);

        }
        Mafia.setAllMafias(mafias);
        Detective.setAllDetectives(detectives);
        Healer.setAllHealer(healers);
        ArrayList<?> copyMafia = (ArrayList<?>) mafias.clone();
        ArrayList<?> copyDetective = (ArrayList<?>) detectives.clone();
        ArrayList<?> copyHealer = (ArrayList<?>) healers.clone();
        ArrayList<?> copyCommoner = (ArrayList<?>) commoners.clone();

        System.out.println("Choose a Character\n1) Mafia\n2) Detective\n3) Healer\n4) Commoner\n5) Assign Randomly");
        int choice = 0;
        correctInput = 1;
        while (correctInput == 1) {

            try {
                choice = Integer.parseInt(in.nextLine());
                if (choice > 5) {
                    System.out.println("Wrong Choice. Choose from above option");
                } else {
                    correctInput = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong Input. Give Correct Input");
            }
        }
        if (choice == 5) {
            choice = rand.nextInt(4) + 1;
        }
        if (choice == 1) {

            user = new User<Mafia>((Mafia) mafias.get(0));
            user.userFunction(mafias);
        } else if (choice == 2) {
            user = new User<Detective>((Detective) detectives.get(0));
            user.userFunction(detectives);
        } else if (choice == 3) {
            user = new User<Healer>((Healer) healers.get(0));
            user.userFunction(healers);
        } else if (choice == 4) {
            user = new User<Commoner>((Commoner) commoners.get(0));
            user.userFunction(commoners);
        }
        Collections.sort(allPlayers, new SortByID());
        console = new Console();
        console.playGame(allPlayers, mafias, detectives, healers, user);
        printDetails(copyMafia);
        printDetails(copyDetective);
        printDetails(copyHealer);
        printDetails(copyCommoner);

    }

    private void printDetails(ArrayList<?> list) {

        for (Object p : list) {
            System.out.print(p);
        }
        System.out.println(" were " + list.get(0).getClass().getSimpleName());
    }

    private int IDGenerator(int n) {
        int ID = 0;
        boolean check = true;

        while (check) {
            ID = rand.nextInt(n) + 1;
            int i;
            for (i = 0; i < arr.size(); i++) {
                if (ID == arr.get(i)) {
                    break;
                }
            }
            if (i >= arr.size()) {
                check = false;
            }
        }
        arr.add(ID);
        return ID;
    }

}

class SortByID implements Comparator<Player> {

    public int compare(Player a, Player b) {
        return (int) (a.getID() - b.getID());
    }
}

class Console {
    private int round = 0;
    private Scanner in = new Scanner(System.in);

    public void playGame(ArrayList<Player> allPlayers, ArrayList<Player> mafias, ArrayList<Player> detectives,
            ArrayList<Player> healers, User<?> user) {

        int noOfMafia = mafias.size();
        int noOfInnocent = allPlayers.size() - noOfMafia;

        if (noOfMafia > 0 && noOfMafia != noOfInnocent) {
            round++;
            String initialMsg = String.format("Round %d\n%d players are remaining: ", round, allPlayers.size());
            System.out.print(initialMsg);
            for (Player p : allPlayers) {
                System.out.print(p);
            }
            System.out.println();
            Player killedByMafia = killing(mafias, allPlayers, user);
            System.out.println("Mafias have chosen their target.");
            boolean correctGuess = false;
            Player guessPlayer = null;
            if (Detective.getTotalAlive() > 0) {
                if (user.isAlive() && user.getUserRole().getClass() == detectives.get(0).getClass()) {

                    guessPlayer = user.userInput(allPlayers);

                } else {
                    guessPlayer = detection(detectives, allPlayers);
                }
                if (guessPlayer.getClass() == mafias.get(0).getClass()) {
                    correctGuess = true;
                }
            }
            System.out.println("Detectives have chosen a player to test.");

            Player healedPlayer = null;
            if (killedByMafia != null) {
                if (Healer.getTotalAlive() > 0) {
                    if (user.isAlive() && user.getUserRole().getClass() == healers.get(0).getClass()) {

                        healedPlayer = user.userInput(allPlayers);
                        healedPlayer.setHP(healedPlayer.getHP() + 500);

                    } else {
                        healedPlayer = healing(allPlayers);

                    }
                    if (healedPlayer.equals(killedByMafia)) {
                        System.out.println("Healers have chosen someone to heal.");
                        System.out.println("No one died.");
                    } else {
                        allPlayers.remove(killedByMafia);

                        if (user.isAlive()) {
                            if (killedByMafia.equals(user.getUserRole())) {
                                user.setAlive(false);
                            }
                        }
                        System.out.println("Healers have chosen someone to heal.");
                        System.out.print(killedByMafia);
                        System.out.println("has died.");
                    }
                } else if (healedPlayer == null) {
                    allPlayers.remove(killedByMafia);
                    if (user.isAlive()) {
                        if (user.getUserRole().equals(killedByMafia)) {
                            user.setAlive(false);
                        }
                    }
                    // killedByMafia.getClass();right now not removing them from individual but
                    // don't forget it may create problems.
                    System.out.println("Healers have chosen someone to heal.");
                    System.out.print(killedByMafia);
                    System.out.println("has died.");
                }
            } else {
                if (Healer.getTotalAlive() > 0) {
                    if (user.isAlive() && user.getUserRole().getClass() == healers.get(0).getClass()) {

                        healedPlayer = user.userInput(allPlayers);
                        healedPlayer.setHP(healedPlayer.getHP() + 500);

                    } else {
                        healedPlayer = healing(allPlayers);

                    }
                    System.out.println("Healers have chosen someone to heal.");
                    System.out.println("No one died.");
                }
            }

            if (correctGuess) {
                if (user.isAlive()) {
                    if (user.getUserRole().equals(guessPlayer)) {
                        user.setAlive(false);
                    }
                }
                allPlayers.remove(guessPlayer);
                mafias.remove(guessPlayer);
                guessPlayer.setHP(0);
                System.out.print(guessPlayer);
                System.out.println("has been voted out");
            } else {
                if (mafias.size() != (allPlayers.size() - mafias.size())) {
                    if (user.isAlive()) {
                        int index = 0;
                        int correctInput = 1;
                        while (correctInput == 1) {
                            try {
                                System.out.print("Select a person to vote out: ");
                                index = Integer.parseInt(in.nextLine());
                                for (Player p : allPlayers) {
                                    if (p.getID() == index) {
                                        correctInput = 0;
                                        break;
                                    }
                                }
                                if (correctInput == 1) {
                                    System.out.println("Choose a alive Player");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Wrong Input. Give Correct Input");
                            }
                        }

                    }
                    int votingPlayerIndex = voting(allPlayers);

                    if (user.isAlive()) {
                        if (user.getUserRole().equals(allPlayers.get(votingPlayerIndex))) {
                            user.setAlive(false);
                        }
                    }
                    System.out.print(allPlayers.get(votingPlayerIndex));
                    System.out.println("has been voted out.");
                    allPlayers.get(votingPlayerIndex).setHP(0);
                    allPlayers.remove(votingPlayerIndex);

                }
            }
            playGame(allPlayers, mafias, detectives, healers, user);
        } else {
            if (noOfMafia <= 0) {
                System.out.println("The  Mafia have lost");
            } else if (noOfMafia == noOfInnocent) {
                System.out.println("Mafia Won");
            }
        }
    }

    private int voting(ArrayList<Player> allPlayers) {

        Integer[] votingCount = new Integer[allPlayers.size()];

        for (int i = 0; i < allPlayers.size(); i++) {
            votingCount[i] = 0;
        }
        for (Player p : allPlayers) {
            votingCount[p.Vote(allPlayers.size())] += 1;
        }
        int maxIndex = 0;
        for (int i = 0; i < allPlayers.size(); i++) {
            if (votingCount[i] > maxIndex) {
                maxIndex = i;
            }
        }

        return maxIndex;

    }

    private Player healing(ArrayList<Player> allPlayers) {
        Player healedPlayer = Healer.heal(allPlayers);
        healedPlayer.setHP(healedPlayer.getHP() + 500);
        return healedPlayer;
    }

    private Player detection(ArrayList<Player> detectives, ArrayList<Player> allPlayer) {
        Player guessPlayer = ((Detective) detectives.get(0)).guessMafia(allPlayer);
        return guessPlayer;

    }

    private Player killing(ArrayList<Player> mafias, ArrayList<Player> allPlayers, User<?> user) {

        Player killedByMafia;
        if (user.isAlive() && user.getUserRole().getClass() == mafias.get(0).getClass()) {

            killedByMafia = user.userInput(allPlayers);

        } else {
            killedByMafia = ((Mafia) mafias.get(0)).kill(allPlayers);
        }

        if (Mafia.getY() > 0 && Mafia.getCombinedHP() > 0.0) {

            if (Mafia.getCombinedHP() >= killedByMafia.getHP()) {
                Collections.sort(mafias, new SortbyHP());
                int Y = Mafia.getY();

                for (Player m : mafias) {

                    double X = killedByMafia.getHP();

                    double damageToMafia = X / Y;
                    if (m.getHP() >= damageToMafia) {
                        Y--;
                        double newMafiaHP = m.getHP() - damageToMafia;

                        m.setKillingHP(newMafiaHP);
                        killedByMafia.setHP(X - damageToMafia);
                    } else {
                        Y--;
                        double newPlayerHP = X - m.getHP();

                        m.setKillingHP(0);

                        killedByMafia.setHP(newPlayerHP);
                        return killing(mafias, allPlayers, user);
                    }
                }
            } else {
                Collections.sort(mafias, new SortbyHP());
                for (Player m : mafias) {

                    double X = killedByMafia.getHP();

                    double newPlayerHP = X - m.getHP();
                    m.setKillingHP(0);
                    killedByMafia.setHP(newPlayerHP);

                    return null;

                }
            }

            return killedByMafia;
        } else {
            return null;
        }
    }

}

class SortbyHP implements Comparator<Player> {

    public int compare(Player a, Player b) {
        return (int) (a.getHP() - b.getHP());
    }
}

abstract class Player {
    private static Random rand = new Random();
    private double HP = 0;
    private final int ID;
    private boolean User = false;

    public Player(double HP, int ID) {
        this.ID = ID;
        this.HP = HP;
    }

    public void setUser(boolean user) {
        User = user;
    }

    public int getID() {
        return ID;
    }

    public double getHP() {
        return HP;

    }

    public void setHP(double hP) {
        HP = hP;
    }

    public Player giveDifferentPlayer(ArrayList<Player> allPlayers) {
        int index = rand.nextInt(allPlayers.size());
        Player differentPlayer = allPlayers.get(index);
        while (getClass() == differentPlayer.getClass()) {
            index = rand.nextInt(allPlayers.size());
            differentPlayer = allPlayers.get(index);
        }
        return differentPlayer;

    }

    public int Vote(int noOfPlayers) {
        int RandomPlayer = rand.nextInt(noOfPlayers);
        while (RandomPlayer == ID) {
            RandomPlayer = rand.nextInt(noOfPlayers);
        }
        return RandomPlayer;
    }

    @Override
    public String toString() {
        String returString = String.format("Player %d ", this.ID);
        return returString;
    }

    // this method is for Mafia
    public void setKillingHP(double newHP) {
    }

}

class Mafia extends Player {
    private static ArrayList<Player> allMafias;
    private static double combinedHP = 0;
    private static int Y = 0;
    private final static double HP = 2500;

    public Mafia(int ID) {
        super(HP, ID);
        Y++;
        combinedHP += HP;

    }

    public static ArrayList<Player> getAllMafias() {
        return allMafias;
    }

    public static void setAllMafias(ArrayList<Player> allMafias) {
        Mafia.allMafias = allMafias;
    }

    public static int getY() {
        return Y;
    }

    public static void setY() {
        Y--;
    }

    public static void setY(boolean check) {
        Y++;
    }

    public static double getCombinedHP() {
        return combinedHP;
    }

    public static void setCombinedHP(double newCombinedHP) {
        combinedHP = newCombinedHP;
    }

    @Override
    public void setHP(double newHP) {
        if (this.getHP() == 0 && newHP > 0) {
            setY(true);
            Mafia.getAllMafias().add(this);
        }
        double hpToBeCombined = this.getHP() - newHP;
        super.setHP(newHP);
        combinedHP -= hpToBeCombined;
        if (newHP == 0) {
            setY();
            Mafia.getAllMafias().remove(this);
        }
    }

    @Override
    public void setKillingHP(double newHP) {

        if (this.getHP() == 0 && newHP > 0) {
            setY(true);

        }
        double hpToBeCombined = this.getHP() - newHP;
        super.setHP(newHP);
        combinedHP -= hpToBeCombined;
        if (newHP == 0) {
            setY();

        }
    }

    public Player kill(ArrayList<Player> allPlayers) {
        Player playerToBeKilled = this.giveDifferentPlayer(allPlayers);
        return playerToBeKilled;
    }

}

class Detective extends Player {
    private static ArrayList<Player> allDetectives;
    private final static double HP = 800;
    private static int totalAlive = 0;

    public Detective(int ID) {
        super(HP, ID);
        totalAlive++;
    }

    public static ArrayList<Player> getAllDetectives() {
        return allDetectives;
    }

    public static void setAllDetectives(ArrayList<Player> allDetectives) {
        Detective.allDetectives = allDetectives;
    }

    public Player guessMafia(ArrayList<Player> allPlayer) {
        Player maybeMafia = this.giveDifferentPlayer(allPlayer);
        return maybeMafia;
    }

    @Override
    public void setHP(double newHP) {

        if (this.getHP() == 0 && newHP > 0) {
            setTotalAlive(true);
            Detective.getAllDetectives().add(this);
        }
        if (newHP == 0) {
            setTotalAlive();
            Detective.getAllDetectives().remove(this);
        }
        super.setHP(newHP);

    }

    private void setTotalAlive(boolean b) {
        Detective.totalAlive++;
    }

    public static int getTotalAlive() {
        return Detective.totalAlive;
    }

    public static void setTotalAlive() {
        Detective.totalAlive--;
    }
}

class Healer extends Player {
    private static Random rand = new Random();
    private final static double HP = 800;
    private static int totalAlive = 0;
    private static ArrayList<Player> allHealer;

    public Healer(int ID) {
        super(HP, ID);
        totalAlive++;
    }

    public static ArrayList<Player> getAllHealer() {
        return allHealer;
    }

    public static void setAllHealer(ArrayList<Player> allHealer) {
        Healer.allHealer = allHealer;
    }

    public static Player heal(ArrayList<Player> allPlayers) {
        int index = rand.nextInt(allPlayers.size());
        Player healedPlayer = allPlayers.get(index);
        return healedPlayer;
    }

    @Override
    public void setHP(double newHP) {

        if (this.getHP() == 0 && newHP > 0) {
            setTotalAlive(true);
            Healer.getAllHealer().add(this);
        }
        if (newHP == 0) {
            setTotalAlive();
            Healer.getAllHealer().add(this);
        }
        super.setHP(newHP);

    }

    private void setTotalAlive(boolean b) {
        Healer.totalAlive++;
    }

    public static int getTotalAlive() {
        return Healer.totalAlive;
    }

    public static void setTotalAlive() {
        Healer.totalAlive--;
    }

}

class Commoner extends Player {
    private final static double HP = 1000;

    public Commoner(int ID) {
        super(HP, ID);

    }

}

class User<T> {
    private final T userRole;
    private Scanner in = new Scanner(System.in);
    private boolean isAlive = true;

    public User(T userRole) {
        this.userRole = userRole;

    }

    public T getUserRole() {
        return userRole;
    }

    public void userFunction(ArrayList<Player> userOtherMember) {
        userOtherMember.get(0).setUser(true);
        System.out.println("Your are " + userOtherMember.get(0));
        System.out.print("Your are " + userOtherMember.get(0).getClass().getSimpleName());
        if (userRole.getClass().getSimpleName() != "Commoner") {
            if (userOtherMember.size() > 1) {
                System.out.print(" other " + userOtherMember.get(0).getClass().getSimpleName() + " are: ");
            }
            for (int i = 1; i < userOtherMember.size(); i++) {
                System.out.print(userOtherMember.get(i));
            }

        }
        System.out.println();
    }

    public Player userInput(ArrayList<Player> allPlayers) {

        int choice = 0;
        int correctInput = 1;
        Player choosedPlayer = null;
        while (correctInput == 1) {

            try {
                System.out.print(userRole.getClass().getSimpleName() + " Make Your Choice: ");
                choice = Integer.parseInt(in.nextLine());
                for (Player p : allPlayers) {
                    if (p.getID() == choice) {
                        choosedPlayer = p;
                    }
                }

                if (choosedPlayer.getClass().getSimpleName() != "Healer"
                        && choosedPlayer.getClass() == userRole.getClass()) {
                    System.out.println("You cannot choose a " + userRole.getClass().getSimpleName());
                } else {
                    correctInput = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong Input. Give Correct Input");
            } catch (NullPointerException e) {
                System.out.println("Wrong Input. Choose from above Player");
            }
        }
        return choosedPlayer;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

}
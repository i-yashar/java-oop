import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Team> teams = new HashMap<>();

        while (true) {
            String[] tokens = scanner.nextLine().split(";");

            if (tokens[0].equals("END"))
                break;

            switch (tokens[0]) {
                case "Team":
                    String teamName = tokens[1];
                    try {
                        Team newTeam = new Team(teamName);

                        teams.putIfAbsent(teamName, newTeam);
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "Add":
                    teamName = tokens[1];
                    String playerName = tokens[2];

                    if (!teams.containsKey(teamName)) {
                        System.out.println("Team " + teamName + " does not exist.");
                        continue;
                    }

                    try {
                        Player newPlayer = new Player(playerName, Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]));
                        teams.get(teamName).addPlayer(newPlayer);
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "Remove":
                    teamName = tokens[1];
                    playerName = tokens[2];

                    try {
                        teams.get(teamName).removePlayer(playerName);
                    } catch (IllegalArgumentException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case "Rating":
                    teamName = tokens[1];

                    if (!teams.containsKey(teamName)) {
                        System.out.println("Team " + teamName + " does not exist.");
                        continue;
                    }

                    System.out.println(teamName + " - " + Math.round(teams.get(teamName).getRating()));
                    break;
            }
        }
    }
}

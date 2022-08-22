import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Player> players;

    public Team(String name){
        setName(name);
        this.players = new ArrayList<>();
    }

    private void setName(String name){
        if (!name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("A name should not be empty.");
        }
    }

    public String getName(){
        return this.name;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(String name){
        if(players.removeIf(player -> player.getName().equals(name)))
            return;

        throw new IllegalArgumentException("Player " + name + " is not in " + this.name + " team.");
    }

    public double getRating(){
        double totalSkill = 0;

        for (Player player : players) {
            totalSkill += player.overallSkillLevel();
        }

        return totalSkill / players.size();
    }
}

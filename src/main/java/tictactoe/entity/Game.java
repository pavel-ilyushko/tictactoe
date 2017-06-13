package tictactoe.entity;

import lombok.Getter;
import lombok.Setter;
import tictactoe.enums.GameStatus;
import tictactoe.enums.Player;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int scale = 3;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private Player player = Player.X;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Turn> turns = new HashSet<>();

    public void addTurn(Turn turn) {
        turn.setPlayer(player);
        turns.add(turn);
        checkForWinner();
        setPlayer(nextPlayer());
    }

    private void checkForWinner() {
        List<List<Player>> matrix = toMatrix();
        List<List<Player>> winningLines = new ArrayList<>();

        // Rows
        winningLines.addAll(matrix);
        // Columns
        winningLines.addAll(columns(matrix));
        // Diagonals
        winningLines.addAll(diagonals(matrix));

        // Check
        for (List<Player> line : winningLines) {
            if (lineWins(line)) {
                setStatus(statusByWinner(line.get(0)));
                return;
            }
        }

        // Or it's DRAW
        if (turns.size() == scale*scale) {
            setStatus(GameStatus.DRAW);
        }
    }

    private boolean lineWins(List<Player> line) {
        Player first = line.get(0);
        return line.stream().allMatch(p -> nonNull(p) && p == first);
    }

    private List<List<Player>> columns(List<List<Player>> matrix) {
        List<List<Player>> columns = new ArrayList<>(scale);
        for (int i = 0; i < scale; i++) {
            List<Player> col = new ArrayList<>(scale);
            for (int j = 0; j < scale; j++) {
                col.add(matrix.get(j).get(i));
            }
            columns.add(col);
        }

        return columns;
    }

    private List<List<Player>> diagonals(List<List<Player>> matrix) {
        List<List<Player>> diagonals = new ArrayList<>(scale);
        List<Player> d1 = new ArrayList<>(scale);
        List<Player> d2 = new ArrayList<>(scale);
        for (int i = 0, j = scale - 1; i < scale; i++, j--) {
            d1.add(matrix.get(i).get(i));
            d2.add(matrix.get(i).get(j));
        }
        diagonals.add(d1);
        diagonals.add(d2);

        return diagonals;
    }

    public boolean inProgress() {
        return status == GameStatus.IN_PROGRESS;
    }

    private Player nextPlayer() {
        if (!inProgress()) {
            return null;
        }

        switch (player) {
            case X:
                return Player.O;
            case O:
                return Player.X;
        }

        throw new IllegalStateException("No Player!");
    }

    private GameStatus statusByWinner(Player winner) {
        switch (winner) {
            case X:
                return GameStatus.X_WON;
            case O:
                return GameStatus.O_WON;
            default:
                return null;
        }
    }

    public List<List<Player>> toMatrix() {
        List<List<Player>> matrix = new ArrayList<>(scale);

        for (int i = 0; i < scale; i++) {
            matrix.add(new ArrayList<>(
                    Collections.nCopies(scale, (Player)null)));
        }

        for (Turn turn: turns) {
            matrix.get(turn.getRow()).set(
                    turn.getCol(), turn.getPlayer());
        }

        return matrix;
    }

    @Override
    public String toString() {
        return "Game '" +
                 name + '\'' +
                ". Scale: " + scale +
                ". Status: " + status +
                ". Next player: " + player;
    }
}

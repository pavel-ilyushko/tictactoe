package tictactoe.entity;

import lombok.Getter;
import lombok.Setter;
import tictactoe.enums.Player;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Turn {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Game game;
    @Enumerated(EnumType.STRING)
    private Player player;
    private int row;
    private int col;
}

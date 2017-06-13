package tictactoe.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tictactoe.entity.Game;
import tictactoe.entity.Turn;
import tictactoe.repository.GameRepository;

@Controller
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("games", gameRepository.findAll());
        return "home";
    }

    @GetMapping("/{id}")
    String game(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameRepository.findOne(id));
        return "game";
    }

    @GetMapping("/new")
    public String gameForm() {
        return "new";
    }

    @PostMapping("/new")
    public String gameSubmit(@ModelAttribute Game game) {
        gameRepository.save(game);
        return "redirect:/";
    }

    @PostMapping("/turn")
    @ResponseBody
    public String turn(@ModelAttribute Turn turn) {
        Game game = turn.getGame();
        game.addTurn(turn);
        gameRepository.save(game);
        return "ok ;)";
    }
}

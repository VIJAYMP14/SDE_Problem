import com.example.game2048.model.Direction;
import com.example.game2048.model.TileBoard;
import com.example.game2048.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/2048")
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService service;

    public GameController(GameService service) { this.service = service; }

    @GetMapping("/board")
    public TileBoard getBoard() { return service.getBoard(); }

    @PostMapping("/move/{direction}")
    public TileBoard move(@PathVariable Direction direction) {
        return service.move(direction);
    }

    @PostMapping("/restart")
    public void restart() { service.restart(); }
}

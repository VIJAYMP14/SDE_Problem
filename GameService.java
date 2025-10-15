

import com.example.game2048.model.TileBoard;
import com.example.game2048.model.Direction;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private TileBoard board = new TileBoard(4);

    public TileBoard getBoard() { return board; }
    public void restart() { board = new TileBoard(4); }
    public TileBoard move(Direction d) { board.move(d); return board; }
}

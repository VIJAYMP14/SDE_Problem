import { Component, OnInit } from '@angular/core';
import { GameService } from './game.service';

@Component({
  selector: 'app-game-board',
  templateUrl: './game-board.component.html',
  styleUrls: ['./game-board.component.css']
})
export class GameBoardComponent implements OnInit {
  board: number[][] = [];
  score = 0;
  gameOver = false;

  constructor(private gameService: GameService) {}

  ngOnInit() {
    this.loadBoard();
    window.addEventListener('keydown', this.handleKey.bind(this));
  }

  loadBoard() {
    this.gameService.getBoard().subscribe(data => {
      this.board = data.board;
      this.score = data.score;
      this.gameOver = data.gameOverFlag;
    });
  }

  move(direction: string) {
    this.gameService.move(direction).subscribe(data => {
      this.board = data.board;
      this.score = data.score;
      this.gameOver = data.gameOverFlag;
      if (this.gameOver) alert('Game over!');
    });
  }

  handleKey(e: KeyboardEvent) {
    const map: any = { ArrowUp: 'UP', ArrowDown: 'DOWN', ArrowLeft: 'LEFT', ArrowRight: 'RIGHT' };
    if (map[e.key] && !this.gameOver) this.move(map[e.key]);
  }

  restart() {
    this.gameService.restart().subscribe(() => this.loadBoard());
  }
}

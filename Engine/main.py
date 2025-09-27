import random
from ai import get_best_move

class Game:
    def __init__(self, n):
        self.n = n
        self.board = [['.' for _ in range(n)] for _ in range(n)]
        self.moves_count = 0
        self.game_over = False
        self.winner = None
        self.current_player = 0
        self.players = []

    def is_valid_move(self, x, y):
        return 0 <= x < self.n and 0 <= y < self.n and self.board[x][y] == '.'
    
    def make_move(self, x, y, color):
        if not self.is_valid_move(x, y):
            return False
        self.board[x][y] = color
        self.moves_count += 1
        
        if self.check_win(x, y, color):
            self.game_over = True
            self.winner = color
        else:
            self.current_player = 1 - self.current_player
        return True
    
    def check_win(self, x, y, color):
        points = set()
        for i in range(self.n):
            for j in range(self.n):
                if self.board[i][j] == color:
                    points.add((i, j))
        
        if len(points) < 4:
            return False

        P = (x, y)
        for Q in points:
            if Q == P:
                continue
            dx = Q[0] - P[0]
            dy = Q[1] - P[1]

            R1 = (P[0] - dy, P[1] + dx)
            S1 = (Q[0] - dy, Q[1] + dx)
            if R1 in points and S1 in points:
                return True

            R2 = (P[0] + dy, P[1] - dx)
            S2 = (Q[0] + dy, Q[1] - dx)
            if R2 in points and S2 in points:
                return True

        return False
    
    def get_player_color(self):
        return self.players[self.current_player][1]

    def get_player_type(self):
        return self.players[self.current_player][0]

    def is_full(self):
        return self.moves_count >= self.n * self.n

    def print_board(self):
        print("  " + " ".join(str(i) for i in range(self.n)))
        for i in range(self.n):
            row = str(i) + " "
            for j in range(self.n):
                row += self.board[i][j] + " "
            print(row)

    def get_ai_move(self):
        from ai import get_best_move
        move = get_best_move(self)
        if move is not None:
            return move

        empty_cells = []
        for i in range(self.n):
            for j in range(self.n):
                if self.board[i][j] == '.':
                    empty_cells.append((i, j))
        if empty_cells:
            return random.choice(empty_cells)
        return None


def main():
    game = None
    print("Square Game. Type HELP for commands.")
    while True:
        try:
            if(game is not None and game.get_player_type() == 'comp'):
                mov = game.get_ai_move()
                command_line = "MOVE " + str(mov[0]) + "," + str(mov[1])
            else:
                command_line = input().strip()
            if not command_line:
                continue

            parts = command_line.split(maxsplit=1)
            cmd = parts[0].upper()
            args = parts[1] if len(parts) > 1 else ""

            if cmd == "GAME":
                if len(parts[1].split()) < 5 or parts[1].count(',') != 2:
                    print("Incorrect command format", parts)
                    continue
                try:
                    args = parts[1].split(',')
                    n = int(args[0].strip())
                    if n <= 2:
                        print("Incorrect command N")
                        continue
                    u1_parts = args[1].strip().split()
                    u2_parts = args[2].strip().split()
                    if len(u1_parts) != 2 or len(u2_parts) != 2:
                        print("Incorrect command 3")
                        continue
                    u1_type, u1_color = u1_parts[0], u1_parts[1]
                    u2_type, u2_color = u2_parts[0], u2_parts[1]
                    if u1_type not in ['user', 'comp'] or u2_type not in ['user', 'comp']:
                        print("Incorrect command 4")
                        continue
                    if u1_color not in ['W', 'B'] or u2_color not in ['W', 'B']:
                        print("Incorrect command 5")
                        continue
                    if u1_color == u2_color:
                        print("Incorrect command 6")
                        continue
                except Exception:
                    print("Incorrect command 7")
                    continue

                game = Game(n)
                game.players = [(u1_type, u1_color), (u2_type, u2_color)]
                print("New game started")

                if game.get_player_type() == 'comp':
                    move = game.get_ai_move()
                    if move:
                        x, y = move
                        color = game.get_player_color()
                        game.make_move(x, y, color)
                        print(f"{color} ({x}, {y})")
                        if game.game_over:
                            if game.winner:
                                print(f"Game finished. {game.winner} wins!")
                            else:
                                print("Game finished. Draw")
                            game = None
                        elif game.is_full():
                            print("Game finished. Draw")
                            game = None

            elif cmd == "MOVE":
                if game is None:
                    print("Game is not started")
                    continue
                if parts[1].count(',') != 1:
                    print("Incorrect command length")
                    continue
                try:
                    coords = parts[1].split(',')
                    x = int(coords[0].strip())
                    y = int(coords[1].strip())
                except Exception:
                    print("Incorrect command")
                    continue

                color = game.get_player_color()
                if not game.make_move(x, y, color):
                    print("Incorrect move")
                    continue

                game.print_board()

                if game.game_over:
                    if game.winner:
                        print(f"Game finished. {game.winner} wins!")
                    else:
                        print("Game finished. Draw")
                    game = None
                elif game.is_full():
                    print("Game finished. Draw")
                    game = None
                else:
                    if game.get_player_type() == 'comp':
                        move = game.get_ai_move()
                        if move:
                            x, y = move
                            color = game.get_player_color()
                            game.make_move(x, y, color)
                            print(f"{color} ({x}, {y})")
                            game.print_board()
                            if game.game_over:
                                if game.winner:
                                    print(f"Game finished. {game.winner} wins!")
                                else:
                                    print("Game finished. Draw")
                                game = None
                            elif game.is_full():
                                print("Game finished. Draw")
                                game = None
            elif cmd == "EXIT":
                print("Goodbye!")
                break
            elif cmd == "HELP":
                print("Available commands: GAME, MOVE, EXIT, HELP")
            else:
                print("Incorrect command")

        except EOFError:
            break
        except KeyboardInterrupt:
            print("\nGoodbye!")
            break


if __name__ == "__main__":
    main()
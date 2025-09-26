import random

def get_best_move(game):
    current_color = game.get_player_color()
    opponent_color = 'B' if current_color == 'W' else 'W'
    n = game.n

    empty_cells = []
    for i in range(n):
        for j in range(n):
            if game.board[i][j] == '.':
                empty_cells.append((i, j))

    for x, y in empty_cells:
        game.board[x][y] = current_color
        if game.check_win(x, y, current_color):
            game.board[x][y] = '.'
            return (x, y)
        game.board[x][y] = '.'

    for x, y in empty_cells:
        game.board[x][y] = opponent_color
        if game.check_win(x, y, opponent_color):
            game.board[x][y] = '.'
            return (x, y)
        game.board[x][y] = '.'

    return None
import random

def _get_potential_cells(game, color):
    """
    Возвращает множество клеток, которые могут участвовать в квадратах с фишками `color`.
    Это:
      - Все пустые клетки, которые вместе с 3 фишками color образуют квадрат (выигрыш)
      - Все пустые клетки, которые вместе с 2 фишками color могут стать квадратом
    """
    n = game.n
    board = game.board
    points = [(i, j) for i in range(n) for j in range(n) if board[i][j] == color]
    potential = set()

    if len(points) < 2:
        return set()

    for i in range(len(points)):
        for j in range(i + 1, len(points)):
            A = points[i]
            B = points[j]
            ax, ay = A
            bx, by = B

            dx = bx - ax
            dy = by - ay

            C1 = (ax - dy, ay + dx)
            D1 = (bx - dy, by + dx)

            C2 = (ax + dy, ay - dx)
            D2 = (bx + dy, by - dx)

            candidates = [C1, D1, C2, D2]
            for pt in candidates:
                x, y = pt
                if 0 <= x < n and 0 <= y < n and board[x][y] == '.':
                    potential.add((x, y))

    return potential


def get_best_move(game):
    """
    Возвращает лучший ход, используя только потенциально значимые клетки.
    Логика:
      1. Выигрышный ход
      2. Блокировка
      3. Среди потенциальных клеток — выбрать ту, что максимизирует наши квадраты / минимизирует чужие
    """
    current_color = game.get_player_color()
    opponent_color = 'B' if current_color == 'W' else 'W'
    n = game.n

    all_empty = [(i, j) for i in range(n) for j in range(n) if game.board[i][j] == '.']
    if not all_empty:
        return None

    for x, y in all_empty:
        game.board[x][y] = current_color
        if game.check_win(x, y, current_color):
            game.board[x][y] = '.'
            return (x, y)
        game.board[x][y] = '.'

    for x, y in all_empty:
        game.board[x][y] = opponent_color
        if game.check_win(x, y, opponent_color):
            game.board[x][y] = '.'
            return (x, y)
        game.board[x][y] = '.'

    my_potential = _get_potential_cells(game, current_color)
    opp_potential = _get_potential_cells(game, opponent_color)
    
    candidate_cells = my_potential | opp_potential

    if candidate_cells:
        best_score = -10**9
        best_move = None

        for x, y in candidate_cells:
            score = 0

            game.board[x][y] = current_color

            if (x, y) in my_potential:
                score += 2
            if (x, y) in opp_potential:
                score -= 1

            game.board[x][y] = '.'

            if score > best_score:
                best_score = score
                best_move = (x, y)

        if best_move:
            return best_move

    return random.choice(all_empty)
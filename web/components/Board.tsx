"use client";

import { useGame } from "@/hooks/useGame";
import { Square } from "./Square";

export function Board() {
  const { board, size, makeMove, status, loading } = useGame();

  const handleClick = (x: number, y: number) => {
    if (board[x][y] === "") {
      makeMove(x, y);
    }
  };

  const gameActive = status === "playing";

  return (
    <div className="inline-block border-2 rounded-lg p-4 bg-gray-50">
      {board.map((row, x) => (
        <div key={x} className="flex">
          {row.map((cell, y) => (
            <Square
              key={`${x}-${y}`}
              value={cell}
              onClick={() => handleClick(x, y)}
              disabled={!gameActive || loading || cell !== ""}
            />
          ))}
        </div>
      ))}
    </div>
  );
}

"use client";

import { useState } from "react";
import { useGame } from "@/hooks/useGame";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

export function GameControls() {
  const { startNewGame, status, userColor, loading } = useGame();
  const [size, setSize] = useState(5);
  const [color, setColor] = useState<"W" | "B">("W");

  const handleStart = () => {
    startNewGame(size, color);
  };

  return (
    <div className="mb-6 flex flex-col gap-4 items-start">
      <div className="flex gap-4 items-center">
        <label className="text-sm font-medium">Размер:</label>
        <Select
          value={size.toString()}
          onValueChange={(v) => setSize(Number(v))}
          disabled={status === "playing" || loading}
        >
          <SelectTrigger className="w-[80px]">
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            {[3, 4, 5, 6, 7, 8].map((n) => (
              <SelectItem key={n} value={n.toString()}>
                {n}×{n}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>

        <label className="text-sm font-medium">Цвет:</label>
        <Select
          value={color}
          onValueChange={(v) => setColor(v as "W" | "B")}
          disabled={status === "playing" || loading}
        >
          <SelectTrigger className="w-[100px]">
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="W">Белые</SelectItem>
            <SelectItem value="B">Чёрные</SelectItem>
          </SelectContent>
        </Select>

        <Button
          onClick={handleStart}
          disabled={status === "playing" || loading}
        >
          {status === "playing" ? "Идёт игра..." : "Новая игра"}
        </Button>
      </div>

      {status === "won" && (
        <div className="text-lg font-bold text-green-600">
          Победили{" "}
          {userColor === useGame.getState().winner ? "вы" : "компьютер"}!
        </div>
      )}
      {status === "draw" && (
        <div className="text-lg font-bold text-yellow-600">Ничья!</div>
      )}
    </div>
  );
}

"use client";

import { useState } from "react";
import { useGame } from "@/src/hooks/useGame";
import { Button } from "@/src/components/ui/button";
import { Input } from "@/src/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/src/components/ui/select";
import { Alert, AlertDescription } from "@/src/components/ui/alert";
import { RotateCcw, Crown, Users } from "lucide-react";

export function GameControls() {
  const { startNewGame, status, winner, loading } = useGame();
  const [sizeInput, setSizeInput] = useState<string>("5");
  const [color, setColor] = useState<"W" | "B">("W");

  const size = Math.min(10, Math.max(3, parseInt(sizeInput) || 5));

  const handleStart = () => {
    startNewGame(size, color);
  };

  const isPlaying = status === "playing";
  const isDisabled = isPlaying || loading;

  return (
    <div className="w-full max-w-md space-y-4">
      <div className="flex flex-col sm:flex-row gap-3 items-start sm:items-center">
        <div className="flex flex-col gap-1.5 flex-1">
          <label className="text-sm font-medium">Размер поля (3–10):</label>
          <Input
            type="number"
            min="3"
            max="10"
            value={sizeInput}
            onChange={(e) => setSizeInput(e.target.value)}
            disabled={isDisabled}
            className="w-full"
          />
        </div>

        <div className="flex flex-col gap-1.5">
          <label className="text-sm font-medium">Цвет:</label>
          <Select
            value={color}
            onValueChange={(v) => setColor(v as "W" | "B")}
            disabled={isDisabled}
          >
            <SelectTrigger className="w-[110px]">
              <SelectValue />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="W">Белые</SelectItem>
              <SelectItem value="B">Чёрные</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <Button
          onClick={handleStart}
          disabled={isDisabled}
          className="mt-6 h-10 px-4"
        >
          {isPlaying ? (
            <>
              <RotateCcw className="mr-2 h-4 w-4 animate-spin" />
              Идёт игра...
            </>
          ) : (
            "Новая игра"
          )}
        </Button>
      </div>

      {status === "won" && (
        <Alert variant="default" className="bg-gradient-to-r from-amber-50 to-amber-100 border-amber-300">
          <Crown className="h-4 w-4 text-amber-700" />
          <AlertDescription className="font-semibold text-amber-800">
            {winner === useGame.getState().userColor ? "Вы победили!" : "Победил компьютер!"}
          </AlertDescription>
        </Alert>
      )}

      {status === "draw" && (
        <Alert variant="default" className="bg-gradient-to-r from-gray-50 to-gray-100 border-gray-300">
          <Users className="h-4 w-4 text-gray-700" />
          <AlertDescription className="font-semibold text-gray-800">
            Ничья!
          </AlertDescription>
        </Alert>
      )}
    </div>
  );
}
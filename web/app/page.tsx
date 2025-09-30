import Image from "next/image";
import { GameControls } from "@/components/GameControls";
import { Board } from "@/components/Board";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex flex-col items-center justify-center p-4">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle className="text-2xl text-center">
            Игра в квадраты
          </CardTitle>
        </CardHeader>
        <CardContent className="flex flex-col items-center gap-6">
          <GameControls />
          <Board />
          <p className="text-sm text-gray-500 text-center">
            Выигрывает тот, кто первым построит квадрат из своих фишек.
          </p>
        </CardContent>
      </Card>
    </div>
  );
}

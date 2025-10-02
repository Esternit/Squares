import Image from "next/image";
import { GameControls } from "@/src/components/GameControls";
import { Board } from "@/src/components/Board";
import { Card, CardContent, CardHeader, CardTitle } from "@/src/components/ui/card";

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-blue-50 to-cyan-50 flex flex-col items-center justify-center p-4">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader className="text-center pb-4">
          <CardTitle className="text-2xl font-bold text-gray-800">Игра в квадраты</CardTitle>
          <p className="text-sm text-gray-600 mt-1">
            Постройте квадрат из своих фишек первым!
          </p>
        </CardHeader>
        <CardContent className="flex flex-col items-center gap-6">
          <GameControls />
          <Board />
        </CardContent>
      </Card>
    </div>
  );
}

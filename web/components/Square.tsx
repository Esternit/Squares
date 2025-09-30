import { Button } from "@/components/ui/button";

interface SquareProps {
  value: string;
  onClick: () => void;
  disabled: boolean;
}

export function Square({ value, onClick, disabled }: SquareProps) {
  const colorClass =
    value === "w" || value === "W"
      ? "bg-white text-gray-800"
      : value === "b" || value === "B"
      ? "bg-black text-white"
      : "bg-gray-100 hover:bg-gray-200";

  return (
    <Button
      variant="outline"
      size="lg"
      className={`w-12 h-12 p-0 font-bold text-lg rounded-md border-2 ${colorClass}`}
      onClick={onClick}
      disabled={disabled}
    >
      {value ? (value.toUpperCase() === "W" ? "●" : "●") : ""}
    </Button>
  );
}

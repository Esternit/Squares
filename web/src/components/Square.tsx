import { Button } from "@/src/components/ui/button";

interface SquareProps {
  value: string;
  onClick: () => void;
  disabled: boolean;
}

export function Square({ value, onClick, disabled }: SquareProps) {
  const getBgClass = () => {
    if (value === "w" || value === "W") return "bg-white border-gray-400 text-gray-800";
    if (value === "b" || value === "B") return "bg-gray-900 text-white";
    return "bg-gray-50 hover:bg-gray-100";
  };

  return (
    <Button
      variant="outline"
      size="lg"
      className={`w-12 h-12 p-0 font-bold text-lg rounded-md border-2 transition-all
        ${getBgClass()} ${disabled ? "cursor-not-allowed opacity-80" : "hover:opacity-100"}`}
      onClick={onClick}
      disabled={disabled}
    >
      {value ? "●" : ""}
    </Button>
  );
}
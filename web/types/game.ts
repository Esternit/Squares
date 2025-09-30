export type PlayerColor = 'W' | 'B';

export interface BoardDto {
  size: number;
  data: string;
  nextPlayerColor: string;
}

export interface GameRequestDto {
  size: number;
  playerType: 'user' | 'comp';
  playerColor: PlayerColor;
}

export interface MoveDto {
  x: number;
  y: number;
  data: string;
  size: number;
  color: string;
}

export interface SimpleMoveDto {
  x: number;
  y: number;
  color: string;
}

export interface BoardStateDto {
  size: number;
  data: string[];
}
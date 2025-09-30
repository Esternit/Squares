import { create } from 'zustand';
import axios from 'axios';
import { BoardStateDto, GameRequestDto, MoveDto, PlayerColor } from '../types/game';

const API_BASE = '/api';

interface GameState {
  board: string[][];
  size: number;
  currentPlayer: PlayerColor;
  userColor: PlayerColor;
  status: 'idle' | 'playing' | 'won' | 'draw';
  winner: PlayerColor | null;
  loading: boolean;

  startNewGame: (size: number, userColor: PlayerColor) => Promise<void>;
  makeMove: (x: number, y: number) => Promise<void>;
  checkWin: (board: string[][], color: string) => boolean;
}

export const useGame = create<GameState>((set, get) => ({
  board: [],
  size: 5,
  currentPlayer: 'W',
  userColor: 'W',
  status: 'idle',
  winner: null,
  loading: false,

  startNewGame: async (size, userColor) => {
    set({ loading: true });
    try {
      const request: GameRequestDto = {
        size,
        playerType: 'user',
        playerColor: userColor,
      };

      const res = await axios.post<BoardStateDto>(`${API_BASE}/new-game`, request);
      const board = res.data.data.map(row => row.split('').map(c => c === '.' ? '' : c));

      set({
        board,
        size,
        userColor,
        currentPlayer: userColor,
        status: 'playing',
        winner: null,
        loading: false,
      });
    } catch (err) {
      console.error('Failed to start new game', err);
      set({ loading: false });
    }
  },

  makeMove: async (x, y) => {
    const state = get();
    if (state.status !== 'playing' || state.loading) return;

    const color = state.userColor;
    const flatData = state.board.flat().map(c => c || ' ').join('');

    const moveDto: MoveDto = {
      x,
      y,
      data: flatData,
      size: state.size,
      color,
    };

    set({ loading: true });

    try {
      const res = await axios.post<BoardStateDto>(`${API_BASE}/make-move`, moveDto);
      const newBoard = res.data.data.map(row => 
  row.split('').map(c => c === '.' ? '' : c)
);

      if (get().checkWin(newBoard, color.toLowerCase())) {
        set({ status: 'won', winner: color, board: newBoard, loading: false });
        return;
      }

      const isFull = newBoard.flat().every(cell => cell !== '');
      if (isFull) {
        set({ status: 'draw', board: newBoard, loading: false });
        return;
      }

      const aiColor = color === 'W' ? 'B' : 'W';
      if (get().checkWin(newBoard, aiColor.toLowerCase())) {
        set({ status: 'won', winner: aiColor, board: newBoard, loading: false });
        return;
      }

      set({
        board: newBoard,
        currentPlayer: color,
        loading: false,
      });
    } catch (err) {
      console.error('Move failed', err);
      set({ loading: false });
    }
  },

  checkWin: (board, color) => {
    const n = board.length;
    return false;
  },
}));
import { create } from 'zustand';
import axios from 'axios';
import { BoardStateDto, GameRequestDto, MoveDto, PlayerColor } from '../types/game';

const API_BASE = 'http://localhost:8080/api';

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

  makeMove: async (x: number, y) => {
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

      const newBoard = res.data.data.map(row => row.split('').map(c => c === '.' ? '' : c));
      
      if (res.data.gameOver) {
        if (res.data.winner === 'draw') {
          set({ status: 'draw', board: newBoard, loading: false });
        } else if (res.data.winner === 'W' || res.data.winner === 'B') {
          set({ status: 'won', winner: res.data.winner as PlayerColor, board: newBoard, loading: false });
        } else {
          set({ status: 'draw', board: newBoard, loading: false });
        }
      } else {
        set({ board: newBoard, loading: false });
      }
    } catch (err) {
      console.error('Move failed', err);
      set({ loading: false });
    }
  }
}));
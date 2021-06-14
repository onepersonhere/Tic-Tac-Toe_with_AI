package tictactoe;

public class Move {
        public char ch;
        public int score;
        public int[] pos;

        public Move(char ch, int score, int[] pos){
            this.ch = ch;
            this.score = score;
            this.pos = pos;
        }

        public Move(int score){
            this.score = score;
        }

        public Move(){}
}

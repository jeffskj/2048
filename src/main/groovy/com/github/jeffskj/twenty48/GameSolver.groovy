package com.github.jeffskj.twenty48

import groovyx.gpars.GParsPool

class GameSolver {
    private Game game;

    GameSolver(Game game) {
        this.game = game
    }
    
    void solveTo(int maxTile) {
        GParsPool.withPool {
            while (!game.over && game.maxTile < maxTile) {
//                println "$game.over $game.won $game.score"
                
                def possibleMovesByScore = Direction.values().collectParallel { Direction dir ->
                    def tmpBoard = game.board.board.clone()
                    def score = scoreForDirection(tmpBoard, dir)
                    score += Direction.values().sum { scoreForDirection(tmpBoard.clone(), it) }
                    
                    def max = game.board.maxValue
                    def cornerVal = [0,0,3,3,0,3,3,0].collate(2).find { game.board.get(it[0], it[1]) == max }
                    if (cornerVal) {
                        score += 64
                    }
                    
                    return [dir, score]
                }
                
                possibleMovesByScore = possibleMovesByScore.sort { it[1] }.reverse()
//                print "$possibleMovesByScore -> "
                                
                for (i in 0..possibleMovesByScore.size()-1) {
                    def dir = possibleMovesByScore[i][0]
//                    println dir
                    def result = game.move(dir)
                    if (result.moved) { break }
                }
//                println game.board
            }
        }
    }
    
    private int scoreForDirection(Map board, Direction dir) {
        if (dir == Direction.UP) { return 0 } // never move up unless we have to
        return game.board.move(board, dir).scores
    }
}

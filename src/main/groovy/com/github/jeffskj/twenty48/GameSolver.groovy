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
                    if (dir == Direction.UP) { return [dir, 0] }
                    def result = game.board.move(dir, true)
                    return [dir, result.scores]
                }
                
                possibleMovesByScore = possibleMovesByScore.sort { it[1] }.reverse()
//                print "$possibleMovesByScore -> "
                                
                for (i in 0..possibleMovesByScore.size()-1) {
                    def dir = possibleMovesByScore[i][0]
                    println dir
                    def result = game.move(dir)
                    if (result.moved) { break }
                }
                println game.board
            }
        }
    }
}

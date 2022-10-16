# Base Game

## Board
- N x N tiles
- Under each tile is either:
    - A bomb
    - A number, indicating how many bombs there are adjacent to the tile
    - An empty space

## Player(s)
- Single player
- Can click on uncleared tiles:   
    - To clear them
    - To place a marker on them (indicates a bomb)
- The player wins if they clear all non-bomb tiles
- The player loses if they click on a bomb tile

### Notes
- Classic version has a bomb counter that goes down with each marker placed (even if the marker is placed the a wrong spot)


# Minesweeper: Versus

## Board
- N x N tiles (customizable but with defaults available)
- K bombs (customizable but with defaults available)
- Under each tile is either:
    - A bomb
    - A number, indicating how many bombs there are adjacent to the tile
    - An empty space

## Players(s)
- 2+ Players
- Can click on uncleared tiles:   
    - To clear them (wins points per tile cleared)
    - To place a marker on them (indicates a bomb)

## Scoring
- Players win points for each tile they clear
- Players lose points if they click on a bomb tile

## Markers
- All players can see markers placed, and all markers make the bomb counter count down

## Clicked on a bomb tile
- If a player clicks on a bomb tile, their final score suffers a penalty, they are no longer able to clear tiles (but can still place markers)
- Once a player is out of the game, the remaining players still have a few additional minutes (registered in the Final Stage Timer) to clear more tiles

## End of the game
- After a player has clicked on a bomb tile and the Final Stage Timer reaches 0, the final scores are counted
- There is a collective score and a player score

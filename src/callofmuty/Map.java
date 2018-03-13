package callofmuty;

import java.awt.Graphics2D;

public class Map{
    
    private int[][] map;
    private int mapWidth,mapHeight, tileSize;
    private static TileType grass = TileType.GRASS, rock = TileType.ROCK, dirt = TileType.ROCK;
    
    // Grass = 0 ; Rock = 1 ; Dirt = 2;
    
    public Map(int[][] map, int tileSize){
        this.map=map;
        mapHeight=map.length;
        mapWidth=map[0].length;
        this.tileSize = tileSize;
    }
    
    public Map(int mapWidth, int mapHeight, int tileSize){
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.tileSize = tileSize;
        map = new int[mapWidth][mapHeight];
        for (int i = 1 ; i<mapWidth-1 ; i++){
            for (int j = 1; j<mapHeight-1; j++){
                map[i][j] = 0;
            }
        }
        for (int i = 0 ; i<mapWidth ; i++){
            map[i][0] = 1;
            map[i][mapHeight-1] = 1;
        }
        for (int j = 1 ; j<mapHeight-1 ; j++){
            map[0][j] = 1;
            map[mapWidth-1][j] = 1;
        }
    }
    
    public void draw(Graphics2D g2d){
        for (int i = 0 ; i<mapHeight ; i++){
            for (int j = 0; j<mapWidth; j++){
                switch(map[j][i]){
                    case 1:
                        g2d.drawImage(rock.getImage(),j*64, i*64, tileSize, tileSize, null);
                        break;
                    case 2:
                        g2d.drawImage(dirt.getImage(), j*64, i*64, tileSize, tileSize, null);
                        break;
                    default:
                        g2d.drawImage(grass.getImage(), j*64, i*64, tileSize, tileSize, null);
}
            }
        }
    }
    
    public int getTile(int x, int y){
        return map[x/tileSize][y/tileSize];
    }
    
}

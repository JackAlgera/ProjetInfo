package callofmuty;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BonusItem {
    
    public static final int HEALING = 0, SPEED_BOOST = 1, NUMBER_OF_ITEMS = 2;
    private static double HEALING_VALUE = 10, SPEED_BOOST_VALUE = 1.4; // Hp per second ; speed multiplier
    private static long TIME_BEFORE_REACTIVATING = 1000, HEALING_DURATION = 3000, SPEED_BOOST_DURATION = 3000; // in ms
    private static BufferedImage healingImage = Tools.selectTile(Tools.tileset, 2, 7), speedBoostImage = Tools.selectTile(Tools.tileset, 2, 8);

    private int x, y, width = 30, height = 30, type, id, playerId;
    private boolean isActive;
    private long timeOfDeactivation;
    private BufferedImage image;

    public BonusItem(int id, int playerId){ // used to initialize player's itemList
        this(0, 0, 0, id, playerId);
    }
    
    public BonusItem(int x, int y, int type, int id, int playerId){
        this.x = x;
        this.y = y;
        this.type = type;
        this.id = id;
        this.playerId = playerId;
        isActive = false;
        image = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public int getPlayerId() {
        return playerId;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public boolean isActivable() {
        return(!isActive && System.currentTimeMillis()-TIME_BEFORE_REACTIVATING>=timeOfDeactivation);
    }
    
    public void setActive(boolean isActive){
        boolean formerActive = this.isActive;
        this.isActive = isActive;
        if(formerActive && !isActive){
            timeOfDeactivation = System.currentTimeMillis();
        }
        if(isActive){
            switch(type){
                case HEALING:
                    image = healingImage;
                    break;
                case SPEED_BOOST:
                    image = speedBoostImage;
                    break;
                default :
                    image = null;
            }
        }
    }
    
    public void setPosition(int[] position){
        if(position.length==2){
            x = position[0];
            y = position[1];
        }
    }
    
    public void setType(int type){
        this.type = type;
    }
    
    public int getType(){
        return type;
    }
    
    public void setId(int id){
        this.id = id;
    }

    Effect getEffect() {
        Effect effect;
        switch(type){
            case HEALING:
                effect = new Effect(Effect.HEALING, HEALING_DURATION, HEALING_VALUE);
                break;
            case SPEED_BOOST:
                effect = new Effect(Effect.FASTER, SPEED_BOOST_DURATION, SPEED_BOOST_VALUE);
                break;
            default:
            effect = new Effect();
        }
        return effect;
    }

    public void draw(Graphics2D g2d, int texturesize, GamePanel game){
        if (isActive) {
            double zoomRatio = game.getZoomRatio()*game.getScreenSizeZoomRatio();
            g2d.drawImage(image,game.getGameX()+(int)((x+((double)width-texturesize)/2)*zoomRatio),(int)((y+((double)height-texturesize)/2)*zoomRatio),(int)(texturesize*zoomRatio),(int)(texturesize*zoomRatio), null);
            // drawing hitbox
            //Rectangle hitbox = getHitBox();
            //g2d.drawRect(game.getGameX()+hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }
    }
    
    public Rectangle getHitBox(){
        return new Rectangle((int)(x),(int)(y),(int)(width),(int)(height));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BonusItem other = (BonusItem) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.playerId != other.playerId) {
            return false;
        }
        return true;
    }
    
    
}

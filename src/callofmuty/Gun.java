package callofmuty;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;

public class Gun {
    
    public static final int NO_GUN = 0, PISTOL = 1, UZI = 2, SNIPER = 3, SHOTGUN = 4, AK = 5;
    private static final BufferedImage pistolImage = Tools.selectWeaponTile(Tools.WeaponTileset, 1, 1, 1);
    private static final BufferedImage uziImage = Tools.selectWeaponTile(Tools.WeaponTileset, 2, 3, 1); 
    private static final BufferedImage sniperImage = Tools.selectWeaponTile(Tools.WeaponTileset, 3, 1, 2);  
    private static final BufferedImage shotgunImage = Tools.selectWeaponTile(Tools.WeaponTileset, 1, 7, 2); 
    private static final BufferedImage akImage = Tools.selectWeaponTile(Tools.WeaponTileset, 1, 3, 2); 
    
    private int ammunition,stockAmmo, id, startingAmmo;
    private Image image;
    private double damage, rateOfFire, lastShotTimeStamp, reloadTime, bulletSpeed, initialRateOfFire;
    private SoundPlayer gunSound;
    
    public Gun(){
        ammunition = 0;
        id = NO_GUN;
        lastShotTimeStamp = System.currentTimeMillis();
    }
    
    public boolean isEmpty(){
        return ammunition==0;
    }
    
    public double getDamage(){
        return damage;
    }
    
    public void setId(int id)throws IOException, JavaLayerException{
        this.id = id;
        
        switch(this.id){
            case PISTOL:
                ammunition = 12;
                image = pistolImage;
                rateOfFire = 500; //in milliseconds
                damage = 15;
                reloadTime = 1000;
                bulletSpeed = 1.0;
                stockAmmo = ammunition;
                startingAmmo=ammunition;
                initialRateOfFire=rateOfFire;
                gunSound = new SoundPlayer("shootingSound.mp3", false);
                break;
                
            case UZI:
                ammunition = 10;
                image = uziImage;
                rateOfFire = 200;
                damage = 5;
                reloadTime = 1000;
                bulletSpeed = 0.8;
                stockAmmo = ammunition;
                startingAmmo=ammunition;
                initialRateOfFire=rateOfFire;
                gunSound = new SoundPlayer("shootingSound.mp3", false);
                break;
                
            case SNIPER:
                ammunition = 5;
                image = sniperImage;
                rateOfFire = 1000;
                damage = 35;
                reloadTime = 1000;
                bulletSpeed = 1.8;
                stockAmmo = ammunition;
                startingAmmo=ammunition;
                initialRateOfFire=rateOfFire;
                gunSound = new SoundPlayer("shootingSound.mp3", false);
                break;
                
            case SHOTGUN:
                ammunition = 8;
                image = shotgunImage;
                rateOfFire = 650;
                damage = 22;
                reloadTime = 1000;
                bulletSpeed = 1.2;
                stockAmmo = ammunition;
                startingAmmo=ammunition;
                initialRateOfFire=rateOfFire;
                gunSound = new SoundPlayer("shootingSound.mp3", false);
                break;
                
            case AK:
                ammunition = 10;
                image = akImage;
                rateOfFire = 280;
                damage = 10;
                reloadTime = 1000;
                bulletSpeed = 1.0;
                stockAmmo = ammunition;
                startingAmmo=ammunition;
                initialRateOfFire=rateOfFire;
                gunSound = new SoundPlayer("shootingSound.mp3", false);
                break;
                
                
        }
    }
    
    public int getId(){
        return id;
    }
    
    public double getBulletSpeed(){
        return bulletSpeed;
    }
    
    public SoundPlayer getGunSound(){
        return gunSound;
    }
    
    public void draw(Graphics2D g2d, Player player){
        switch(id){
            case NO_GUN: //draw nothing
                break;
            default:
                g2d.drawImage(image, (int) player.getPosX()+15, (int) player.getPosY()+10, image.getWidth(null), image.getHeight(null), null);
        }
        
    }
    
    public boolean shoot(boolean unlimitedBullets)throws IOException, JavaLayerException{ // if gun can shoot, shoots and returns true, else returns false
        boolean test = (unlimitedBullets || ammunition>0) && System.currentTimeMillis()-rateOfFire>=lastShotTimeStamp;
        if (test){
            
            if(!unlimitedBullets){
                ammunition--;
                rateOfFire=initialRateOfFire;
            }
            lastShotTimeStamp = System.currentTimeMillis();
            if(ammunition==0){
                if(stockAmmo !=0 ){
                    stockAmmo=stockAmmo-startingAmmo;
                    ammunition=startingAmmo;
                    rateOfFire+=reloadTime;
                } else {
                    setId(NO_GUN);
                }
                
            }
        }
        return test;
    }
    
}

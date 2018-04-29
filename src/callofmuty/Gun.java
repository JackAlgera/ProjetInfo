package callofmuty;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Gun {
    
    public static final int NO_GUN = 0, PISTOL = 1, UZI = 2, SNIPER = 3, SHOTGUN = 4, AK = 5, MAGNUM = 6, MITRAILLEUSE = 7;
    private static final BufferedImage pistolImage = Tools.selectWeaponTile(Tools.WeaponTileset, 1, 1, 1);
    private static final BufferedImage uziImage = Tools.selectWeaponTile(Tools.WeaponTileset, 2, 3, 1); 
    private static final BufferedImage sniperImage = Tools.selectWeaponTile(Tools.WeaponTileset, 3, 1, 2);  
    private static final BufferedImage shotgunImage = Tools.selectWeaponTile(Tools.WeaponTileset, 1, 7, 2); 
    private static final BufferedImage akImage = Tools.selectWeaponTile(Tools.WeaponTileset, 1, 3, 2); 
    private static final BufferedImage magnumImage = Tools.selectWeaponTile(Tools.WeaponTileset, 2, 1, 1);
    private static final BufferedImage mitrailleuseImage = Tools.selectWeaponTile(Tools.WeaponTileset,2, 5, 2);
    
    private int ammunition,stockAmmo, id, startingAmmo, xImage, yImage, tailleGun;
    private Image image;
    private double damage, rateOfFire, lastShotTimeStamp, reloadTime, bulletSpeed, initialRateOfFire, distanceMaxShoot, bulletSpread;
    private SoundPlayer gunSound, uziSound, sniperSound,shotgunSound;
    
    public Gun(){
        ammunition = 0;
        id = NO_GUN;
        lastShotTimeStamp = System.currentTimeMillis();
        gunSound = new SoundPlayer("shootingSound.mp3", false);
        uziSound = new SoundPlayer("uziSound.mp3", false);
        sniperSound = new SoundPlayer("sniperSound.mp3", false);
        shotgunSound = new SoundPlayer("shotgunSound.mp3", false);
    }
    
    public double getDamage(){
        return damage;
    }
    
    public void setId(int id, int numberOfCartridges){
        this.id = id;
        
        switch(this.id){
            case PISTOL:
                ammunition = 6;
                image = pistolImage;
                rateOfFire = 500; //in milliseconds
                damage = 15;
                reloadTime = 1000;
                bulletSpeed = 1.0;
                distanceMaxShoot = 550;
                bulletSpread = 0.139;
                xImage = 1;
                yImage = 1;
                tailleGun = 1;
                break;
                
            case UZI:
                ammunition = 20;
                image = uziImage;
                rateOfFire = 150;
                damage = 5;
                reloadTime = 1000;
                bulletSpeed = 0.8;
                distanceMaxShoot = 450;
                bulletSpread = 0.174;
                xImage = 2;
                yImage = 3;
                tailleGun = 1;
                break;
                
            case SNIPER:
                ammunition = 4;
                image = sniperImage;
                rateOfFire = 1000;
                damage = 35;
                reloadTime = 1000;
                bulletSpeed = 1.8;
                distanceMaxShoot = 800;
                bulletSpread = 0.0017;
                xImage = 3;
                yImage = 1;
                tailleGun = 2;
                break;
                
            case SHOTGUN:
                ammunition = 5;
                image = shotgunImage;
                rateOfFire = 650;
                damage = 22;
                reloadTime = 1000;
                bulletSpeed = 1.2;
                distanceMaxShoot = 400;
                bulletSpread = 0.017;
                xImage = 1;
                yImage = 7;
                tailleGun = 2;
                break;
                
            case AK:
                ammunition = 14;
                image = akImage;
                rateOfFire = 280;
                damage = 10;
                reloadTime = 1000;
                bulletSpeed = 1.0;
                distanceMaxShoot = 500;
                bulletSpread = 0.037;
                xImage = 1;
                yImage = 3;
                tailleGun = 2;
                break;
                
            case MAGNUM:
                ammunition = 6;
                image = magnumImage;
                rateOfFire = 700;
                damage = 25;
                reloadTime = 1000;
                bulletSpeed = 1.8;
                distanceMaxShoot = 700;
                bulletSpread = 0.034;
                xImage = 2;
                yImage = 1;
                tailleGun = 1;
                break;
                
            case MITRAILLEUSE:
                ammunition = 20;
                image = mitrailleuseImage;
                rateOfFire = 200;
                damage = 7;
                reloadTime = 1250;
                bulletSpeed = 1.0;
                distanceMaxShoot = 550;
                bulletSpread = 0.139;
                xImage = 2;
                yImage = 5;
                tailleGun = 2;
                break;
                
            case NO_GUN:
                ammunition = 0;       
        }
        stockAmmo = ammunition * numberOfCartridges;
        startingAmmo = ammunition;
        initialRateOfFire = rateOfFire;
    }
    
    public int getId(){
        return id;
    }
    
    public double getBulletSpeed(){
        return bulletSpeed;
    }
    
    public double getDistanceMaxShoot() {
        return distanceMaxShoot;
    }
    
    public double getBulletSpread(){
        return bulletSpread;
    }
    
    public void draw(Graphics2D g2d, Player player){
        switch(id){
            case NO_GUN: //draw nothing
                break;
            default:
                g2d.drawImage(image, (int) getGunPositionY(player), (int) player.getPosY() + 18, image.getWidth(null), image.getHeight(null), null);
        }
        
    }
    
    public boolean shoot(boolean unlimitedBullets, boolean muteShootingSound){ // if gun can shoot, shoots and returns true, else returns false
        boolean test = (unlimitedBullets || ammunition>0) && System.currentTimeMillis()-rateOfFire>=lastShotTimeStamp;
        if (test){
            if(!unlimitedBullets){
                ammunition--;
                rateOfFire=initialRateOfFire;
            }
            lastShotTimeStamp = System.currentTimeMillis();
            if(ammunition==0){
                if(stockAmmo !=0 ){
                    stockAmmo-=startingAmmo;
                    ammunition=startingAmmo;
                    rateOfFire+=reloadTime;
                } else {
                    setId(NO_GUN, 0);
                }
                
            }
            if (!muteShootingSound){
                playShootingSound();
            }
        }
        return test;
    }
    
    public void changeGunDirection(int k){
        if (k == 1){
            this.image = Tools.selectWeaponTile(Tools.WeaponTileset, this.xImage, this.yImage +k*this.tailleGun, this.tailleGun);
        } else {
            this.image = Tools.selectWeaponTile(Tools.WeaponTileset, this.xImage, this.yImage , this.tailleGun);
        }
    }
    
    public void playShootingSound() {
        switch (id) {
            case NO_GUN:
                break;
            case UZI:
                uziSound.play();
                break;
            case SNIPER:
                sniperSound.play();
                break;
            case SHOTGUN:
                shotgunSound.play();
                break;
            default:
                gunSound.play();
            }
    }
    
    public int getGunPositionY(Player player)
    {
        int gunPos = (int)player.getPosX() - 6;
        switch(id)
        {
            case PISTOL :
                gunPos = (int)player.getPosX() + 5;
                break;
            case UZI :
                gunPos = (int)player.getPosX() - 2;
                break;
            case SNIPER :
                gunPos = (int)player.getPosX() - 2;
                break;
            case SHOTGUN :
                gunPos = (int)player.getPosX() - 9;
                break;
            case AK :
                gunPos = (int)player.getPosX() - 11;
                break;
            case MAGNUM :

                break;
            case MITRAILLEUSE :
                gunPos = (int)player.getPosX() - 11;
                break;
        }
        
        switch(player.getCurrentImage())
        {
            case 2:
                gunPos += 2;
                break;
            case 3:
                gunPos += 4;
                break;
            case 4:
                gunPos += 2;
                break;
        }
        return gunPos;
    }
    
}

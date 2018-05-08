package callofmuty;

public class GameMode {
    
    private int id;
    private boolean[] Options; //Op 0 Suggested map, Op 1 Rubberballs, Op 2 Active Items, Op 3 Modefast
    private int gunGestion;
    private int Team;
    private String description;
    
    public static final int DEFAULT=0,ROYAL=1,TEAM=2, ALONE=3,
                RANDOM =0, ALWAYSON = 1,
                ALLVSALL = 0, ALLVSONE = 1, TEAMVSTEAM = 2,
                NUMBER_OF_OPTIONS = 4;
    private static final double FAST_MODE_MULTIPLIER = 1.7;
    private static final int NUMBER_OF_BOUNCES = 1;
    
    private static String DEFAULT_DESCRIPTION = "Bla bla default gamemode description",
            ROYAL_DESCRIPTION = "PAN PAN EVERYWHERE",
            TEAM_DESCRIPTION = "PAN PAN but not on everyone",
            ALONE_DESCRIPTION = "You have no friends";
    
    public GameMode(){
        Options= new boolean[] {false,false,false,false};
        setId(DEFAULT);
    }

    public void setId(int id) {
        this.id = id;
        switch (this.id) {
            case DEFAULT:
                gunGestion = RANDOM;
                Team = ALLVSALL;
                description = DEFAULT_DESCRIPTION;
            break;
            case ROYAL:
                gunGestion = ALWAYSON;
                Team = ALLVSALL;
                description = ROYAL_DESCRIPTION;
            break;
            case TEAM:
                gunGestion = ALWAYSON;
                Team = TEAMVSTEAM;
                description = TEAM_DESCRIPTION;

            break;
            case ALONE:
                gunGestion = ALWAYSON;
                Team = ALLVSONE;
                description = ALONE_DESCRIPTION;
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getNumberOfBounces(){
        int numberOfBounces;
        if(Options[1]){
            numberOfBounces = NUMBER_OF_BOUNCES;
        } else {
            numberOfBounces = 0;
        }
        return numberOfBounces;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getGunGestion() {
        return this.gunGestion;
    }  
    
    public boolean getOption(int i) {
        return this.Options[i];
    }
    
    public void setOption(int i, boolean option){
        Options[i] = option;
    }
    
    public int getTeam(){
        return this.Team;
    }

    double getTimerMultiplier() {
        double multiplier;
        if(Options[3]){
            multiplier = FAST_MODE_MULTIPLIER;
        } else {
            multiplier = 1;
        }
        return multiplier;
    }
}

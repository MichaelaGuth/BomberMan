package main.game.player;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: guthomic
 * Date: 29. 4. 2020
 * Time: 14:22
 */
public enum PlayerPose {
    LOOK_DOWN("LookDown.png"), LOOK_UP("LookUp.png"), LOOK_LEFT("LookLeft.png"), LOOK_RIGHT("LookRight.png"), PROFILE_PIC("ProfilePic.png"), PROFILE_PIC_DEATH("ProfilePicDeath.png"),
    LOOK_DOWN_IMMUNE("LookDownImmunity.png"), LOOK_UP_IMMUNE("LookUpImmunity.png"), LOOK_LEFT_IMMUNE("LookLeftImmunity.png"), LOOK_RIGHT_IMMUNE("LookRightImmunity.png");

    private String imageName;

    /**
     * Constructor of PlayerPose.
     * @param imageName The name of PlayerPose.
     */
    PlayerPose(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets the PlayerPose's image.
     * @return The PlayerPose's image.
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Generates the list of all possible poses.
     * @return The list of all possible poses.
     */
    public static LinkedList<PlayerPose> getAllPoses() {
        LinkedList<PlayerPose> poses = new LinkedList<>();

        poses.add(LOOK_DOWN);
        poses.add(LOOK_LEFT);
        poses.add(LOOK_UP);
        poses.add(LOOK_RIGHT);
        poses.add(PROFILE_PIC);
        poses.add(PROFILE_PIC_DEATH);
        poses.add(LOOK_DOWN_IMMUNE);
        poses.add(LOOK_LEFT_IMMUNE);
        poses.add(LOOK_RIGHT_IMMUNE);
        poses.add(LOOK_UP_IMMUNE);

        return poses;
    }
}

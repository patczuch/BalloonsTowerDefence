package tower_defence;

public class WavesConfig {
    public interface BallonsNumber {
        int get(int wave);
    }

    public interface BallonsSpeed {
        double get(int wave);
    }
    public final BallonsNumber redBaloonsNumber;
    public final BallonsNumber blueBallonsNumber;
    public final BallonsNumber greenBallonsNumber;
    public final BallonsNumber purpleBallonsNumber;
    public final BallonsSpeed ballonsSpeed;
    public WavesConfig(BallonsNumber redBaloonsNumber, BallonsNumber blueBallonsNumber, BallonsNumber greenBallonsNumber, BallonsNumber purpleBallonsNumber, BallonsSpeed ballonsSpeed)
    {
        this.redBaloonsNumber = redBaloonsNumber;
        this.blueBallonsNumber = blueBallonsNumber;
        this.greenBallonsNumber = greenBallonsNumber;
        this.purpleBallonsNumber = purpleBallonsNumber;
        this.ballonsSpeed = ballonsSpeed;
    }
}

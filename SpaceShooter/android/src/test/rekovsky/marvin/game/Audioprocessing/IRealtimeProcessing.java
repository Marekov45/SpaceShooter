package test.rekovsky.marvin.game.Audioprocessing;

public interface IRealtimeProcessing {

    public float[][] calculateNextBlock(float[][] audioBlock);

    public  void setFilter(float[][] filter);
}

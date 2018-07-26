package test.rekovsky.marvin.game.AudioOutput;

import android.content.Context;
import test.rekovsky.marvin.game.TextReader;


public class AudioOutput {

    private AudioThread audioThread;
    private Context context;

    public AudioOutput(Context context){
        this.context = context;

    }

    private  void createOutputThread(){
        if(audioThread!= null) {
            audioThread.cancel(true);
            audioThread.stop();



        }
        audioThread = new AudioThread(this.getAudioSamples(),new float[0][0]);
    }

    public void startCurrentOutputThread(){
        createOutputThread();
        audioThread.execute();
    }

    public void stopAudioOutputThread(){
        if(audioThread!= null) {
            audioThread.cancel(true);
            audioThread.stop();
        }
    }


    private float[][] getAudioSamples(){
        float[][] samples = (new TextReader(context)).scanCSV();
        return  samples;
    }

}

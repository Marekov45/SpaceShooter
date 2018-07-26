package test.rekovsky.marvin.game.AudioOutput;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

public class AudioThread extends AsyncTask {

    private AudioTrack myTrack;
    private float[][] audioFile;
    private int byteConverter;
    private int bufferSize;
    private boolean running;

    public AudioThread(float[][] audioFile, float[][] filter) {
        this.audioFile = audioFile;
        byteConverter = Short.SIZE / Byte.SIZE;
        bufferSize = audioFile.length / byteConverter;
        myTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 48000, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_FLOAT, bufferSize, AudioTrack.MODE_STREAM);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        System.out.println("Background worker started.");
        running = true;
        //Process
        //Output
        while (running) {
            myTrack.play();
          //  myTrack.write(audioFile,0,bufferSize,AudioTrack.WRITE_NON_BLOCKING);
        }
        System.out.println("Background worker broke up.");
        return null;
    }

    public void stop() {
        running = false;
    }
}

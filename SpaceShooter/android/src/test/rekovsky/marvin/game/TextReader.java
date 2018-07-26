package test.rekovsky.marvin.game;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class TextReader {

    Context context;

    public TextReader(Context context){
        this.context = context;
    }


    public float[][] scanCSV(){

        InputStream is = null;
        float[][] result = new float[48000][2];


       // is = context.getResources().openRawResource(R.raw.castanetes);


        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        StringTokenizer st = null;
        try {

            int linecounter = 0;
            while ((line = reader.readLine()) != null && linecounter < 48000) {
                st = new StringTokenizer(line, ",");



                String nexNumber = st.nextToken();
                double parsed =  Double.valueOf(nexNumber);
                result[linecounter][0] = (float) parsed;

                nexNumber = st.nextToken();
                parsed =  Double.valueOf(nexNumber);
                result[linecounter][1] = (float) parsed;

                if(st.countTokens() > 2){
                    System.out.println("Some Tokens not read in line " + linecounter);
                };
                linecounter++;
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return  result;
    }
}

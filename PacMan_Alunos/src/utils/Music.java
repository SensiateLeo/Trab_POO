package utils;
/*
* @authors Hiago de Franco, Leonardo Sensiate, Mateus Castilho Leite e Vinícius Nakasone.
* Baseado em material do Prof. Jose Fernando Junior disponibilizado pelo professor Luiz Eduardo.
*/
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


public class Music 
{
    private ContinuousAudioDataStream continuousaudiostream;
    private AudioData audiodata;

    public Music() 
    {
    }    
   public void executaSom(String caminho) 
   {
        try 
        {
            String st = caminho;
            InputStream in = new FileInputStream(st);
            AudioStream au = new AudioStream(in);
            AudioPlayer.player.start(au);
        } 
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void executaLoop(String caminho) 
    {
        try 
        {
            String st = caminho;
            InputStream in = new FileInputStream(st);
            AudioStream au = new AudioStream(in);
            audiodata = au.getData();
            continuousaudiostream = new ContinuousAudioDataStream(audiodata);
            AudioPlayer.player.start(continuousaudiostream);
        } catch (IOException ex) 
        {
             System.err.println(ex.getMessage());
        }
    }
    public String retornaCaminho(String caminhoRelativo) 
    {
        return getClass().getResource(caminhoRelativo).getPath();
    }
}

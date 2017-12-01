
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Maps 
{  
    private static int tamlim;
    private static int tamcol;
    
    public static char[][] leitura()
    {
        int i,j;
        try
        {
            FileReader fr = new FileReader("map1.txt");
            BufferedReader br = new BufferedReader(fr);
            tamlim = Integer.parseInt(br.readLine());
            tamcol = Integer.parseInt(br.readLine());
            fr.close();
            br.close();
        } 
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Maps.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Maps.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        char map1[][] = new char[tamlim][tamcol]; 
 
        try 
        {
            FileReader fr = new FileReader("map1.txt");
            BufferedReader br = new BufferedReader(fr);
            tamlim = Integer.parseInt(br.readLine());
            tamcol = Integer.parseInt(br.readLine());
            i = 0;
            while (br.ready()) 
            {
                String linha = br.readLine();
                for(j=0;j<tamcol;j++)
                {
                   map1[i][j] = linha.charAt(j);
                }
                i++;
            }
            br.close();
            fr.close();
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println(ex.getMessage());
        } catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        }
  
        return map1;
    }
    
}

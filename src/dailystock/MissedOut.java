/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailystock;

import static dailystock.MainClass.stocks;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ANUJ
 */
public class MissedOut {
    
    static ArrayList<String> price=new ArrayList<String>();
    static ArrayList<String> chg=new ArrayList<String>();
    static ArrayList<String> vlm=new ArrayList<String>();
    //add stocks here in arraylist
    
    static ArrayList<String> stocks=new ArrayList<String>(Arrays.asList("ABB.NS","ABCAPITAL.NS","ACC.BO","ADANIENT.BO","ADANIPOWER.BO","ADANIPORTS.BO","ANDHRABANK.BO","ADLABS.BO","ALBK.BO","AMARAJABAT.NS","AMBUJACEM.BO","APTECHT.BO","ARVIND.BO","ASHOKLEY.NS","ASIANPAINT.BO",
            /*"BAJAJ-AUTO.BO"*/"BATAINDIA.BO","BEL.BO","BEML.BO","BERGEPAINT.BO","BHARTIARTL.NS","BHEL.BO","BIOCON.BO","BANKBARODA.BO","BANKINDIA.BO","BOMDYEING.BO",
            "BPCL.BO","BRITANNIA.BO","CADILAHC.BO","CANBK.BO","CENTURYTEX.BO","CHENNPETRO.BO","CIPLA.BO","COLPAL.BO","DABUR.BO","DENABANK.BO","DISHTV.BO","ENGINERSIN.NS","EXIDEIND.BO","GAIL.BO",
            "GATI.NS","GITANJALI.BO","GODREJIND.BO","GRASIM.BO","HCC.NS","HCLTECH.BO","HDFCBANK.BO","HEROMOTOCO.NS","HINDALCO.BO","HINDUNILVR.BO","HINDPETRO.BO","ICICIBANK.BO","IDEA.BO",
            "IDFC.BO","IDFCBANK.NS","IGL.BO","INDHOTEL.BO","INDUSINDBK.BO","INFY.NS","IOB.BO","IOC.BO","IPCALAB.BO","ITC.BO","J&KBANK.BO","JPINFRATEC.NS","LTTS.NS",
            "L&TFH.BO","MARUTI.BO","MPHASIS.BO","NESTLEIND.BO","NIITLTD.BO","NTPC.NS","ORIENTBANK.BO","ONGC.BO","PETRONET.NS","PIDILITIND.BO","PFC.NS","PNB.NS","PTC.NS","RAYMOND.BO","RELCAPITAL.NS",
            "RELIANCE.BO","SAIL.NS","SBIN.BO","SRF.BO","SUNPHARMA.BO","SUZLON.BO","SYNDIBANK.NS","TATAMOTORS.NS","TATAPOWER.BO","TECHM.NS","TCS.NS","TATASTEEL.NS","TV18BRDCST.BO",
            "TVSMOTOR.BO","VIJAYABANK.BO","VOLTAS.BO","WIPRO.NS","YESBANK.BO","ZEEL.BO","BHARATFORG.BO",
            "BAJFINANCE.BO","BAJAJFINSV.BO","BLUESTARCO.BO","CAPF.BO","CDSL.NS","COCHINSHIP.NS","DHFL.NS","DMART.NS","EDELWEISS.BO","ENDURANCE.NS","EQUITAS.NS","FORCEMOT.BO","GABRIEL.NS","GODREJCP.NS","HAVELLS.NS","HDIL.BO","JISLJALEQS.BO",
            "LICHSGFIN.BO","M&MFIN.NS","NBCC.NS","NMDC.NS","RNAVAL.NS","SIS.BO","TATACHEM.BO","THERMAX.NS","UPL.BO","VIPIND.BO"));
       
    
    public static void missed(String date) 
    {
        Collections.sort(stocks);
        try{
            
            double arr[]=new double[3];
        FileWriter fr=new FileWriter(date+".txt");
        for (int j = 0; j < stocks.size(); j++) {
                
                    System.out.println(stocks.get(j));
                    arr=missingfunc(stocks.get(j),j);
                    fr.write(stocks.get(j)+"\r\n");
                    fr.write(arr[0]+"\r\n");
                    fr.write(arr[1]+"\r\n");
                    fr.write(arr[2]+"\r\n");
              
            }
        
        fr.close();
        
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    static public double[] missingfunc(String s, int n) throws IOException
     {
          int count=0;
          //String[] columns={"Date","Close","Volume"};
          String[][] rows=new String[5][3];
    
         ArrayList<String> list=new ArrayList<>();
         
         Document doc = Jsoup.connect("https://in.finance.yahoo.com/quote/"+s+"/history?period1=1514485800&period2=1514917800&interval=1d&filter=history&frequency=1d")
                 .timeout(10000)
                 .followRedirects(true)
                 .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                 .ignoreHttpErrors(true)
                 .referrer("http://www.google.com")
                 .get();

        String[] a0=new String[10];
        
    for (Element table : doc.select("table")) {
        for (Element row : table.select("tr")) {
            Elements tds = row.select("td");
            if (tds.size()>6 )
            {                
                String text=tds.get(4).text()+":"+tds.get(6).text();
                System.out.println(text);
                a0=text.split(":");
                
                for(int i=0;i<a0.length;i++)
            {
                list.add(a0[i]);
            }
            
              
             }
        }
    }
                for(int i=0;i<list.size();i++)
                {
                    System.out.println(list.get(i));
                }
                
                //int a=0;
                
             String first=list.get(0).replaceAll(",", "");
             double close=Double.parseDouble(first);
             String vol=list.get(1);
             String second=list.get(2).replaceAll(",", "");
             double prevclose=Double.parseDouble(second);
             double change=close-prevclose;
             
             vol=vol.replaceAll(",", "");
             double v=Double.parseDouble(vol);
             
             double arr[]=new double[3];
             arr[0]=close;
             arr[1]=change;
             arr[2]=v;
             
             System.out.println(arr[0]);
             System.out.println(arr[1]);
             System.out.println(arr[2]);
             return arr;
   
           
     }
    
    public static void main(String args[])
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar cal = new GregorianCalendar();
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int month = cal.get(GregorianCalendar.MONTH);
        int year = cal.get(GregorianCalendar.YEAR);
        Date dt=new Date();
        cal.set(year, month, day-1);
        dt=cal.getTime();
        missed(dateFormat.format(dt));
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANUJ
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dailystock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ANUJ
 */
public class MainClass {
    
    static JFrame jf=new JFrame("Stock Quotes");
    
    static JFrame jff=new JFrame("Stock Quotes");
    
    static JPanel jp=new JPanel(new GridLayout(1,2));
    
    static String[] columns={"Code","Price","Change","Volume"};
    
    
    static ArrayList<String> price=new ArrayList<String>();
    static ArrayList<String> chg=new ArrayList<String>();
    static ArrayList<String> vlm=new ArrayList<String>();
    //add stocks here in arraylist
    
    static ArrayList<String> stocks=new ArrayList<String>(Arrays.asList("ABB.NS","ABCAPITAL.NS","ACC.BO","ADANIENT.BO","ADANIPOWER.BO","ADANIPORTS.BO","ANDHRABANK.BO","ADLABS.BO","ALBK.BO","AMARAJABAT.BO","AMBUJACEM.BO","APTECHT.BO","ARVIND.BO","ASHOKLEY.NS","ASIANPAINT.BO",
            "BAJAJ-AUTO.BO","BATAINDIA.BO","BEL.BO","BEML.BO","BERGEPAINT.BO","BHARTIARTL.NS","BHEL.BO","BIOCON.BO","BANKBARODA.BO","BANKINDIA.BO","BOMDYEING.BO",
            "BPCL.BO","BRITANNIA.BO","CADILAHC.BO","CANBK.BO","CENTURYTEX.BO","CHENNPETRO.BO","CIPLA.BO","COLPAL.BO","DABUR.BO","DENABANK.BO","DISHTV.BO","ENGINERSIN.NS","EXIDEIND.BO","GAIL.BO",
            "GATI.NS","GITANJALI.BO","GODREJIND.BO","GRASIM.BO","HCC.NS","HCLTECH.BO","HDFCBANK.BO","HEROMOTOCO.NS","HINDALCO.BO","HINDUNILVR.BO","HINDPETRO.BO","ICICIBANK.BO","IDEA.BO",
            "IDFC.BO","IDFCBANK.NS","IGL.BO","INDHOTEL.BO","INDUSINDBK.BO","INFY.NS","IOB.BO","IOC.BO","IPCALAB.BO","ITC.BO","J&KBANK.BO","JPINFRATEC.NS","LTTS.NS",
            "L&TFH.BO","MARUTI.BO","MPHASIS.BO","NESTLEIND.BO","NIITLTD.BO","NTPC.NS","ORIENTBANK.BO","ONGC.BO","PETRONET.NS","PIDILITIND.BO","PFC.NS","PNB.NS","PTC.NS","RAYMOND.BO","RELCAPITAL.NS",
            "RELIANCE.BO","SAIL.NS","SBIN.BO","SRF.BO","SUNPHARMA.BO","SUZLON.BO","SYNDIBANK.NS","TATAMOTORS.NS","TATAPOWER.BO","TECHM.NS","TCS.NS","TATASTEEL.NS","TV18BRDCST.BO",
            "TVSMOTOR.BO","VIJAYABANK.BO","VOLTAS.BO","WIPRO.NS","YESBANK.BO","ZEEL.BO","BHARATFORG.NS",
            "BAJFINANCE.BO","BAJAJFINSV.BO","BLUESTARCO.BO","CAPF.BO","CDSL.NS","COCHINSHIP.NS","DHFL.NS","DMART.NS","EDELWEISS.BO","ENDURANCE.NS","EQUITAS.NS","FORCEMOT.BO","GABRIEL.NS","GODREJCP.NS","HAVELLS.NS","HDIL.BO","JISLJALEQS.BO",
            "LICHSGFIN.BO","M&MFIN.NS","NBCC.NS","NMDC.NS","RNAVAL.NS","SIS.BO","TATACHEM.BO","THERMAX.NS","UPL.BO","VIPIND.BO"));
    
    static Elements moduleBody = null,sectionC=null,module=null;
        static Element headerQuoteContainer=null;
    
    /**
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException
    {
          
        GridBagConstraints c = new GridBagConstraints();
        JLabel jlbl=new JLabel("    Loading stock quotes...Please Wait!");
               
        jlbl.setPreferredSize(new Dimension(250,250));
        jff.add(jlbl);
        
        jff.setSize(new Dimension(250,250));
        jff.setLocationRelativeTo(null);
        jff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jff.setVisible(true);

        int j=0;
        Collections.sort(stocks);
        
            for (j = 0; j < stocks.size(); j++) {
                try 
                {
                    
                    yahoofin(stocks.get(j),j);
              
            }
            
        
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
            }
        
        String[][] matrix=new String[price.size()][4];
        matrix=display();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        
        GregorianCalendar cl = new GregorianCalendar();
        int days = cl.get(GregorianCalendar.DAY_OF_MONTH);
        int months = cl.get(GregorianCalendar.MONTH);
        int years = cl.get(GregorianCalendar.YEAR);
        
        cl.set(years, months, days);
        int dayweek=cl.get(Calendar.DAY_OF_WEEK);
        
        if(dayweek!=7 && dayweek!=1)
            
        {
            
        
        FileWriter fw=new FileWriter(dateFormat.format(date)+".txt");
        
        for(int k=0;k<price.size();k++)
        {
            for(int m=0;m<4;m++)
            {
            fw.write(matrix[k][m]);
            fw.write("\r\n");
            }
            fw.write("\r\n");
        }
        fw.close();
        
        JTable jt = new JTable(matrix,columns);
        
        TableColumn tc=jt.getColumnModel().getColumn(2);
        tc.setCellRenderer(new TableCustom(2));
        TableColumn t=jt.getColumnModel().getColumn(0);
                    t.setPreferredWidth(200);
        
        TableColumn tcc=jt.getColumnModel().getColumn(1);
                    tcc.setPreferredWidth(80);
        
        jt.setFillsViewportHeight(true);
        jt.setFont(new Font("Arial",Font.BOLD,20));
        jt.setRowHeight(30);
        jp.add(jt);
        BoxLayout box=new BoxLayout(jp,BoxLayout.X_AXIS);
        jp.setLayout(box);
        
        }
        GregorianCalendar cal = new GregorianCalendar();
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int month = cal.get(GregorianCalendar.MONTH);
        int year = cal.get(GregorianCalendar.YEAR);
                    int dy=1,coun=1;
                    Date dt=new Date();
                    String[][]oldtable=new String[stocks.size()][4];
                    
                    while(coun<=5)
                    {

                        cal.set(year, month, day-dy);
                        dt=cal.getTime();
                        int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
                        System.out.println(dayofweek);

                        if(dayofweek!=7 && dayofweek!=1)
                        {
                            try{
                            
                            switch(dayofweek)
                        {
                        
                        case 2:
                            
                            oldtable=fileread(dt);
                            JTable jt2=new JTable(oldtable,columns);

                    TableColumn tc1=jt2.getColumnModel().getColumn(2);
                    tc1.setCellRenderer(new TableCustom(2));
                    TableColumn t1=jt2.getColumnModel().getColumn(0);
                    t1.setPreferredWidth(100);
                    
                    TableColumn tc2=jt2.getColumnModel().getColumn(1);
                    tc2.setPreferredWidth(80);
                    
                    jt2.setFillsViewportHeight(true);
                    jt2.setFont(new Font("Arial",Font.BOLD,20));
                    jt2.setRowHeight(30);
                    
                    //jp.setSize(200,1000);
                    jp.add(jt2);
                    BoxLayout box2=new BoxLayout(jp,BoxLayout.X_AXIS);
                    jp.setLayout(box2);
                        break;
                            
                        case 3:
                            oldtable=fileread(dt);
                            JTable jt3=new JTable(oldtable,columns);

                    TableColumn tc3=jt3.getColumnModel().getColumn(2);
                    tc3.setCellRenderer(new TableCustom(2));
                    TableColumn t3=jt3.getColumnModel().getColumn(0);
                    t3.setPreferredWidth(100);
                    TableColumn tcc3=jt3.getColumnModel().getColumn(1);
                    tcc3.setPreferredWidth(80);
                    
                    jt3.setFillsViewportHeight(true);
                    jt3.setFont(new Font("Arial",Font.BOLD,20));
                    jt3.setRowHeight(30);
                    
                    jp.add(jt3);
                    BoxLayout box1=new BoxLayout(jp,BoxLayout.X_AXIS);
                    jp.setLayout(box1);
                    jp.setSize(200,1000);
                    
                            break;
                            
                        case 4:
                            oldtable=fileread(dt);
                            JTable jt4=new JTable(oldtable,columns);

                    TableColumn tc4=jt4.getColumnModel().getColumn(2);
                    tc4.setCellRenderer(new TableCustom(2));
                    TableColumn t4=jt4.getColumnModel().getColumn(0);
                    t4.setPreferredWidth(100);
                    
                    TableColumn t4c=jt4.getColumnModel().getColumn(1);
                    t4c.setPreferredWidth(80);
                    
                    jt4.setFillsViewportHeight(true);
                    jt4.setFont(new Font("Arial",Font.BOLD,20));
                    jt4.setRowHeight(30);
                    
                    jp.setSize(200,1000);
                    jp.add(jt4);
                    BoxLayout box4=new BoxLayout(jp,BoxLayout.X_AXIS);
                    jp.setLayout(box4);
        
                    
                            break;
                            
                        case 5:
                            oldtable=fileread(dt);
                            JTable jt5=new JTable(oldtable,columns);

                    TableColumn tc5=jt5.getColumnModel().getColumn(2);
                    tc5.setCellRenderer(new TableCustom(2));
                    TableColumn t5=jt5.getColumnModel().getColumn(0);
                    t5.setPreferredWidth(100);
                    
                    TableColumn t5c=jt5.getColumnModel().getColumn(1);
                    t5c.setPreferredWidth(80);
                    
                    jt5.setFillsViewportHeight(true);
                    jt5.setFont(new Font("Arial",Font.BOLD,20));
                    jt5.setRowHeight(30);
                    
                    jp.setSize(200,1000);
                    jp.add(jt5);
                    BoxLayout box5=new BoxLayout(jp,BoxLayout.X_AXIS);
                    jp.setLayout(box5);
                    
                   
                    break;
                    
                        case 6:
                            oldtable=fileread(dt);
                            JTable jt6=new JTable(oldtable,columns);

                    TableColumn tc6=jt6.getColumnModel().getColumn(2);
                    tc6.setCellRenderer(new TableCustom(2));
                    TableColumn t6=jt6.getColumnModel().getColumn(0);
                    t6.setPreferredWidth(200);
                    
                    TableColumn t6c=jt6.getColumnModel().getColumn(1);
                    t6c.setPreferredWidth(80);
                    jt6.setFillsViewportHeight(true);
                    jt6.setFont(new Font("Arial",Font.BOLD,20));
                    jt6.setRowHeight(30);
                    
                    jp.setSize(200,1000);
                    jp.add(jt6);
                    BoxLayout box6=new BoxLayout(jp,BoxLayout.X_AXIS);
                    jp.setLayout(box6);
        
                    
                            break;
                            
                    }
                        }
                            
                            catch (FileNotFoundException x) {
                    
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, x);
                    
                } catch (IOException e) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
                    JOptionPane.showMessageDialog(jf, "Error.");
                }
                  
                                
                    coun++;
                    }
                        dy++;
                        
                    }
                    
                
            
        jff.setVisible(false);
        
        JScrollPane js2=new JScrollPane(jp);
        js2.getVerticalScrollBar().setUnitIncrement(16);
        jf.add(js2);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(new Dimension(5000,5000));
        jf.setVisible(true);
        
        sensex();
        
        
    }
    
    public static void moneycntrl(String url, int j) throws IOException
    {
        String s="";
        Document doc = Jsoup.connect(url).get();
        Elements body=doc.getElementsByClass("main");
        for(Element list:body)
        {
            System.out.println("Text: "+list.text());
            s=list.text();
        }
        
        int bracket=s.indexOf("(")-6;
        String change=s.substring(bracket,bracket+6);
        
        System.out.println("Change: "+change);
        chg.add(j, change);
        
        int v=s.indexOf("VOLUME");
        int avgvol=s.indexOf("AVERAGE");
        
        int live=s.indexOf("LIVE");
        
        String pr=s.substring(live+22,bracket);
        System.out.println("Price: "+pr);
        price.add(j, pr);
        
        String p=s.substring(v+6, avgvol);
        p=p.replaceAll("\\s+", "");
        p=p.replaceAll(",","");
        double v1=Double.parseDouble(p)/1000000;
        DecimalFormat numberFormat = new DecimalFormat("#.000");
        
        String vstr=numberFormat.format(v1);
        System.out.println("Volume: "+p);
        vlm.add(j, vstr);
    
    }
     
     static public String[][] display()
     {
         
         String[][] data=new String[stocks.size()][4];
         
         int i,j;
         for(i=0;i<stocks.size();i++)
         {
             for(j=0;j<4;j++)
             {
                 switch(j)
                 {
                     case 0:
                         data[i][0]=stocks.get(i);
                         break;
                     case 1:
                         data[i][1]=price.get(i)+" ";
                         break;
                     case 2:
                         data[i][2]=chg.get(i);
                         break;
                     case 3:
                         data[i][3]=vlm.get(i);
                         break;
                 }
             }
            
         }         
         return data;
     }
     
     public static void yahoofin(String stock, int j) throws IOException, InterruptedException
     {
         try{
         Document document = Jsoup.connect("https://in.finance.yahoo.com/quote/"+stock+"?p="+stock)
                 .timeout(30000)
                 .followRedirects(true)
                 .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                 .ignoreHttpErrors(true)
                 .referrer("http://www.google.com")
                 .get();
         
         Element content = document.getElementById("app");
        String s=content.text();
        System.out.println(s);
        
        int psign,nsign;
        String chng="",pr="";
        int w=s.indexOf("watchlist");
        String sub=s.substring(w,w+50);
        
        if(sub.contains("-") && sub.contains("+"))
        {
            psign=sub.indexOf("+");
            int obrkt=sub.indexOf("(");
            chng=sub.substring(psign,obrkt);
            int wa=sub.indexOf("watchlist");
            pr=sub.substring(wa+9,psign);
            
        }
        
        else if(sub.contains("+") && !sub.contains("-"))
        {
            psign=sub.indexOf("+");
            int obrkt=sub.indexOf("(");
            chng=sub.substring(psign,obrkt);
            int wa=sub.indexOf("watchlist");
            pr=sub.substring(wa+9,psign);
        }
        else if(sub.contains("-") && !sub.contains("+"))
        {
            nsign=sub.indexOf("-");
            int obrkt=sub.indexOf("(");
            chng=sub.substring(nsign,obrkt);
            int wa=sub.indexOf("watchlist");
            pr=sub.substring(wa+9,nsign);
        }
        
        else
        {
            nsign=sub.indexOf("-");
            chng="0.00";
            int wa=sub.indexOf("watchlist");
            pr=sub.substring(wa+9,(sub.indexOf(chng)+1));
        }
        
        
        int vol=s.indexOf("Volume");
        int avg=s.indexOf("Avg.");
        String volume=s.substring(vol+6,avg);
        
        pr=pr.replaceAll("\\s+", "");
        chng=chng.replaceAll("\\s+", "");
        volume=volume.replaceAll("\\s+", "");
        
        chng=chng.replaceAll("\\s+", "");
        volume=volume.replaceAll(",", "");
        
        double v1=Double.parseDouble(volume)/1000000;
        DecimalFormat numberFormat = new DecimalFormat("#.000");
        
        String vstr=numberFormat.format(v1);
        
        System.out.println("Price "+pr);
        System.out.println("Change "+chng);
        System.out.println("Volume "+vstr);
        price.add(j, pr);
        vlm.add(j, vstr);
        chg.add(j, chng);
         }
         catch(Exception e)
         {
             e.printStackTrace();
             TimeUnit.SECONDS.sleep(1);
             JFrame close=new JFrame("Error");
             
             JLabel jl=new JLabel("Network Error. Pease retry!");
             
             close.add(jl,SwingConstants.CENTER);
             
             close.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             close.setLocationRelativeTo(null);
             close.setSize(new Dimension(150,150));
             close.setVisible(true);             
             
         }
        
    
    }
     
     public static String[][] fileread( Date dt) throws IOException
     {
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         String [][] oldtable=new String[stocks.size()][4];
         System.out.println(dateFormat.format(dt));
                        try
                        {
                            FileReader fr1=new FileReader(dateFormat.format(dt)+".txt");
                            BufferedReader br1=new BufferedReader(fr1);
                            String word="";
                            int cnt=1,index=0;
                    
                        while((word=br1.readLine())!=null)
                            {
                            if(index<stocks.size() && !word.equals("\n") && !word.equals("") && !word.equals(null))
                                {
                                switch (cnt) {
                                    case 1:
                                        oldtable[index][0]=word;
                                        cnt++;
                                        break;
                                    case 2:
                                        oldtable[index][1]=word;
                                        cnt++;
                                        break;
                                    case 3:
                                    oldtable[index][2]=word;
                                    cnt++;
                                    break;
                                    case 4:
                                    //convert to million
                                    String volume=word.replaceAll("\\s+","");
                                    
                                    oldtable[index][3]=volume;
                                    index++;
                                    cnt=1;
                                    break;
                            }
                        
                        }
                            //FileReading finished !!!
                                 
                    }
                    fr1.close();
                    br1.close();
                   
                    for(int z=0;z<stocks.size();z++)
                    {
                        for(int v=0;v<4;v++)
                            System.out.println(oldtable[z][v]);
                        System.out.println();
                    }
                }
                        catch (FileNotFoundException ex) {
                    
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                   
                } catch (IOException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(jf, "Error.");
                }
                    
                    return oldtable;
     }
     
     static public void sensex() throws IOException
     {
          int count=0;
          String[] columns={"Date","Close","Volume"};
          String[][] rows=new String[5][3];
    
         ArrayList<String> list=new ArrayList<>();
         
         Document doc = Jsoup.connect("https://in.finance.yahoo.com/quote/%5EBSESN/history?p=%5EBSESN").get();

        String[] a0=new String[10];
        
    for (Element table : doc.select("table")) {
        for (Element row : table.select("tr")) {
            Elements tds = row.select("td");
            if (tds.size()>6 )
            {                
                String text=tds.get(0).text() + ":" + tds.get(4).text()+":"+tds.get(6).text();
                System.out.println(text);
                a0=text.split(":");
                
                for(int i=0;i<a0.length;i++)
            {
                list.add(a0[i]);
            }
            
              
             }
        }
    }
                for(int i=0;i<15;i++)
                {
                    System.out.println(list.get(i));
                }
                
                int a=0;
                             
   
           for(int i=0;i<list.size();i++)
            {
              if(count<5)
              {
                if(a<3)
                {
                    if(a!=2)
                    {
                    rows[count][a]=list.get(i);
                    a++;
                    }
                    else
                    {
                        rows[count][a]=list.get(i);
                        count++;
                        a=0;
                    }
                    
                }
               
            }
              else
                  break;
              
        }
           
           for(int i=0;i<rows.length;i++)
           {
               for(int j=0;j<3;j++)
                   System.out.print(rows[i][j]+"\t");
               System.out.println();
           }
           
    
   JFrame jf=new JFrame("SENSEX Daily");
   
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        
        JTable jtindex=new JTable(model)
        {
            
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                
                return getValueAt(0, column).getClass();
            }
        };
        
        jtindex.setRowHeight(40);
        
        
                    JPanel jp=new JPanel();
                    jp.setSize(200,1000);
                    jp.add(jtindex);
                    BoxLayout box6=new BoxLayout(jp,BoxLayout.X_AXIS);
                    jp.setLayout(box6);
                    
   jtindex.setPreferredScrollableViewportSize(jtindex.getPreferredSize());
   jtindex.setFont(new Font("Arial",Font.BOLD,30));
   JScrollPane jsindex=new JScrollPane(jp);
        jsindex.getVerticalScrollBar().setUnitIncrement(16);
        jf.add(jsindex);
   jf.add(jsindex);
   jf.setLocationRelativeTo(null);
   //jf.pack();
   jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   jf.setSize(new Dimension(500,500));
   jf.setVisible(true);
     }
     
     
}
     

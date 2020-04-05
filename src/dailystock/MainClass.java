/*
 * MinimalDev Copyrights Reserved 2018
 */

package dailystock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
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
 * @author ANUJ.PANT
 */
public class MainClass {

    static JFrame jf = new JFrame("Stock Quotes");

    static JFrame jff = new JFrame("Stock Quotes");

    static JPanel jp = new JPanel(new GridLayout(1, 2));

    static String[] columns = {"Code", "Price", "Change", "Volume"};

    static ArrayList<String> price = new ArrayList<String>();
    static ArrayList<String> chg = new ArrayList<String>();
    static ArrayList<String> vlm = new ArrayList<String>();
    //add stocks here in arraylist

    static LinkedList<String> stocks = new LinkedList<String>(Arrays.asList("3MINDIA.NS", "AARTIIND.NS", "AAVAS.NS", "ABBOTINDIA.NS", "ACC.NS", "ADANIGAS.NS", 
            "ADANIGREEN.NS", "ADANIPORTS.NS", "ADANIPOWER.NS", "ADANITRANS.NS", "ABCAPITAL.NS", "ABFRL.NS", "ADVENZYMES.NS", "AEGISCHEM.NS", "AFFLE.NS",
            "AIAENG.NS", "AJANTPHARM.NS", "AKZOINDIA.NS", "APLLTD.NS", "ALKEM.NS", "ALLCARGO.NS", "AMARAJABAT.NS", "AMBER.NS", "AMBUJACEM.NS", "APLAPOLLO.NS",
            "APOLLOHOSP.NS", "APOLLOTYRE.NS", "ARVINDFASN.NS", "ASAHIINDIA.NS", "ASHOKLEY.NS", "ASHOKA.NS", "ASIANPAINT.NS", "ASTERDM.NS", 
            "ASTRAL.NS", "ASTRAZEN.NS", "ATUL.NS", "AUBANK.NS", "AUROPHARMA.NS", "AVANTIFEED.NS", "DMART.NS", "AXISBANK.NS", 
            "BAJAJ-AUTO.NS", "BAJAJCON.NS", "BAJAJELEC.NS", "BAJFINANCE.NS", "BAJAJFINSV.NS", "BAJAJHLDNG.NS", "BALKRISIND.NS", "BALMLAWRIE.NS", 
            "BALRAMCHIN.NS", "BANDHANBNK.NS", "BANKBARODA.NS", "BANKINDIA.NS", "MAHABANK.NS", "BASF.NS", "BATAINDIA.NS", "BAYERCROP.NS", 
            "BEML.NS", "BERGEPAINT.NS", "BDL.NS", "BEL.NS", "BHARATFORG.NS", "BHEL.NS", "BPCL.NS", "BHARTIARTL.NS", 
            "INFRATEL.NS", "BIOCON.NS", "BIRLACORPN.NS", "BSOFT.NS", "BLISSGVS.NS", "BLUEDART.NS", "BLUESTARCO.NS", "BBTC.NS", 
            "BOMDYEING.NS", "BOSCHLTD.NS", "BRIGADE.NS", "BRITANNIA.NS", "BSE.NS", "CADILAHC.NS", "CANFINHOME.NS", "CANBK.NS", 
            "CAPLIPOINT.NS", "CGCL.NS", "CARBORUNIV.NS", "CARERATING.NS", "CASTROLIND.NS", "CCL.NS", "CEATLTD.NS", "CENTRALBK.NS", 
            "CDSL.NS", "CENTURYPLY.NS", "CERA.NS", "CESC.NS", "CHALET.NS", "CHAMBLFERT.NS", "CHENNPETRO.NS", "CHOLAFIN.NS", 
            "CIPLA.NS", "CUB.NS", "COALINDIA.NS", "COCHINSHIP.NS", "COLPAL.NS", "CONCOR.NS", "COROMANDEL.NS", "CREDITACC.NS", 
            "CRISIL.NS", "CROMPTON.NS", "CUMMINSIND.NS", "CYIENT.NS", "DABUR.NS", "DALBHARAT.NS", "DBCORP.NS", "DCBBANK.NS", 
            "DCMSHRIRAM.NS", "DEEPAKNTR.NS", "DELTACORP.NS", "DHFL.NS", "DBL.NS", "DISHTV.NS", "DCAL.NS", "DIVISLAB.NS", 
            "DIXON.NS", "DLF.NS", "LALPATHLAB.NS", "DRREDDY.NS", "EIDPARRY.NS", "ECLERX.NS", "EDELWEISS.NS", "EICHERMOT.NS", 
            "EIHOTEL.NS", "ELGIEQUIP.NS", "EMAMILTD.NS", "ENDURANCE.NS", "ENGINERSIN.NS", "EQUITAS.NS", "ERIS.NS", "ESCORTS.NS", 
            "ESSELPACK.NS", "EXIDEIND.NS", "FDC.NS", "FMGOETZE.NS", "FINEORG.NS", "FINCABLES.NS", "FSL.NS", "FORTIS.NS", 
            "FCONSUMER.NS", "FLFL.NS", "FRETAIL.NS", "GAIL.NS", "GALAXYSURF.NS", "GARFIBRES.NS", "GAYAPROJ.NS", "GEPIL.NS", 
            "GET&D.NS", "GICRE.NS", "GHCL.NS", "GILLETTE.NS", "GLAXO.NS", "GLENMARK.NS", "GMRINFRA.NS", "GODFRYPHLP.NS", 
            "GODREJAGRO.NS", "GODREJCP.NS", "GODREJIND.NS", "GODREJPROP.NS", "GRANULES.NS", "GRAPHITE.NS", "GRASIM.NS", "GREAVESCOT.NS", 
            "GRINDWELL.NS", "GUJALKALI.NS", "GUJGASLTD.NS", "GMDCLTD.NS", "GNFC.NS", "GPPL.NS", "GSFC.NS", "GSPL.NS", 
            "GULFOILLUB.NS", "HATSUN.NS", "HAVELLS.NS", "HCLTECH.NS", "HDFCAMC.NS", "HDFCBANK.NS", "HDFCLIFE.NS", "HEG.NS", 
            "HEIDELBERG.NS", "HERITGFOOD.NS", "HEROMOTOCO.NS", "HEXAWARE.NS", "HFCL.NS", "HSCL.NS", "HIMATSEIDE.NS", "HINDALCO.NS", "HAL.NS", "HINDCOPPER.NS", 
            "HINDPETRO.NS", "HINDUNILVR.NS", "HINDZINC.NS", "HONAUT.NS", "HUDCO.NS", "HDFC.NS", "ICICIBANK.NS", "ICICIGI.NS", "ICICIPRULI.NS", "ISEC.NS", 
            "ICRA.NS", "IDBI.NS", "IDFCFIRSTB.NS", "IFBIND.NS", "IFCI.NS", "IIFL.NS", "ITDC.NS", "IBULHSGFIN.NS", "IBULISL.NS", "IBREALEST.NS", "IBVENTURES.NS", 
            "INDIAMART.NS", "INDIANB.NS", "IEX.NS", "IOC.NS", "IOB.NS", "INDOSTAR.NS", "IGL.NS", "INDUSINDBK.NS", "INFIBEAM.NS", "NAUKRI.NS", "INFY.NS", "INOXLEISUR.NS", 
            "INTELLECT.NS", "INDIGO.NS", "IPCALAB.NS", "IRB.NS", "IRCON.NS", "ITC.NS", "ITDCEM.NS", "ITI.NS", "JAGRAN.NS", "JAICORPLTD.NS", "JISLJALEQS.NS", "JAMNAAUTO.NS", 
            "JBCHEPHARM.NS", "JINDALSAW.NS", "JSLHISAR.NS", "JSL.NS", "JINDALSTEL.NS", "JKCEMENT.NS", "JKLAKSHMI.NS", "JKPAPER.NS", "JKTYRE.NS", 
            "JMFINANCIL.NS", "JCHAC.NS", "JSWENERGY.NS", "JSWSTEEL.NS", "JUBLFOOD.NS", "JUBILANT.NS", "JUSTDIAL.NS", "JYOTHYLAB.NS", "KAJARIACER.NS", 
            "KALPATPOWR.NS", "KANSAINER.NS", "KARURVYSYA.NS", "KSCL.NS", "KEC.NS", "KEI.NS", "KENNAMET.NS", "KIRLOSENG.NS", "KNRCON.NS", 
            "KOLTEPATIL.NS", "KOTAKBANK.NS", "KPITTECH.NS", "KPRMILL.NS", "KRBL.NS", "L&TFH.NS", "LTTS.NS", "LAXMIMACH.NS", "LTI.NS", 
            "LT.NS", "LAURUSLABS.NS", "LEMONTREE.NS", "LICHSGFIN.NS", "LINDEINDIA.NS", "LUPIN.NS", "LUXIND.NS", "MAGMA.NS", "MGL.NS", 
            "MAHSCOOTER.NS", "MAHSEAMLES.NS", "M&MFIN.NS", "M&M.NS", "MAHINDCIE.NS", "MHRIL.NS", "MAHLOG.NS", "MANAPPURAM.NS", "MRPL.NS", "MARICO.NS", "MARUTI.NS", 
            "MASFIN.NS", "MFSL.NS", "METROPOLIS.NS", "MINDACORP.NS", "MINDAIND.NS", "MINDTREE.NS", "MIDHANI.NS", "MMTC.NS", "MOIL.NS", "MOTHERSUMI.NS", "MOTILALOFS.NS", 
            "MPHASIS.NS", "MRF.NS", "MCX.NS", "MUTHOOTFIN.NS", "NH.NS", "NATCOPHARM.NS", "NATIONALUM.NS", "NFL.NS", "NBVENTURES.NS", "NAVINFLUOR.NS", "NBCC.NS", 
            "NCC.NS", "NESCO.NS", "NESTLEIND.NS", "NETWORK18.NS", "NHPC.NS", "NIITTECH.NS", "NILKAMAL.NS", "NAM-INDIA.NS", "NLCINDIA.NS", "NMDC.NS", "NTPC.NS", 
            "OBEROIRLTY.NS", "ONGC.NS", "OIL.NS", "OMAXE.NS", "OFSS.NS", "ORIENTCEM.NS", "ORIENTELEC.NS", "ORIENTREF.NS", "PAGEIND.NS", "PARAGMILK.NS", "PCJEWELLER.NS", 
            "PERSISTENT.NS", "PETRONET.NS", "PFIZER.NS", "PHILIPCARB.NS", "PHOENIXLTD.NS", "PIIND.NS", "PIDILITIND.NS", "PEL.NS", "PNBHOUSING.NS", 
            "PNCINFRA.NS", "POLYCAB.NS", "PFC.NS", "POWERGRID.NS", "PRAJIND.NS", "PRESTIGE.NS", "PRSMJOHNSN.NS", "PGHL.NS", "PGHH.NS", 
            "PTC.NS", "PNB.NS", "PVR.NS", "QUESS.NS", "RADICO.NS", "RVNL.NS", "RAIN.NS", "RAJESHEXPO.NS", "RALLIS.NS", 
            "RCF.NS", "RATNAMANI.NS", "RAYMOND.NS", "RBLBANK.NS", "RECLTD.NS", "REDINGTON.NS", "RELAXO.NS", "RELCAPITAL.NS", "RELIANCE.NS", 
            "RELINFRA.NS", "RPOWER.NS", "REPCOHOME.NS", "RESPONIND.NS", "RITES.NS", "SADBHAV.NS", "SANOFI.NS", "SBILIFE.NS", "SCHAEFFLER.NS", 
            "SIS.NS", "SHK.NS", "SFL.NS", "SHILPAMED.NS", "SHOPERSTOP.NS", "SHREECEM.NS", "RENUKA.NS", "SHRIRAMCIT.NS", 
            "SRTRANSFIN.NS", "SIEMENS.NS", "SJVN.NS", "SKFINDIA.NS", "SOBHA.NS", "SOLARINDS.NS", "SONATSOFTW.NS", "SPANDANA.NS", 
            "SPICEJET.NS", "SRF.NS", "STARCEMENT.NS", "SBIN.NS", "SAIL.NS", "SWSOLAR.NS", "STRTECH.NS", "STAR.NS", 
            "SUDARSCHEM.NS", "SPARC.NS", "SUNPHARMA.NS", "SUNTV.NS", "SUNDARMFIN.NS", "SUNCLAYLTD.NS", "SUNDRMFAST.NS", "SUNTECK.NS", 
            "SUPRAJIT.NS", "SUPREMEIND.NS", "SUZLON.NS", "SWANENERGY.NS", "SYMPHONY.NS", "SYNGENE.NS" , "TCIEXP.NS" , "TCNSBRANDS.NS" , "TTKPRESTIG.NS" , 
            "TVTODAY.NS" , "TV18BRDCST.NS" , "TVSMOTOR.NS" , "TAKE.NS" , "TASTYBITE.NS" , "TCS.NS" , "TATACONSUM.NS" , "TATAELXSI.NS" , "TATAINVEST.NS" , 
            "TATAMTRDVR.NS" , "TATAMOTORS.NS" , "TATAPOWER.NS" , "TATASTLBSL.NS" , "TATASTEEL.NS" , "TEAMLEASE.NS" , "SYNGENE.NS" , "TECHM.NS" , "TECHNOE.NS" , 
            "NIACL.NS" , "RAMCOCEM.NS" , "THERMAX.NS" , "THYROCARE.NS" , "TIMETECHNO.NS" , "SYNGENE.NS" , "TIMKEN.NS" , "TITAN.NS" , "TORNTPHARM.NS" , 
            "TORNTPOWER.NS" , "TRENT.NS" , "TRIDENT.NS" , "TRITURBINE.NS" , "TIINDIA.NS" , "UCOBANK.NS" , "UFLEX.NS" , "UPL.NS" , "UJJIVAN.NS" , 
            "ULTRACEMCO.NS" , "UNIONBANK.NS" , "UBL.NS" , "MCDOWELL-N.NS" , "VGUARD.NS" , "VMART.NS" , "VIPIND.NS" , "VRLLOG.NS" , "VSTIND.NS" , 
            "WABAG.NS" , "VAIBHAVGBL.NS" , "VAKRANGEE.NS" , "VTL.NS" , "VARROC.NS" , "VBL.NS" , "VEDL.NS" , "VENKEYS.NS" , "VINATIORGA.NS" , 
            "IDEA.NS" , "VOLTAS.NS" , "WABCOINDIA.NS" , "WELCORP.NS" , "WELSPUNIND.NS" , "WESTLIFE.NS" , "WHIRLPOOL.NS" , "WIPRO.NS" , "WOCKPHARMA.NS" , 
            "ZEEL.NS" , "ZENSARTECH.NS" , "ZYDUSWELL.NS" , "ECLERX.NS" , "YESBANK.NS" , "SPENCERS.NS" , "BORORENEW.NS" , "APARINDS.NS" , "BIRLAMONEY.NS"));

    static Elements moduleBody = null, sectionC = null, module = null;
    static Element headerQuoteContainer = null;

    /**
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {

        GridBagConstraints c = new GridBagConstraints();
        JLabel jlbl = new JLabel("    Loading stock quotes...Please Wait");

        jlbl.setPreferredSize(new Dimension(250, 250));
        jff.add(jlbl);

        jff.setSize(new Dimension(250, 250));
        jff.setLocationRelativeTo(null);
        jff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jff.setVisible(true);

        int j;
        Collections.sort(stocks);

        for (j = 0; j < stocks.size(); j++) {
            try {

                yahoofin(stocks.get(j), j);

            } catch (Exception e) {
                System.out.println("Exception occured : " + e);

            }
        }

        String[][] matrix;
        matrix = display();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        GregorianCalendar cl = new GregorianCalendar();
        int days = cl.get(GregorianCalendar.DAY_OF_MONTH);
        int months = cl.get(GregorianCalendar.MONTH);
        int years = cl.get(GregorianCalendar.YEAR);

        cl.set(years, months, days);
        int dayweek = cl.get(Calendar.DAY_OF_WEEK);

        if (dayweek!=7 && dayweek!=1) {

            try (FileWriter fw = new FileWriter(dateFormat.format(date) + ".txt")) {
                for (int k = 0; k < price.size(); k++) {
                    for (int m = 0; m < 4; m++) {
                        fw.write(matrix[k][m]);
                        fw.write("\r\n");
                    }
                    fw.write("\r\n");
                }
            }

            JTable jt = new JTable(matrix, columns);

            TableColumn tc = jt.getColumnModel().getColumn(2);
            tc.setCellRenderer(new TableCustom(2));
            TableColumn t = jt.getColumnModel().getColumn(0);
            t.setPreferredWidth(200);

            TableColumn tcc = jt.getColumnModel().getColumn(1);
            tcc.setPreferredWidth(80);

            jt.setFillsViewportHeight(true);
            jt.setFont(new Font("Arial", Font.BOLD, 20));
            jt.setRowHeight(50);
            jt.setSize(100, 50);
            jp.add(jt);
            BoxLayout box = new BoxLayout(jp, BoxLayout.X_AXIS);
            jp.setLayout(box);

        }
        GregorianCalendar cal = new GregorianCalendar();
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int month = cal.get(GregorianCalendar.MONTH);
        int year = cal.get(GregorianCalendar.YEAR);
        int dy = 1, coun = 1;
        Date dt;
        String[][] oldtable;

        while (coun <= 5) {

            cal.set(year, month, day - dy);
            dt = cal.getTime();
            int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println(dayofweek);

            if (dayofweek != 7 && dayofweek != 1) {
                try {

                    switch (dayofweek) {

                        case 2:

                            oldtable = fileread(dt);
                            JTable jt2 = new JTable(oldtable, columns);

                            TableColumn tc1 = jt2.getColumnModel().getColumn(2);
                            tc1.setCellRenderer(new TableCustom(2));
                            TableColumn t1 = jt2.getColumnModel().getColumn(0);
                            t1.setPreferredWidth(100);

                            TableColumn tc2 = jt2.getColumnModel().getColumn(1);
                            tc2.setPreferredWidth(80);

                            jt2.setFillsViewportHeight(true);
                            jt2.setFont(new Font("Arial", Font.BOLD, 20));
                            jt2.setRowHeight(50);

                            //jp.setSize(200,1000);
                            jp.add(jt2);
                            BoxLayout box2 = new BoxLayout(jp, BoxLayout.X_AXIS);
                            jp.setLayout(box2);
                            break;

                        case 3:
                            oldtable = fileread(dt);
                            JTable jt3 = new JTable(oldtable, columns);

                            TableColumn tc3 = jt3.getColumnModel().getColumn(2);
                            tc3.setCellRenderer(new TableCustom(2));
                            TableColumn t3 = jt3.getColumnModel().getColumn(0);
                            t3.setPreferredWidth(100);
                            TableColumn tcc3 = jt3.getColumnModel().getColumn(1);
                            tcc3.setPreferredWidth(80);

                            jt3.setFillsViewportHeight(true);
                            jt3.setFont(new Font("Arial", Font.BOLD, 20));
                            jt3.setRowHeight(50);

                            jp.add(jt3);
                            BoxLayout box1 = new BoxLayout(jp, BoxLayout.X_AXIS);
                            jp.setLayout(box1);
                            jp.setSize(200, 1000);

                            break;

                        case 4:
                            oldtable = fileread(dt);
                            JTable jt4 = new JTable(oldtable, columns);

                            TableColumn tc4 = jt4.getColumnModel().getColumn(2);
                            tc4.setCellRenderer(new TableCustom(2));
                            TableColumn t4 = jt4.getColumnModel().getColumn(0);
                            t4.setPreferredWidth(100);

                            TableColumn t4c = jt4.getColumnModel().getColumn(1);
                            t4c.setPreferredWidth(80);

                            jt4.setFillsViewportHeight(true);
                            jt4.setFont(new Font("Arial", Font.BOLD, 20));
                            jt4.setRowHeight(50);

                            jp.setSize(200, 1000);
                            jp.add(jt4);
                            BoxLayout box4 = new BoxLayout(jp, BoxLayout.X_AXIS);
                            jp.setLayout(box4);

                            break;

                        case 5:
                            oldtable = fileread(dt);
                            JTable jt5 = new JTable(oldtable, columns);

                            TableColumn tc5 = jt5.getColumnModel().getColumn(2);
                            tc5.setCellRenderer(new TableCustom(2));
                            TableColumn t5 = jt5.getColumnModel().getColumn(0);
                            t5.setPreferredWidth(100);

                            TableColumn t5c = jt5.getColumnModel().getColumn(1);
                            t5c.setPreferredWidth(80);

                            jt5.setFillsViewportHeight(true);
                            jt5.setFont(new Font("Arial", Font.BOLD, 20));
                            jt5.setRowHeight(50);

                            jp.setSize(200, 1000);
                            jp.add(jt5);
                            BoxLayout box5 = new BoxLayout(jp, BoxLayout.X_AXIS);
                            jp.setLayout(box5);

                            break;

                        case 6:
                            oldtable = fileread(dt);
                            JTable jt6 = new JTable(oldtable, columns);

                            TableColumn tc6 = jt6.getColumnModel().getColumn(2);
                            tc6.setCellRenderer(new TableCustom(2));
                            TableColumn t6 = jt6.getColumnModel().getColumn(0);
                            t6.setPreferredWidth(200);

                            TableColumn t6c = jt6.getColumnModel().getColumn(1);
                            t6c.setPreferredWidth(80);
                            jt6.setFillsViewportHeight(true);
                            jt6.setFont(new Font("Arial", Font.BOLD, 20));
                            jt6.setRowHeight(50);

                            jp.setSize(200, 1000);
                            jp.add(jt6);
                            BoxLayout box6 = new BoxLayout(jp, BoxLayout.X_AXIS);
                            jp.setLayout(box6);

                            break;

                    }
                } catch (FileNotFoundException x) {

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

        JScrollPane js2 = new JScrollPane(jp);
        js2.getVerticalScrollBar().setUnitIncrement(16);
        jf.add(js2);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(new Dimension(5000, 5000));
        jf.setVisible(true);

        sensex();

    }

    public static void moneycntrl(String url, int j) throws IOException {
        String s = "";
        Document doc = Jsoup.connect(url).get();
        Elements body = doc.getElementsByClass("main");
        for (Element list : body) {
            System.out.println("Text: " + list.text());
            s = list.text();
        }

        int bracket = s.indexOf("(") - 6;
        String change = s.substring(bracket, bracket + 6);

        System.out.println("Change: " + change);
        chg.add(j, change);

        int v = s.indexOf("VOLUME");
        int avgvol = s.indexOf("AVERAGE");

        int live = s.indexOf("LIVE");

        String pr = s.substring(live + 22, bracket);
        System.out.println("Price: " + pr);
        price.add(j, pr);

        String p = s.substring(v + 6, avgvol);
        p = p.replaceAll("\\s+", "");
        p = p.replaceAll(",", "");
        double v1 = Double.parseDouble(p) / 1000000;
        DecimalFormat numberFormat = new DecimalFormat("#.000");

        String vstr = numberFormat.format(v1);
        System.out.println("Volume: " + p);
        vlm.add(j, vstr);

    }

    static public String[][] display() {

        String[][] data = new String[stocks.size()][4];

        int i, j;
        for (i = 0; i < stocks.size(); i++) {
            for (j = 0; j < 4; j++) {
                switch (j) {
                    case 0:
                        data[i][0] = stocks.get(i);
                        break;
                    case 1:
                        data[i][1] = price.get(i) + " ";
                        break;
                    case 2:
                        data[i][2] = chg.get(i);
                        break;
                    case 3:
                        data[i][3] = vlm.get(i);
                        break;
                }
            }

        }
        return data;
    }

    public static void yahoofin(String stock, int j) throws IOException, InterruptedException {
        String priceChange, stockPrice, copyPrice, vstr;
        try {
            stock = stock.replaceAll("\\s+", "");
            Document document = Jsoup.connect("https://in.finance.yahoo.com/quote/" + stock + "?p=" + stock)
                    .timeout(100000)
                    .followRedirects(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .ignoreHttpErrors(true)
                    .referrer("http://www.google.com")
                    .get();

            Element content = document.getElementById("app");
            String response = content.text();
            System.out.println(response);
            int psign, nsign;
            
            int indexAfterStock = response.indexOf(stock) + stock.length();
            indexAfterStock += 45;  //cover ) NSE - NSE Real Time Price. Currency in INR 
            stockPrice = response.substring(indexAfterStock);
            stockPrice = stockPrice.substring(0, 70);
            copyPrice = stockPrice;
            int indexForPositiveOrNegative = stockPrice.indexOf("+");
            if(indexForPositiveOrNegative == -1){
                indexForPositiveOrNegative = stockPrice.indexOf("-");
            }
            if(indexForPositiveOrNegative == -1){
                indexForPositiveOrNegative = stockPrice.indexOf("0.00");
            }
            copyPrice = copyPrice.substring(indexForPositiveOrNegative);
            int indexPercentChange = copyPrice.indexOf("(");
            priceChange = copyPrice.substring(0, indexPercentChange - 1);
            stockPrice = stockPrice.substring(0, indexForPositiveOrNegative);
//            String sub = s.substring(w, w + 50);
//
//            if (sub.contains("-") && sub.contains("+")) {
//                psign = sub.indexOf("+");
//                int obrkt = sub.indexOf("(");
//                chng = sub.substring(psign, obrkt);
//                int wa = sub.indexOf("watchlist");
//                pr = sub.substring(wa + 9, psign);
//
//            } else if (sub.contains("+") && !sub.contains("-")) {
//                psign = sub.indexOf("+");
//                int obrkt = sub.indexOf("(");
//                chng = sub.substring(psign, obrkt);
//                int wa = sub.indexOf("watchlist");
//                pr = sub.substring(wa + 9, psign);
//            } else if (sub.contains("-") && !sub.contains("+")) {
//                nsign = sub.indexOf("-");
//                int obrkt = sub.indexOf("(");
//                chng = sub.substring(nsign, obrkt);
//                int wa = sub.indexOf("watchlist");
//                pr = sub.substring(wa + 9, nsign);
//            } else {
//                nsign = sub.indexOf("-");
//                chng = "0.00";
//                int wa = sub.indexOf("watchlist");
//                pr = sub.substring(wa + 9, (sub.indexOf(chng) + 1));
//            }

            int vol = response.indexOf("Volume");
            int avg = response.indexOf("Avg.");
            String volume = response.substring(vol + 6, avg);
            
            stockPrice = stockPrice.replaceAll("\\s+", "");
            priceChange = priceChange.replaceAll("\\s+", "");
            volume = volume.replaceAll("\\s+", "");

            priceChange = priceChange.replaceAll("\\s+", "");
            volume = volume.replaceAll(",", "");

            double v1 = Double.parseDouble(volume) / 1000000;
            DecimalFormat numberFormat = new DecimalFormat("#.000");

            vstr = numberFormat.format(v1);

            System.out.println("Price " + stockPrice);
            System.out.println("Change " + priceChange);
            System.out.println("Volume " + vstr);
            price.add(j, stockPrice);
            vlm.add(j, vstr);
            chg.add(j, priceChange);
        } catch (Exception e) {
            System.out.println("Exception occured : " + e);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Price ");
            System.out.println("Change ");
            System.out.println("Volume ");
            price.add(j, "N/A");
            vlm.add(j, "N/A");
            chg.add(j, "N/A");
        }
    }

    public static String[][] fileread(Date dt) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[][] oldtable = new String[stocks.size()][4];
        System.out.println(dateFormat.format(dt));
        try {
            BufferedReader br1;
            try (FileReader fr1 = new FileReader(dateFormat.format(dt) + ".txt")) {
                br1 = new BufferedReader(fr1);
                String word;
                int cnt = 1, index = 0;
                while ((word = br1.readLine()) != null) {
                    if (!word.equals("") && index < stocks.size() && !word.equals("\n")) {
                        switch (cnt) {
                            case 1:
                                oldtable[index][0] = word;
                                cnt++;
                                break;
                            case 2:
                                oldtable[index][1] = word;
                                cnt++;
                                break;
                            case 3:
                                oldtable[index][2] = word;
                                cnt++;
                                break;
                            case 4:
                                //convert to million
                                String volume = word.replaceAll("\\s+", "");
                                
                                oldtable[index][3] = volume;
                                index++;
                                cnt = 1;
                                break;
                        }
                    } else {
                    }
                    //FileReading finished !!!
                    
                }
            }
            br1.close();

            for (int z = 0; z < stocks.size(); z++) {
                for (int v = 0; v < 4; v++) {
                    System.out.println(oldtable[z][v]);
                }
                System.out.println();
            }
        } catch (FileNotFoundException ex) {

            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(jf, "Error.");
        }

        return oldtable;
    }

    static public void sensex() throws IOException {
        int count = 0;
        String[] columns = {"Date", "Close", "Volume"};
        String[][] rows = new String[5][3];

        ArrayList<String> list = new ArrayList<>();

        Document doc = Jsoup.connect("https://in.finance.yahoo.com/quote/%5EBSESN/history?p=%5EBSESN").get();

        String[] a0;

        for (Element table : doc.select("table")) {
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                if (tds.size() > 6) {
                    String text = tds.get(0).text() + ":" + tds.get(4).text() + ":" + tds.get(6).text();
                    System.out.println(text);
                    a0 = text.split(":");
                    list.addAll(Arrays.asList(a0));
                }
            }
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(list.get(i));
        }

        int a = 0;

        for (int i = 0; i < list.size(); i++) {
            if (count < 5) {
                if (a < 3) {
                    if (a != 2) {
                        rows[count][a] = list.get(i);
                        a++;
                    } else {
                        rows[count][a] = list.get(i);
                        count++;
                        a = 0;
                    }

                }

            } else {
                break;
            }

        }

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(rows[i][j] + "\t");
            }
            System.out.println();
        }

        JFrame jf = new JFrame("SENSEX Daily");

        DefaultTableModel model = new DefaultTableModel(rows, columns);

        JTable jtindex = new JTable(model) {

            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column) {

                return getValueAt(0, column).getClass();
            }
        };

        jtindex.setRowHeight(40);

        JPanel jp = new JPanel();
        jp.setSize(200, 1000);
        jp.add(jtindex);
        BoxLayout box6 = new BoxLayout(jp, BoxLayout.X_AXIS);
        jp.setLayout(box6);

        jtindex.setPreferredScrollableViewportSize(jtindex.getPreferredSize());
        jtindex.setFont(new Font("Arial", Font.BOLD, 30));
        JScrollPane jsindex = new JScrollPane(jp);
        jsindex.getVerticalScrollBar().setUnitIncrement(16);
        jf.add(jsindex);
        jf.add(jsindex);
        jf.setLocationRelativeTo(null);
        //jf.pack();
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(new Dimension(500, 500));
        jf.setVisible(true);
    }

}

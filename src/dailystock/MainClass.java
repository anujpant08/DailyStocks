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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    static LinkedList<String> stocks = new LinkedList<String>(Arrays.asList(""));

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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        GregorianCalendar cl = new GregorianCalendar();
        int days = cl.get(GregorianCalendar.DAY_OF_MONTH);
        int months = cl.get(GregorianCalendar.MONTH);
        int years = cl.get(GregorianCalendar.YEAR);

        cl.set(years, months, days);
        int dayweek = cl.get(Calendar.DAY_OF_WEEK);
        File currentFile = null;
        Date lastCurrDate = new Date();
        if(dayweek == 7){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_WEEK, -1);
            lastCurrDate = calendar.getTime();
        }else if(dayweek == 1){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_WEEK, 1);
            lastCurrDate = calendar.getTime();
        }
        currentFile = new File(dateFormat.format(lastCurrDate) + ".txt");
        String[][] matrix = new String[stocks.size()][4];
        if(!currentFile.exists()){
            for (j = 0; j < stocks.size(); j++) {
                try {
                    yahoofin(stocks.get(j), j);
                } catch (Exception e) {
                    System.out.println("Exception occured : " + e);

                }
            }
        matrix = display();
        }



        JTable jt = null;
        if (dayweek!=7 && dayweek!=1) {

            if(currentFile != null && currentFile.exists()){
                //file exists
                System.out.println("Current file exists");
                jt = new JTable(fileread(new Date()), columns);
            }else {
                try (FileWriter fw = new FileWriter(dateFormat.format(date) + ".txt")) {
                    for (int k = 0; k < price.size(); k++) {
                        for (int m = 0; m < 4; m++) {
                            fw.write(matrix[k][m]);
                            fw.write("\r\n");
                        }
                        fw.write("\r\n");
                    }
                }
                jt = new JTable(matrix, columns);
            }

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
                            jt2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
                            jt3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
                            jt4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
                            jt5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
                            jt6.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
            Document document = Jsoup.connect("https://groww.in/v1/api/stocks_data/v1/accord_points/exchange/NSE/segment/CASH/latest_prices_ohlc/" + stock.substring(0, stock.length() - 3))
                    .timeout(100000)
                    .followRedirects(true)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .referrer("http://www.google.com")
                    .get();

            String response = document.body().text();
            System.out.println(response);
            Pattern stockPricePattern = Pattern.compile("\"ltp\":([^,]+),\"volume\":([^,]+),[^,]+,[^,]+,[^,]+,[^,]+,[^,]+,\"dayChange\":([^,]+)");
            Matcher stockPriceMatcher = stockPricePattern.matcher(response);
            if(stockPriceMatcher.find()){
                stockPrice = stockPriceMatcher.group(1);
                vstr = stockPriceMatcher.group(2);
                priceChange = stockPriceMatcher.group(3);
                if(!priceChange.startsWith("-") & Double.parseDouble(priceChange) != 0.0){
                    priceChange = "+" + priceChange;
                }
            }else{
                stockPrice = "0.0";
                priceChange = "0.0";
                vstr = "0.0";
            }
//            int indexAfterStock = response.indexOf(stock) + stock.length();
//            indexAfterStock += 62;  //cover ) NSE - NSE Real Time Price. Currency in INR
//            stockPrice = response.substring(indexAfterStock);
//            stockPrice = stockPrice.substring(0, 70);
//            copyPrice = stockPrice;
//            int indexForPositiveOrNegative = stockPrice.indexOf("+");
//            if(indexForPositiveOrNegative == -1){
//                indexForPositiveOrNegative = stockPrice.indexOf("-");
//            }
//            if(indexForPositiveOrNegative == -1){
//                indexForPositiveOrNegative = stockPrice.indexOf("0.00");
//            }
//            copyPrice = copyPrice.substring(indexForPositiveOrNegative);
//            int indexPercentChange = copyPrice.indexOf("(");
//            priceChange = copyPrice.substring(0, indexPercentChange - 1);
//            stockPrice = stockPrice.substring(0, indexForPositiveOrNegative);
//
//            int vol = response.indexOf("Volume");
//            int avg = response.indexOf("Avg.");
//            String volume = response.substring(vol + 6, avg);
            
            stockPrice = stockPrice.replaceAll("\\s+", "");
            priceChange = priceChange.replaceAll("\\s+", "");
//            volume = volume.replaceAll("\\s+", "");

            priceChange = priceChange.replaceAll("\\s+", "");
            vstr = vstr.replaceAll(",", "");

            double v1 = Double.parseDouble(vstr) / 1000000;
            DecimalFormat numberFormat = new DecimalFormat("#.000");

            vstr = numberFormat.format(v1);

            System.out.println("Price " + stockPrice);
            System.out.println("Change " + priceChange);
            System.out.println("Volume " + vstr);
            price.add(j, stockPrice);
            vlm.add(j, vstr);
            chg.add(j, priceChange);
        } catch (Exception e) {
            System.out.println("Exception occurred : " +  e);
            e.printStackTrace();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Price ");
            System.out.println("Change ");
            System.out.println("Volume ");
            price.add(j, "0.0");
            vlm.add(j, "0.0");
            chg.add(j, "0.0");
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

        Document doc = Jsoup.connect("https://finance.yahoo.com/quote/%5EBSESN/history?p=%5EBSESN").get();

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

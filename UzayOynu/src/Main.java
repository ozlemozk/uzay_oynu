import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        OyunEkrani ekran=new OyunEkrani("Uzay Oynu");
        ekran.setResizable(false); //ekran değişim
        ekran.setFocusable(false);

        ekran.setSize(800,600); //formun boyutlarını ayarlıyoruz
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        Oyun oyun=new Oyun();
        oyun.requestFocus();//klavyeden işlemleri direk anlamak 
        oyun.addKeyListener(oyun);//klavyeden işlemleri almamızı sağlar 
        oyun.setFocusable(true);//odağı jpanel verdik 
        oyun.setFocusTraversalKeysEnabled(false);//klavye  girişler için  
        ekran.add(oyun);
        ekran.setVisible(true);
    }
}

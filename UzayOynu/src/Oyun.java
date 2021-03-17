import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


class Ates
{
    int x,y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}

public class Oyun  extends  JPanel implements KeyListener,ActionListener
{
    Timer timer=new Timer(5, this);
    private int GecenSure=0;
    private int  HarcananAtes=0;
    private BufferedImage image;
    private ArrayList<Ates> atesler=new ArrayList<>();
    private int AtesdirY=1;//ATEŞ OLUŞUCAK VE ATEŞLERİMİ Y KOORDİNATİNA EKLERİZ BURDA
    private int TopX=0;//SAĞA SOLA GİTME AYARLAMAK İÇİN
    private int  TopDirX=2;
    private int UzayGemisiX=0;
    private int DirUzayX=20;
    public boolean kontrolet()
    {
        return atesler.stream().anyMatch((atesler1) -> (new Rectangle(atesler1.getX(),atesler1.getY(),10,20).intersects(new Rectangle(TopX,0,20,20))));
    }

    public Oyun()
    {
        try
        {
            image=ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        }
        catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        GecenSure+=5;
        g.setColor(Color.red);
        g.fillOval(TopX, 0,20,20);
        g.drawImage(image,UzayGemisiX,490,image.getWidth()/10,image.getHeight()/10,this);
        atesler.stream().filter((atesler1) -> (atesler1.getY()<0)).forEach((atesler1) -> {
            atesler.remove(atesler1);
        });
        g.setColor(Color.BLUE);
        atesler.stream().forEach((atesler1) -> {
            g.fillRect(atesler1.getX(), atesler1.getY(),10,20);
        });
        if (kontrolet())
        {
            timer.stop();
            String mesaj="Kazandınız...\n"
                    + "Harcanan ateş :"+HarcananAtes+
                    "\nGeçen süre :"+GecenSure/1000.0;
            JOptionPane.showMessageDialog(this,mesaj);
            System.exit(0);

        }

    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int c=e.getKeyCode();
        if (c==KeyEvent.VK_LEFT)
        {
            if (UzayGemisiX<=0)
            {
                UzayGemisiX=0;
            }
            else
            {
                UzayGemisiX-=DirUzayX;
            }

        }
        else if (c==KeyEvent.VK_RIGHT)
        {
            if (UzayGemisiX>=750)
            {
                UzayGemisiX=750;
            }
            else
            {
                UzayGemisiX+=DirUzayX;
            }
        }
        else if (c==KeyEvent.VK_UP)
        {
            boolean add = atesler.add(new Ates(UzayGemisiX+15,250)); //ateş oluşturma
            HarcananAtes++;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        atesler.stream().forEach((atesler1) -> {
            atesler1.setY(atesler1.getY()-AtesdirY);
        });
        TopX+=TopDirX;
        if (TopX>=750)
        {
            TopDirX=-TopDirX;
        }
        if (TopX<=0)
        {
            TopDirX=-TopDirX;
        }
        repaint();
    }

}

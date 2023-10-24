import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RescueDotsSurface<function, StopWatch> extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    public ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
    ArrayList<String> MedKit = new ArrayList<String>();
    ArrayList<String> Pulse = new ArrayList<String>();
    ArrayList<String> CPR = new ArrayList<String>();
    ArrayList<String> Phone = new ArrayList<String>();

    MedKit medKit=new MedKit();
    Heart Heart=new Heart();
    Cpr Cpr=new Cpr();
    Phone phone=new Phone();

    int[] kit = new int[22];
    int[] heart = new int[30];
    int[] Cpri = new int[49];
    int[] Phon = new int[49];

    boolean question=false;
    int cor1;
    int cor2;
    int cor3;
    int cor4;
    int correct=0;
    int a=1;
    int k=1;
    int index;

    public ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
    public Point2D.Double startPoint;
    public Point2D.Double x;
    public Point2D.Double y;
    public Point2D.Double mouse = null;
    public boolean dragging = false;

    public int pointSize = 15;
    public int LEVEL=0;

    Timer timer;
    long startTime;
    boolean timend =false;
    int t=0;
    int t2=0;
    int t3=0;
    int t4=0;
    long tm1 = 0;
    long tm2 = 0;
    long tm3 = 0;
    long tm4 = 0;
    long[] howlong =new long[4];
    long totaltime=0;

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;

        JButton Menu = new JButton("MENU");
        Menu.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        Menu.setBackground(Color.lightGray);
        this.setLayout(null);
        Menu.setBounds(1100, 900, 140, 70);

        JButton Reset = new JButton("RESET LEVEL");
        Reset.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        Reset.setBackground(Color.lightGray);

        JButton Restart = new JButton("RESTART GAME");
        Restart.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        Restart.setBackground(Color.lightGray);

        JButton End = new JButton("END GAME");
        End.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        End.setBackground(Color.lightGray);

        JButton Quit = new JButton("Quit");
        Quit.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        Quit.setBackground(Color.lightGray);

        JButton Cancel = new JButton("X");
        Cancel.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        Cancel.setBackground(Color.red);

        JButton op1 = new JButton();
        op1.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        op1.setBackground(Color.lightGray);

        JButton op2 = new JButton();
        op2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        op2.setBackground(Color.lightGray);

        JButton op3 = new JButton();
        op3.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        op3.setBackground(Color.lightGray);

        JButton next = new JButton("NEXT");
        next.setBackground(Color.lightGray);

        JButton Start = new JButton("START");
        Start.setFont(new Font("TimesRoman", Font.PLAIN, g2.getFont().getSize() * 4));
        Start.setBackground(Color.lightGray);
        this.setLayout(null);
        Start.setBounds(500, 500, 200, 150);

        Reset.setBounds(100, 900, 220, 70);
        End.setBounds(620, 900, 180, 70);
        Quit.setBounds(830, 900, 180, 70);
        Cancel.setBounds(950,820,60,60 );
        Restart.setBounds(350, 900, 240, 70);


        Menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add(Reset);
                add(End);
                add(Cancel);
                add(Restart);
                add(Quit);
            }
        });

        End.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                question=false;
                points.clear();
                lines.clear();
                removeAll();
                revalidate();
                repaint();
                LEVEL=5;
            }
        });

        Restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                question=false;
                MedKit.clear();
                Pulse.clear();
                CPR.clear();
                Phone.clear();
                a=1;
                k=1;
                tm1=0;
                tm2=0;
                tm3=0;
                tm4=0;
                t2=0;
                t3=0;
                t4=0;
                t=0;
                timend =false;
                startTime=1;
                correct=0;
                cor1=0;
                cor2=0;
                cor3=0;
                cor4=0;
                points.clear();
                lines.clear();
                removeAll();
                revalidate();
                repaint();
                LEVEL=0;

            }
        });

        Quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

        Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                remove(Quit);
                remove(Restart);
                remove(Cancel);
                remove(Reset);
                remove(End);
                revalidate();
                repaint();
            }
        });

        JLabel label= new JLabel();
        label.setBounds(1170,5,300,50);
        label.setFont((new Font("TimesRoman", Font.PLAIN, 30)));

        if (LEVEL == 0) {

            g2.setColor(Color.white);
            g2.setFont(((new Font("TimesRoman", Font.PLAIN, 80))));
            g2.drawString("RESCUE DOTS",350,100);
            g2.setFont(((new Font("TimesRoman", Font.PLAIN, 50))));
            g2.drawString("Zasady gry:",80,180);
            g2.setFont(((new Font("TimesRoman", Font.PLAIN, 30))));
            g2.drawString("-Połącz kropki zgodnie z kolejnością nie przekraczając limitu czasu.",80,250);
            g2.drawString("-Kropka przy numerze oznacza, że należy domknąć kształt.",80,300);
            g2.drawString("-W razie pomyłki można rozpocząć poziom od nowa lub całą grę.",80,350);
            g2.drawString("-Punkty są przyznawane za prawidłową odpowiedź na pytanie.",80,400);
            add(Start);

            Start.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL = 1;
                    startTime=-1;
                }

            });
        } else if (LEVEL == 1) {

            t=0;
            long duration = 10000;
            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            add(Menu);
            JLabel finalLabel = label;

            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (startTime < 0) {
                        startTime = System.currentTimeMillis();
                    }
                    long now = System.currentTimeMillis();
                    long clockTime = now - startTime;
                    if(clockTime < duration && t<20)
                    {
                        finalLabel.setText(df.format(clockTime));
                    }
                    if (clockTime >= duration) {
                        clockTime = duration;
                        timer.stop();
                        timend =true;
                        finalLabel.setText(df.format(clockTime));

                    }else if(t==20)
                    {
                        howlong[0]=TimeUnit.MILLISECONDS.toSeconds(clockTime);
                        timer.setDelay(1000);
                        tm1= howlong[0];
                        clockTime=0;
                        timer.stop();
                        System.out.println("czass "+ howlong[0]);
                        remove(label);
                        remove(finalLabel);
                        finalLabel.setVisible(false);

                    }

                    add(label);

                }
            });
            timer.setInitialDelay(0);

            if(startTime==-1) {

                timer.start();
            }

            if(t==21)
            {
                g2.setColor(Color.white);
                g2.setFont(((new Font("TimesRoman", Font.PLAIN, 30))));
                g2.drawString("T: "+tm1+ "s",1170,40);
            }
            if(timend ==true) {
                g2.setColor(Color.RED);
                g2.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
                g2.drawString("Czas się skończył, spróbój jeszcze raz", 180, 750);
            }

            final String[] quest = {"Co znajdziemy w apteczce?", "Czym zdezynfekujemy skaleczenie?", "Czym zabezpieczymy dużą ranę?"};

            g2.setColor(Color.white);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2.drawString("LEVEL "+ LEVEL, 850, 40);
            g2.drawString("PUNKTY " + correct, 1000, 40);

            this.setLayout(null);
            op1.setBounds(980, 300, 250, 50);
            op2.setBounds(980, 400, 250, 50);
            op3.setBounds(980, 500, 250, 50);
            next.setBounds(900, 800, 250, 50);

            Reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    MedKit.clear();
                    a=1;
                    k=1;
                    cor1=0;
                    correct=0;
                    startTime=-1;
                    totaltime=0;
                    tm1=0;
                    t=0;
                    timend =false;
                    question=false;
                    points.clear();
                    lines.clear();
                    remove(finalLabel);
                    remove(label);
                    finalLabel.setVisible(false);
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL=1;
                }
            });

            if (question == true) {

                g2.setColor(Color.white);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 23));

                Random losuj = new Random();
                index = losuj.nextInt(quest.length);
                g2.drawString(quest[index], 880, 200);

                if (index == 0) {
                    op1.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                    op2.setFont(new Font("TimesRoman", Font.PLAIN, 15));;
                    op3.setFont(new Font("TimesRoman", Font.PLAIN, 14));;
                    op1.setText("octanisept,plaster, bandaż");
                    op2.setText("plaster, bandaż, okulary");
                    op3.setText("octanisept, nożyczki, krem z filtrem");
                    add(op1);
                    add(op2);
                    add(op3);
                } else if (index == 1) {
                    op1.setText("wodą");
                    op2.setText("octaniseptem");
                    op3.setText("mydłem");
                    add(op1);
                    add(op2);
                    add(op3);

                } else if (index == 2) {
                    op1.setText("plastrem");
                    op2.setText("papierem");
                    op3.setText("bandażem i gazą");
                    add(op1);
                    add(op2);
                    add(op3);
                }
            }
            if (index == 0) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor1 = 1;
                        correct++;
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });
                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

            } else if (index == 1) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        op1.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor1 = 1;
                        correct++;
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

            } else if (index == 2) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op1.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor1 = 1;
                        correct++;
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

            }

            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    question=false;
                    points.clear();
                    lines.clear();
                    remove(finalLabel);
                    remove(label);
                    finalLabel.setVisible(false);
                    t=0;
                    startTime=-1;
                    timend =false;
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL = 2;

                }
            });

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 17));
            g2.drawString("0.", medKit.medkitX[0] - 10, medKit.medkitY[0] - 15);
            for (int i = 1; i < 12; i++) {
                String s = String.format("%d", i);
                g2.drawString(s, medKit.medkitX[i] - 10, medKit.medkitY[i] - 15);
            }
            for (int i = 12; i < 16; i++) {
                String s = String.format("%d", i);
                g2.drawString(s, medKit.medkitX[i + 1] - 10, medKit.medkitY[i + 1] - 15);
            }
            g2.drawString("/16.", medKit.medkitX[8] + 5, medKit.medkitY[8] - 15);
            g2.drawString("A", 445, 365);
            g2.drawString("B", medKit.medkitX[18] - 10, medKit.medkitY[18] - 15);
            g2.drawString("C.", medKit.medkitX[19] - 10, medKit.medkitY[19] - 15);
            g2.drawString("D", 290, 455);
            g2.drawString("E", medKit.medkitX[21] - 10, medKit.medkitY[21] - 15);

            points.add(new Point2D.Double(150, 320));
            points.add(new Point2D.Double(150, 470));
            points.add(new Point2D.Double(150, 620));
            points.add(new Point2D.Double(350, 620));
            points.add(new Point2D.Double(550, 620));
            points.add(new Point2D.Double(750, 620));
            points.add(new Point2D.Double(750, 470));
            points.add(new Point2D.Double(750, 320));
            points.add(new Point2D.Double(670, 315));
            points.add(new Point2D.Double(560, 320));
            points.add(new Point2D.Double(340, 320));
            points.add(new Point2D.Double(240, 315));
            points.add(new Point2D.Double(150, 320));
            points.add(new Point2D.Double(290, 210));
            points.add(new Point2D.Double(400, 145));
            points.add(new Point2D.Double(520, 145));
            points.add(new Point2D.Double(620, 210));
            points.add(new Point2D.Double(670, 315));
            points.add(new Point2D.Double(455, 380));
            points.add(new Point2D.Double(455, 470));
            points.add(new Point2D.Double(455, 580));
            points.add(new Point2D.Double(300, 470));
            points.add(new Point2D.Double(455, 470));
            points.add(new Point2D.Double(610, 470));

            kit[0] = 0;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2));

            if (dragging && startPoint != null) {
                g2.setColor(Color.WHITE);
                g2.drawLine((int) startPoint.x, (int) startPoint.y, (int) mouse.x, (int) mouse.y);
            }

            for (int i = 0; i < lines.size(); i++) {
                g2.setColor(Color.WHITE);
                Line2D l = lines.get(i);
                g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
            }

            for (int i = 0; i < points.size(); i++) {
                Point2D.Double p = points.get(i);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect((int) (p.x - (pointSize / 2)), (int) (p.y - (pointSize / 2)), pointSize, pointSize, pointSize, pointSize);
                g2.setColor(Color.RED);

            }
        } else if (LEVEL == 2) {

            long duration = 70000;
            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            JLabel finalLabel = label;

            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (startTime < 0) {
                        startTime = System.currentTimeMillis();
                    }
                    long now = System.currentTimeMillis();
                    long clockTime = now - startTime;
                    if( t2<28)
                    {
                        finalLabel.setText(df.format(clockTime));
                    }
                    if (clockTime >= duration) {
                        clockTime = duration;
                        timer.stop();
                        timend =true;
                        finalLabel.setText(df.format(clockTime));

                    }else if(t2==28)
                    {
                        howlong[1]=TimeUnit.MILLISECONDS.toSeconds(clockTime);
                        timer.setDelay(1000);
                        tm2= howlong[1];
                        clockTime=0;
                        timer.stop();
                        System.out.println("czass "+ howlong[1]);
                        remove(label);
                        remove(finalLabel);
                        finalLabel.setVisible(false);
                    }

                    add(label);
                }
            });
            timer.setInitialDelay(0);

            if(startTime==-1) {

                timer.start();
            }

            if(t2==29)
            {
                g2.setColor(Color.white);
                g2.setFont(((new Font("TimesRoman", Font.PLAIN, 30))));
                g2.drawString("T: "+tm2+ "s",1170,40);
            }
            if(timend==true) {
                g2.setColor(Color.RED);
                g2.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
                g2.drawString("Czas się skończył, spróbój jeszcze raz", 180, 750);
            }
            add(Menu);
            final String[] quest = {"Jakie jest prawidłowe tętno?(uderzenia serca na minutę)", "Jakie są pierwsze objawy zawału?", "Co to jest saturacja?"};

            g2.setColor(Color.white);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2.drawString("LEVEL "+LEVEL, 850, 40);
            g2.drawString("PUNKTY " + correct, 1000, 40);

            Reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if(cor2==1)
                    {
                        correct--;
                        cor2=0;
                    }
                    Pulse.clear();
                    a=1;
                    k=1;
                    t2=0;
                    startTime=-1;
                    tm2=0;
                    t2=0;
                    timend =false;
                    remove(label);
                    remove(finalLabel);
                    finalLabel.setVisible(false);
                    Pulse.clear();
                    question=false;
                    points.clear();
                    lines.clear();
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL=2;
                }
            });

            this.setLayout(null);
            op1.setBounds(980, 300, 250, 50);
            op2.setBounds(980, 400, 250, 50);
            op3.setBounds(980, 500, 250, 50);
            next.setBounds(900, 800, 250, 50);

            if (question == true) {

                Random losuj = new Random();
                index = losuj.nextInt(quest.length);

                if(index ==0)
                {
                    g2.setFont(new Font("TimesRoman", Font.PLAIN, 23));
                    g2.drawString(quest[index], 700, 200);

                }
                else
                {
                    g2.setFont(new Font("TimesRoman", Font.PLAIN, 23));
                    g2.drawString(quest[index], 900, 200);
                }

                if (index == 0) {
                    op1.setText("od 60 do 100");
                    op2.setText("od 10 do 40");
                    op3.setText("od 150 do 300");
                    add(op1);
                    add(op2);
                    add(op3);
                } else if (index == 1) {
                    op1.setFont((new Font("TimesRoman", Font.PLAIN, 16)));
                    op2.setFont((new Font("TimesRoman", Font.PLAIN, 16)));
                    op3.setFont((new Font("TimesRoman", Font.PLAIN, 16)));
                    op1.setText("ból głowy, duszności, ból oczu");
                    op2.setText("ból w klatce, ucisk, duszności");
                    op3.setText("wysypka, ucisk, ból oczu");
                    add(op1);
                    add(op2);
                    add(op3);

                } else if (index == 2) {
                    op1.setText("nawodnienie organizmu");
                    op2.setText("puls");
                    op3.setText("nasycenie krwi tlenem");
                    add(op1);
                    add(op2);
                    add(op3);
                }
            }

            if (index == 0) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor2=1;
                        correct++;
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });
                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

            } else if (index == 1) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        op1.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor2=1;
                        correct++;
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });
            } else if (index == 2) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op1.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor2=1;
                        correct++;
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });
            }

            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    question=false;
                    remove(finalLabel);
                    remove(label);
                    finalLabel.setVisible(false);
                    startTime=-1;
                    t2=0;
                    timend =false;
                    points.clear();
                    lines.clear();
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL = 3;

                }
            });

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 17));
            for (int i = 0; i < 14; i++) {
                String s = String.format("%d", i);
                g2.drawString(s, Heart.heartX[i] - 10, Heart.heartY[i] - 15);
            }
            g2.drawString("0.", Heart.heartX[14] - 10, Heart.heartY[14] - 15);
            g2.drawString(" /14", Heart.heartX[9] + 10, Heart.heartY[9] - 15);

            for (int i = 15; i < 30; i++) {
                String s = String.format("%d", i);
                g2.drawString(s, Heart.heartX[i] - 10, Heart.heartY[i] - 15);
            }

            points.add(new Point2D.Double(245, 430));
            points.add(new Point2D.Double(200, 390));
            points.add(new Point2D.Double(130, 375));
            points.add(new Point2D.Double(85, 415));
            points.add(new Point2D.Double(75, 490));
            points.add(new Point2D.Double(105, 560));
            points.add(new Point2D.Double(170, 605));
            points.add(new Point2D.Double(245, 650));
            points.add(new Point2D.Double(330, 600));
            points.add(new Point2D.Double(380, 540));
            points.add(new Point2D.Double(400, 480));
            points.add(new Point2D.Double(390, 410));
            points.add(new Point2D.Double(350, 375));
            points.add(new Point2D.Double(290, 390));
            points.add(new Point2D.Double(470, 540));
            points.add(new Point2D.Double(525, 480));
            points.add(new Point2D.Double(540, 600));
            points.add(new Point2D.Double(585, 540));
            points.add(new Point2D.Double(645, 530));
            points.add(new Point2D.Double(685, 290));
            points.add(new Point2D.Double(690, 700));
            points.add(new Point2D.Double(745, 355));
            points.add(new Point2D.Double(760, 670));
            points.add(new Point2D.Double(780, 520));
            points.add(new Point2D.Double(830, 520));
            points.add(new Point2D.Double(865, 465));
            points.add(new Point2D.Double(880, 570));
            points.add(new Point2D.Double(910, 515));
            points.add(new Point2D.Double(945, 515));

            heart[0] = 0;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2));

            if (dragging && startPoint != null) {
                g2.setColor(Color.WHITE);
                g2.drawLine((int) startPoint.x, (int) startPoint.y, (int) mouse.x, (int) mouse.y);
            }

            for (int i = 0; i < lines.size(); i++) {
                g2.setColor(Color.WHITE);
                Line2D l = lines.get(i);
                g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
            }

            for (int i = 0; i < points.size(); i++) {
                Point2D.Double p = points.get(i);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect((int) (p.x - (pointSize / 2)), (int) (p.y - (pointSize / 2)), pointSize, pointSize, pointSize, pointSize);
                g2.setColor(Color.RED);
            }
        } else if (LEVEL == 3) {

            long duration = 90000;
            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            JLabel finalLabel = label;

            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (startTime < 0) {
                        startTime = System.currentTimeMillis();
                    }
                    long now = System.currentTimeMillis();
                    long clockTime = now - startTime;
                    if(clockTime < duration && t3<47)
                    {
                        finalLabel.setText(df.format(clockTime));
                    }
                    if (clockTime >= duration) {
                        clockTime = duration;
                        timer.stop();
                        timend =true;
                        finalLabel.setText(df.format(clockTime));

                    }else if(t3==47)
                    {
                        howlong[2]=TimeUnit.MILLISECONDS.toSeconds(clockTime);
                        timer.setDelay(1000);
                        tm3= howlong[2];
                        clockTime=0;
                        timer.stop();
                        System.out.println("czass "+ howlong[2]);
                        remove(label);
                        remove(finalLabel);
                        finalLabel.setVisible(false);
                    }

                    add(label);
                }
            });
            timer.setInitialDelay(0);

            if(startTime==-1) {

                timer.start();
            }
            if(t3==48)
            {
                g2.setColor(Color.white);
                g2.setFont(((new Font("TimesRoman", Font.PLAIN, 30))));
                g2.drawString("T: "+tm3+ "s",1170,40);
            }
            if(timend==true) {
                g2.setColor(Color.RED);
                g2.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
                g2.drawString("Czas się skończył, spróbój jeszcze raz", 180, 750);
            }
            add(Menu);
            final String[] quest = {"Stusunek uciśnięć klatki do wdechów dla dorosłego:", "Stusunek uciśnięć klatki do wdechów dla dzieci:", "RKO wykonujemy, gdy:"};

            g2.setColor(Color.white);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2.drawString("LEVEL "+LEVEL, 850, 40);
            g2.drawString("PUNKTY " + correct, 1000, 40);

            Reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    CPR.clear();
                    if(cor3==1)
                    {
                        correct--;
                        cor3=0;
                    }
                    a=1;
                    k=1;
                    t3=0;
                    startTime=-1;
                    tm3=0;
                    t3=0;
                    timend =false;
                    CPR.clear();
                    remove(label);
                    remove(finalLabel);
                    finalLabel.setVisible(false);
                    question=false;
                    points.clear();
                    lines.clear();
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL=3;
                }
            });

            this.setLayout(null);
            op1.setBounds(1000, 300, 250, 50);
            op2.setBounds(1000, 400, 250, 50);
            op3.setBounds(1000, 500, 250, 50);
            next.setBounds(900, 800, 250, 50);

            if (question == true) {

                Random losuj = new Random();
                index = losuj.nextInt(quest.length);

                if(index ==2) {
                    g2.drawString(quest[index], 900, 200);
                }else {
                    g2.setFont(new Font("TimesRoman", Font.PLAIN, 23));
                    g2.drawString(quest[index], 700, 200);
                }

                if (index == 0) {
                    op1.setText("30:2");
                    op2.setText("31:2");
                    op3.setText("20:2");
                    add(op1);
                    add(op2);
                    add(op3);
                } else if (index == 1) {
                    op1.setText("5:2");
                    op2.setText("15:2");
                    op3.setText("40:2");
                    add(op1);
                    add(op2);
                    add(op3);

                } else if (index == 2) {
                    op1.setFont((new Font("TimesRoman", Font.PLAIN, 15)));
                    op2.setFont((new Font("TimesRoman", Font.PLAIN, 15)));
                    op3.setFont((new Font("TimesRoman", Font.PLAIN, 15)));
                    op1.setBounds(850, 300, 400, 50);
                    op2.setBounds(850, 400, 400, 50);
                    op3.setBounds(850, 500, 400, 50);
                    op1.setText("doszło do utraty przytomności i oddycha");
                    op2.setText("osoba jest przytomna, ale tętno jest słabo wyczuwalne");
                    op3.setText("doszło do utraty przytomności i osoba nie oddycha");
                    add(op1);
                    add(op2);
                    add(op3);
                }
            }
            if (index == 0) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor3=1;
                        correct++;
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });
                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });


            } else if (index == 1) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        op1.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor3=1;
                        correct++;
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

            } else if (index == 2) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op1.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor3=1;
                        correct++;
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

            }

            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    question=false;
                    remove(finalLabel);
                    remove(label);
                    finalLabel.setVisible(false);
                    startTime=-1;
                    t3=0;
                    timend =false;
                    points.clear();
                    lines.clear();
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL = 4;

                }
            });

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            g2.drawString("0.", Cpr.cprX[8] - 5, Cpr.cprY[8] - 15);
            for (int i = 1; i < 49; i++) {
                String s = String.format("%d", i);
                if (i == 1 || i == 2) {
                    g2.drawString(s, Cpr.cprX[i] - 13, Cpr.cprY[i] - 15);
                } else if (i == 3) {
                    g2.drawString(s, Cpr.cprX[i] - 15, Cpr.cprY[i] - 7);
                } else if (i == 4) {
                    g2.drawString(s + "/8.", Cpr.cprX[i] - 10, Cpr.cprY[i] - 12);
                } else if (i == 5 || i == 6 || i == 7) {
                    g2.drawString(s, Cpr.cprX[i] + 13, Cpr.cprY[i] - 7);
                } else if (i == 9 || i == 10) {
                    g2.drawString(s, Cpr.cprX[i] - 8, Cpr.cprY[i] - 11);
                } else if (i == 11 || i == 12 || i == 13) {
                    g2.drawString(s, Cpr.cprX[i] - 30, Cpr.cprY[i]);
                } else if (i == 14) {
                    g2.drawString(s + "/20.", Cpr.cprX[i] - 15, Cpr.cprY[i] + 25);
                } else if (i >= 15 && i <= 18) {
                    g2.drawString(s, Cpr.cprX[i] + 10, Cpr.cprY[i]);
                } else if (i == 19) {
                    g2.drawString(s, Cpr.cprX[i] + 10, Cpr.cprY[i] - 10);
                } else if (i == 21 || i == 22) {
                    g2.drawString(s, Cpr.cprX[i] - 10, Cpr.cprY[i] - 10);
                } else if (i == 23) {
                    g2.drawString(s, Cpr.cprX[i] - 5, Cpr.cprY[i] - 10);
                } else if (i >= 24 && i <= 28) {
                    g2.drawString(s, Cpr.cprX[i] - 5, Cpr.cprY[i] - 10);
                } else if (i == 29) {
                    g2.drawString(s, Cpr.cprX[i] - 10, Cpr.cprY[i] - 10);
                } else if (i == 30) {
                    g2.drawString(s, Cpr.cprX[i] + 10, Cpr.cprY[i] + 5);
                } else if (i >= 31 && i <= 37) {
                    g2.drawString(s, Cpr.cprX[i] - 10, Cpr.cprY[i] + 25);
                } else if (i == 38 || i == 39 || i == 41) {
                    g2.drawString(s, Cpr.cprX[i] + 10, Cpr.cprY[i]);
                } else if (i == 40 || i >= 42 && i < 46) {
                    g2.drawString(s, Cpr.cprX[i] - 10, Cpr.cprY[i] - 10);
                } else if (i == 47) {
                    g2.drawString("46", Cpr.cprX[i] + 10, Cpr.cprY[i]);
                } else if (i == 48) {
                    g2.drawString("  " +
                            "/47", Cpr.cprX[i], Cpr.cprY[i] + 25);
                }
            }

            points.add(new Point2D.Double(300, 300));
            points.add(new Point2D.Double(275, 310));
            points.add(new Point2D.Double(260, 340));
            points.add(new Point2D.Double(270, 380));
            points.add(new Point2D.Double(300, 400));
            points.add(new Point2D.Double(330, 380));
            points.add(new Point2D.Double(340, 340));
            points.add(new Point2D.Double(325, 310));
            points.add(new Point2D.Double(280, 425));
            points.add(new Point2D.Double(245, 430));
            points.add(new Point2D.Double(210, 470));
            points.add(new Point2D.Double(210, 520));
            points.add(new Point2D.Double(260, 580));
            points.add(new Point2D.Double(300, 600));
            points.add(new Point2D.Double(340, 580));
            points.add(new Point2D.Double(390, 520));
            points.add(new Point2D.Double(390, 470));
            points.add(new Point2D.Double(355, 430));
            points.add(new Point2D.Double(315, 425));
            points.add(new Point2D.Double(250, 620));
            points.add(new Point2D.Double(175, 635));
            points.add(new Point2D.Double(140, 635));
            points.add(new Point2D.Double(115, 600));
            points.add(new Point2D.Double(70, 600));
            points.add(new Point2D.Double(45, 630));
            points.add(new Point2D.Double(52, 666));
            points.add(new Point2D.Double(80, 680));
            points.add(new Point2D.Double(120, 670));
            points.add(new Point2D.Double(140, 655));
            points.add(new Point2D.Double(150, 700));
            points.add(new Point2D.Double(210, 700));
            points.add(new Point2D.Double(305, 700));
            points.add(new Point2D.Double(410, 700));
            points.add(new Point2D.Double(480, 700));
            points.add(new Point2D.Double(600, 700));
            points.add(new Point2D.Double(740, 700));
            points.add(new Point2D.Double(745, 635));
            points.add(new Point2D.Double(725, 605));
            points.add(new Point2D.Double(705, 615));
            points.add(new Point2D.Double(695, 670));
            points.add(new Point2D.Double(660, 655));
            points.add(new Point2D.Double(570, 635));
            points.add(new Point2D.Double(480, 620));
            points.add(new Point2D.Double(380, 610));
            points.add(new Point2D.Double(390, 650));

            Cpri[0] = 0;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2));

            if (dragging && startPoint != null) {
                g2.setColor(Color.WHITE);
                g2.drawLine((int) startPoint.x, (int) startPoint.y, (int) mouse.x, (int) mouse.y);
            }

            for (int i = 0; i < lines.size(); i++) {
                g2.setColor(Color.WHITE);
                Line2D l = lines.get(i);
                g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
            }

            for (int i = 0; i < points.size(); i++) {
                Point2D.Double p = points.get(i);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect((int) (p.x - (pointSize / 2)), (int) (p.y - (pointSize / 2)), pointSize, pointSize, pointSize, pointSize);
                g2.setColor(Color.RED);

            }
        } else if (LEVEL == 4) {
            long duration = 90000;
            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            JLabel finalLabel = label;

            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (startTime < 0) {
                        startTime = System.currentTimeMillis();
                    }
                    long now = System.currentTimeMillis();
                    long clockTime = now - startTime;
                    if(clockTime < duration && t4<47)
                    {
                        finalLabel.setText(df.format(clockTime));
                    }
                    if (clockTime >= duration) {
                        clockTime = duration;
                        timer.stop();
                        timend =true;
                        finalLabel.setText(df.format(clockTime));

                    }else if(t4==47)
                    {
                        howlong[3]=TimeUnit.MILLISECONDS.toSeconds(clockTime);
                        timer.setDelay(1000);
                        tm4= howlong[3];
                        clockTime=0;
                        timer.stop();
                        System.out.println("czass "+ howlong[3]);
                        remove(label);
                        remove(finalLabel);
                        finalLabel.setVisible(false);
                    }
                    add(label);
                }
            });
            timer.setInitialDelay(0);

            if(startTime==-1) {

                timer.start();
            }
            if(t4==48)
            {
                g2.setColor(Color.white);
                g2.setFont(((new Font("TimesRoman", Font.PLAIN, 30))));
                g2.drawString("T: "+tm4+ "s",1170,40);
            }
            if(timend==true) {
                g2.setColor(Color.RED);
                g2.setFont((new Font("TimesRoman", Font.PLAIN, 40)));
                g2.drawString("Czas się skończył, spróbój jeszcze raz", 180, 750);
            }
            add(Menu);
            final String[] quest = {"Numer alarmowy to:", "999 to numer:", "Numer pogotowia wodnego to:"};

            g2.setColor(Color.white);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2.drawString("LEVEL "+LEVEL, 850, 40);
            g2.drawString("PUNKTY " + correct, 1000, 40);

            Reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if(cor4==1)
                    {
                        correct--;
                        cor4=0;
                    }
                    Phone.clear();
                    a=1;
                    k=1;
                    t=0;
                    t4=0;
                    startTime=-1;
                    tm4=0;
                    t4=0;
                    timend =false;
                    remove(label);
                    remove(finalLabel);
                    finalLabel.setVisible(false);
                    Phone.clear();
                    question=false;
                    points.clear();
                    lines.clear();
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL=4;
                }
            });

            this.setLayout(null);
            op1.setBounds(900, 300, 250, 50);
            op2.setBounds(900, 400, 250, 50);
            op3.setBounds(900, 500, 250, 50);
            next.setBounds(900, 800, 250, 50);

            if (question == true) {

                Random losuj = new Random();
                index = losuj.nextInt(quest.length);
                g2.drawString(quest[index], 800, 200);

                if (index == 0) {
                    op1.setText("112");
                    op2.setText("601");
                    op3.setText("981");
                    add(op1);
                    add(op2);
                    add(op3);
                } else if (index == 1) {
                    op1.setText("policji");
                    op2.setText("pogotowia");
                    op3.setText("straży pożarnej");
                    add(op1);
                    add(op2);
                    add(op3);

                } else if (index == 2) {
                    op1.setText("100 601 100");
                    op2.setText("600 600 100");
                    op3.setText("601 100 100");
                    add(op1);
                    add(op2);
                    add(op3);
                }
            }

            if (index == 0) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor4=1;
                        correct++;
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });
                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op1.setBackground(Color.green);
                        add(next);
                    }
                });

            } else if (index == 1) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        op1.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor4=1;
                        correct++;
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op3.setBackground(Color.red);
                        op2.setBackground(Color.green);
                        add(next);
                    }
                });

            } else if (index == 2) {
                op1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op1.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        op2.setBackground(Color.red);
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });

                op3.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        cor4=1;
                        correct++;
                        op3.setBackground(Color.green);
                        add(next);
                    }
                });
            }

            next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    question=false;
                    points.clear();
                    lines.clear();
                    removeAll();
                    revalidate();
                    repaint();
                    LEVEL = 5;
                }
            });

            g2.setFont(new Font("TimesRoman", Font.PLAIN, 17));
            g2.drawString("0.", phone.phoneX[18] - 25, phone.phoneY[18] + 5);
            for (int i = 1; i < 18; i++) {
                String s = String.format("%d", i);
                if (i == 1 || i == 16 || i == 17) {
                    g2.drawString(s, phone.phoneX[i] - 30, phone.phoneY[i] + 5);
                } else if (i >= 9 && i <= 12) {
                    g2.drawString(s, phone.phoneX[i] + 15, phone.phoneY[i] + 5);
                } else {
                    g2.drawString(s, phone.phoneX[i] - 10, phone.phoneY[i] - 15);
                }
            }
            g2.drawString("19.", phone.phoneX[36] - 35, phone.phoneY[36] + 5);

            for (int i = 19; i < 36; i++) {
                String s = String.format("%d", i + 1);
                if (i >= 19 && i <= 21) {
                    g2.drawString(s, phone.phoneX[i] - 35, phone.phoneY[i] + 5);
                } else if (i >= 31 && i <= 33) {
                    g2.drawString(s, phone.phoneX[i] + 20, phone.phoneY[i] + 5);
                } else if (i == 34 || i == 35) {
                    g2.drawString(s, phone.phoneX[i] - 10, phone.phoneY[i] + 30);
                } else {
                    g2.drawString(s, phone.phoneX[i] - 10, phone.phoneY[i] - 15);
                }
            }

            g2.drawString("37.", phone.phoneX[48] - 10, phone.phoneY[48] - 15);

            for (int i = 37; i < 48; i++) {
                String s = String.format("%d", i + 1);

                g2.drawString(s, phone.phoneX[i] - 10, phone.phoneY[i] - 15);
            }

            points.add(new Point2D.Double(50, 440));
            points.add(new Point2D.Double(50, 390));
            points.add(new Point2D.Double(90, 310));
            points.add(new Point2D.Double(155, 270));
            points.add(new Point2D.Double(230, 255));
            points.add(new Point2D.Double(310, 255));
            points.add(new Point2D.Double(380, 255));
            points.add(new Point2D.Double(450, 270));
            points.add(new Point2D.Double(500, 310));
            points.add(new Point2D.Double(560, 390));
            points.add(new Point2D.Double(560, 440));
            points.add(new Point2D.Double(450, 440));
            points.add(new Point2D.Double(450, 390));
            points.add(new Point2D.Double(380, 350));
            points.add(new Point2D.Double(310, 330));
            points.add(new Point2D.Double(225, 350));
            points.add(new Point2D.Double(160, 390));
            points.add(new Point2D.Double(160, 440));
            points.add(new Point2D.Double(100, 740));
            points.add(new Point2D.Double(100, 690));
            points.add(new Point2D.Double(100, 640));
            points.add(new Point2D.Double(160, 550));
            points.add(new Point2D.Double(215, 490));
            points.add(new Point2D.Double(215, 410));
            points.add(new Point2D.Double(280, 410));
            points.add(new Point2D.Double(280, 490));
            points.add(new Point2D.Double(340, 490));
            points.add(new Point2D.Double(340, 410));
            points.add(new Point2D.Double(400, 410));
            points.add(new Point2D.Double(400, 490));
            points.add(new Point2D.Double(460, 550));
            points.add(new Point2D.Double(500, 640));
            points.add(new Point2D.Double(500, 690));
            points.add(new Point2D.Double(500, 740));
            points.add(new Point2D.Double(380, 740));
            points.add(new Point2D.Double(230, 740));
            points.add(new Point2D.Double(310, 545));
            points.add(new Point2D.Double(250, 545));
            points.add(new Point2D.Double(205, 575));
            points.add(new Point2D.Double(185, 630));
            points.add(new Point2D.Double(205, 675));
            points.add(new Point2D.Double(250, 700));
            points.add(new Point2D.Double(310, 700));
            points.add(new Point2D.Double(370, 700));
            points.add(new Point2D.Double(415, 675));
            points.add(new Point2D.Double(435, 630));
            points.add(new Point2D.Double(415, 575));
            points.add(new Point2D.Double(370, 545));


            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2));

            if (dragging && startPoint != null) {
                g2.setColor(Color.WHITE);
                g2.drawLine((int) startPoint.x, (int) startPoint.y, (int) mouse.x, (int) mouse.y);
            }

            for (int i = 0; i < lines.size(); i++) {
                g2.setColor(Color.WHITE);
                Line2D l = lines.get(i);
                g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
            }

            for (int i = 0; i < points.size(); i++) {
                Point2D.Double p = points.get(i);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect((int) (p.x - (pointSize / 2)), (int) (p.y - (pointSize / 2)), pointSize, pointSize, pointSize, pointSize);
                g2.setColor(Color.RED);
            }

        }else if(LEVEL==5)
        {
            add(Menu);
            startTime=1;
            totaltime=tm1+tm2+tm3+tm4;
            remove(label);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2.setColor(Color.white);
            g2.drawString("Wszystkie zdobyte punkty  " + correct, 250, 400);
            g2.drawString("Całkowity czas gry  "+totaltime+"s", 250, 500);

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        Point click = e.getPoint();
        System.out.println(click.getX());
        System.out.println(click.getY());

        if (SwingUtilities.isLeftMouseButton(e)) {
            mouse = new Point2D.Double(e.getX(), e.getY());

            if (dragging) {
                for (int i = 0; i < points.size(); i++) {
                    Point2D.Double p = points.get(i);
                    if (p.distance(mouse) < pointSize / 1.5) {
                        lines.add(new Line2D.Double(startPoint, p));

                    }
                }
            }

            if (LEVEL == 1) {

                if (mouse.x < medKit.medkitX[k] + 20 && mouse.x > medKit.medkitX[k] - 20 && mouse.y < medKit.medkitY[k] + 20 && mouse.y > medKit.medkitY[k] - 20) {

                    MedKit.add("OK");
                    t++;
                    kit[a] = a;
                    System.out.println("W tablicy znajdują się punkt: " + kit[k]);
                    a++;
                    k++;
                } else {
                    boolean oke = false;
                    dragging = true;
                    if (dragging) {
                        for (int l = 0; l < 22; l++) {

                            if (mouse.x < medKit.medkitX[l] + 20 && mouse.x > medKit.medkitX[l] - 20 && mouse.y < medKit.medkitY[l] + 20 && mouse.y > medKit.medkitY[l] - 20) {
                                System.out.println("Zla kolejnosc: " + click);
                                MedKit.add("Błąd");
                                oke = true;
                            }
                        }
                        if (oke) {
                            a++;
                            k++;
                        }
                    } else if (dragging == false) {
                        for (int l = 0; l < 22; l++) {
                            if (l == a || l == a - 1 && mouse.x < medKit.medkitX[l] + 20 && mouse.x > medKit.medkitX[l] - 20 && mouse.y < medKit.medkitY[l] + 20 && mouse.y > medKit.medkitY[l] - 20) {
                                System.out.println("Ten punkt już jest: ");
                            }
                            if (mouse.x < medKit.medkitX[l] + 20 && mouse.x > medKit.medkitX[l] - 20 && mouse.y < medKit.medkitY[l] + 20 && mouse.y > medKit.medkitY[l] - 20) {
                                System.out.println("Zla klikniecie: " + click);
                            }

                        }
                    }
                }

                if (k == 22) {
                    if (MedKit.contains("Błąd")) {
                        System.out.println("Sproboj jeszcze raz");
                        k=1;
                        a=1;
                    } else {
                        System.out.println("Przechodzisz dalej");
                        question = true;
                        k=1;
                        a=1;
                    }
                }

            }else if (LEVEL == 2) {

                if (mouse.x < Heart.heartX[k] + 20 && mouse.x > Heart.heartX[k] - 20 && mouse.y < Heart.heartY[k] + 20 && mouse.y > Heart.heartY[k] - 20) {

                    Pulse.add("OK");
                    t2++;
                    heart[a] = a;
                    System.out.println(k);
                    System.out.println("W tablicy znajdują się punkt: " + heart[k]);
                    a++;
                    k++;

                } else {
                    boolean oke = false;
                    dragging = true;
                    if (dragging) {
                        for (int l = 0; l < 30; l++) {

                            if (mouse.x < Heart.heartX[l] + 20 && mouse.x > Heart.heartX[l] - 20 && mouse.y < Heart.heartY[l] + 20 && mouse.y > Heart.heartY[l] - 20) {
                                System.out.println("Zla kolejnosc: " + click);
                                Pulse.add("Błąd");
                                oke = true;
                            }
                        }
                        if (oke) {
                            a++;
                            k++;
                        }
                    } else if (dragging == false) {
                        for (int l = 0; l < 30; l++) {
                            if (l == a || l == a - 1 && mouse.x < Heart.heartX[l] + 20 && mouse.x > Heart.heartX[l] - 20 && mouse.y < Heart.heartY[l] + 20 && mouse.y > Heart.heartY[l] - 20) {
                                System.out.println("Ten punkt już jest: ");
                            }
                            if (mouse.x < Heart.heartX[l] + 20 && mouse.x > Heart.heartX[l] - 20 && mouse.y < Heart.heartY[l] + 20 && mouse.y > Heart.heartY[l] - 20) {
                                System.out.println("Zla klikniecie: " + click);
                            }

                        }
                    }
                }
                if (k == 30) {
                    if (Pulse.contains("Błąd")) {
                        System.out.println("Sproboj jeszcze raz");
                        k=1;
                        a=1;
                    } else {
                        System.out.println("Przechodzisz dalej");
                        question = true;
                        k=1;
                        a=1;
                    }
                }


            }else if (LEVEL == 3) {

                if (mouse.x < Cpr.cprX[k] + 20 && mouse.x > Cpr.cprX[k] - 20 && mouse.y < Cpr.cprY[k] + 20 && mouse.y > Cpr.cprY[k] - 20) {

                    CPR.add("OK");
                    t3++;
                    Cpri[a] = a;
                    System.out.println(k);
                    System.out.println("W tablicy znajdują się punkt: " + Cpri[k]);
                    a++;
                    k++;


                } else {
                    boolean oke = false;
                    dragging = true;
                    if (dragging) {
                        for (int l = 0; l < 49; l++) {

                            if (mouse.x < Cpr.cprX[l] + 20 && mouse.x > Cpr.cprX[l] - 20 && mouse.y < Cpr.cprY[l] + 20 && mouse.y > Cpr.cprY[l] - 20) {
                                System.out.println("Zla kolejnosc: " + click);
                                CPR.add("Błąd");
                                oke = true;
                            }
                        }
                        if (oke) {
                            a++;
                            k++;
                        }
                    } else if (dragging == false) {
                        for (int l = 0; l < 49; l++) {
                            if (l == a || l == a - 1 && mouse.x < Cpr.cprX[l] + 20 && mouse.x > Cpr.cprX[l] - 20 && mouse.y < Cpr.cprY[l] + 20 && mouse.y > Cpr.cprY[l] - 20) {
                                System.out.println("Ten punkt już jest: ");
                            }
                            if (mouse.x < Cpr.cprX[l] + 20 && mouse.x > Cpr.cprX[l] - 20 && mouse.y < Cpr.cprY[l] + 20 && mouse.y > Cpr.cprY[l] - 20) {
                                System.out.println("Zla klikniecie: " + click);
                            }

                        }
                    }
                }

                if (k == 49) {
                    if (CPR.contains("Błąd")) {
                        System.out.println("Sproboj jeszcze raz");
                        k=1;
                        a=1;
                    } else {
                        System.out.println("Przechodzisz dalej");
                        question = true;
                        k=1;
                        a=1;
                    }
                }

            }else if (LEVEL == 4) {

                if (mouse.x < phone.phoneX[k] + 20 && mouse.x > phone.phoneX[k] - 20 && mouse.y < phone.phoneY[k] + 20 && mouse.y > phone.phoneY[k] - 20) {

                    Phone.add("OK");
                    t4++;
                    Phon[a] = a;
                    System.out.println(k);
                    System.out.println("W tablicy znajdują się punkt: " + Phon[k]);
                    a++;
                    k++;

                } else {
                    boolean oke = false;
                    dragging = true;
                    if (dragging) {
                        for (int l = 0; l < 49; l++) {

                            if (mouse.x < phone.phoneX[l] + 20 && mouse.x > phone.phoneX[l] - 20 && mouse.y < phone.phoneY[l] + 20 && mouse.y > phone.phoneY[l] - 20) {
                                System.out.println("Zla kolejnosc: " + click);
                                Phone.add("Błąd");
                                oke = true;
                            }
                        }
                        if (oke) {
                            a++;
                            k++;
                        }
                    } else if (dragging == false) {
                        for (int l = 0; l < 49; l++) {
                            if (l == a || l == a - 1 && mouse.x < phone.phoneX[l] + 20 && mouse.x > phone.phoneX[l] - 20 && mouse.y < phone.phoneY[l] + 20 && mouse.y > phone.phoneY[l] - 20) {
                                System.out.println("Ten punkt już jest: ");
                            }
                            if (mouse.x < phone.phoneX[l] + 20 && mouse.x > phone.phoneX[l] - 20 && mouse.y < phone.phoneY[l] + 20 && mouse.y > phone.phoneY[l] - 20) {
                                System.out.println("Zla klikniecie: " + click);
                            }
                        }
                    }
                }

                if (k == 49) {
                    if (Phone.contains("Błąd")) {
                        System.out.println("Sproboj jeszcze raz");
                        k=1;
                        a=1;
                    } else {
                        System.out.println("Przechodzisz dalej");
                        question = true;
                        k=1;
                        a=1;
                    }
                }
            }
            startPoint = null;
            dragging = false;

            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouse = new Point2D.Double(e.getX(), e.getY());
            dragging = true;
            for (int i = 0; i < points.size(); i++) {
                Point2D.Double p = points.get(i);

                if (p.distance(mouse) < pointSize / 2 && startPoint == null) {
                    startPoint = p;
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
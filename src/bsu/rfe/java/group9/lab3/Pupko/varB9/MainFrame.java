package bsu.rfe.java.group9.lab3.Pupko.varB9;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private Double[] coefficients;
    private JFileChooser fileChooser = null;
    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;

    public MainFrame(Double[] coefficients) {
        super("tab mnogochl po sheme gornera");
        this.coefficients = coefficients;
        this.setSize(700, 500);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 700) / 2, (kit.getScreenSize().height - 500) / 2);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Table");
        menuBar.add(tableMenu);
        JMenu aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu);
        Action saveToTextAction = new AbstractAction("Save to File") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                if (MainFrame.this.fileChooser.showSaveDialog(MainFrame.this) == 0) {
                    MainFrame.this.saveToTextFile(MainFrame.this.fileChooser.getSelectedFile());
                }

            }
        };
        this.saveToTextMenuItem = fileMenu.add(saveToTextAction);
        this.saveToTextMenuItem.setEnabled(false);
        Action saveToGraphicsAction = new AbstractAction("Save for graphics drawing") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                if (MainFrame.this.fileChooser.showSaveDialog(MainFrame.this) == 0) {
                    MainFrame.this.saveToGraphicsFile(MainFrame.this.fileChooser.getSelectedFile());
                }

            }
        };
        this.saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        this.saveToGraphicsMenuItem.setEnabled(false);
        Action searchValueAction = new AbstractAction("Find the mnogochlen value") {
            public void actionPerformed(ActionEvent event) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "enter value for search", "Searching for value", 3);
                MainFrame.this.renderer.setNeedle(value);
                MainFrame.this.getContentPane().repaint();
            }
        };
        this.searchValueMenuItem = tableMenu.add(searchValueAction);
        this.searchValueMenuItem.setEnabled(false);
        Action aboutAction = new AbstractAction("authors") {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(MainFrame.this, "Author:\nSamosyuk Ilya\n9-th group\nv 1.0", "authors", 3);
            }
        };
        aboutMenu.add(aboutAction);
        aboutMenu.setEnabled(true);
        JLabel labelForFrom = new JLabel("X changing from:");
        this.textFieldFrom = new JTextField("0.0", 10);
        this.textFieldFrom.setMaximumSize(this.textFieldFrom.getPreferredSize());
        JLabel labelForTO = new JLabel("to:");
        this.textFieldTo = new JTextField("1.0", 10);
        this.textFieldTo.setMaximumSize(this.textFieldTo.getPreferredSize());
        JLabel labelForStep = new JLabel("with step:");
        this.textFieldStep = new JTextField("0.1", 10);
        this.textFieldStep.setMaximumSize(this.textFieldStep.getPreferredSize());
        Box hboxRange = Box.createHorizontalBox();
        hboxRange.setBorder(BorderFactory.createBevelBorder(1));
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.add(labelForFrom);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(this.textFieldFrom);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForTO);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(this.textFieldTo);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForStep);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(this.textFieldStep);
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.setPreferredSize(new Dimension(Double.valueOf(hboxRange.getMaximumSize().getWidth()).intValue(), Double.valueOf(hboxRange.getMinimumSize().getHeight()).intValue() * 2));
        this.getContentPane().add(hboxRange, "North");
        JButton buttonCalc = new JButton("Calculate");
        buttonCalc.addActionListener((e) -> {
            try {
                Double from = Double.parseDouble(this.textFieldFrom.getText());
                Double to = Double.parseDouble(this.textFieldTo.getText());
                Double step = Double.parseDouble(this.textFieldStep.getText());
                this.data = new GornerTableModel(from, to, step, this.coefficients);
                JTable table = new JTable(this.data);
                table.setDefaultRenderer(Double.class, this.renderer);
                table.setRowHeight(30);
                this.hBoxResult.removeAll();
                this.hBoxResult.add(new JScrollPane(table));
                this.getContentPane().validate();
                this.saveToTextMenuItem.setEnabled(true);
                this.saveToGraphicsMenuItem.setEnabled(true);
                this.searchValueMenuItem.setEnabled(true);
            } catch (NumberFormatException var6) {
                JOptionPane.showMessageDialog(this, "mistake in float writing", "wrong number format", 2);
            }

        });
        JButton buttonReset = new JButton("Reset");
        buttonReset.addActionListener((e) -> {
            this.textFieldFrom.setText("0.0");
            this.textFieldTo.setText("1.0");
            this.textFieldStep.setText("0.1");
            this.hBoxResult.removeAll();
            this.hBoxResult.add(new JPanel());
            this.saveToTextMenuItem.setEnabled(false);
            this.saveToGraphicsMenuItem.setEnabled(false);
            this.searchValueMenuItem.setEnabled(false);
            this.getContentPane().validate();
        });
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setPreferredSize(new Dimension(Double.valueOf(hboxButtons.getMaximumSize().getWidth()).intValue(), Double.valueOf(hboxButtons.getMinimumSize().getHeight()).intValue() * 2));
        this.getContentPane().add(hboxButtons, "South");
        this.hBoxResult = Box.createHorizontalBox();
        this.hBoxResult.add(new JPanel());
        this.getContentPane().add(this.hBoxResult, "Center");
    }

    protected void saveToGraphicsFile(File selectedFile) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));

            for(int i = 0; i < this.data.getRowCount(); ++i) {
                out.writeDouble((Double)this.data.getValueAt(i, 0));
                out.writeDouble((Double)this.data.getValueAt(i, 1));
            }

            out.close();
        } catch (Exception var4) {
        }

    }

    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);
            out.println("results of tab gorners scheme: ");
            out.print("mnogochlen: ");

            Double var10001;
            int i;
            for(i = 0; i < this.coefficients.length; ++i) {
                var10001 = this.coefficients[i];
                out.print(var10001 + "*X^" + (this.coefficients.length - i - 1));
                if (i != this.coefficients.length - 1) {
                    out.println(" + ");
                }
            }

            out.println("");
            var10001 = this.data.getFrom();
            out.println("interval from " + var10001 + " to " + this.data.getTo() + " with step " + this.data.getStep());
            out.println("=============================================");

            for(i = 0; i < this.data.getRowCount(); ++i) {
                Object var5 = this.data.getValueAt(i, 0);
                out.println("Value at point " + var5 + " equals " + this.data.getValueAt(i, 1));
            }

            out.close();
        } catch (FileNotFoundException var4) {
        }

    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Impossible to tab th mnogochlen for whom you haven't entered coefficients");
            System.exit(-1);
        }

        Double[] coefficients = new Double[args.length];
        int i = 0;

        try {
            String[] var3 = args;
            int var4 = args.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String arg = var3[var5];
                coefficients[i++] = Double.parseDouble(arg);
            }
        } catch (NumberFormatException var7) {
            System.out.println("String changing mistake '" + args[i] + "' in double");
            System.exit(-2);
        }

        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(3);

    }
}

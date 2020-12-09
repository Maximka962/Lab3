package bsu.rfe.java.group9.lab3.Pupko.varB9;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        this.formatter.setMaximumFractionDigits(5);
        this.formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = this.formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        this.formatter.setDecimalFormatSymbols(dottedDouble);
        this.panel.add(this.label);
        this.panel.setLayout(new FlowLayout(0));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        String formattedDouble = this.formatter.format(value);
        this.label.setText(formattedDouble);
        if (col == 1 && this.needle != null && this.needle.equals(formattedDouble)) {
            this.panel.setBackground(Color.PINK);
        } else {
            this.panel.setBackground(Color.WHITE);
        }

        if (col <= 1) {
            String razdel = "\\.";
            String copy = formattedDouble + ".0";
            String[] subStr = copy.split(razdel);
            int drobn = Integer.parseInt(subStr[1]);

            int sum;
            for(sum = 0; drobn != 0; drobn /= 10) {
                sum += drobn % 10;
            }

            if (sum % 10 == 0 && sum != 0) {
                this.panel.setBackground(Color.ORANGE);
            }
        }

        return this.panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }
}

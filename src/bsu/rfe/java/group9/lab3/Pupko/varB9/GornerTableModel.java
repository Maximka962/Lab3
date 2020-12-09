package bsu.rfe.java.group9.lab3.Pupko.varB9;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return this.from;
    }

    public Double getTo() {
        return this.to;
    }

    public Double getStep() {
        return this.step;
    }

    public int getRowCount() {
        return Double.valueOf(Math.ceil((this.to - this.from) / this.step)).intValue() + 1;
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int row, int col) {
        double result = 0.0D;
        double x = this.from + this.step * (double)row;

        for(int i = 0; i < this.coefficients.length; ++i) {
            result += Math.pow(x, (double)(this.coefficients.length - 1 - i)) * this.coefficients[i];
        }

        if (col == 1) {
            return result;
        } else if (col == 2) {
            String s2 = Double.toString(result);
            int j = s2.indexOf(46);
            return s2.charAt(j - 1) == s2.charAt(j + 1);
        } else {
            return x;
        }
    }

    public String getColumnName(int col) {
        if (col == 0) {
            return "X value";
        } else {
            return col == 1 ? "Mnogochlen value" : "Restricted symmetry";
        }
    }

    public Class<?> getColumnClass(int col) {
        return col == 2 ? Boolean.class : Double.class;
    }
}
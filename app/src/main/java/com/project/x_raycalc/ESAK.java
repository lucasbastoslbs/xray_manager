package com.project.x_raycalc;

public class ESAK {
    private double rendimento, espessura, ma, s, bsf;
    private boolean isTorax;

    private double esak;

    public ESAK(){
        this.esak = 0;
    }

    public ESAK(double rendimento, double espessura, double ma, double s, double bsf, boolean isTorax){
        this.rendimento = rendimento;
        this.espessura = espessura;
        this.ma = ma;
        this.s = s;
        this.bsf = bsf;
        this.isTorax = isTorax;
        this.esak = calculaESAK();
    }

    public double calculaESAK(){
        double dfs;
        if (isTorax){
            dfs = 180 - espessura;
        }
        else {
            dfs = 100 - espessura;
        }
        return rendimento * (Math.pow((80/dfs),2)) * this.ma*this.s * this.bsf;
    }

    public String getData(){
        return String.format("%.3f;%.3f;%.3f;%.3f;%.3f;%.3f",this.rendimento,this.espessura,this.ma,this.s,this.bsf,this.esak);
    }

    public double getRendimento() {
        return rendimento;
    }

    public void setRendimento(double rendimento) {
        this.rendimento = rendimento;
    }

    public double getEspessura() {
        return espessura;
    }

    public void setEspessura(double espessura) {
        this.espessura = espessura;
    }

    public double getMa() {
        return ma;
    }

    public void setMa(double ma) {
        this.ma = ma;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getBsf() {
        return bsf;
    }

    public void setBsf(double bsf) {
        this.bsf = bsf;
    }

    public boolean isTorax() {
        return isTorax;
    }

    public void setTorax(boolean torax) {
        isTorax = torax;
    }

    public void setEsak(double esak){ this.esak = esak; }

    public double getEsak(){ return this.esak; }
}

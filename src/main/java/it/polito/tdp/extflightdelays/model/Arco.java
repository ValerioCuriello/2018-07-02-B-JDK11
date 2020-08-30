package it.polito.tdp.extflightdelays.model;

public class Arco {
	
	private int ap;
	private int aa;
	private double peso;
	
	
	
	public Arco(int ap, int aa, double peso) {
		super();
		this.ap = ap;
		this.aa = aa;
		this.peso = peso;
	}



	public int getAp() {
		return ap;
	}



	public void setAp(int ap) {
		this.ap = ap;
	}



	public int getAa() {
		return aa;
	}



	public void setAa(int aa) {
		this.aa = aa;
	}



	public double getPeso() {
		return peso;
	}



	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	

}

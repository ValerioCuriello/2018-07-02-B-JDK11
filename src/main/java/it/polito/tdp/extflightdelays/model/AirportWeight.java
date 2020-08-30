package it.polito.tdp.extflightdelays.model;

public class AirportWeight implements Comparable<AirportWeight> {

	private int a;
	private double peso;
	
	
	public AirportWeight(int a, double peso) {
		super();
		this.a = a;
		this.peso = peso;
	}


	public int getA() {
		return a;
	}


	public void setA(int a) {
		this.a = a;
	}


	public double getPeso() {
		return peso;
	}


	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	
	public String toString() {
		return a+ "peso: " + peso;
	}


	@Override
	public int compareTo(AirportWeight o) {
		if(this.getPeso()<o.getPeso()) {
			 return -1;
		 }
		 else if(this.getPeso()>o.getPeso()) {
			 return 1;
		 }
			
			return 0;
		
	}
	
}

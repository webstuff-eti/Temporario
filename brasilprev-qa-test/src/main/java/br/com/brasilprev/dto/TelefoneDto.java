package br.com.brasilprev.dto;

public class TelefoneDto {

	public Long codigo;

	public String ddd;

	public String numero;

	public TelefoneDto(String ddd, String numero) {
		this.ddd = ddd;
		this.numero = numero;
	}


}
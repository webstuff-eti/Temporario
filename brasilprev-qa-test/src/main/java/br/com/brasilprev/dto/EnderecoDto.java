package br.com.brasilprev.dto;

public class EnderecoDto {

  public Long codigo;

  public String logradouro;

  public Integer numero;

  public String complemento;

  public String bairro;

  public String cidade;

  public String estado;


  public EnderecoDto(String logradouro, Integer numero, String complemento, String bairro, String cidade, String estado) {
    this.logradouro = logradouro;
    this.numero = numero;
    this.complemento = complemento;
    this.bairro = bairro;
    this.cidade = cidade;
    this.estado = estado;
  }
}
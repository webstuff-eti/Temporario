package br.com.brasilprev.dto;

import java.util.List;

public class PessoaDto {

  public Long codigo;

  public String nome;

  public String cpf;

  public List<EnderecoDto> enderecos;

  public List<TelefoneDto> telefones;


  public PessoaDto(String nome, String cpf, List<EnderecoDto> enderecos, List<TelefoneDto> telefones) {
    this.nome = nome;
    this.cpf = cpf;
    this.enderecos = enderecos;
    this.telefones = telefones;
  }



  public PessoaDto() {

  }
}
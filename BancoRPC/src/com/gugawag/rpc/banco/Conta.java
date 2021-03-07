package com.gugawag.rpc.banco;

public class Conta {

  private String numero;
  private Double saldo;

  public Conta(String numero, Double saldo) {
    this.numero = numero;
    this.saldo = saldo;
  }

  public String getNumero() {
    return this.numero;
  }

  public Double getSaldo() {
    return this.saldo;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }
  
}

package io.vertx.blog.first.model;

import io.vertx.core.json.JsonObject;

public class Fatura {

    private int id;
    
    private String clienteNome;

    private String dataFatura;

    private String dataVencimento;

    private double valor;

    private String fornecedor;
    
    private String estado;

//    public Fatura(int id, String clienteNome, String dataFatura, String dataVencimento, double valor, String fornecedor, String estado) {
//        this.id = id;
//        this.clienteNome = clienteNome;
//        this.dataFatura = dataFatura;
//        this.dataVencimento = dataVencimento;
//        this.valor = valor;
//        this.fornecedor = fornecedor;
//        this.estado = estado;
//    }
    
    public Fatura(JsonObject json){
        this.id = json.getInteger("FaturaID");
        this.clienteNome = json.getString("InsertUser");
        this.dataFatura = json.getString("DataFatura");
        this.dataVencimento = json.getString("DataVencimento");
        this.valor = json.getDouble("Valor");
        this.fornecedor = json.getString("FornecedorID");
        this.estado = json.getString("EstadoFaturaID");
    }
    
    public int getId() {
        return id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public String getDataFatura() {
        return dataFatura;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public double getValor() {
        return valor;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public String getEstado() {
        return estado;
    }  
}

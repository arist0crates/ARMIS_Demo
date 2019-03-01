package io.vertx.blog.first.model;

import io.vertx.core.json.JsonObject;

public class FaturaDTO {

    private int id;
    
    private String clienteNome;

    private String dataFatura;

    private String dataVencimento;

    private double valor;

    private int fornecedor;
    
    private int estado;

//    public Fatura(int id, String clienteNome, String dataFatura, String dataVencimento, double valor, String fornecedor, String estado) {
//        this.id = id;
//        this.clienteNome = clienteNome;
//        this.dataFatura = dataFatura;
//        this.dataVencimento = dataVencimento;
//        this.valor = valor;
//        this.fornecedor = fornecedor;
//        this.estado = estado;
//    }
    
    public FaturaDTO(JsonObject json){
        this.id = json.getInteger("FaturaID");
        this.clienteNome = json.getString("InsertUser");
        this.dataFatura = json.getString("DataFatura");
        this.dataVencimento = json.getString("DataVencimento");
        this.valor = json.getDouble("Valor");
        this.fornecedor = json.getInteger("FornecedorID");
        this.estado = json.getInteger("EstadoFaturaID");
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

    public int getFornecedor() {
        return fornecedor;
    }

    public int getEstado() {
        return estado;
    }  
}

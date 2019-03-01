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
    
    public Fatura(JsonObject json){
        this.id = json.getInteger("FaturaID");
        this.clienteNome = json.getString("InsertUser");
        this.dataFatura = json.getString("DataFatura");
        this.dataVencimento = json.getString("DataVencimento");
        this.valor = json.getDouble("Valor");
        this.fornecedor = json.getString("DescritivoFornecedor");
        this.estado = json.getString("DescritivoEstadoFatura");
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the clienteNome
     */
    public String getClienteNome() {
        return clienteNome;
    }

    /**
     * @return the dataFatura
     */
    public String getDataFatura() {
        return dataFatura;
    }

    /**
     * @return the dataVencimento
     */
    public String getDataVencimento() {
        return dataVencimento;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @return the fornecedor
     */
    public String getFornecedor() {
        return fornecedor;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }
}

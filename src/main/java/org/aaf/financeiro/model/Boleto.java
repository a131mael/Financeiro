/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aaf.financeiro.model;

import java.util.Date;

public class Boleto  {

    private Long id;
    
    private Date vencimento;
    
    private Date emissao;
    
    private double valorNominal;
    
    private String nossoNumero;
    
    private Double valorPago;
    
    private String dataPagamento;
    
    private Boolean decurso;
    
    private String movimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public double getValorNominal() {
		return valorNominal;
	}

	public void setValorNominal(double valorNominal) {
		this.valorNominal = valorNominal;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getMovimento() {
		return movimento;
	}

	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}

	public Boolean isDecurso() {
		return decurso;
	}

	public void setDecurso(Boolean decurso) {
		this.decurso = decurso;
	}
    
    
    
    
}

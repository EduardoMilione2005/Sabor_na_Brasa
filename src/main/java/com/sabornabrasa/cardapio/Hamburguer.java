package com.sabornabrasa.cardapio;

import com.sabornabrasa.relatorio.VisitorRelatorio;
import java.util.ArrayList;
import java.util.List;
public class Hamburguer implements ItemCardapio, Cloneable {
    private String nome;
    private String pao;
    private String carne;
    private String queijo;
    private boolean bacon;
    private boolean salada;
    private String molho;
    private double preco;
    private final List<Ingrediente> extras = new ArrayList<>();
    public Hamburguer() {}
    public void setNome(String nome)      { this.nome = nome; }
    public void setPao(String pao)        { this.pao = pao; }
    public void setCarne(String carne)    { this.carne = carne; }
    public void setQueijo(String queijo)  { this.queijo = queijo; }
    public void setBacon(boolean bacon)   { this.bacon = bacon; }
    public void setSalada(boolean salada) { this.salada = salada; }
    public void setMolho(String molho)    { this.molho = molho; }
    public void setPreco(double preco)    { this.preco = preco; }
    public void adicionarExtra(Ingrediente i) { extras.add(i); }
    @Override public String getNome()  { return nome; }
    @Override public double getPreco() { return preco; }
    public String getPao()    { return pao; }
    public String getCarne()  { return carne; }
    public String getQueijo() { return queijo; }
    public boolean isBacon()  { return bacon; }
    public boolean isSalada() { return salada; }
    public String getMolho()  { return molho; }
    public List<Ingrediente> getExtras() { return List.copyOf(extras); }
    @Override public void exibir(String prefixo) {}
    @Override
    public void aceitar(VisitorRelatorio visitor) { visitor.visitarHamburguer(this); }
    @Override
    public Hamburguer clone() {
        try { return (Hamburguer) super.clone(); }
        catch (CloneNotSupportedException e) { throw new RuntimeException(e); }
    }
    @Override
    public String toString() {
        return "Hamburguer{nome='" + nome + "', preco=" + preco + '}';
    }
}

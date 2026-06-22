package com.sabornabrasa.sistema;

public class ConfiguracaoSistema {
    private static ConfiguracaoSistema instancia;
    private final String nomeHamburgueria;
    private final String versao;
    private boolean modoDebug;
    private ConfiguracaoSistema() {
        this.nomeHamburgueria = "Sabor na Brasa";
        this.versao = "2.0";
        this.modoDebug = false;
    }
    public static synchronized ConfiguracaoSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }
        return instancia;
    }
    public String getNomeHamburgueria() { return nomeHamburgueria; }
    public String getVersao()           { return versao; }
    public boolean isModoDebug()        { return modoDebug; }
    public void setModoDebug(boolean v) { this.modoDebug = v; }
    @Override
    public String toString() {
        return nomeHamburgueria + " v" + versao;
    }
}

package com.sabornabrasa.pedido;

import java.util.HashMap;
import java.util.Map;
public class InterpretadorPedido {
    private static final Map<String, String> DICIONARIO = new HashMap<>();
    static {
        DICIONARIO.put("TRADICIONAL", "Hamburguer Tradicional (R$ 32,00)");
        DICIONARIO.put("VEGANO",      "Hamburguer Vegano (R$ 35,00)");
        DICIONARIO.put("PREMIUM",     "Hamburguer Premium (R$ 58,00)");
        DICIONARIO.put("BACON",       "+ Bacon (R$ 5,00)");
        DICIONARIO.put("QUEIJO",      "+ Queijo Extra (R$ 3,00)");
        DICIONARIO.put("BATATA",      "+ Batata Frita (R$ 12,00)");
        DICIONARIO.put("REFRI",       "+ Refrigerante (R$ 8,00)");
    }
    public String interpretar(String expressao) {
        String[] tokens = expressao.toUpperCase().split("\\+");
        StringBuilder resultado = new StringBuilder("Pedido interpretado: ");
        for (String token : tokens) {
            String t = token.trim();
            resultado.append(DICIONARIO.getOrDefault(t, "??" + t + "??")).append(" | ");
        }
        return resultado.toString();
    }
}

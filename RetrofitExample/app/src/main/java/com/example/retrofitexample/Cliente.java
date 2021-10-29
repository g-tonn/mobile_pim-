package com.example.retrofitexample;

public class Cliente {

    private int cod_clnt;
    private String usuario;
    private String senha;
    private String status_del_lgo;
    private int fk_pessoa_cod_psoa;

    public int getCod_clnt() {
        return cod_clnt;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
}

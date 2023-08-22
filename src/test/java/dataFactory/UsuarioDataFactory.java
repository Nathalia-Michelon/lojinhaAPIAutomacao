package dataFactory;

import pojo.UsuarioPojo;

public class UsuarioDataFactory {

    public static UsuarioPojo criarUsuarioAdministrador(){

        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("nathaliamichelon");
        usuario.setUsuarioSenha("123456");

        return usuario;
    }
}

package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do Módulo de Produto")
public class ProdutoTest {

    // criando atributo privado da classe, pq vai usar em todos os testes
    private String token;

    @BeforeEach//antes de cada teste, faça algo...obter o token
    public void beforeEach(){

        //Configurando os dados da API Rest da Lojinha
        baseURI = "http://165.227.93.41"; //URI do servidor
        basePath = "/lojinha"; //caminho inicial da aplicação

        //Obter o token do usuario admin
        this.token = given() //Given = Dado que
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
            .when()//qual metodo vai usar
                .post("/v2/login")
            .then()//então = mostra o que acondece depois da requisição
                .extract() // extrair o que esta dentro do corpo da resposta
                    .path("data.token");//dentro do data pega o token

    }

    @Test
    @DisplayName("Validar que o valor do produto igual 0.00 não é permitido")
    public void testValidarLimitesZeradoProibidosValorProduto(){

        //Tentar inserir um produto com valor 0.00 e validar que a mensagem de erro foi apresentada e o
        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()//entao valide que o atributo body
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
        //status code retornado foi 422
    }


    @Test
    @DisplayName("Validar que o valor do produto igual 7000.01 não é permitido")
    public void testValidarLimitesMaiorSeteMilProibidosValorProduto(){

        //Tentar inserir um produto com valor 7000.01 e validar que a mensagem de erro foi apresentada e o
        //status code retornado foi 422

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()//entao valide que o atributo body
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
}




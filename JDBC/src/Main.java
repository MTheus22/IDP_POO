import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException {
        // 1. Habilitar o driver JDBC a partir da aplicação cliente;
        // ok


        // 2. Estabelecer uma conexão entre a aplicação cliente e servidor do banco de dados;
        String paramsConexao = "jdbc:h2:mem:testdb";
        String usuario = "";
        String senha = "";
        Connection conexao = DriverManager.getConnection(paramsConexao, usuario, senha);

        // 3. Montar e executar a consulta SQL desejada;
        String criacaoSql = "create table pessoa (id int auto_increment primary key, nome varchar(150), qtdAcesso int, naturalidade varchar(75))";
        Statement stmtC = conexao.createStatement();
        stmtC.executeUpdate(criacaoSql);
        stmtC.close();

        // 3. Inserir múltiplos registros na tabela Pessoa usando List.
        List<Pessoa> pessoas = obterListaDePessoas(); // Suponhamos que você tenha uma lista de objetos Pessoa

        for (Pessoa pessoa : pessoas) {
            String insercaoSql = "INSERT INTO pessoa (nome, qtdAcesso, naturalidade) VALUES ('" + pessoa.getNome() + "', " + pessoa.getQuantidadeAcesso() + ", '" + pessoa.getNaturalidade() + "')";
            Statement stmtInsercao = conexao.createStatement();
            stmtInsercao.executeUpdate(insercaoSql);
            stmtInsercao.close();
        }

        // Consulta para verificar os dados inseridos.
        String consulta = "select * from pessoa";
        Statement stmt = conexao.createStatement();
        ResultSet resultado = stmt.executeQuery(consulta);

        // 4. Processar no cliente o resultado da consulta.
        System.out.println("\n");
        while (resultado.next()) {
            String nome = resultado.getString("nome");
            Integer quantidade = resultado.getInt("qtdAcesso");
            String naturalidade = resultado.getString("naturalidade");

            Pessoa pessoa = new Pessoa();
            pessoa.setNome(nome);
            pessoa.setQuantidadeAcesso(quantidade);
            pessoa.setNaturalidade(naturalidade);

            //print
            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("Quantidade de Acessos: " + pessoa.getQuantidadeAcesso());
            System.out.println("Naturalidade: " + pessoa.getNaturalidade() + "\n");
        }

        System.out.println("\nTchau, até a próxima\n\n\t\t:-)");
    }

    // Método para adicionar pessoas usando o List
    private static List<Pessoa> obterListaDePessoas() {
        List<Pessoa> pessoas = new ArrayList<>();

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Matheus");
        pessoa1.setQuantidadeAcesso(22);
        pessoa1.setNaturalidade("Brasília");
        pessoas.add(pessoa1);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Luís Eduardo");
        pessoa2.setQuantidadeAcesso(13);
        pessoa2.setNaturalidade("Bahia");
        pessoas.add(pessoa2);

        Pessoa pessoa3 = new Pessoa();
        pessoa3.setNome("Vítor");
        pessoa3.setQuantidadeAcesso(5);
        pessoa3.setNaturalidade("São Paulo");
        pessoas.add(pessoa3);

        return pessoas;
    }
}

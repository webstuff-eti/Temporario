package br.com.brasilprev.repository;

import br.com.brasilprev.modelo.Pessoa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	PessoaRepository repository;


	private static final String PARTE_CPF_EXISTENTE = "86730543";
	private static final String CPF_EXISTENTE =       "86730543540";
	private static final String CPF_INEXISTENTE = "86730541111";

	private static final String DDD_EXISTENTE = "11";
	private static final String DDD_INEXISTENTE = "";
	private static final String NUMERO_TEL_EXISTENTE = "";
	private static final String NUMERO_TEL_INEXISTENTE = "";


	private static final String NOME_PESSOA_EXISTENTE = "reno";
	private static final String PARTE_NOME_PESSOA_EXISTENTE = "reno";

	private static final String NUM_TEL__EXISTENTE =  "999570146";



	@Test
	public void deve_procurar_pessoa_pelo_cpf() {
		Optional<Pessoa> pessoa = repository.findByCpf( CPF_EXISTENTE );
		assertTrue(  pessoa.isPresent() );
		assertEquals(CPF_EXISTENTE, pessoa.get().getCpf());
	}

	@Test
	public void deve_encontrar_pessoa_cpf_inexistente() {
		Optional<Pessoa> pessoa = repository.findByCpf( CPF_INEXISTENTE );
		assertTrue(  !pessoa.isPresent() );
	}

	@Test
	public void deve_encontrar_pessoa_pelo_ddd_e_numero_de_telefone() {
		Optional<Pessoa> pessoa = repository.findByTelefoneDddAndTelefoneNumero( DDD_EXISTENTE, NUMERO_TEL_EXISTENTE );
	}

	@Test
	public void nao_deve_encontrar_pessoa_cujo_ddd_e_telefone_nao_estejam_cadastradados() {
	}

	@Test
	public void deve_filtrar_pessoas_por_parte_do_nome() {
		List<Pessoa> pessoas = repository.findAll();
		List<Pessoa> pessoasFiltradas = pessoas.stream().filter( p -> p.getNome().contains( PARTE_NOME_PESSOA_EXISTENTE ) ).collect(Collectors.toList());
		//assertEquals(NOME_PESSOA_EXISTENTE, pessoasFiltradas.get( 0 ).getNome());
	}

	@Test
	public void deve_filtrar_pessoas_por_parte_do_cpf() {
		List<Pessoa> pessoas = repository.findAll();
		List<Pessoa> pessoasFiltradas = pessoas.stream().filter( p -> p.getCpf().contains( PARTE_CPF_EXISTENTE ) ).collect(Collectors.toList());
		assertEquals(CPF_EXISTENTE, pessoasFiltradas.get( 0 ).getCpf());
	}

	@Test
	public void deve_filtrar_pessoas_por_filtro_composto() {
		List<Pessoa> pessoas = repository.findAll();

	}

	@Test
	public void deve_filtrar_pessoas_pelo_ddd_do_telefone() {
		List<Pessoa> pessoas = repository.findAll();
		pessoas.stream().filter( p -> p.getCodigo().equals( DDD_EXISTENTE ) );	}

	@Test
	public void deve_filtrar_pessoas_pelo_ddd_do_telefonev2() {
		List<Pessoa> pessoas = repository.findAll();


    }

	@Test
	public void deve_filtrar_pessoas_pelo_numero_do_telefone() {
		List<Pessoa> pessoas = repository.findAll();
				pessoas.forEach(
						         pessoa -> pessoa.getTelefones().stream().
										                         filter( telefone -> telefone.getDdd().equals( NUM_TEL__EXISTENTE) ).
										 						collect(    Collectors.toList() )   );
	}

}

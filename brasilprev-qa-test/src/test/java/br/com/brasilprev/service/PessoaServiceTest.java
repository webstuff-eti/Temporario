package br.com.brasilprev.service;

import br.com.brasilprev.dto.EnderecoDto;
import br.com.brasilprev.dto.PessoaDto;
import br.com.brasilprev.dto.TelefoneDto;
import br.com.brasilprev.modelo.Pessoa;
import br.com.brasilprev.repository.PessoaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PessoaServiceTest {

	@Autowired
	private PessoaService service;

	@MockBean
	private PessoaRepository repository;


	private static final String DDD = "41";
	private static final String TELEFONE = "999570146";

	private static final String MESMO_CPF = "86730543540";
	private static final String CPF = "86730549540";

	private static final String CPF_01 = "86711543540";
	private static final String CPF_02 = "86732243540";
	private static final String MESMO_TELEFONE = "987334678";



	@Before
	public void setUp() {
		BDDMockito.given(this.repository.save( Mockito.any( Pessoa.class ))).willReturn(new Pessoa());
		BDDMockito.given(this.repository.findByTelefoneDddAndTelefoneNumero(Mockito.anyString(), Mockito.anyString())).willReturn( java.util.Optional.of( new Pessoa() ) );
	}

	@Test
	public void deve_salvar_pessoa_no_repositorio() throws Exception {

		EnderecoDto endereco = new EnderecoDto( "Alameda Jauaper", 200, "Apto.22", "Moema", "São Paulo", "SP" );
		List<EnderecoDto> enderecos = new ArrayList<>();
		enderecos.add( endereco );

		TelefoneDto telefoneTiago = new TelefoneDto("11", "987355547");
		List<TelefoneDto> telefones = new ArrayList<>();
		telefones.add( telefoneTiago );

		PessoaDto pessoaDto = new PessoaDto( "João",CPF, enderecos, telefones );
		Optional<Pessoa> joaoSalvo = Optional.ofNullable( this.service.salvar( pessoaDto ) );
		Assert.assertTrue(joaoSalvo.isPresent());

	}

	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {

		EnderecoDto endereco_joao = new EnderecoDto( "Alameda Jauaper", 200, "Apto.22", "Moema", "São Paulo", "SP" );
		List<EnderecoDto> enderecos_joao = new ArrayList<>();
		enderecos_joao.add( endereco_joao );

		TelefoneDto telefoneJoao = new TelefoneDto("11", "981114467");
		List<TelefoneDto> telefones_joao = new ArrayList<>();
		telefones_joao.add( telefoneJoao );


		EnderecoDto endereco_maria = new EnderecoDto( "Avenida Lins de Vasconcelos", 123, "Apto.10", "Vila ,Ariana", "São Paulo", "SP" );
		List<EnderecoDto> enderecos_maria = new ArrayList<>();
		enderecos_maria.add( endereco_maria );

		TelefoneDto telefoneMaria = new TelefoneDto("41", "983333469");
		List<TelefoneDto> telefones_maria = new ArrayList<>();
		telefones_maria.add( telefoneMaria );


		PessoaDto joao = new PessoaDto( "João",MESMO_CPF, enderecos_joao, telefones_joao );
		Optional<Pessoa> joaoSalvo = Optional.ofNullable( this.service.salvar( joao ) );
		Assert.assertTrue(joaoSalvo.isPresent());

		PessoaDto maria = new PessoaDto("Maria", MESMO_CPF, enderecos_maria, telefones_maria);
		Optional<Pessoa> mariaSalvo = Optional.ofNullable( this.service.salvar( maria ) );
		Assert.assertNull(mariaSalvo.get().getCodigo());
	}

	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_telefone() throws Exception {
		EnderecoDto endereco_joao = new EnderecoDto( "Alameda Jauaper", 200, "Apto.22", "Moema", "São Paulo", "SP" );
		List<EnderecoDto> enderecos_joao = new ArrayList<>();
		enderecos_joao.add( endereco_joao );

		TelefoneDto telefoneJoao = new TelefoneDto("11", MESMO_TELEFONE);
		List<TelefoneDto> telefones_joao = new ArrayList<>();
		telefones_joao.add( telefoneJoao );


		EnderecoDto endereco_maria = new EnderecoDto( "Avenida Lins de Vasconcelos", 123, "Apto.10", "Vila ,Ariana", "São Paulo", "SP" );
		List<EnderecoDto> enderecos_maria = new ArrayList<>();
		enderecos_maria.add( endereco_maria );

		TelefoneDto telefoneMaria = new TelefoneDto("11", MESMO_TELEFONE);
		List<TelefoneDto> telefones_maria = new ArrayList<>();
		telefones_maria.add( telefoneMaria );


		PessoaDto joao = new PessoaDto( "João",CPF_01, enderecos_joao, telefones_joao );
		Optional<Pessoa> joaoSalvo = Optional.ofNullable( this.service.salvar( joao ) );
		Assert.assertTrue(joaoSalvo.isPresent());

		PessoaDto maria = new PessoaDto("Maria", CPF_02, enderecos_maria, telefones_maria);
		Optional<Pessoa> mariaSalvo = Optional.ofNullable( this.service.salvar( maria ) );
		Assert.assertNull(mariaSalvo.get().getCodigo() );
	}
	
	@Test
	public void deve_retornar_execao_de_nao_encontrado_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() throws Exception {
		String telefone = "967342267";
		String ddd = "11";
		String expectedMessages = "Não existe pessoa com o telefone (" + ddd + ")" + telefone;
	}

	@Test
    public void deve_retornar_dados_do_telefone_dentro_da_excecao_de_telefone_nao_encontrado_exception() throws Exception {
    }
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
		Optional<Pessoa> pessoa = Optional.ofNullable( this.service.buscarPorTelefone( DDD, TELEFONE ) );
		Assert.assertTrue(pessoa.isPresent());
	}
}

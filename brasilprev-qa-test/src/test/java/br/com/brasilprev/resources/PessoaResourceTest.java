package br.com.brasilprev.resources;

import br.com.brasilprev.dto.PessoaDto;
import br.com.brasilprev.modelo.Endereco;
import br.com.brasilprev.modelo.Pessoa;
import br.com.brasilprev.modelo.Telefone;
import br.com.brasilprev.service.PessoaService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PessoaResourceTest { //extends ApplicationTests

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PessoaService pessoaService;


    private static final String URL_PESSOA_RESOURCE = "/pessoas";
    private static final String DDD_URL_PESSOA_RESOURCE = "/41";
    private static final String TEL_PESSOA_RESOURCE = "/999570146";

    private static final Long ID_CODIGO = Long.valueOf(1);
    private static final String NOME_PESSOA_BD = "Iago";
    private static final String CPF_PESSOA_BD =  "86730543540";

    private List<Endereco> enderecosPessoa = new ArrayList<>();
    List<Telefone> telefonesPessoa = new ArrayList<>();


    private Pessoa dadosPessoaBuilder(){

        Endereco endereco = new Endereco( "Rua dos Gerânios", 497, "XXXX", "Pricumã", "Boa Vista", "RR" );
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add( endereco );

        Telefone telefone = new Telefone("41", "999570146");
        List<Telefone> telefones = new ArrayList<>();
        telefones.add( telefone );

        return new Pessoa( NOME_PESSOA_BD, CPF_PESSOA_BD, enderecos, telefones);
    }


    @SneakyThrows
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BDDMockito.given(this.pessoaService.salvar( Mockito.any( PessoaDto.class ))).willReturn(new Pessoa());
        BDDMockito.given(this.pessoaService.buscarPorTelefone(Mockito.anyString(), Mockito.anyString())).willReturn(this.dadosPessoaBuilder());
    }
	
	@SneakyThrows
    @Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {

        Pessoa pessoa = this.dadosPessoaBuilder();

        mvc.perform( MockMvcRequestBuilders.get(URL_PESSOA_RESOURCE + DDD_URL_PESSOA_RESOURCE + TEL_PESSOA_RESOURCE)
                .accept( MediaType.APPLICATION_JSON))
                .andExpect( status().isOk())
                .andExpect(jsonPath("$.nome").value(pessoa.getNome()))
                .andExpect(jsonPath("$.cpf").value(pessoa.getCpf()))
                .andExpect(jsonPath("$.telefones.[0].ddd").value(pessoa.getTelefones().get( 0 ).getDdd()))
                .andExpect(jsonPath("$.telefones.[0].numero").value(pessoa.getTelefones().get( 0 ).getNumero()));
	}

	
    @Test
    public void deve_retornar_erro_nao_encontrado_quando_buscar_pessoa_por_telefone_inexistente() throws Exception {


    }
    
    @Test
    public void deve_salvar_nova_pessoa_no_sistema() {
    }
    
    @Test
    public void deve_salvar_suas_pessoas_com_o_mesmo_cpf() {
    }
    
    @Test
    public void deve_salvar_suas_pessoas_com_o_mesmo_telefone() {
    }
    
    @Test
    public void deve_fitrar_pessoas_pelo_nome() {
    }
}

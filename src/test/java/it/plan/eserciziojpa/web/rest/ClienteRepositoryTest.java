package it.plan.eserciziojpa.web.rest;

import it.plan.eserciziojpa.EsercizioJpa032020App;
import it.plan.eserciziojpa.domain.Cliente;
import it.plan.eserciziojpa.domain.Ordine;
import it.plan.eserciziojpa.domain.Prodotto;
import it.plan.eserciziojpa.domain.RigaOrdine;
import it.plan.eserciziojpa.repository.*;
import it.plan.eserciziojpa.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static it.plan.eserciziojpa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = EsercizioJpa032020App.class)
public class ClienteRepositoryTest {


    private final Logger log = LoggerFactory.getLogger(ClienteRepositoryTest.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ConArticolo myQueryImplementation;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    RigaOrdineRepository rigaOrdineRepository;



    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteRepository, myQueryImplementation);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }







    int clientiCon2OrdiniCount;
    public void utilityAddClienteOrdine(int conNordini){
        //crea nel db un cliente con il nome fornito in forma di numero, crea ed aggiunge conNordini

        clientiCon2OrdiniCount++;
        Cliente c1 = new Cliente ("nome"+clientiCon2OrdiniCount);
        Cliente sc1 = clienteRepository.save(c1);
        for (int o=0; o<conNordini; o++) {
            Ordine o1 = new Ordine (sc1);
            this.ordineRepository.save(o1);
        }
    }

    public Ordine utilityAddClienteArticoSpecifico(int cliente){
        //crea nel db un cliente con il nome fornito in forma di numero, crea un ordine e lo restituisce

        Cliente c1 = new Cliente ("nome"+cliente);
        Cliente sc1 = clienteRepository.save(c1);
        Ordine o1 = new Ordine (sc1);
        return ordineRepository.save(o1);
    }

    public Prodotto utilityProdottoDB (String descrizione){
        Prodotto p = new Prodotto ();
        p.setDescrizione(descrizione);
        return this.prodottoRepository.save(p);
    }

    public void utilityAddProdottoOrdine (Prodotto p, Ordine o, int hm) {
        RigaOrdine r= new RigaOrdine (hm, o, p);
        this.rigaOrdineRepository.save(r);
    }


    void utilityAssertClientiCon2Ordini (int aspettativa) {
        //testa che la query clientiCon2Ordini() restituitiosca il numero aspettativa
        List <Cliente>  clientiCon2Ordini= clienteRepository.clientiCon2Ordini();
        assertThat (clientiCon2Ordini).hasSize(aspettativa);
    }

    void utilityAssertClientiCon0Ordini (int aspettativa) {
        //testa che la query clientiCon0Ordini() restituitiosca il numero aspettativa
        List <Cliente>  clientiCon2Ordini= clienteRepository.clientiCon0Ordini();
        assertThat (clientiCon2Ordini).hasSize(aspettativa);
    }

    @Test
    @Transactional
    public void clientiCon2OrdiniNoClientiTest() throws Exception {
    /*
    Estrai tutti i clienti che hanno almeno due ordini
    test con un dataset di 3 clienti:
    Client 1 0 ordini,
    Cliente 2 0 Ordini
    Cliente 3 1 Ordine
    testa che la query ritorni un set vuoto
    */
        utilityAddClienteOrdine(0);
        utilityAddClienteOrdine(0);
        utilityAddClienteOrdine( 1);
        utilityAssertClientiCon2Ordini (0);

    }

    @Test
    @Transactional
    public void clientiCon2OrdiniConClientiTest() throws Exception {
    /*
    Estrai tutti i clienti che hanno almeno due ordini
    test con un data set di 5 clienti
    Client 1 0 ordini,
    Cliente 2 2 Ordini
    Cliente 3 1 Ordine
    Client 4 10 Ordini
    Cliente 5 3 Ordini
    testa che la query ritorni un un set con i clienti 2, 4 e 5
    */
        utilityAddClienteOrdine(0);
        utilityAssertClientiCon2Ordini (0);
        utilityAddClienteOrdine(2);
        utilityAssertClientiCon2Ordini (1);
        utilityAddClienteOrdine(1);
        utilityAssertClientiCon2Ordini (1);
        utilityAddClienteOrdine(10);
        utilityAssertClientiCon2Ordini (2);
        utilityAddClienteOrdine(3);
        utilityAssertClientiCon2Ordini (3);
    }


    @Test
    @Transactional
    public void clientiCon0OrdiniNoClientiTest() throws Exception {
    /*
    Estrai tutti i clienti che non hanno ordini
    test con un dataset di 3 clienti:
    Client 1 1 ordini,
    Cliente 2 2 Ordini
    Cliente 3 5 Ordine
    testa che la query ritorni un set vuoto
    */
        utilityAddClienteOrdine(1);
        utilityAddClienteOrdine(2);
        utilityAddClienteOrdine( 5);
        utilityAssertClientiCon0Ordini (0);

    }

    @Test
    @Transactional
    public void clientiCon0OrdiniConClientiTest() throws Exception {
    /*
    Estrai tutti i clienti che hanno almeno due ordini
    test con un data set di 5 clienti
    Client 1 0 ordini, testa che ritorni 11
    Cliente 2 2 Ordini, testa sempre che ritorni 1
    Cliente 3 0 Ordine, testa che ritorni 2
    Client 4 10 Ordini, testa che ritorni 2
    Cliente 5 0 Ordini, testa che ritorni 3

    */
        utilityAddClienteOrdine(0);
        utilityAssertClientiCon0Ordini (1);
        utilityAddClienteOrdine(2);
        utilityAssertClientiCon0Ordini (1);
        utilityAddClienteOrdine(0);
        utilityAssertClientiCon0Ordini (2);
        utilityAddClienteOrdine(10);
        utilityAssertClientiCon0Ordini (2);
        utilityAddClienteOrdine(0);
        utilityAssertClientiCon0Ordini (3);
    }



    @Test
    @Transactional
    public void conArticoloTest()throws Exception {

/*
    Estrai  tutti i clienti che hanno ordinato uno specifico artico
    test con un data set di 3 clienti e 5 articoli
    Cliente 1 con articolo 1,2,3
    cliente 2 con articollo 2,3
    cliente 3 con articolo 3,5

    testa  che la query restituisca
    1 per articolo 1
    2 per articolo 2
    3 per articolo 3
    0 per articolo 4
    1 per articolo 5

    */

        Ordine o1= utilityAddClienteArticoSpecifico (1);
        Ordine o2= utilityAddClienteArticoSpecifico (2);
        Ordine o3= utilityAddClienteArticoSpecifico (3);
        Prodotto p1= this.utilityProdottoDB("p1");
        Prodotto p2= this.utilityProdottoDB("p2");
        Prodotto p3= this.utilityProdottoDB("p3");
        Prodotto p4= this.utilityProdottoDB("p3");
        Prodotto p5= this.utilityProdottoDB("p3");

        utilityAddProdottoOrdine (p1, o1,3);
        utilityAddProdottoOrdine (p2, o1,3);
        utilityAddProdottoOrdine (p3, o1,3);

        utilityAddProdottoOrdine (p2, o2,3);
        utilityAddProdottoOrdine (p3, o2,3);


        utilityAddProdottoOrdine (p3, o3,3);
        utilityAddProdottoOrdine (p5, o3,3);

        List <Cliente>  conArticolo= myQueryImplementation.conArticolo (p1.getId());
        assertThat (conArticolo).hasSize(1);

        conArticolo= myQueryImplementation.conArticolo (p2.getId());
        assertThat (conArticolo).hasSize(2);

        conArticolo= myQueryImplementation.conArticolo (p3.getId());
        assertThat (conArticolo).hasSize(3);

        conArticolo= myQueryImplementation.conArticolo (p4.getId());
        assertThat (conArticolo).hasSize(0);

        conArticolo= myQueryImplementation.conArticolo (p5.getId());
        assertThat (conArticolo).hasSize(1);
    }









//utility per clientiOptOrdini_
    //controlla che cliente e ordine siano quelli previsti
        void testClienteOrdine (List <Object[]> source, int order,  Cliente c, Ordine o )  throws Exception{
        Object [] oo= source.get(order);
        Cliente cr= (Cliente) oo[0];
        Ordine or= (Ordine) oo[1];
        assertThat (c).isEqualTo(cr);
        assertThat (o).isEqualTo(or);

    }



    @Test
    @Transactional
    public void clientiOptOrdiniTest() throws Exception {
        /* clientiOptOrdini - Estrai tutti i clienti e, per quelli che hanno ordini, anche l’ordine (ovviamente la query potrà restituire lo stesso cliente più volte per ogni ordine)
 set con 3 clienti al quale vengono aggiunti progressivamente ordini.
 la lista conterrà in sequenza:
 3 elementi (nessun ordine), poi
 3 elementi (cliente 1 ha un ordine), poi
 4 elementi (cliente 2 ha 2 ordini),poi
 5 elementi  (cliente 2 e 4 hanno 2 ordini)


         */

        List <Object[]>  clientiOptOrdini= clienteRepository.clientiOptOrdini();
        assertThat (clientiOptOrdini).hasSize(0);


        Cliente c1 = new Cliente ("c1");
        Cliente c2 = new Cliente ("c2");
        Cliente c3 = new Cliente ("c3");

        Cliente sc1 = clienteRepository.save(c1);
        Cliente sc2= clienteRepository.save(c2);
        Cliente sc3=clienteRepository.save(c3);

        // clientiOptOrdini run 1. 3 clienti tutti senza righe
// 3 elementi (nessun ordine)
        clientiOptOrdini= clienteRepository.clientiOptOrdini();
        assertThat (clientiOptOrdini).hasSize(3);


        Ordine o1_1 = new Ordine (sc1);
        Ordine o1_1r= this.ordineRepository.save(o1_1);
        clientiOptOrdini= clienteRepository.clientiOptOrdini();

        // clientiOptOrdini run 2. 3 clienti restituiti, n9on cambia se un cliente ha una riga come adesso o 0 come prima
        // 3 elementi (cliente 1 ha un ordine)
        assertThat (clientiOptOrdini).hasSize(3);

        //controlla che cliente e ordine siano quelli previsti
         testClienteOrdine (clientiOptOrdini, 0, sc1, o1_1r );


        // clientiOptOrdini run 3. 4 clienti restituiti, il cliente 1 passa da 1 a 2 righe e aumenta di 1 la lista
    // 4 elementi (cliente 2 ha 2 ordini)
        Ordine o1_2 = new Ordine (sc1);
        Ordine o1_2r= this.ordineRepository.save(o1_2);
        clientiOptOrdini= clienteRepository.clientiOptOrdini();
        assertThat (clientiOptOrdini).hasSize(4);

        //controlla che cliente e ordine siano quelli previsti
        testClienteOrdine (clientiOptOrdini, 0, sc1, o1_1r );
        testClienteOrdine (clientiOptOrdini, 1, sc1, o1_2r );

        // clientiOptOrdini run 4. il cliente 3 passa da 0 a 2 righe e aumenta di 1 la lista
    // 5 elementi  (cliente 2 e 4 hanno 2 ordini)
        Ordine o3_1 = new Ordine (sc3);
        Ordine o3_1r= this.ordineRepository.save(o3_1);
        Ordine o3_2 = new Ordine (sc3);
        Ordine o3_2r= this.ordineRepository.save(o3_2);

        clientiOptOrdini= clienteRepository.clientiOptOrdini();
        assertThat (clientiOptOrdini).hasSize(5);

        //controlla che cliente e ordine siano quelli previsti
        testClienteOrdine (clientiOptOrdini, 3, sc3, o3_1r );
        testClienteOrdine (clientiOptOrdini, 4, sc3, o3_2r );
    }



    public void utilityIteraETesta (List <Object[]> toTest, long [] assertions, int arrayIndex) throws Exception {
        /*
       testa che toTest restituisca una lista di array di oggetti, testandola con l'array assertions
       per esempio, preso  l'arrAy assertions tipo {2,1,0}, controlla che nella lista...
       l'oggetto arrayIndex dell'elemento 0 della  lista sia un long e sia uguale a tipo[0]...2
       l'oggetto arrayIndex dell'elemento 1 della  lista sia un long e sia uguale a tipo[1]...1
       l'oggetto arrayIndex dell'elemento 2 della  lista sia un long e sia uguale a tipo[2]...0
       */
        int hmIndex=0;
        for (Object[] to : toTest) {
            long hmo= (long)  to[arrayIndex];
            assertThat (hmo).isEqualTo(assertions[hmIndex]);
            hmIndex++;
        }
    }


    public void UtilityTestProdottiNUmOrdini(long [] assertions) throws Exception {
        this.utilityIteraETesta(clienteRepository.prodottiNUmOrdini() , assertions, 1);
    }

    @Test
    @Transactional
    public void ProdottiNUmOrdiniTest() throws Exception {
        /*
        Estrai tutti i prodotti e, per ogni prodotto, il numero totale di ordini in cui è stato ordinato
        set con 3 prodotti che vengono progressivamente aggiunti ad ordinii
         la lista conterrà in sequenza:
        0 nessun ordine, poi
        1 prodotto 1 in un ordine, poi
        2 prodotto 2 in un ordine, poi
        3 prodotto 1 in altri 2 ordini, poi
        4 prodotto 3 in 2 ordini
         */
        Prodotto p1= utilityProdottoDB ("p1");
        Prodotto p2= utilityProdottoDB ("p2");
        Prodotto p3= utilityProdottoDB ("p3");

        // 0 nessun ordine, poi
        long[] totest0={0,0,0};
        this.UtilityTestProdottiNUmOrdini(totest0);

        //1 prodotto 1 in un ordine, poi
        Ordine o1 = utilityAddClienteArticoSpecifico (1);
        utilityAddProdottoOrdine (p1, o1,3);
        long[] totest1={1,0,0};
        this.UtilityTestProdottiNUmOrdini(totest1);

        //2 prodotto 2 in un ordine, poi
        Ordine o2 = utilityAddClienteArticoSpecifico (2);
        utilityAddProdottoOrdine (p2, o2,3);
        long[] totest2={1,1,0};
        this.UtilityTestProdottiNUmOrdini(totest2);

        //3 prodotto 1 in altri 2 ordini, poi

        Ordine o31 = utilityAddClienteArticoSpecifico (3);
        Ordine o32 = utilityAddClienteArticoSpecifico (5);
        utilityAddProdottoOrdine (p1, o31,3);
        utilityAddProdottoOrdine (p1, o32,3);
        long[] totest3={3,1,0};
        this.UtilityTestProdottiNUmOrdini(totest3);

        //4 prodotto 3 in 2 ordini
        Ordine o41 = utilityAddClienteArticoSpecifico (6);
        Ordine o42 = utilityAddClienteArticoSpecifico (2);
        utilityAddProdottoOrdine (p3, o41,3);
        utilityAddProdottoOrdine (p3, o42,3);
        long[] totest4={3,1,2};
        this.UtilityTestProdottiNUmOrdini(totest4);
    }

    public void utilityTestclienteProdottoNumPezzi(long [] assertions) throws Exception {
        utilityIteraETesta (clienteRepository.clienteProdottoNumPezzi(),assertions,2);
    }

    @Test
    @Transactional
    public void utilityClienteProdottoNumPezziTest() throws Exception {
        /*Estrai, per ogni cliente e prodotto, la quantità totale di pezzi ordinati
         set con 3 prodotti e 3 clienti
          la lista conterrà in sequenza
          0 - 3 prodotti e 3 clienti, nessun ordine
          1 -cliente 1 compra 10 pezzi del prodotto 1 in un ordine
          2 cliente 2 compra 15 pezzi del prodotto 2 in un ordine
          3 - cliente 1 compra 20 pezzi del prodotto 1 e 3 in ordine
*/

        //0 - 3 prodotti e 3 clienti, nessun ordine
        Prodotto p1= utilityProdottoDB ("p1");
        Prodotto p2= utilityProdottoDB ("p2");
        Prodotto p3= utilityProdottoDB ("p3");

        Ordine o1 = utilityAddClienteArticoSpecifico (1);
        Ordine o2 = utilityAddClienteArticoSpecifico (2);
        Ordine o3 = utilityAddClienteArticoSpecifico (3);




        //1 -cliente 1 compra 10 pezzi del prodotto 1 in un ordine
        utilityAddProdottoOrdine (p1, o1,10);
        long[] totest1={10};
        utilityTestclienteProdottoNumPezzi (totest1);

        //2 cliente 2 compra 15 pezzi del prodotto 2 in un ordine
        utilityAddProdottoOrdine (p2, o2,15);
        long[] totest2={10,15};
        utilityTestclienteProdottoNumPezzi (totest2);

        //3 - cliente 1 compra 20 pezzi del prodotto 1 e 3 in un ordine
        utilityAddProdottoOrdine (p1, o1,20);
        utilityAddProdottoOrdine (p3, o1,20);
        long[] totest3={30,20, 15};
        utilityTestclienteProdottoNumPezzi (totest3);
    }


    public void utilityclienteOrdiniMax3Righetest (long [] assertions) throws Exception {
        utilityIteraETesta (clienteRepository.clienteOrdiniMax3Righe(), assertions,1);
    }

    @Test
    @Transactional
    public void clienteOrdiniMax3Righetest() throws Exception {
        /*Estrai, per ogni cliente, il numero di ordini con meno di 3 righe (incluse quindi 0)
        set 3 clienti
        0-nessun ordine
        1- cliente 1 fa ordine con 0 righe
        2- cliente 2 fa ordine con 2 righe
        3- cliente 3 fa ordine con 3 riga
        4- cliente 1 fa un'altro ordine con 1 riga
        5- cliente 1 fa un'altro ordine con 4 righe


        */

        Cliente c1 = new Cliente ("c1");
        Cliente c2 = new Cliente ("c2");
        Cliente c3 = new Cliente ("c3");

        Cliente sc1 = clienteRepository.save(c1);
        Cliente sc2= clienteRepository.save(c2);
        Cliente sc3=clienteRepository.save(c3);

        Prodotto p1= utilityProdottoDB ("p1");
        Prodotto p2= utilityProdottoDB ("p2");
        Prodotto p3= utilityProdottoDB ("p3");

//        1- cliente 1 fa ordine con 0 righe
        Ordine o1_1 = new Ordine (sc1);
        long[] totest1={0};
        utilityclienteOrdiniMax3Righetest (totest1);


        //       2- cliente 2 fa ordine con 2 righe
        Ordine o2_1 = new Ordine (sc2);
        utilityAddProdottoOrdine (p1, o2_1,3);
        utilityAddProdottoOrdine (p2, o2_1,3);
        long[] totest2={0,2};
        utilityclienteOrdiniMax3Righetest (totest2);



        //       3- cliente 3 fa ordine con 3 riga
        //       4- cliente 1 fa un'altro ordine con 1 riga
        //5- cliente 1 fa un'altro ordine con 4 righe

    }
}
